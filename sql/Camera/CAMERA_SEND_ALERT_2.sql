create or replace PROCEDURE CAMERA_SEND_ALERT_2 
IS
  reminder_id NUMBER;
  booked_date DATE;
  c_result SYS_REFCURSOR;
  my_record CAM_QUEUE_TOPPER%ROWTYPE;
  is_cam_available NUMBER;
  make VARCHAR2(20);
  model_type VARCHAR2(20);
BEGIN
  booked_date := SYSDATE; 
  booked_date := TRUNC(booked_date);
  --booked_date := next_day (booked_date,'FRIDAY'); -- To be commented after testing
  
  -- This is to send remainder about cancellation
  OPEN c_result FOR
    select * from CAM_QUEUE_TOPPER WHERE REQUEST_DATE = booked_date;
    
    LOOP
       FETCH c_result INTO my_record;
       EXIT WHEN c_result%NOTFOUND;
       SELECT REMINDERS_SEQ.NEXTVAL INTO reminder_id FROM DUAL;
       SELECT status, make, model INTO is_cam_available, make, model_type FROM CAMERAS WHERE ID=my_record.cam_id;
       INSERT INTO REMINDERS VALUES (reminder_id, 'Your request for the camera '  || make ||' ' ||  model_type || ' has been cancelled',SYSDATE, my_record.patron_id);
       UPDATE CAM_QUEUE SET STATUS=4 WHERE ID=my_record.id;
       
    END LOOP;
    CLOSE c_result;
    -- This is to send remainder to the person next in the queue
    OPEN c_result FOR
    select * from CAM_QUEUE_TOPPER WHERE REQUEST_DATE = booked_date;
    
    LOOP
       FETCH c_result INTO my_record;
       EXIT WHEN c_result%NOTFOUND;
       SELECT REMINDERS_SEQ.NEXTVAL INTO reminder_id FROM DUAL;
       
       SELECT status, make, model INTO is_cam_available, make, model_type FROM CAMERAS WHERE ID=my_record.cam_id;
       IF is_cam_available > 0 THEN
        INSERT INTO REMINDERS VALUES (reminder_id, 'The requested camera '  || make ||' ' ||  model_type || ' is not available. Sorry for the inconvinience',SYSDATE, my_record.patron_id);
       ELSE
        INSERT INTO REMINDERS VALUES (reminder_id, 'You can get your camera '  || make ||' ' ||  model_type,SYSDATE, my_record.patron_id);
       END IF;
    END LOOP;
    CLOSE c_result;
  
END CAMERA_SEND_ALERT_2; ---