CREATE TYPE role_type AS ENUM ('ADMIN', 'USER', 'GUEST');

ALTER TABLE usuarios ADD COLUMN role role_type;