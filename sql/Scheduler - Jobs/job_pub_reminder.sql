BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => 'JOB_PUB_REMINDER',
            job_type => 'STORED_PROCEDURE',
            job_action => 'PUB_SEND_REMINDERS',
            number_of_arguments => 0,
            start_date => NULL,
            repeat_interval => 'FREQ=DAILY',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'Sends return reminders for publications');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => 'JOB_PUB_REMINDER', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
END; ---
