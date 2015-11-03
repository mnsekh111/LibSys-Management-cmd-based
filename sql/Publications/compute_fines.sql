
create or replace procedure compute_fines 
is
  cursor un_returned return checks_out%ROWTYPE is
  select * from checks_out where SYSDATE > END_TIME and ACT_RETURN_TIME is null and START_TIME <> END_TIME;
  temp checks_out%ROWTYPE;
  is_fine_exist number(1) :=0;
begin
  
  open un_returned;
  Loop exit when un_returned%notfound;
    fetch un_returned into temp;
    is_fine_exist :=0;
    select count(1) into is_fine_exist from Fines where CHECKS_OUT_ID = temp.id;
    if(is_fine_exist = 1) then
      update Fines 
        set AMOUNT = ceil(SYSDATE - temp.end_time)*2
        where STATUS = 'UNPAID';
    elsif (temp.id is not null) then
      insert into Fines values(FINES_ID.nextval,temp.id,2,'UNPAID',temp.END_TIME);
    end if;
  END LOOP;
  commit;
end; ---
