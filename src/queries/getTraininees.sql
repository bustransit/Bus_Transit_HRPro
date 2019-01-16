SELECT employee.firstname, employee.lastname, employee_position.position_name
FROM training, training_participants, employee, employee_position
WHERE training_participants.training_id=2
AND training_participants.emp_id=employee.emp_id
AND employee.position_id=employee_position.position_id
GROUP BY employee.emp_id;