create or replace procedure renew(cpyid in Copies.id%type,patronid Patron.id%type,ret_message in out varchar2)
is
copy_record Copies%rowtype;
checks_out_record checks_out%rowtype;
begin
  select * into copy_record from copies where id = cpyid;
  select * into checks_out_record from Checks_out where copy_id = cpyid and patron_id = patronid and end_time > sysdate and ACT_RETURN_TIME is null;
  if(copy_record.copy_type = 'HARD') then
    if(can_renew(cpyid) = 1) then
      update checks_out 
        set end_time = SYSDATE + (end_time - start_time)
          where copy_id = cpyid and patron_id = patronid and act_return_time is null;
      ret_message := 'Renewed successfully';
    else
      ret_message := 'Cannot be renewed. There are people waiting for this copy';
    end if;
  else
    ret_message := 'Electronic Copies cannot be renewed';
  end if;
  exception when no_data_found then
    ret_message := 'Renew Failed. You are not currently having this book';
end; ---
