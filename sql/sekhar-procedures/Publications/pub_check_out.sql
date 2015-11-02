create or replace PROCEDURE PUB_CHECK_OUT (patronid in Patron.id%TYPE,cid in Copies.id %TYPE, output_message in out varchar2)
is
  copy_record COPIES%ROWTYPE;
  copy_status COPIES.STATUS%type;
  is_already_checked number(1) := 0;
begin
  select * into copy_record from Copies where id = cid;
  if(SQL%NOTFOUND) then
    output_message := 'That copy is not valid';
  else 
    copy_status := copy_record.status;
    if(copy_status = 'OUT') then
      output_message := 'That copy is not available';
      ADD_TO_PUB_QUEUE(patronid,cid,output_message);
    else
      output_message := 'Copy is available';
      if(is_copy_reserved(cid)=1) then
        if(HAS_STUDENT_TAKEN_COURSE(patronid,cid) = 1) then
          insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,cid,SYSDATE,sysdate + numtodsinterval(4,'hour'),null);
          update copies
            set status = 'OUT' where id = cid;
          commit;
          output_message :=  output_message || '..Checked out the reserved copy for 4 hours';
        else
          output_message :=  output_message || '..This copy is reserved. You cannot take this';
        end if;
      elsif(copy_record.copy_type = 'HARD') then
        update copies
          set status = 'OUT' where id = cid;
          commit;
          
        CASE publication_type(copy_record.pid)
            WHEN 0 THEN
              insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,cid,SYSDATE,sysdate + numtodsinterval(12,'hour'),null);
            WHEN 2 THEN
              insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,cid,SYSDATE,sysdate + numtodsinterval(12,'hour'),null);
            WHEN 1 THEN
              if(PATRON_TYPE(patronid) = 0) then
                insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,cid,SYSDATE,sysdate + numtodsinterval(30,'day'),null);
              else
                insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,cid,SYSDATE,sysdate + numtodsinterval(4,'day'),null);
              end if;
          END case;
          output_message := output_message || '..' || 'Checked out successfully';
      else
          select count(1) into is_already_checked from checks_out where patron_id = patronid and copy_id = cid and act_return_time is null;
          if(is_already_checked = 0) then
            insert into CHECKS_OUT values(checks_out_id.NEXTVAL,patronid,cid,SYSDATE,SYSDATE,null);
            output_message := output_message || '..' || 'Checked out successfully';
          else
            output_message := output_message || '..' || 'You have already checked out this electronic copy';
          end if;
      end if;
    end if;
  end if;
  Exception when no_data_found then
    output_message := output_message || '..' || 'No data found';
end;
