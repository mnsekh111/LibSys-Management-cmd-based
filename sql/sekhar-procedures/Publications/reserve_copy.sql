create or replace function reserve_copy(cpyid in Copies.id%type,facid in Faculty.id%type)
return varchar2
as
  ret_mess varchar2(100);
  faculty_record Faculty%rowtype := null;
  copy_record Copies%rowtype :=null;
begin
  select * into copy_record from Copies where id = cpyid;
  if(copy_record.status = 'IN') then
    if(copy_record.copy_type='HARD') then
      if(publication_type(copy_record.pid) = 1) then
        select * into faculty_record from Faculty where id = facid;
        if(is_copy_reserved(cpyid) = 0) then
          insert into Reservation values(faculty_record.course_id,faculty_record.dept,cpyid,sysdate,ADD_MONTHS(sysdate,4));
          commit;
          ret_mess := 'Copy reserved successfully';
      
        else
          ret_mess := 'Copy is already reserved';
        end if;
      else
        ret_mess := 'Publication and Journals are not for reservation';
        
      end if;
    else
      ret_mess := 'Electronic copies are not for reservation';
    end if;
  else
    ret_mess := 'This copy is not available at the moment.';
  end if;
  return ret_mess;
  
  exception when no_data_found then 
    ret_mess := 'Not Authorized to perform this action or Invalid copy. Not found ';
    return ret_mess;
end;
