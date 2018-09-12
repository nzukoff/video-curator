CREATE TABLE `videos` (
  `id`          INT           NOT NULL  AUTO_INCREMENT,
  `version`     INT           NOT NULL  DEFAULT 0,
  `title`       VARCHAR(255)   NOT NULL,
  `votes`       INT           NULL,
  `link`        VARCHAR(255)   NOT NULL,
  `created`     TIMESTAMP     NOT NULL  DEFAULT NOW(),
  `modified`    TIMESTAMP     NOT NULL  DEFAULT NOW(),
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
