create or replace PROCEDURE CAMERA_AVAILABLE(
  borrow_week IN NUMBER,
  c_result OUT SYS_REFCURSOR
)
IS
  return_time date;
  borrow_date date;
BEGIN
  borrow_date := SYSDATE;
  FOR Lcntr IN 1..borrow_week
    LOOP
      borrow_date := next_day (borrow_date,'FRIDAY');
    END LOOP;
  return_time := borrow_date-1;
  return_time :=TRUNC(return_time)+18/24;
  OPEN c_result FOR
  SELECT * FROM CAMERAS; -- WHERE STATUS = 0 OR ID IN (SELECT CAM_ID FROM BOOKED_CAMS WHERE END_TIME = return_time AND RETURNED_TIME IS NULL);
END CAMERA_AVAILABLE;

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


create or replace PROCEDURE CAMERA_CHECKED_OUT(
  patron_id IN NUMBER,
  c_result OUT SYS_REFCURSOR
)
IS
BEGIN
  OPEN c_result FOR
  SELECT BOOKED_CAMS.ID, CAMERAS.MAKE,CAMERAS.MODEL,CAMERAS.CONFIG,CAMERAS.LID,CAMERAS.MEMORY FROM CAMERAS, BOOKED_CAMS 
  WHERE BOOKED_CAMS.PATRON_ID = patron_id AND BOOKED_CAMS.CAM_ID = CAMERAS.ID AND BOOKED_CAMS.RETURNED_TIME IS NULL;
END CAMERA_CHECKED_OUT;


create or replace PROCEDURE CAMERA_REQ(
  patron_id IN NUMBER,
  cam_id IN NUMBER,
  borrow_week IN NUMBER,
  queue_number OUT NUMBER
) IS 
  borrow_date date;
  queue_id NUMBER;
BEGIN
  borrow_date := SYSDATE;
  FOR Lcntr IN 1..borrow_week
    LOOP
      borrow_date := next_day (borrow_date,'FRIDAY');
    END LOOP;
    borrow_date := TRUNC(borrow_date);
  SELECT cam_queue_seq.NEXTVAL INTO queue_id FROM DUAL;
  INSERT INTO CAM_QUEUE VALUES(queue_id, cam_id, patron_id, borrow_date, 0);
  SELECT COUNT(*) INTO queue_number FROM CAM_QUEUE WHERE CAM_ID = cam_id AND PATRON_ID = patron_id AND REQUEST_DATE = borrow_date;
END CAMERA_REQ;


create or replace PROCEDURE CAMERA_RETURN(
  booking_id IN NUMBER,
  status OUT NUMBER
) 
IS
  cam_id NUMBER;
BEGIN
  SELECT CAM_ID INTO cam_id FROM BOOKED_CAMS WHERE ID = booking_id AND RETURNED_TIME IS NULL;
   UPDATE BOOKED_CAMS SET RETURNED_TIME = SYSDATE WHERE ID = booking_id;
  UPDATE CAMERAS SET STATUS = 0 WHERE ID = cam_id;
  status := 1;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
  status :=0;
END CAMERA_RETURN;


create or replace PROCEDURE CAMERA_SEND_ALERT_1 
IS
  reminder_id NUMBER;
  booked_date DATE;
  c_result SYS_REFCURSOR;
  my_record CAM_QUEUE_TOPPER%ROWTYPE;
BEGIN
  booked_date := SYSDATE; 
  booked_date := TRUNC(booked_date);
  booked_date := next_day (booked_date,'FRIDAY'); -- To be commented after testing
  OPEN c_result FOR
    select * from CAM_QUEUE_TOPPER;
    
    LOOP
       FETCH c_result INTO my_record;
       EXIT WHEN c_result%NOTFOUND;
       SELECT REMINDERS_SEQ.NEXTVAL INTO reminder_id FROM DUAL;
       INSERT INTO REMINDERS VALUES (reminder_id, 'You can get you camera : '+to_char(my_record.cam_id),SYSDATE, my_record.patron_id);
    END LOOP;
    CLOSE c_result;
  
END CAMERA_SEND_ALERT_1;