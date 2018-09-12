use videotest;

set FOREIGN_KEY_CHECKS = 0;
truncate table users;
truncate table videos;
truncate table comments;
set FOREIGN_KEY_CHECKS = 1;

insert into `users` (`username`,`password`) values
  ('cmedford@gmail.com','password'),
  ('azukoff@gmail.com','password'),
  ('pghaderi@gmail.com','password');

insert into `videos` (`title`,`votes`,`link`,`user_id`) values
  ('1612',50,'https://www.youtube.com/watch?v=jRHQPG1xd9o',1),
  ('Flinstones',19,'https://www.youtube.com/watch?v=zua831utwMM',1),
  ('What is Galvanize?',3,'https://www.youtube.com/watch?v=LrKNCs60yVQ',2),
  ('What happens if youre allergic to water?',104,'https://www.youtube.com/watch?v=8aUVN523r_A',2),
  ('Rayna meets a robot',1000,'https://www.youtube.com/watch?v=h1E-FlguwGw',2),
  ('Reggae Shark',35,'https://www.youtube.com/watch?v=A3ytTKZf344',3);

insert into `comments` (`text`,`votes`,`user_id`,`video_id`) values
  ('Dope.',1,2,1),
  ('Incredible',19,3,2),
  ('Whoa. Happy im not allergic to water.',10,1,4),
  ('Reggae shark!',999,1,6),
  ('Cute',6,3,5),
  ('Funky tunes',4,2,1),
  ('Ballin!',0,1,3);
