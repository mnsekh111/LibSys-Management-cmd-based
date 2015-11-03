create or replace PROCEDURE PUB_RETURN (cpy_id in Copies.id %TYPE, output_message in out varchar2)
is
  check_out_record checks_out%ROWTYPE;
  cpy_type COPIES.COPY_TYPE%type;
begin
  select copy_type into cpy_type from Copies where ID = cpy_id;
  if(cpy_type = 'HARD') then
    select * into check_out_record from Checks_out where copy_id = cpy_id and ACT_RETURN_TIME is null; 
    --Check_out_record not null
    
    output_message := 'That copy is returned successfully';
    
    --compute_fines(check_out_record.copy_id);
    update CHECKS_OUT set
      ACT_RETURN_TIME = SYSDATE
      where COPY_ID = check_out_record.copy_id;
    update Copies set
      STATUS = 'IN' where ID = cpy_id;
     
    if(CAN_RENEW(cpy_id) = 0) then 
      check_out_next_in_queue(cpy_id);
    end if;
    
    
  else
    output_message := 'Electronic copies need not be returned.Thanks any ways';
  end if;
  exception
  when no_data_found 
  then 
  output_message := 'That copy is not checked out'; 

end; ---

