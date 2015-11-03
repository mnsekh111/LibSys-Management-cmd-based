set serveroutput on;

declare
output_message varchar2(1000);
begin
  PUB_CHECK_OUT(3,6,output_message);
  SYS.DBMS_OUTPUT.PUT_LINE(output_message);
end;

declare
output_message varchar2(1000);
begin
  PUB_RETURN(6,output_message);
  SYS.DBMS_OUTPUT.PUT_LINE(output_message);
end;

select * from copies;
select * from CHECKS_OUT;
select * from pub_queue;
select * from Fines;

update copies set STATUS = 'IN';
delete from checks_out;
delete from PUB_QUEUE;
delete from fines;
commit;