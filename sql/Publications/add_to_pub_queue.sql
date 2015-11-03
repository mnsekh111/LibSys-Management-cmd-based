create or replace procedure add_to_pub_queue(patid in Patron.id%type,cpyid in Copies.pid%type, output_message in out varchar2)
is
  is_present number(1) :=0;
  is_present_check_out number(1) :=0;
  count_students number(5):=0;
  count_faculty number(5) :=0;
begin
  select count(1) into is_present from Pub_queue where patronid = patid and cid = cpyid;
  select count(1) into is_present_check_out from Checks_out where PATRON_ID = patid and COPY_ID = cpyid and ACT_RETURN_TIME is null;
  
  if(is_present = 0 and is_present_check_out = 0) then
    select count(*) into count_students from Pub_queue where cid = cpyid and patronid in (select id from Student);
    select count(*) into count_faculty from Pub_queue where cid = cpyid and patronid in (select id from Faculty);
    if(patron_type(patid) = 0) then
      insert into Pub_queue values(cpyid,patid,count_faculty+1);
      output_message:= output_message || '..' || 'Added to Faculty Queue - ' || (count_faculty+1) ;
    else
      insert into Pub_queue values(cpyid,patid,10000+count_students+1);
      output_message:= output_message || '..' || 'Added to Queue - ' || (count_faculty+count_students+1);    
    end if;
  elsif (is_present = 1) then
    output_message:= output_message || '..' ||'Already in waiting queue for this publication';
  else
    output_message:= output_message || '..' ||'You have already checked out this copy';
  end if;
end;