DROP SEQUENCE CAM_FINES_SEQ  ;
DROP SEQUENCE REMINDERS_SEQ  ;
DROP SEQUENCE cam_queue_seq  ;
DROP SEQUENCE booked_cams_seq;

DROP PROCEDURE CAMERA_4_CHKOUT;
DROP PROCEDURE CAMERA_AVAILABLE         ;
DROP PROCEDURE CAMERA_BOOK              ;
DROP PROCEDURE CAMERA_CHECKED_OUT       ;
DROP PROCEDURE CAMERA_FINES             ;
DROP PROCEDURE CAMERA_REQ               ;
DROP PROCEDURE CAMERA_RETURN            ;
DROP PROCEDURE CAMERA_SEND_ALERT_1      ;
DROP PROCEDURE CAMERA_SEND_ALERT_2      ;
DROP PROCEDURE FINES_MONTHLY_OUTSTANDING;
DROP PROCEDURE PAY_ALL_FINES            ;
DROP PROCEDURE PAY_FINE                 ;
DROP PROCEDURE ROOMS_INVALIDATE;
DROP TABLE Cam_Fines PURGE;
DROP TABLE Booked_Cams PURGE;
DROP TABLE Booked PURGE;
DROP TABLE Fines PURGE;
DROP TABLE CHECKS_OUT PURGE;

DROP TABLE Rooms PURGE;
DROP TABLE Reservation PURGE;
DROP TABLE Copies PURGE;

DROP TABLE Written_by PURGE;
DROP TABLE Pub_Journal PURGE;
DROP TABLE Pub_Book PURGE;
DROP TABLE Pub_ConferencePapers PURGE;

DROP TABLE Authors PURGE;
DROP TABLE Course_Taken PURGE;
DROP TABLE Reminders PURGE;
DROP TABLE Faculty PURGE;
DROP TABLE Student PURGE;
DROP TABLE Courses PURGE;
DROP TABLE Departments PURGE;
DROP TABLE Faculty_Category PURGE;
DROP TABLE CAM_QUEUE PURGE;
DROP TABLE Pub_queue PURGE;
DROP TABLE Copies PURGE;
DROP TABLE Publications PURGE;
DROP TABLE Patron PURGE;
DROP TABLE Degree_Program PURGE;
DROP TABLE Nationality PURGE;
DROP TABLE Cameras PURGE;
DROP TABLE Library PURGE;
