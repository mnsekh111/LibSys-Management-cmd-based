create sequence booked_cams_seq;---
alter table booked_cams add (returned_time date);---

CREATE TABLE cam_queue (
	id number(10),
	cam_id number(10),
	patron_id number(10),
	request_date date,
	status number(1),
	
	CONSTRAINT pk_cam_queue PRIMARY KEY (id),
	CONSTRAINT fk_cam_queue_cam FOREIGN KEY (cam_id) REFERENCES CAMERAS(ID),
	CONSTRAINT fk_cam_queue_patrons FOREIGN KEY (patron_id) REFERENCES PATRON(ID)
	 
);---

create sequence cam_queue_seq;---


  CREATE OR REPLACE VIEW "CAM_QUEUE_TOPPER_HIDDEN" ("ID", "CAM_ID", "REQUEST_DATE") AS 
  (select min(id) AS ID,cam_id, request_date from cam_queue where status=0 group by cam_id, REQUEST_DATE);---
 


  CREATE OR REPLACE VIEW "CAM_QUEUE_TOPPER" ("ID", "CAM_ID", "PATRON_ID", "REQUEST_DATE", "MAKE", "MODEL", "CONFIG", "LID", "MEMORY", "STATUS") AS 
  (select CQ.id, CQ.cam_id, CQ.patron_id, CQ.request_date, CAM.make, CAM.model, 
CAM.config, CAM.lid, CAM.memory, CAM.status 
from cam_queue CQ, CAM_QUEUE_TOPPER_HIDDEN CQTH, CAMERAS CAM 
where CQ.id = CQTH.id AND CQ.cam_id = CAM.id);---


CREATE SEQUENCE REMINDERS_SEQ;---
CREATE SEQUENCE CAM_FINES_SEQ;---

CREATE OR REPLACE VIEW OUTSTANDING_AMOUNT AS 
SELECT patron_id AS PATRON_ID, SUM(amount) AS AMOUNT 
FROM 
(SELECT BC.patron_id, CM.amount FROM BOOKED_CAMS BC, CAM_FINES CM WHERE CM.booked_cam_id=BC.id AND STATUS='UNPAID'  
UNION 
SELECT CO.patron_id, FI.amount FROM CHECKS_OUT CO, FINES FI WHERE CO.id = FI.checks_out_id AND STATUS='UNPAID')  
GROUP BY patron_id;---


ALTER TABLE BOOKED DROP COLUMN STATUS;---

ALTER TABLE BOOKED ADD (STATUS VARCHAR(7));---

CREATE OR REPLACE VIEW ALL_FINES AS(SELECT 'C'||CMF.id as ID, CAM.MAKE ||' - ' || CAM.MODEL as DESCRIPTION, BC.patron_id, CMF.amount,CMF.STATUS  FROM BOOKED_CAMS BC, CAM_FINES CMF, CAMERAS CAM WHERE CMF.booked_cam_id=BC.id AND CMF.STATUS='UNPAID' AND CAM.id = CMF.booked_cam_id 
UNION 
SELECT 'B'||FI.id as ID ,PUB.TITLE || ' - Copy : '|| COP.id as DESCRIPTION, CO.patron_id, FI.amount, FI.STATUS 
FROM CHECKS_OUT CO, FINES FI, COPIES COP, PUBLICATIONS PUB WHERE CO.id = FI.checks_out_id AND FI.STATUS='UNPAID' AND COP.id = CO.COPY_ID AND PUB.ID=COP.PID);---

