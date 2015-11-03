
set SERVEROUTPUT ON;

-- 
declare 
inp number(1);
begin
inp:=patron_type(11);
DBMS_OUTPUT.PUT_LINE(inp);
end;


select sysdate - numtodsinterval(1,'hour') from dual;