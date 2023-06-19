CREATE USER app_test WITH password 'p@ssw0rd';
ALTER ROLE app_test SUPERUSER;
CREATE DATABASE app_test;
GRANT ALL PRIVILEGES ON DATABASE app_test TO app_test;
