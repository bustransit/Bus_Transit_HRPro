SELECT
user.username, position.position_name,
UPPER(CONCAT(employee.lastname,', ',employee.firstname,' ',
LEFT(employee.middlename,1))) AS 'Fullname',
department.department_code,
department.department_name
FROM USER, POSITION, employee, department
WHERE
employee.position_code = position.position_code
AND 
department.department_code = position.department_code
AND
user.uli = employee.uli
AND
user.username = 'hiringofficer'
AND user.password = LEFT(MD5('hiringofficer'),18);

SELECT LEFT(MD5('admin'),18);

UPDATE USER SET PASSWORD=MD5(username);

