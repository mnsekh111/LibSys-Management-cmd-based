create or replace PROCEDURE CAMERA_SEND_ALERT_1 
IS
  reminder_id NUMBER;
  booked_date DATE;
  c_result SYS_REFCURSOR;
  my_record CAM_QUEUE_TOPPER%ROWTYPE;
  is_cam_available NUMBER;
BEGIN
  booked_date := SYSDATE; 
  booked_date := TRUNC(booked_date);
  --booked_date := next_day (booked_date,'FRIDAY'); -- To be commented after testing
  OPEN c_result FOR
    select * from CAM_QUEUE_TOPPER;
    
    LOOP
       FETCH c_result INTO my_record;
       EXIT WHEN c_result%NOTFOUND;
       SELECT REMINDERS_SEQ.NEXTVAL INTO reminder_id FROM DUAL;
       
       SELECT status INTO is_cam_available FROM CAMERAS WHERE ID=my_record.cam_id;
       IF is_cam_available > 0 THEN
        INSERT INTO REMINDERS VALUES (reminder_id, 'The requested camera is not available. Sorry for the inconvinience',SYSDATE, my_record.patron_id);
       ELSE
        INSERT INTO REMINDERS VALUES (reminder_id, 'You can get your camera',SYSDATE, my_record.patron_id);
       END IF;
    END LOOP;
    CLOSE c_result;
  
END CAMERA_SEND_ALERT_1; ---