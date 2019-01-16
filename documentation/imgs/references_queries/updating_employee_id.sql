UPDATE employee
SET uli = CONCAT(position_code,'-',LEFT(lastname,1),LEFT(firstname,1),LEFT(middlename,1),'-',DATE_FORMAT(birthdate, "%m%d%Y"),'-',emp_id)
WHERE employee.emp_id = employee.emp_id;

UPDATE USER
SET uli = (SELECT employee.uli FROM employee WHERE employee.emp_id = user.emp_id);