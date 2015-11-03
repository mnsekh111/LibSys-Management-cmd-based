create or replace function has_student_taken_course(patid Patron.id%type, cpyid Copies.id%type)
return number
is
  curr_reservation Reservation%rowtype;
  has_taken number(1) :=0;
begin
    select * into curr_reservation from Reservation where copy_id = cpyid and end_time > sysdate;
    select count(1) into has_taken from course_taken where patron_id = patid and dep_abbreviation = curr_reservation.dep_abbreviation
    and id = curr_reservation.course_id;
    return has_taken;
    exception when others then
    return -1;
end; ---

