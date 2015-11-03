CREATE OR REPLACE VIEW PUB_RES_REQ_SCREEN_VIEW
AS
  SELECT pq.cid,pq.PATRONID,cpsv.TITLE,cpsv.COPY_TYPE
  FROM Copies_pub_screen_view cpsv,Pub_queue pq
  WHERE cpsv.id = pq.cid;---
