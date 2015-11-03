create or replace view PUB_CHECK_OUT_SCREEN_VIEW_HIST as
  select cpsv.id,cpsv.pid,cpsv.TITLE,cpsv.COPY_TYPE,co.START_TIME,co.END_TIME,co.ACT_RETURN_TIME,co.PATRON_ID from CHECKS_OUT co,
COPIES_PUB_SCREEN_VIEW cpsv 
  where co.copy_id = cpsv.Id and co.ACT_RETURN_TIME is not null; ---