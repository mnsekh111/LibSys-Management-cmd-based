CREATE OR REPLACE VIEW COPIES_PUB_SCREEN_VIEW
AS
  SELECT c.ID,c.pid,p.title,c.COPY_TYPE
  FROM Copies c,Publications p
  WHERE c.pid = p.id;---