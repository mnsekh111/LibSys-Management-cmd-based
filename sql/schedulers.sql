BEGIN
DBMS_SCHEDULER.DROP_JOB (
job_name => 'CAM_FINES_JOB');
END; ---

BEGIN
DBMS_SCHEDULER.DROP_JOB (
job_name => 'MONTHLY_OUTSTANDING_REMINDER');
END; ---


BEGIN
DBMS_SCHEDULER.DROP_JOB (
job_name => 'ROOMS_INVALIDATOR');
END; ---


BEGIN
DBMS_SCHEDULER.DROP_JOB (
job_name => 'SEND_CAM_REMINDER');
END; ---


BEGIN
DBMS_SCHEDULER.DROP_JOB (
job_name => 'SEND_CAM_REMINDER_2');
END; ---





 BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"SEND_CAM_REMINDER"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'CSUBBIA.CAMERA_SEND_ALERT_1',
            number_of_arguments => 0,
            start_date => TO_TIMESTAMP_TZ('2015-10-28 19:53:02.000000000 AMERICA/NEW_YORK','YYYY-MM-DD HH24:MI:SS.FF TZR'),
            repeat_interval => 'FREQ=WEEKLY;BYDAY=FRI;BYHOUR=9;BYMINUTE=0;BYSECOND=0',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'THIS WILL SEND REMINDERS TO PATRONS REG CAM CEHCKOUT');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"SEND_CAM_REMINDER"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"SEND_CAM_REMINDER"');
END; ---

 BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"SEND_CAM_REMINDER_2"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'CSUBBIA.CAMERA_SEND_ALERT_2',
            number_of_arguments => 0,
            start_date => NULL,
            repeat_interval => NULL,
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'This will send cancellation reminder for the queue topper and will send a new request to next user');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"SEND_CAM_REMINDER_2"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"SEND_CAM_REMINDER_2"');
END; ---


BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"CAM_FINES_JOB"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'CSUBBIA.CAMERA_FINES',
            number_of_arguments => 0,
            start_date => TO_TIMESTAMP_TZ('2015-10-28 19:53:02.000000000 AMERICA/NEW_YORK','YYYY-MM-DD HH24:MI:SS.FF TZR'),
            repeat_interval => 'FREQ=HOURLY;BYDAY=MON,TUE,WED,THU,FRI,SAT,SUN;BYMINUTE=0;BYSECOND=1',
            end_date => NULL,
                job_class => '"SYS"."DBMS_JOB$"',
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'This will handle the fines for camera');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"CAM_FINES_JOB"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"CAM_FINES_JOB"');
END; ---



BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"MONTHLY_OUTSTANDING_REMINDER"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'CSUBBIA.FINES_MONTHLY_OUTSTANDING',
            number_of_arguments => 0,
            start_date => TO_TIMESTAMP_TZ('2015-10-29 12:28:14.000000000 AMERICA/NEW_YORK','YYYY-MM-DD HH24:MI:SS.FF TZR'),
            repeat_interval => 'FREQ=MONTHLY;BYMONTHDAY=1;BYHOUR=12;BYMINUTE=0;BYSECOND=1',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'This will send monthly outstanding reminders to the patrons');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"MONTHLY_OUTSTANDING_REMINDER"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"MONTHLY_OUTSTANDING_REMINDER"');
END; ---


BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"ROOMS_INVALIDATOR"',
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
             name => '"ROOMS_INVALIDATOR"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"ROOMS_INVALIDATOR"');
END; ---
