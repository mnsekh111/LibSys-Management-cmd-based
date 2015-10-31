CREATE OR REPLACE PROCEDURE ROOMS_INVALIDATE 
AS
  c_result SYS_REFCURSOR;
  my_record BOOKED%ROWTYPE;
BEGIN

  OPEN c_result FOR 
    SELECT * FROM BOOKED WHERE STATUS = 'VALID' AND START_TIME < SYSDATE - (1/24) AND CHECKED_OUT IS NULL;
    LOOP
      FETCH c_result INTO my_record;
      EXIT WHEN c_result%NOTFOUND;
      UPDATE BOOKED SET STATUS = 'INVALID' WHERE PATRON_ID = my_record.PATRON_ID AND ROOM_NUMBER=my_record.room_number AND START_TIME=my_record.start_time;
    END LOOP;
  
  CLOSE c_result;
  
  
EXCEPTION
WHEN OTHERS THEN
  NULL;
END ROOMS_INVALIDATE; ---
