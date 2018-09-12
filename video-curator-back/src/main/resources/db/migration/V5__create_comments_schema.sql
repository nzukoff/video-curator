CREATE TABLE `comments` (
  `id`        INT           NOT NULL  AUTO_INCREMENT,
  `version`   INT           NOT NULL  DEFAULT 0,
  `text`      VARCHAR(1000) NOT NULL,
  `votes`     INT           NULL,
  `created`   TIMESTAMP     NOT NULL  DEFAULT NOW(),
  `modified`  TIMESTAMP     NOT NULL  DEFAULT NOW(),
  `user_id`   INT           NOT NULL,
  `video_id`  INT           NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user2_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`id`),
  CONSTRAINT `fk_video_id`
  FOREIGN KEY (`video_id`)
  REFERENCES `videos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);