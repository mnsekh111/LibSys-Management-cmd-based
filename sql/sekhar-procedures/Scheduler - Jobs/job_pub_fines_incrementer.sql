BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
            job_name => '"SMNATARA"."JOB_PUB_FINES_INCREMENTER"',
            job_type => 'STORED_PROCEDURE',
            job_action => 'SMNATARA.COMPUTE_FINES',
            number_of_arguments => 0,
            start_date => NULL,
            repeat_interval => 'FREQ=MINUTELY',
            end_date => NULL,
            enabled => FALSE,
            auto_drop => FALSE,
            comments => 'Increments the fines for publication resources');

         
     
 
    DBMS_SCHEDULER.SET_ATTRIBUTE( 
             name => '"SMNATARA"."JOB_PUB_FINES_INCREMENTER"', 
             attribute => 'logging_level', value => DBMS_SCHEDULER.LOGGING_OFF);
      
  
    
    DBMS_SCHEDULER.enable(
             name => '"SMNATARA"."JOB_PUB_FINES_INCREMENTER"');
END;
