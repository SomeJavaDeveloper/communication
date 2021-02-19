insert into usr (id, username, password, active, email, profile_pic, real_name, date_of_birth, city)
values (1, 'admin', 'admin123', true, 'admin123@gmail.com', 'default-profile-icon.png', 'Artyom Kosenko', '12.10.1990', 'Penza'),
       (2, 'user', 'user123', true, 'user123@gmail.com', 'default-profile-icon.png', 'Pasha Alekseev', '23.02.1999', 'Cheboksary');

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');
insert into user_role (user_id, roles)
values (2, 'USER');
