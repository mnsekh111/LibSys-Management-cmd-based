CREATE TABLE Patron (
	fname varchar2(25) NOT NULL,
	lname varchar2(25) NOT NULL,
	id number(10) NOT NULL,
	status ENUM('GOOD','BAD'),
	country_name varchar2(50) NOT NULL,
	
	CONSTRAINT fk_patron_nationality FOREIGN KEY (country_name) REFERENCES Nationality (country_name),
	CONSTRAINT pk_patron PRIMARY KEY id
);

CREATE TABLE Student (
	phone varchar2(25) NOT NULL,
	alt_phone varchar2(25) NOT NULL,
	dob date NOT NULL,
	sex varchar2(25) NOT NULL,
	street varchar2(25) NOT NULL,
	city varchar2(25) NOT NULL,
	postcode varchar2(25) NOT NULL,
	id number(10) NOT NULL,
	program varchar2(25) NOT NULL,
	year number(2)
	
	CONSTRAINT pk_student PRIMARY KEY id,
	CONSTRAINT fk_student FOREIGN KEY (id) REFERENCES Patron (id),
	CONSTRAINT fk_student_program FOREIGN KEY (program) REFERENCES Degree_Program (program)
);

CREATE TABLE Faculty (
	category varchar2(25) NOT NULL,
	id number(10) NOT NULL,
	
	CONSTRAINT pk_faculty PRIMARY KEY id,
	CONSTRAINT fk_faculty FOREIGN KEY (id) REFERENCES Patron (id),
	CONSTRAINT fk_faculty_category FOREIGN KEY (category) REFERENCES Faculty_Category (category)
);

CREATE TABLE Nationality (
	country_name varchar2(50) NOT NULL,
	
	CONSTRAINT pk_country_name PRIMARY KEY country_name
);

CREATE TABLE Faculty_Category (
	category varchar2(25) NOT NULL,
	
	CONSTRAINT pk_faculty_category PRIMARY KEY category
);

CREATE TABLE Degree_Program (
	program varchar2(25) NOT NULL,
	
	CONSTRAINT pk_degree_program PRIMARY KEY program
);

CREATE TABLE Reminders (
	id number(10),
	message varchar2(500) NOT NULL,
	time_sent date NOT NULL,
	patron_id  number(20) NOT NULL,
	
	CONSTRAINT pk_reminders PRIMARY KEY id,
	CONSTRAINT fk_reminders_patrons FOREIGN KEY (patron_id) REFERENCES Patron (id)
);

CREATE TABLE Fines (
	id number(10),
	amount number(10) NOT NULL,
	status ENUM('PAID','UNPAID'),
	
	CONSTRAINT pk_fines PRIMARY KEY id,
	
);

CREATE TABLE Departments(
	abbreviation varchar2(3),
	name varchar2(50),
	
	CONSTRAINT pk_departments PRIMARY KEY abbreviation,
);

CREATE TABLE Courses (
	id number(10),
	name varchar2(50),
	dep_abbreviation varchar2(3),
	
	CONSTRAINT pk_courses PRIMARY KEY (id, dep_abbreviation),
	CONSTRAINT fk_courses_departments FOREIGN KEY (dep_abbreviation) REFERENCES Departments (abbreviation)
);

CREATE TABLE Course_Taken (
	patron_id number(10),
	dep_abbreviation varchar2(3),
	id number(10),
	year number(2),
	semester number(2),
	
	CONSTRAINT pk_courses_taken PRIMARY KEY (patron_id, dep_abbreviation, id),
	CONSTRAINT fk_courses_taken_1 FOREIGN KEY (dep_abbreviation) REFERENCES Courses (abbreviation),
	CONSTRAINT fk_courses_taken_2 FOREIGN KEY (id) REFERENCES Courses (id)
);


CREATE TABLE Authors(
	id varchar2(10),
	name varchar2(50) NOT NULL,
  
	CONSTRAINT pk_authors PRIMARY KEY (id)
);


CREATE TABLE Publications(
	title varchar2(50) NOT NULL,
	id varchar2(10),
	year_of_pub date NOT NULL,
	
	CONSTRAINT pk_publications PRIMARY KEY (id)
	
);


CREATE TABLE Pub_ConferencePapers(
	conf_num varchar2(10),
	confName varchar2(50),
	
	CONSTRAINT pk_pub_conferencepapers PRIMARY KEY (conf_num),
	CONSTRAINT fk_pub_conferencepapers FOREIGN KEY (conf_num) REFERENCES Publications(id)
	
);


CREATE TABLE Pub_Book(
	edition varchar2(5) NOT NULL,
	isbn varchar2(10),
	publisher varchar2(50),
	
	CONSTRAINT pk_pub_book PRIMARY KEY (isbn),
	CONSTRAINT fk_pub_book FOREIGN KEY (isbn) REFERENCES Publications(id)
	
);


CREATE TABLE Pub_Journal(
	issn varchar2(10),
	
	CONSTRAINT pk_pub_journal PRIMARY KEY (issn),
	CONSTRAINT fk_pub_journal FOREIGN KEY (issn) REFERENCES Publications(id)
);

CREATE TABLE Written_by(
	pid varchar2(10),
	aid varchar2(10),
	
	CONSTRAINT fk_written_by_authors FOREIGN KEY (aid) REFERENCES Authors(id),
	CONSTRAINT fk_written_by_publications FOREIGN KEY (pid) REFERENCES Publications(id),
	CONSTRAINT pk_written_by PRIMARY KEY (pid,cid)

);


CREATE TABLE Reservation(
	id number(10) NOT NULL,
	start date NOT NULL,
	end data NOT NULL,

	CONSTRAINT pk_reservation PRIMARY KEY (cid,id,start,end)

);

CREATE TABLE Library(
	id number(10),
	name varchar2(50)
	
	CONSTRAINT pk_library PRIMARY KEY (id)
);

CREATE TABLE Copies(
	id number(10),
	copy_type ENUM("ELECTRONIC","HARD"),
	lib_id number(10),
	
	CONSTRAINT pk_copies PRIMARY KEY (id),
	CONSTRAINT fk_copies_library FOREIGN KEY (id) REFERENCES Library(id),
);

Rooms and cameras are pending

------------------------------------------------------------------------------------------------



drop table Department;

create table Department(
  did number(3),
  dname varchar2(25) NOT NULL,
  constraint pk_dept PRIMARY KEY (did)
);

create table Course(
  cid varchar2(6),
  cname varchar2(25) NOT NULL,
  did number(3) default 0,
  
  constraint fk_course_dept foreign key (did) references Department (did)
  on delete cascade,
  constraint pk_course PRIMARY KEY (cid)
);

insert into Department VALUES (0, 'UN_KNOWN');
insert into Department VALUES (1, 'Computer Science');
insert into Department VALUES (2, 'Electrical Engineering');
insert into Department VALUES (3, 'Mechanical Engineering');
insert into Department VALUES (4, 'Social Science');
insert into Department VALUES (5, 'Recreation and Sports');

insert into Course VALUES ('CSC500','Database',1);
insert into Course VALUES ('CSC501','Operating Systems',1);
insert into Course VALUES ('ECE500','Comp Architcture',2);
insert into Course VALUES ('ECE300','Basic Electric',2);
insert into Course VALUES ('MEC100','Automotive',3);
insert into Course VALUES ('MEC200','Industrial',3);
insert into Course VALUES ('SS222','History',4);
insert into Course VALUES ('SS231','Civilization',4);
insert into Course VALUES ('SS111','Welcome to SS',4);
insert into Course VALUES ('CSC600','Cricket',5);


create table Student(

snumber number(9),
sfname varchar2(20) NOT NULL,
slname varchar2(20) NOT NULL, 
sphNum number(10) NOT NULL,
saltphNum number(10) NOT NULL,
dob date NOT NULL,
nationality varchar2(20) NOT NULL,
did number(3) default 000,
sclassif varchar2(10) default 'B.S',

constraint fk_student_dept foreign key (did) REFERENCES Department (did),
constraint pk_student primary key (snumber)
);

create table Faculty(

fnumber number(9),
fname varchar2(20) NOT NULL,
dob date NOT NULL,
nationality varchar2(20) NOT NULL,
did number(3) default 0,
fcategory varchar2(10) NOT NULL,

constraint fk_faculty_dept foreign key (did) REFERENCES Department (did),
constraint pk_faculty primary key (fnumber)
);

create table Student_Course(
snumber number(9),
cid varchar2(6),

constraint pk_student_course primary key (snumber,cid),
constraint fk_student_course_1 foreign key (cid) references Course (cid) on delete cascade,
constraint fk_student_course_2 foreign key (snumber) references Student (snumber) 
on delete cascade
);

create table Address(
  
  addressid number(10),
  street varchar2(20) not null,
  city varchar2(20) not null,
  postCode number(10) not null,
  snumber number(9) not null,
  
  constraint fk_address_student foreign key (snumber) references Student (snumber)
  on delete cascade,
  constraint pk_address primary key (snumber,addressid)
);


create table Authors(
  aid varchar2(10),
  aname varchar2(50) not null,
  isbn varchar2(25)not null,
  
  constraint fk_authors_books foreign key (isbn) references Books (isbn),
  constraint fk_authors_jour foreign key (isbn) references Journal (isbn),
  constraint pk_authors primary key (aid,isbn)
);

create table ConfAuthors(
  aid varchar2(10),
  aname varchar2(50) not null,
  confNum number(5),
  
  constraint fk_cauthors_conf foreign key (confNum) references Conference (confNum),
  constraint pk_cauthors primary key (aid,confNum)
);


create table Books(
  isbn varchar2(25),
  title varchar2(100) not null,
  edition number(3) not null,
  publisher varchar2(25)not null,
  yop date not null,
  
  constraint pk_books primary key (isbn)
);

create table Journal(
  isbn varchar2(25),
  title varchar2(100) not null,
  yop date not null,
  
  constraint pk_journal primary key (isbn)
);


create table Conference(
  confNum number(5),
  confName varchar2(25) not null,
  title varchar2(100) not null,
  yop date not null,
  
  constraint pk_conference primary key (confNum)
);
