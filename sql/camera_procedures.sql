create or replace Procedure CAMERA_BOOK
   (
  patron_id IN NUMBER, 
  camera_id IN NUMBER,
  start_time IN DATE,
  return_id OUT NUMBER
)
   
IS
   cam_id number;
   booking_id number;
   end_time date;

BEGIN

   SELECT status INTO cam_id FROM cameras WHERE id = camera_id;
   SELECT booked_cams_seq.nextval INTO booking_id FROM DUAL;
   
   SELECT NEXT_DAY(SYSDATE, 'THURSDAY') INTO end_time from dual;
   end_time :=TRUNC(end_time)+18/24;

   if cam_id > 0 then
      return_id := 0;
   ELSE
      UPDATE CAMERAS SET STATUS=1 WHERE ID = camera_id;
      INSERT INTO BOOKED_CAMS (ID, CAM_ID, PATRON_ID, START_TIME, END_TIME) VALUES(booking_id, camera_id, patron_id, start_time, end_time);
      return_id := 1;
   end if;
   
   commit;

EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END;



create or replace PROCEDURE CAMERA_AVAILABLE(
  borrow_date IN DATE,
  c_result OUT SYS_REFCURSOR
)
IS
  return_time date;
BEGIN
  return_time := borrow_date-1;
  return_time :=TRUNC(return_time)+18/24;
  OPEN c_result FOR
  SELECT * FROM CAMERAS WHERE STATUS = 0 OR ID IN (SELECT CAM_ID FROM BOOKED_CAMS WHERE END_TIME = return_time AND RETURNED_TIME IS NULL);
END CAMERA_AVAILABLE;
