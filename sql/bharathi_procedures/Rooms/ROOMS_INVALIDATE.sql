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
END ROOMS_INVALIDATE;

BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"CSUBBIA"."ROOMS_INVALIDATOR"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'CSUBBIA.ROOMS_INVALIDATE',
            number_of_arguments => 0,
            start_date => TO_TIMESTAMP_TZ('2015-10-29 16:53:21.000000000 AMERICA/NEW_YORK','YYYY-MM-DD HH24:MI:SS.FF TZR'),
            repeat_interval => 'FREQ=MINUTELY;INTERVAL=30',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'This will invalidate the room reservations after 1 hour or reservation start time');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"CSUBBIA"."ROOMS_INVALIDATOR"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"CSUBBIA"."ROOMS_INVALIDATOR"');
END;
