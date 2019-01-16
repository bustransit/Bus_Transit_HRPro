
## Generat Unique random character with length of 10
SELECT LEFT(MD5(NOW()), 10);

## hashing String
SELECT MD5("password");

## compareing hashed String to md5()
SELECT IF("5f4dcc3b5aa765d61d8327deb882cf99"=MD5("passwor"), 'true', 'false') AS 'Valid';