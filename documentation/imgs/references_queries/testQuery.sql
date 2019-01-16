SELECT IF(MD5("password")="password",'true','false') AS 'Valid';

SELECT LEFT(MD5(NOW()), 18) AS 'Code';

UPDATE test
SET test_id = UPPER(LEFT(MD5(UUID()),10))
WHERE test.test_name = test.test_name;

SELECT 
applicant.applicant_id,
applicant.firstname,
applicant.lastname,
applicant.middlename,
applicant.gender,
applicant.desired_position,
test.test_id,
test.test_name,
test.description,
test.duration
FROM applicant, test,test_examinee
WHERE test_examinee.status='active'
AND applicant.applicant_id = test_examinee.examinee_id
AND test.test_id = test_examinee.exam_id
AND test_examinee.examinee_id = 'APP00002';

UPDATE applicant
SET PASSWORD=MD5(applicant_id);

SELECT IF('b60a7517646dd99bb4c34d7743ba1fbe' = MD5('APP00001'), TRUE, FALSE);