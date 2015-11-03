create or replace PROCEDURE CAMERA_4_CHKOUT(
-- This will list the requests in queue topper so that user can select one to checkout
  patron_id IN NUMBER,
  c_result OUT SYS_REFCURSOR
)
IS
  booked_date DATE;
  today VARCHAR2(1);
   
BEGIN
  -- Comment after testing
  booked_date := SYSDATE; 
  --booked_date := next_day (booked_date,'FRIDAY'); -- To be commented after testing
  booked_date := TRUNC(booked_date);
  SELECT TO_CHAR(SYSDATE, 'd') INTO today FROM DUAL;
  
  IF today = 6 THEN  --This checks if today is Friday. This is handled in java also
  --IF today = 4 THEN
    OPEN c_result FOR
      SELECT * FROM CAM_QUEUE_TOPPER WHERE PATRON_ID = patron_id AND REQUEST_DATE=booked_date;
  ELSE
    OPEN c_result FOR
      SELECT * FROM CAM_QUEUE_TOPPER WHERE 1=2;
  END IF;
END CAMERA_4_CHKOUT; ---