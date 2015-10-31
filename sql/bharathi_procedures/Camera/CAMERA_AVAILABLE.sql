create or replace PROCEDURE CAMERA_AVAILABLE(
 -- List all the cameras available on a particular week
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