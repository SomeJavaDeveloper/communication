delete from user_role;
delete from usr;

insert into usr (id, username, password, active, email, profile_pic, real_name, date_of_birth, city)
values (1, 'admin', '$2a$08$VE4y9k.Chi.QmWWDVBVEE.o3VO2qJddfbUrtDygeXywvDZaBfd4ce',
        true, 'admin123@gmail.com', 'default-profile-icon.png', 'Artyom Kosenko', '1990-10-12', 'Penza'),
       (2, 'user', '$2a$08$3OVVF0fxn/zHgUOcOXalL.7FfHD7/xBsvOFs3bjGz9v3NV.bJYQiC',
        true, 'user123@gmail.com', 'default-profile-icon.png', 'Pasha Alekseev', '1999-02-23', 'Cheboksary');

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN');
