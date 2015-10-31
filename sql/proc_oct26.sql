create or replace PROCEDURE COPIES_AVAILABLE(
  iden in Copies.pid%TYPE,
  copies_result OUT SYS_REFCURSOR
)
IS
BEGIN
  OPEN copies_result FOR
  SELECT c.id,c.pid,p.title,c.copy_type from Copies c, Publications p where c.pid = p.id
  and c.pid = iden;
END;


CREATE SEQUENCE  CHECKS_OUT_ID  MINVALUE 1 MAXVALUE 5000 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  CYCLE ;

create or replace PROCEDURE PUB_CHECK_OUT (patronid in Patron.id%TYPE,copy_id in Copies.id %TYPE, output_message out varchar2)
is
  copy_record COPIES%ROWTYPE;
  copy_status COPIES.STATUS%type;
begin
  select * into copy_record from Copies where id = copy_id;
  if(SQL%NOTFOUND) then
    output_message := 'That copy is not valid';
  else 
    copy_status := copy_record.status;
    if(copy_status = 'OUT') then
      output_message := 'That copy is not available';
    
    else
      output_message := 'Copy is available';
      if(copy_record.copy_type = 'HARD') then
        update copies
          set status = 'OUT' where id = copy_id;
          commit;
          
        CASE publication_type(copy_record.pid)
            WHEN 0 THEN
              insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,copy_id,SYSDATE,sysdate + numtodsinterval(12,'hour'));
            WHEN 0 THEN
              insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,copy_id,SYSDATE,sysdate + numtodsinterval(12,'hour'));
            WHEN 1 THEN
              if(PATRON_TYPE(patronid) = 0) then
                insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,copy_id,SYSDATE,sysdate + numtodsinterval(30,'day'));
              else
                insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,copy_id,SYSDATE,sysdate + numtodsinterval(4,'day'));
              end if;
          END case;
      else
          insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,copy_id,SYSDATE,null);
      end if;
      
      output_message := output_message || '..' || 'Checked out successfully';
    end if;
  end if;
  commit;
end;

declare
output_message varchar2(1000);
begin
  PUB_CHECK_OUT(10,3,output_message);
  SYS.DBMS_OUTPUT.PUT_LINE(output_message);
end;

select * from copies;
select * from CHECKS_OUT;
delete from checks_out;

declare
copy_message varchar2(1000);
begin
  pub_check_out('1',)
end;


create table Pub_queue (
pid varchar2(10),
patronid number(10),
pos_in_queue number(5),

constraint pk_pub_queue PRIMARY KEY (pid,patronid),
constraint fk_pub_queue_patron FOREIGN KEY (patronid) references Patron(id),
constraint fk_pub_queue_pid FOREIGN KEY (pid) references Publications(id)
);

create or replace procedure add_to_pub_queue(patid in Patron.id%type,pubid in Copies.pid%type, output_message out varchar2)
is
  queue_record Pub_queue%ROWTYPE;
  count_students number(5):=0;
  count_faculty number(5) :=0;
begin
  select * into queue_record from Pub_queue where patronid = patid and pid = pubid ;
  if(SQL%NOTFOUND) then
    select count(*) into count_students from Pub_queue where pid = pubid and patid in (select id from Student);
    select count(*) into count_students from Pub_queue where pid = pubid and patid in (select id from Faculty);
    if(patron_type(patid) = 0) then
      insert into Pub_queue values(pubid,patid,count_faculty+1);
      output_message:= output_message || '..' || 'Added to Faculty Queue - ' || (count_faculty+1) ;
    else
      insert into Pub_queue values(pubid,patid,10000+count_students+1);
      output_message:= output_message || '..' || 'Added to Student Queue - ' || (count_faculty+count_students+1);    
    end if;
  else
    output_message:= output_message || '..' ||'Already in waiting queue for this publication';
  end if;
end;

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


set SERVEROUTPUT ON;
declare 
inp number(1);
begin
inp:=patron_type(11);
DBMS_OUTPUT.PUT_LINE(inp);
end;

select sysdate - numtodsinterval(1,'hour') from dual;

