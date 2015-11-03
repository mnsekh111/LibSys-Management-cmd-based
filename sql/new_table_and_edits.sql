create table Pub_queue (
cid number(10),
patronid number(10),
pos_in_queue number(5),

constraint pk_pub_queue PRIMARY KEY (cid,patronid),
constraint fk_pub_queue_patron FOREIGN KEY (patronid) references Patron(id),
constraint fk_pub_queue_pid FOREIGN KEY (cid) references Copies(id)
);---

alter table checks_out
add ( ACT_RETURN_TIME date);---

alter table Student 
add classification varchar2(25);---

alter table Student
add constraint chk_student_class CHECK ((classification = 'UNDERGRADUATE' and upper( SUBSTR (program, 1, 1))='B') or 
(classification = 'GRADUATE' and upper(SUBSTR(program,1,1))='M') or ((classification = 'POSTGRADUATE' and upper(SUBSTR(program,1,1))='P')));---

