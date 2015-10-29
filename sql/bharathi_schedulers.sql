-- One more scheduler that I have not included.


 BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"CSUBBIA"."SEND_CAM_REMINDER_2"',
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
             name => '"CSUBBIA"."SEND_CAM_REMINDER_2"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"CSUBBIA"."SEND_CAM_REMINDER_2"');
END;


BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"CSUBBIA"."CAM_FINES_JOB"',
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
             name => '"CSUBBIA"."CAM_FINES_JOB"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"CSUBBIA"."CAM_FINES_JOB"');
END;
