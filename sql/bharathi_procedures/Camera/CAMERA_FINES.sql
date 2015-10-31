create or replace PROCEDURE CAMERA_FINES 
AS 
  c_result SYS_REFCURSOR;
  my_record BOOKED_CAMS%ROWTYPE;
  fine_id NUMBER;
  hour_difference NUMBER;
BEGIN
  -- This will take care of fines for cameras.
  OPEN c_result FOR
    SELECT * FROM BOOKED_CAMS WHERE RETURNED_TIME IS NULL AND END_TIME<SYSDATE;
  
  LOOP
    FETCH c_result INTO my_record;
    EXIT WHEN c_result%NOTFOUND;
    fine_id := 0;
    hour_difference := 0;
    SELECT ROUND(((sysdate) - my_record.end_time)*24) INTO hour_difference FROM DUAL;
    BEGIN
      SELECT ID INTO fine_id FROM CAM_FINES WHERE BOOKED_CAM_ID = my_record.id;
      UPDATE CAM_FINES SET AMOUNT = AMOUNT+1, STATUS='UNPAID' WHERE ID = fine_id; -- If the user has partially paid the money. We cannot set the hour difference as the fine amount.
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
      SELECT CAM_FINES_SEQ.NEXTVAL INTO fine_id FROM DUAL;
      INSERT INTO CAM_FINES(ID, BOOKED_CAM_ID, AMOUNT, STATUS) VALUES (fine_id, my_record.id, hour_difference, 'UNPAID');
    END;
  END LOOP;
  CLOSE c_result;
END CAMERA_FINES;