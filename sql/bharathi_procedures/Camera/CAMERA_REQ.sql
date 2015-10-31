create or replace PROCEDURE CAMERA_REQ(
 --Insert into cam queue
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