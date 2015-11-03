
CREATE OR REPLACE VIEW QUERY4 AS
SELECT COUNT(*) AS "NO OF NULL RESERVATIONS"
FROM PATRON P, BOOKED B
WHERE P.ID=B.PATRON_ID AND B.CHECKED_OUT IS NULL AND B.CHECKED_IN IS NULL AND B.STATUS='INVALID' AND B.END_TIME>='30-OCT-15' AND B.END_TIME<='06-NOV-15' 
;