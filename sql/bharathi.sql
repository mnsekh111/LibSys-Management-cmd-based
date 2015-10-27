create sequence booked_cams_seq;
alter table booked_cams add (returned_time date);

CREATE TABLE cam_queue (
	id number(10),
	cam_id number(10),
	patron_id number(10),
	request_date date,
	status number(1), -- 0 - new request, 1- checkedout, 2- someone else has checked out
	
	CONSTRAINT pk_cam_queue PRIMARY KEY (id),
	CONSTRAINT fk_cam_queue_cam FOREIGN KEY (cam_id) REFERENCES CAMERAS(ID),
	CONSTRAINT fk_cam_queue_patrons FOREIGN KEY (patron_id) REFERENCES PATRON(ID)
	 
);

create sequence cam_queue_seq;

CREATE VIEW CAM_QUEUE_TOPPER AS (select min(id) AS ID,cam_id, patron_id, request_date from cam_queue where status=0 group by cam_id, patron_id, REQUEST_DATE);
CREATE SEQUENCE REMINDERS_SEQ;
