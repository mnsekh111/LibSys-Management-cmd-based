create or replace function is_pub_checked_out (pubid in Publications.id%type, patid in Patron.id%type)
return number
is
  is_checked number(1) := 0;
begin
  select count(1) into is_checked from CHECKS_OUT where patron_id = patid and copy_id in (select id from Copies where pid = pubid)
  and end_time > sysdate;
  
  return is_checked;
  
  exception when no_data_found then
  return -1;
end; ---