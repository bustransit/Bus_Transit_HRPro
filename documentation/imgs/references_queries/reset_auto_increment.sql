SET @num:=0;
UPDATE USER SET INDEX = @num :=(@num+1);
ALTER TABLE USER AUTO_INCREMENT=1;

UPDATE USER SET position_code = (SELECT position_code FROM POSITION WHERE employee.emp_id=position.rec_id);