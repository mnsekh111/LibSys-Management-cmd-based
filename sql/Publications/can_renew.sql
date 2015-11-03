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
end; ---