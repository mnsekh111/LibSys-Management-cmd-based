create or replace function pub_get_res_requests(iden in PATRON.id%TYPE)
return SYS_REFCURSOR
IS
  requested_items SYS_REFCURSOR;
BEGIN
  OPEN requested_items FOR
  SELECT * from PUB_RES_REQ_SCREEN_VIEW where patronid= iden;
  return requested_items;
END;
