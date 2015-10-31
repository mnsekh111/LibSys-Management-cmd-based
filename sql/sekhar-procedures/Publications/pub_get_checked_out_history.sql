create or replace function pub_get_checked_out_history(iden in PATRON.id%TYPE)
return SYS_REFCURSOR
IS
  checked_out_items SYS_REFCURSOR;
BEGIN
  OPEN checked_out_items FOR
  SELECT * from PUB_CHECK_OUT_SCREEN_VIEW where pid= iden;
  return checked_out_items;
END;