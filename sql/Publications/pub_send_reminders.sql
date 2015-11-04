create or replace procedure pub_send_reminders
as
  cursor checks_out_cur is select * from Checks_out where act_return_time is null and start_time <> end_time;
  checks_out_rec checks_out%rowtype;
  time_diff number(5):=0;
  pub_id Publications.id%type;
  pub_title publications.title%type;
  
begin
  if not checks_out_cur%isopen then
    open checks_out_cur;
  end if;
  loop 
    fetch checks_out_cur into checks_out_rec;
    exit when checks_out_cur%notfound;
    time_diff := TRUNC(checks_out_rec.end_time) - TRUNC(sysdate);
    select pid into pub_id from Copies where id = checks_out_rec.copy_id;
    select title into pub_title from Publications where id = pub_id;
    
    case 
      when (time_diff = 3 or time_diff=1) then
        insert into reminders values(Reminders_seq.nextval,pub_title || '('||checks_out_rec.copy_id||')'||' is due on '||checks_out_rec.end_time,SYSDATE,checks_out_rec.patron_id);
      when (time_diff = -30) then
        insert into reminders values(Reminders_seq.nextval,pub_title || '('||checks_out_rec.copy_id||')'||' was due on '||checks_out_rec.end_time,SYSDATE,checks_out_rec.patron_id);
      when (time_diff = -90) then
        insert into reminders values(Reminders_seq.nextval,pub_title || '('||checks_out_rec.copy_id||')'||' was due on '||checks_out_rec.end_time,SYSDATE,checks_out_rec.patron_id);
        update Patron
          set STATUS = 'BAD'
            where id = checks_out_rec.patron_id;
        commit;
    end case;
  end loop;
end; ---