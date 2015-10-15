/* Create Database */
CREATE DATABASE  IF NOT EXISTS `csc540`;

/* Create Tables */
DROP TABLE IF EXISTS `Scores`;
CREATE TABLE `Scores` (
  `Team` varchar(45) NOT NULL,
  `Day` varchar(45) NOT NULL,
  `Opponent` varchar(45) NOT NULL,
  `Runs` int(11) NOT NULL,
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/* Insert Data */

INSERT INTO `Scores` VALUES ('Dragons','Sunday','Swallows',4,1),('Tigers','Sunday','Bay Stars',9,2),('Carp','Sunday','Giants',2,3),('Swallows','Sunday','Dragons',7,4),('Bay Stars','Sunday','Tigers',2,5),('Giants','Sunday','Carp',4,6),('Dragons','Monday','Carp',6,7),('Tigers','Monday','Bay Stars',5,8),('Carp','Monday','Dragons',3,9),('Swallows','Monday','Giants',0,10),('Bay Stars','Monday','Tigers',7,11),('Giants','Monday','Swallows',5,12);