INSERT INTO authority(name) VALUES('ROLE_ADMIN');
INSERT INTO authority(name) VALUES('ROLE_USER');
INSERT INTO user(created_by, created_date, activated, email, first_name, lang_key, last_name, password, receive_mail, username) VALUES (1, CURRENT_DATE(), true, 'admin@gmail.com', 'Admin', 'en', 'Admin', '$2a$10$3eLqJA5.BhTL05Eh3OqM1.cnR8U9UUGZm3biIeeW7mh9btCvhDtb6', true, 'admin');
INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority(user_id, authority_id) VALUES (1, 2);
INSERT INTO user(created_by, created_date, activated, email, first_name, lang_key, last_name, password, receive_mail, username) VALUES (1, CURRENT_DATE(), true, 'user@gmail.com', 'User', 'en', 'User', '$2a$10$bU0UNcoJ.6qCxIcxORIJ4ubYeWu6uey69Z5MMzV.3OVqQaxKoGqjm', true, 'user');
INSERT INTO user_authority(user_id, authority_id) VALUES (2, 2);

