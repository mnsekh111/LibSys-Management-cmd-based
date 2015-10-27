INSERT INTO Library (id,name) VALUES(0,'James B. Hunt, Jr. Library');
INSERT INTO Library (id,name) VALUES(1,'D.H. Hill Library');

INSERT INTO Departments (abbreviation, name) VALUES('CH','Chemistry');
INSERT INTO Courses (id,name,dep_abbreviation) VALUES(101,'Introduction to Chemistry','CH');
INSERT INTO Courses (id,name,dep_abbreviation) VALUES(102,'Introduction to Organic Chemistry','CH');
INSERT INTO Courses (id,name,dep_abbreviation) VALUES(103,'Introduction to Physical Chemistry','CH');
INSERT INTO Courses (id,name,dep_abbreviation) VALUES(104,'Introduction to Inorganic Chemistry','CH');

INSERT INTO Nationality (country_name) VALUES ('USA (United States of America)');
INSERT INTO Nationality (country_name) VALUES ('Chile');

INSERT INTO Degree_Program (program) VALUES('B.S.');
INSERT INTO Degree_Program (program) VALUES('M.S.');
INSERT INTO Degree_Program (program) VALUES('M.A.');
INSERT INTO Degree_Program (program) VALUES('Ph.D.');

INSERT INTO Faculty_Category (category) VALUES('Assistant Professor');
INSERT INTO Faculty_Category (category) VALUES('Associate Professor');
INSERT INTO Faculty_Category (category) VALUES('Professor');
INSERT INTO Faculty_Category (category) VALUES('Lecturer');

SELECT INSERT_STUDENT('Jesse','Pinkman',1,'GOOD','USA (United States of America)','123456789','123456787','03-Oct-1988','Male','1511 Graduate Lane','Raleigh',27606,'B.S.',1,'CH') FROM DUAL;
SELECT INSERT_STUDENT('Walt','Jr.',2,'GOOD','USA (United States of America)','123456780','123456781','03-Nov-1988','Male','1512 Graduate Lane','Raleigh',27606,'B.S.',2,'CH') FROM DUAL;
SELECT INSERT_STUDENT('Gale','Boetticher',3,'GOOD','Chile','123456782','123456783','03-Dec-1988','Male','1513 Graduate Lane','Raleigh',27606,'B.S.',3,'CH') FROM DUAL;
SELECT INSERT_STUDENT('Saul','Goodman',4,'GOOD','USA (United States of America)','123456784','123456785','03-Jan-1988','Male','1513 Graduate Lane','Raleigh',27606,'M.S.',2,'CH') FROM DUAL;

SELECT INSERT_FACULTY('Walter','White',5,'GOOD','USA (United States of America)','Professor','CH',101) FROM DUAL;
SELECT INSERT_FACULTY('Gustavo','Fring',6,'GOOD','USA (United States of America)','Assistant Professor','CH',102) FROM DUAL;
SELECT INSERT_FACULTY('Hank','Schrader',7,'GOOD','USA (United States of America)','Associate Professor','CH',103) FROM DUAL;
SELECT INSERT_FACULTY('Skyler','White',8,'GOOD','USA (United States of America)','Professor','CH',104) FROM DUAL;

INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (1,'CH',101,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (2,'CH',101,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (3,'CH',101,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (2,'CH',102,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (3,'CH',102,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (4,'CH',102,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (3,'CH',103,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (4,'CH',103,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (1,'CH',103,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (1,'CH',104,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (2,'CH',104,15,2);
INSERT INTO Course_Taken (patron_id,dep_abbreviation,id,year,semester) VALUES (4,'CH',104,15,2);

INSERT INTO Authors (id,name) VALUES ('A1','SK Goyal');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('Introduction to Chemistry','B1',TO_DATE('2005','YYYY'));
INSERT INTO Pub_Book (edition,isbn,publisher) VALUES (1,'B1','Pub1');
INSERT INTO Written_by (pid,aid) VALUES ('B1','A1');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (1,'ELECTRONIC',0,'IN');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (2,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A2','HC Verma');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('Introduction to Organic Chemistry','B2',TO_DATE('2006','YYYY'));
INSERT INTO Pub_Book (edition,isbn,publisher) VALUES (2,'B2','Pub2');
INSERT INTO Written_by (pid,aid) VALUES ('B2','A2');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (3,'ELECTRONIC',0,'IN');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (4,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A3','Resnick Halliday Walker');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('Introduction to Physical Chemistry','B3',TO_DATE('2000','YYYY'));
INSERT INTO Pub_Book (edition,isbn,publisher) VALUES (3,'B3','Pub3');
INSERT INTO Written_by (pid,aid) VALUES ('B3','A3');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (5,'HARD',0,'IN');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (6,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A4','RC Mukherjee');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('Introduction to Inorganic Chemistry','B4',TO_DATE('2005','YYYY'));
INSERT INTO Pub_Book (edition,isbn,publisher) VALUES (4,'B4','Pub4');
INSERT INTO Written_by (pid,aid) VALUES ('B4','A4');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (7,'HARD',0,'IN');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (8,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A5','Roberto Navigli');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('Journal of Web Semantic','J1',TO_DATE('2010','YYYY'));
INSERT INTO Pub_Journal (issn) VALUES ('J1');
INSERT INTO Written_by (pid,aid) VALUES ('J1','A5');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (9,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A6','Tim Berners Lee');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('International Journal on Semantic Web and Information','J2',TO_DATE('2011','YYYY'));
INSERT INTO Pub_Journal (issn) VALUES ('J2');
INSERT INTO Written_by (pid,aid) VALUES ('J2','A6');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (10,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A7','HyeongSik Kim');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('Optimization Techniques for Large Scale Graph Analytics on Map Reduce','C1',TO_DATE('2013','YYYY'));
INSERT INTO Pub_ConferencePapers (conf_num,confName) VALUES ('C1','WWW');
INSERT INTO Written_by (pid,aid) VALUES ('C1','A7');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (11,'HARD',0,'IN');

INSERT INTO Authors (id,name) VALUES ('A8','Sidan Gao');
INSERT INTO Publications (title,id,year_of_pub) VALUES ('An agglomerative query model for discovery in linked data: semantics and approach','C2',TO_DATE('2014','YYYY'));
INSERT INTO Pub_ConferencePapers (conf_num,confName) VALUES ('C2','SIGMOD');
INSERT INTO Written_by (pid,aid) VALUES ('C2','A8');
INSERT INTO Copies(id,copy_type,lib_id,status) VALUES (12,'HARD',0,'IN');

INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (1,2,0,3,'CONF');
INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (2,3,0,3,'STUDY');
INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (3,4,1,3,'STUDY');
INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (4,3,0,3,'CONF');
INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (5,4,0,3,'STUDY');
INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (6,4,0,3,'STUDY');
INSERT INTO Rooms(room_number,capacity,library_id,floor_no,room_type) VALUES (7,2,0,2,'STUDY');

INSERT INTO Cameras (id,make,model,config,lid,memory,status) VALUES (1,'Olympus','E-620','14-­42mm lens 1:3.5­5.6',0,'16GB',1);
INSERT INTO Cameras (id,make,model,config,lid,memory,status) VALUES (2,'Canon','EOS Rebel T4i','18-­135mm EF­S IS STM Lens',0,'32GB',1);
INSERT INTO Cameras (id,make,model,config,lid,memory,status) VALUES (3,'Canon','EOS Rebel T4i','18­-135mm EF­S IS STM Lens',0,'32GB',1);

-- Waiting for response from Avi on this part...
--INSERT INTO CHECKS_OUT (id,patron_id,copy_id,start_time,end_time) VALUES (1,1,3,'08-Nov-2015','13-Nov-2015');
--UPDATE Copies SET status = 'OUT' WHERE id = 3;
--INSERT INTO CHECKS_OUT (id,patron_id,copy_id,start_time,end_time) VALUES (2,4,7,'07-Nov-2015','11-Nov-2015');
--UPDATE Copies SET status = 'OUT' WHERE id = 7;
--INSERT INTO CHECKS_OUT (id,patron_id,copy_id,start_time,end_time) VALUES (3,2,8,'01-Jul-2015','08-Aug-2015');
--UPDATE Copies SET status = 'OUT' WHERE id = 8;
--INSERT INTO CHECKS_OUT (id,patron_id,copy_id,start_time,end_time) VALUES (4,3,3,'01-Oct-2015','10-Oct-2015');
--UPDATE Copies SET status = 'OUT' WHERE id = 3;

INSERT INTO Booked (patron_id,room_number,start_time,end_time,checked_out,checked_in) VALUES (5,3,TO_DATE('2015/11/01 09:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/11/01 11:30', 'YYYY/MM/DD HH24:MI'),NULL,NULL);
INSERT INTO Booked (patron_id,room_number,start_time,end_time,checked_out,checked_in) VALUES (1,5,TO_DATE('2015/10/12 15:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/10/12 17:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/10/12 15:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/10/12 17:00', 'YYYY/MM/DD HH24:MI'));
INSERT INTO Booked (patron_id,room_number,start_time,end_time,checked_out,checked_in) VALUES (8,6,TO_DATE('2015/11/02 11:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/11/02 13:30', 'YYYY/MM/DD HH24:MI'),NULL,NULL);
INSERT INTO Booked (patron_id,room_number,start_time,end_time,checked_out,checked_in) VALUES (6,1,TO_DATE('2015/10/20 09:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/10/20 10:30', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/10/20 09:00', 'YYYY/MM/DD HH24:MI'),TO_DATE('2015/10/20 10:30', 'YYYY/MM/DD HH24:MI'));

INSERT INTO Booked_Cams (id,cam_id,patron_id,start_time,end_time) VALUES (1,2,3,'13-Nov-2015','19-Nov-2015');
INSERT INTO Booked_Cams (id,cam_id,patron_id,start_time,end_time) VALUES (2,1,1,'30-Oct-2015','05-Nov-2015');
INSERT INTO Booked_Cams (id,cam_id,patron_id,start_time,end_time) VALUES (3,3,2,'16-Oct-2015','22-Oct-2015');
