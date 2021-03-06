CREATE TABLE `users` (
  `id`          INT           NOT NULL  AUTO_INCREMENT,
  `version`     INT           NOT NULL  DEFAULT 0,
  `username`    VARCHAR(255)  NOT NULL,
  `password`    VARCHAR(255)  NOT NULL,
  `enabled`     BOOLEAN       NOT NULL  DEFAULT FALSE,
  `created`     TIMESTAMP     NOT NULL  DEFAULT NOW(),
  `modified`    TIMESTAMP     NOT NULL  DEFAULT NOW(),
  PRIMARY KEY (`id`));
