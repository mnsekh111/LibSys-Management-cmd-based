create or replace Procedure BOOK_CAMERA
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