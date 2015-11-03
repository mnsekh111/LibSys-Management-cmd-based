begin
  if(is_copy_reserved(6) = 1) then
    dbms_output.put_line('Copy is reserved');
  else
    dbms_output.put_line('Copy is not reserved');
  end if;
end;

begin
  dbms_output.put_line(RESERVE_COPY(6,1));
end;