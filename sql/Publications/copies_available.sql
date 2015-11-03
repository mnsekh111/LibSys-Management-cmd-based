create or replace PROCEDURE COPIES_AVAILABLE(
  iden in Copies.pid%TYPE,
  copies_result OUT SYS_REFCURSOR
)
IS
BEGIN
  OPEN copies_result FOR
  SELECT c.id,c.pid,p.title,c.copy_type,c.status from Copies c, Publications p where c.pid = p.id
  and c.pid = iden;
END; ---
