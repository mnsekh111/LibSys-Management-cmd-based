create or replace procedure check_out_next_in_queue(copy_id in Copies.ID%TYPE) 
is
  next_patron PATRON.ID%Type;
  output_message varchar2(5000);
begin
  select PATRONID into next_patron from pub_queue where POS_IN_QUEUE = (select min(pos_in_queue) from pub_queue where cid = copy_id); 
  PUB_CHECK_OUT(next_patron,copy_id,output_message);
  
  delete from pub_queue where PATRONID = next_patron and cid = copy_id;
  update PUB_QUEUE 
    set POS_IN_QUEUE = POS_IN_QUEUE-1
    where cid = copy_id and PATRON_TYPE(next_patron) = PATRON_TYPE(patronid);
  commit;
end;

create or replace function can_renew(copy_id in COPIES.ID%type) return number
is
  is_present number(1):=0;
begin
  select count(1) into is_present from  pub_queue where CID = copy_id;
  if(is_present = 0) then
    return 1;
  else
    return 0;
  end if;
end;