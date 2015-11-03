
create or replace function is_copy_reserved (cpyid in Copies.id%type)
return number
is
  is_reserved number(1) := 0;
begin
  select count(1) into is_reserved from Reservation where copy_id = cpyid
  and end_time > sysdate;
  
  return is_reserved;
  
  exception when no_data_found then
  return -1;
end; ---
