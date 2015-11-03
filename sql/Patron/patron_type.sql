create or replace function patron_type(iden in Patron.id%type)
return number
is
  cnt number(5) := 0;
begin
  select count(*) into cnt from student where id = iden;
  if(cnt = 1) then
    return 1;
  else
    return 0;
  end if;
end;