CREATE TABLE Nationality (
    country_name varchar2(50) NOT NULL,

    CONSTRAINT pk_country_name PRIMARY KEY (country_name)
);

CREATE TABLE Degree_Program (
    program varchar2(25) NOT NULL,

    CONSTRAINT pk_degree_program PRIMARY KEY (program)
);

CREATE TABLE Faculty_Category (
    category varchar2(25) NOT NULL,

    CONSTRAINT pk_faculty_category PRIMARY KEY (category)
);

CREATE TABLE Patron(
    fname varchar2(25) NOT NULL,
    lname varchar2(25) NOT NULL,
    id number(10) NOT NULL,
    status varchar2(25) NOT NULL,
    country_name varchar2(50) NOT NULL,

    CONSTRAINT fk_patron_nationality FOREIGN KEY (country_name) REFERENCES Nationality (country_name),
    CONSTRAINT pk_patron PRIMARY KEY (id),
    CONSTRAINT chk_status CHECK(status IN ('GOOD','BAD'))
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
    year number(2),

    CONSTRAINT pk_student PRIMARY KEY (id),
    CONSTRAINT fk_student FOREIGN KEY (id) REFERENCES Patron (id),
    CONSTRAINT fk_student_program FOREIGN KEY (program) REFERENCES Degree_Program (program)
);

CREATE TABLE Faculty (
    category varchar2(25) NOT NULL,
    id number(10) NOT NULL,

    CONSTRAINT pk_faculty PRIMARY KEY (id),
    CONSTRAINT fk_faculty FOREIGN KEY (id) REFERENCES Patron (id),
    CONSTRAINT fk_faculty_category FOREIGN KEY (category) REFERENCES Faculty_Category (category)
);

CREATE TABLE Reminders (
    id number(10),
    message varchar2(500) NOT NULL,
    time_sent date NOT NULL,
    patron_id  number(20) NOT NULL,

    CONSTRAINT pk_reminders PRIMARY KEY (id),
    CONSTRAINT fk_reminders_patrons FOREIGN KEY (patron_id) REFERENCES Patron (id)
);



CREATE TABLE Departments(
    abbreviation varchar2(3),
    name varchar2(50),

    CONSTRAINT pk_departments PRIMARY KEY (abbreviation)
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
    CONSTRAINT fk_courses_taken_1 FOREIGN KEY (id, dep_abbreviation) REFERENCES Courses (id, dep_abbreviation)
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
    CONSTRAINT pk_written_by PRIMARY KEY (pid,aid)
);

CREATE TABLE Library(
    id number(10),
    name varchar2(50),

    CONSTRAINT pk_library PRIMARY KEY (id)
);

CREATE TABLE Copies(
    id number(10),
    copy_type varchar2(25),
    lib_id number(10),

    CONSTRAINT pk_copies PRIMARY KEY (id),
    CONSTRAINT fk_copies_library FOREIGN KEY (id) REFERENCES Library(id),
    CONSTRAINT chk_copy_type CHECK(copy_type IN ('ELECTRONIC','HARD'))
);


CREATE TABLE Reservation(
    course_id number(10),
    dep_abbreviation varchar2(3),
    copy_id number(10),
    start_time date,
    end_time date,

    CONSTRAINT pk_reservation PRIMARY KEY (course_id, copy_id, dep_abbreviation, start_time, end_time),
    CONSTRAINT fk_reserv_dep_abbrev FOREIGN KEY (course_id, dep_abbreviation) REFERENCES Courses(id, dep_abbreviation),
    CONSTRAINT fk_reservation_copy FOREIGN KEY (copy_id) REFERENCES Copies(id)


);

CREATE TABLE Rooms(
    room_number number(10),
    capacity number(3) NOT NULL,
    library_id number(10) NOT NULL,
    floor_no number(2) NOT NULL,
    room_type varchar2(25),

    CONSTRAINT pk_rooms PRIMARY KEY (room_number),
    CONSTRAINT fk_rooms_library FOREIGN KEY (library_id) REFERENCES Library(id),
    CONSTRAINT chk_room_type CHECK(room_type IN ('CONF','STUDY'))

);

CREATE TABLE Cameras(
    id number(10),
    make varchar2(20),
    model VARCHAR2(20) NOT NULL,
    config VARCHAR2(20),
    lid VARCHAR(20),
    memory VARCHAR2(20),
    status number(1),

    CONSTRAINT pk_cameras PRIMARY KEY (id)

);

CREATE TABLE CHECKS_OUT(
    id number(10),
    patron_id number(10),
    copy_id number(10),
    start_time date,
    end_time date,

    CONSTRAINT pk_checks_out PRIMARY KEY (id),
    CONSTRAINT fk_checks_out_patron FOREIGN KEY (patron_id) REFERENCES Patron(id),
    CONSTRAINT fk_checks_out_copies FOREIGN KEY (copy_id) REFERENCES Copies(id)
);

CREATE TABLE Fines (
    id number(10),
    checks_out_id number(10),
    amount number(10) NOT NULL,
    status varchar2(25),
    fine_date date,

    CONSTRAINT pk_fines PRIMARY KEY (id),
    CONSTRAINT fk_fines_checks_out FOREIGN KEY (checks_out_id) REFERENCES CHECKS_OUT(id),
    CONSTRAINT chk_fine_status CHECK(status IN ('PAID','UNPAID'))
);

CREATE TABLE Booked(
    patron_id number(10),
    room_number number(10),
    start_time date,
    end_time date,
    checked_out date,
    checked_in date,

    CONSTRAINT pk_booked PRIMARY KEY (patron_id, room_number, start_time, end_time),
    CONSTRAINT fk_booked_patron FOREIGN KEY (patron_id) REFERENCES Patron(id),
    CONSTRAINT fk_booked_rooms FOREIGN KEY (room_number) REFERENCES Rooms(room_number)

);


CREATE TABLE Booked_Cams(
    id number(10),
    cam_id number(10),
    patron_id number(10),
    start_time date,
    end_time date,

    CONSTRAINT pk_booked_cams PRIMARY KEY (id),
    CONSTRAINT fk_booked_cams_patron FOREIGN KEY (patron_id) REFERENCES Patron(id),
    CONSTRAINT fk_booked_cams_cameras FOREIGN KEY (cam_id) REFERENCES Cameras(id)
);


CREATE TABLE Cam_Fines (
    id number(10),
    booked_cam_id number(10),
    amount number(10) NOT NULL,
    status varchar2(25),
    fine_date date,

    CONSTRAINT pk_cam_fines PRIMARY KEY (id),
    CONSTRAINT fk_fines_booked_cams FOREIGN KEY (booked_cam_id) REFERENCES Booked_Cams(id),
    CONSTRAINT chk_cam_fine_status CHECK(status IN ('PAID','UNPAID'))
);

