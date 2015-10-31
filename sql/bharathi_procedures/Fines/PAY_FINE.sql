CREATE OR REPLACE PROCEDURE PAY_FINE(
  input_id IN VARCHAR2,
  patron_id IN NUMBER,
  result OUT NUMBER
) 
AS 
  table_type VARCHAR2(1);
  fine_id VARCHAR2(10);
  check_flag NUMBER;
BEGIN
  SELECT COUNT(*) INTO check_flag FROM ALL_FINES WHERE ID = input_id AND PATRON_ID = patron_id;
  IF check_flag = 0 THEN
    result := 0;
  ELSE
    table_type := substr(input_id,1,1);
    fine_id := substr(input_id,2);
    --DBMS_OUTPUT.put_line (input_id);
    IF(table_type  = 'C') THEN
      UPDATE CAM_FINES SET STATUS = 'PAID' WHERE ID = fine_id;
      --DBMS_OUTPUT.put_line (fine_id);
    ELSIF(table_type  = 'B') THEN
      UPDATE FINES SET STATUS = 'PAID' WHERE ID = fine_id;
    END IF;
  result := 1;
  END IF;
EXCEPTION
WHEN OTHERS THEN
  result := 0;
END PAY_FINE; ---