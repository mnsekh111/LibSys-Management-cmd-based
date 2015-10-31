CREATE OR REPLACE VIEW PUB_CHECK_OUT_SCREEN_VIEW_HIST
AS
  SELECT cpsv.id,cpsv.pid,cpsv.TITLE,cpsv.COPY_TYPE,co.START_TIME,co.END_TIME,
    co.ACT_RETURN_TIME
  FROM CHECKS_OUT co, COPIES_PUB_SCREEN_VIEW cpsv
  WHERE co.copy_id = cpsv.Id AND co.ACT_RETURN_TIME IS NOT NULL;