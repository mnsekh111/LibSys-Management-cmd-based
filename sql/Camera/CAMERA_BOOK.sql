create or replace Procedure CAMERA_BOOK
(
 -- This will take care of checking out the cameras
  queue_id IN NUMBER,
  patron_id IN NUMBER,
  return_id OUT NUMBER
)
   
IS
   cam_id number;
   booking_id number;
   request_date date;
   end_time date;
   cam_status number;

BEGIN
 
   SELECT REQUEST_DATE, CAM_ID INTO request_date, cam_id FROM CAM_QUEUE_TOPPER WHERE ID = queue_id;
   
   SELECT STATUS INTO cam_status FROM CAMERAS WHERE ID = cam_id;
   
   

   if cam_status > 0 then
      return_id := 0;
   ELSE
      UPDATE CAM_QUEUE SET STATUS = 2 WHERE REQUEST_DATE = request_date AND CAM_ID = cam_id AND ID NOT IN(queue_id);
      UPDATE CAMERAS SET STATUS = 1 WHERE ID = cam_id;
      SELECT booked_cams_seq.nextval INTO booking_id FROM DUAL;
    
      SELECT NEXT_DAY(SYSDATE, 'THURSDAY') INTO end_time from dual;
      end_time :=TRUNC(end_time)+18/24;
      INSERT INTO BOOKED_CAMS (ID, CAM_ID, PATRON_ID, START_TIME, END_TIME) VALUES(booking_id, cam_id, patron_id, request_date, end_time);
      UPDATE CAM_QUEUE SET STATUS = 1 WHERE ID = queue_id;
      return_id := 1;
   end if;
   
   commit;

EXCEPTION
WHEN OTHERS THEN
   raise_application_error(-20001,'An error was encountered - '||SQLCODE||' -ERROR- '||SQLERRM);
END; ---