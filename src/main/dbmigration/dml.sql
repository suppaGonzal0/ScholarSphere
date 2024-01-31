--- password is admin

INSERT INTO users
    (id, created_on, version, username, email, password, first_name, last_name, country, city, bio)
VALUES (nextval('user_sequence'), '2024-01-16 12:22:38.569+06', 0, 'admin', 'admin@gmail.com',
        '$2a$10$B6wv0zh9xET8i7jGOf9/R.KT90UJbPKHrZlsgU7KX/ed0Z0zbyhUi', 'Admin', 'Admin', 'Indonesia', 'Jakarta', NULL),
       (nextval('user_sequence'), '2024-01-16 12:22:38.569+06', 0, 'arthur', 'arthur@gmail.com',
        '$2a$10$B6wv0zh9xET8i7jGOf9/R.KT90UJbPKHrZlsgU7KX/ed0Z0zbyhUi', 'Arthur', 'Morgan', 'Malaysia', 'Penang', NULL);

INSERT INTO authorities
    (email, authority)
VALUES ('admin@gmail.com', 'ROLE_ADMIN'),
       ('arthur@gmail.com', 'ROLE_REGULAR');