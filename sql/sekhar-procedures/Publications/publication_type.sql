create or replace function publication_type(iden in Publications.id%type)
return number
is
  cnt number(5) := 0;
  cnt1 number(5) := 0;
begin
  select count(*) into cnt from PUB_CONFERENCEPAPERS where conf_num = iden;
  select count(*) into cnt1 from PUB_BOOK where isbn = iden;
  if(cnt = 1) then
    return 0;
  elsif (cnt1 = 1) then
    return 1;
  else
    return 2;
  end if;
end;