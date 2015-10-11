CREATE TABLE IF NOT EXISTS `Scores` (
  `Team` varchar(45) NOT NULL,
  `Day` varchar(45) NOT NULL,
  `Opponent` varchar(45) NOT NULL,
  `Runs` int(11) NOT NULL,
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;