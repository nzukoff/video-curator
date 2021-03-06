CREATE TABLE `roles` (
  `id`        INT                   NOT NULL  AUTO_INCREMENT,
  `version`   INT                   NOT NULL  DEFAULT 0,
  `role`      ENUM('USER', 'ADMIN') NOT NULL,
  `created`   TIMESTAMP             NOT NULL  DEFAULT NOW(),
  `modified`  TIMESTAMP             NOT NULL  DEFAULT NOW(),
  PRIMARY KEY (`id`));
