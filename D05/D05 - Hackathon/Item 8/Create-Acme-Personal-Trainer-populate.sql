start transaction;

create database `Acme-Personal-Trainer`;

use `Acme-Personal-Trainer`;

create user 'acme-user'@'%' 
	identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' 
	identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Acme-Personal-Trainer`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Personal-Trainer`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Personal-Trainer
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  UNIQUE KEY `UK_jj3mmcc0vjobqibj67dvuwihk` (`email`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (13612,0,'Calle Admin 1','administrator1@gmail.com','\0','','Admin1','630187654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','administrator',13586),(13613,0,'Calle Admin 2','administrator2@gmail.com','\0','','Admin2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','administrator',13587);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `registered_moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `credit_card` int(11) NOT NULL,
  `customer` int(11) NOT NULL,
  `working_out` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_mplgstawjtisnunfxy9reypf` (`working_out`,`status`),
  KEY `UK_i0vm4qm2ngmxkvuijmgw1aouy` (`working_out`,`customer`),
  KEY `UK_6ibajsxyejfoefrcm6y95jcev` (`customer`,`status`),
  KEY `UK_rx0g1xv7cy64i4jtf4nw3n9y0` (`customer`,`working_out`,`status`),
  KEY `FK_qrtc8d7gr5htxb67vvyl92du7` (`credit_card`),
  CONSTRAINT `FK_nj8xq0h2rhmr128q6gru6401d` FOREIGN KEY (`working_out`) REFERENCES `working_out` (`id`),
  CONSTRAINT `FK_hkb9gl8ieyivrjqcuhm22axea` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_qrtc8d7gr5htxb67vvyl92du7` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (13922,0,'comment','2019-03-01 10:10:00','ACCEPTED',13902,13676,13779),(13923,0,'comment','2019-03-01 10:10:00','ACCEPTED',13905,13678,13780),(13924,0,'comment','2019-03-01 10:10:00','REJECTED',13906,13679,13781),(13925,0,'comment','2019-03-01 10:10:00','PENDING',13907,13680,13782),(13926,0,'comment','2019-03-01 10:10:00','PENDING',13908,13681,13782),(13927,0,'comment','2019-03-01 10:10:00','PENDING',13905,13678,13782);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_final_mode` bit(1) NOT NULL,
  `published_moment` datetime DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `nutritionist` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_cc0n0t4icsjpt38sigw7j4o1v` (`is_final_mode`),
  KEY `FK_1uiusc65g8ofp9pi1ldxshfoq` (`nutritionist`),
  CONSTRAINT `FK_1uiusc65g8ofp9pi1ldxshfoq` FOREIGN KEY (`nutritionist`) REFERENCES `nutritionist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (13788,0,'Description article1','','2017-03-07 14:00:00','sport','article1',13706),(13789,0,'Description article2','\0','2017-03-07 14:00:00','sport','article2',13706),(13790,0,'Description article3','','2017-03-07 14:00:00','sport','article3',13707),(13791,0,'Description article4','','2017-03-07 14:00:00','sport','article4',13708),(13792,0,'Description article5','','2017-03-07 14:00:00','sport','article5',13709),(13793,0,'Description article6','','2017-03-07 14:00:00','sport','article6',13710);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `auditor` int(11) NOT NULL,
  `curriculum` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kttb8tgj9g4alpumgwjquvgse` (`auditor`,`curriculum`),
  KEY `FK_6fhv9tygu2wipeu8egd1qcdeh` (`curriculum`),
  CONSTRAINT `FK_6fhv9tygu2wipeu8egd1qcdeh` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_3m6p53wfvy7kcl4f4fvphkh9h` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (13928,0,'http://www.attachment.es','Description audit1','2019-01-01 10:10:00','Audit1',13614,13909),(13929,0,'http://www.attachment.es','Description audit1','2019-01-01 10:10:00','Audit2',13614,13910),(13930,0,'http://www.attachment.es','Description audit1','2019-01-01 10:10:00','Audit3',13615,13910),(13931,0,'http://www.attachment.es','Description audit1','2019-01-01 10:10:00','Audit4',13616,13911),(13932,0,'http://www.attachment.es','Description audit1','2019-01-01 10:10:00','Audit5',13617,13912),(13933,0,'http://www.attachment.es','Description audit1','2019-01-01 10:10:00','Audit6',13615,13909);
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  UNIQUE KEY `UK_lmcp5r2bol31t270dvfqypbmk` (`email`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (13614,0,'Calle Auditor 1','auditor1@gmail.com','\0','','Auditor1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',13600),(13615,0,'Calle Auditor 2','auditor2@gmail.com','\0','','Auditor2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',13601),(13616,0,'Calle Auditor 2','auditor3@gmail.com','\0','','Auditor2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',13602),(13617,0,'Calle Auditor 4','auditor4@gmail.com','\0','','Auditor4','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',13603),(13618,0,'Calle Auditor 5','auditor5@gmail.com','\0','','Auditor5','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',13604);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `box`
--

DROP TABLE IF EXISTS `box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `box` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_system_box` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r97hft7mf057u395h2vpulrno` (`name`,`parent`),
  KEY `UK_5e9rnbc6727egwfcsvbl1le8f` (`actor`,`parent`),
  KEY `UK_l2sg19w6kciy8a6jdrdkjprur` (`actor`,`name`,`parent`),
  KEY `UK_l5g0jmje8o7jspo3o3krds6c0` (`actor`,`name`,`is_system_box`),
  KEY `FK_2byqkm71y34wbwpwr7s5m0enc` (`parent`),
  CONSTRAINT `FK_2byqkm71y34wbwpwr7s5m0enc` FOREIGN KEY (`parent`) REFERENCES `box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `box`
--

LOCK TABLES `box` WRITE;
/*!40000 ALTER TABLE `box` DISABLE KEYS */;
INSERT INTO `box` VALUES (13619,0,'','in box',13612,NULL),(13620,0,'','out box',13612,NULL),(13621,0,'','trash box',13612,NULL),(13622,0,'','spam box',13612,NULL),(13623,0,'','notification box',13612,NULL),(13624,0,'','in box',13613,NULL),(13625,0,'','out box',13613,NULL),(13626,0,'','trash box',13613,NULL),(13627,0,'','spam box',13613,NULL),(13628,0,'','notification box',13613,NULL),(13629,0,'','in box',13614,NULL),(13630,0,'','out box',13614,NULL),(13631,0,'','trash box',13614,NULL),(13632,0,'','spam box',13614,NULL),(13633,0,'','notification box',13614,NULL),(13634,0,'\0','auditor box',13614,NULL),(13635,0,'','in box',13615,NULL),(13636,0,'','out box',13615,NULL),(13637,0,'','trash box',13615,NULL),(13638,0,'','spam box',13615,NULL),(13639,0,'','notification box',13615,NULL),(13640,0,'\0','nueva box',13615,NULL),(13641,0,'','in box',13616,NULL),(13642,0,'','out box',13616,NULL),(13643,0,'','trash box',13616,NULL),(13644,0,'','spam box',13616,NULL),(13645,0,'','notification box',13616,NULL),(13646,0,'','in box',13617,NULL),(13647,0,'','out box',13617,NULL),(13648,0,'','trash box',13617,NULL),(13649,0,'','spam box',13617,NULL),(13650,0,'','notification box',13617,NULL),(13651,0,'','in box',13618,NULL),(13652,0,'','out box',13618,NULL),(13653,0,'','trash box',13618,NULL),(13654,0,'','spam box',13618,NULL),(13655,0,'','notification box',13618,NULL),(13794,0,'','in box',13676,NULL),(13795,0,'','out box',13676,NULL),(13796,0,'','trash box',13676,NULL),(13797,0,'','spam box',13676,NULL),(13798,0,'','notification box',13676,NULL),(13799,0,'\0','nueva box',13676,NULL),(13800,0,'','in box',13677,NULL),(13801,0,'','out box',13677,NULL),(13802,0,'','trash box',13677,NULL),(13803,0,'','spam box',13677,NULL),(13804,0,'','notification box',13677,NULL),(13805,0,'\0','nueva box',13677,NULL),(13806,0,'','in box',13678,NULL),(13807,0,'','out box',13678,NULL),(13808,0,'','trash box',13678,NULL),(13809,0,'','spam box',13678,NULL),(13810,0,'','notification box',13678,NULL),(13811,0,'','in box',13679,NULL),(13812,0,'','out box',13679,NULL),(13813,0,'','trash box',13679,NULL),(13814,0,'','spam box',13679,NULL),(13815,0,'','notification box',13679,NULL),(13816,0,'','in box',13680,NULL),(13817,0,'','out box',13680,NULL),(13818,0,'','trash box',13680,NULL),(13819,0,'','spam box',13680,NULL),(13820,0,'','notification box',13680,NULL),(13821,0,'','in box',13681,NULL),(13822,0,'','out box',13681,NULL),(13823,0,'','trash box',13681,NULL),(13824,0,'','spam box',13681,NULL),(13825,0,'','notification box',13681,NULL),(13826,0,'','in box',13682,NULL),(13827,0,'','out box',13682,NULL),(13828,0,'','trash box',13682,NULL),(13829,0,'','spam box',13682,NULL),(13830,0,'','notification box',13682,NULL),(13831,0,'','in box',13706,NULL),(13832,0,'','out box',13706,NULL),(13833,0,'','trash box',13706,NULL),(13834,0,'','spam box',13706,NULL),(13835,0,'','notification box',13706,NULL),(13836,0,'\0','nueva box',13706,NULL),(13837,0,'','in box',13707,NULL),(13838,0,'','out box',13707,NULL),(13839,0,'','trash box',13707,NULL),(13840,0,'','spam box',13707,NULL),(13841,0,'','notification box',13707,NULL),(13842,0,'\0','nueva box',13707,NULL),(13843,0,'','in box',13708,NULL),(13844,0,'','out box',13708,NULL),(13845,0,'','trash box',13708,NULL),(13846,0,'','spam box',13708,NULL),(13847,0,'','notification box',13708,NULL),(13848,0,'','in box',13709,NULL),(13849,0,'','out box',13709,NULL),(13850,0,'','trash box',13709,NULL),(13851,0,'','spam box',13709,NULL),(13852,0,'','notification box',13709,NULL),(13853,0,'','in box',13710,NULL),(13854,0,'','out box',13710,NULL),(13855,0,'','trash box',13710,NULL),(13856,0,'','spam box',13710,NULL),(13857,0,'','notification box',13710,NULL),(13858,0,'','in box',13772,NULL),(13859,0,'','out box',13772,NULL),(13860,0,'','trash box',13772,NULL),(13861,0,'','spam box',13772,NULL),(13862,0,'','notification box',13772,NULL),(13863,0,'\0','nueva box',13772,NULL),(13864,0,'','in box',13773,NULL),(13865,0,'','out box',13773,NULL),(13866,0,'','trash box',13773,NULL),(13867,0,'','spam box',13773,NULL),(13868,0,'','notification box',13773,NULL),(13869,0,'\0','nueva box',13773,NULL),(13870,0,'','in box',13774,NULL),(13871,0,'','out box',13774,NULL),(13872,0,'','trash box',13774,NULL),(13873,0,'','spam box',13774,NULL),(13874,0,'','notification box',13774,NULL),(13875,0,'','in box',13775,NULL),(13876,0,'','out box',13775,NULL),(13877,0,'','trash box',13775,NULL),(13878,0,'','spam box',13775,NULL),(13879,0,'','notification box',13775,NULL),(13880,0,'','in box',13776,NULL),(13881,0,'','out box',13776,NULL),(13882,0,'','trash box',13776,NULL),(13883,0,'','spam box',13776,NULL),(13884,0,'','notification box',13776,NULL),(13885,0,'','in box',13777,NULL),(13886,0,'','out box',13777,NULL),(13887,0,'','trash box',13777,NULL),(13888,0,'','spam box',13777,NULL),(13889,0,'','notification box',13777,NULL),(13890,0,'','in box',13778,NULL),(13891,0,'','out box',13778,NULL),(13892,0,'','trash box',13778,NULL),(13893,0,'','spam box',13778,NULL),(13894,0,'','notification box',13778,NULL);
/*!40000 ALTER TABLE `box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `box_messages`
--

DROP TABLE IF EXISTS `box_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `box_messages` (
  `box` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  KEY `FK_acfjrqu1jeixjmv14c0386o0s` (`messages`),
  KEY `FK_e6boieojekgfg919on0dci4na` (`box`),
  CONSTRAINT `FK_e6boieojekgfg919on0dci4na` FOREIGN KEY (`box`) REFERENCES `box` (`id`),
  CONSTRAINT `FK_acfjrqu1jeixjmv14c0386o0s` FOREIGN KEY (`messages`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `box_messages`
--

LOCK TABLES `box_messages` WRITE;
/*!40000 ALTER TABLE `box_messages` DISABLE KEYS */;
INSERT INTO `box_messages` VALUES (13795,13690),(13801,13691),(13807,13692),(13812,13693),(13817,13694),(13822,13695),(13858,13690),(13858,13691),(13858,13692),(13858,13693),(13858,13694),(13858,13695),(13864,13690),(13864,13691),(13864,13692),(13864,13693),(13864,13694),(13864,13695),(13870,13690),(13870,13691),(13870,13692),(13870,13693),(13870,13694),(13870,13695),(13875,13690),(13875,13691),(13875,13692),(13875,13693),(13875,13694),(13875,13695),(13880,13690),(13880,13691),(13880,13692),(13880,13693),(13880,13694),(13880,13695);
/*!40000 ALTER TABLE `box_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (13656,0,'Active Recovery,Recuperación activa'),(13657,0,'Aerobic Exercise,Ejercicio aeróbico'),(13658,0,'Anaerobic Exercise,Ejercicio anaeróbico'),(13659,0,'Boot Camp,Campo de entrenamiento'),(13660,0,'Circuit,Circuito'),(13661,0,'Compound Exercises,Ejercicios compuestos'),(13662,0,'Cool-Down,Enfriamiento '),(13663,0,'Cross-Training,Entrenamiento combinado'),(13664,0,'DOMS,DMAT'),(13665,0,'Dynamic Warm-Up,Calentamiento dinámmico'),(13666,0,'Foam Rolling,Rodadura de espuma'),(13667,0,'Functional Moves,Movimientos funcionales'),(13668,0,'Heart Rate Zones,Rango de ritmo cardiaco'),(13669,0,'HIIT,Entrenamiento en periodos de alta intensidad'),(13670,0,'Interval Training,Entrenamiento de intervalo'),(13671,0,'Isometrics,Isométricos'),(13672,0,'Plyometrics,Pliometría'),(13673,0,'Resistance,Resistencia'),(13674,0,'Strength Training,Entrenamiento de fuerza'),(13675,0,'Tabata,Tabata');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `publication_moment` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `article` int(11) NOT NULL,
  `customer` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sk96iugwwqighdyu2nq47bwyg` (`article`),
  KEY `FK_i8bfsb8s3enltk34bt2srpuk7` (`customer`),
  CONSTRAINT `FK_i8bfsb8s3enltk34bt2srpuk7` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_sk96iugwwqighdyu2nq47bwyg` FOREIGN KEY (`article`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (13895,0,'2017-03-08 10:00:00','comment1',13788,13676),(13896,0,'2017-03-08 10:00:00','comment2',13788,13677),(13897,0,'2017-03-08 10:00:00','comment3',13790,13678),(13898,0,'2017-04-08 10:00:00','comment4',13791,13679),(13899,0,'2017-05-08 10:00:00','comment5',13792,13680),(13900,0,'2017-06-08 10:00:00','comment6',13788,13681),(13901,0,'2017-07-08 10:00:00','comment7',13790,13682);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `customer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t428d82fusxsjpa9v74qwgbcw` (`customer`),
  CONSTRAINT `FK_t428d82fusxsjpa9v74qwgbcw` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES (13902,0,'VISA',724,'08','21','holderName1','38353348140483',13676),(13903,0,'VISA',587,'10','18','holderName2','4716895067094219',13676),(13904,0,'VISA',587,'03','22','holderName3','4389142361978458',13676),(13905,0,'VISA',147,'02','21','holderName4','4916210851536995',13678),(13906,0,'MCARD',258,'01','20','holderName5','4539294619605521',13679),(13907,0,'MCARD',258,'02','22','holderName6','4024007150182987',13680),(13908,0,'MCARD',254,'02','21','holderName7','4556001181801737',13681);
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `personal_record` int(11) NOT NULL,
  `trainer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_of5n83aytom6e52721o6k6ae` (`personal_record`),
  UNIQUE KEY `UK_b9x8a473n5ej3droxnltmxqpk` (`trainer`),
  UNIQUE KEY `UK_3ai7h3tynp97g8r0g93r84m8w` (`ticker`),
  CONSTRAINT `FK_b9x8a473n5ej3droxnltmxqpk` FOREIGN KEY (`trainer`) REFERENCES `trainer` (`id`),
  CONSTRAINT `FK_of5n83aytom6e52721o6k6ae` FOREIGN KEY (`personal_record`) REFERENCES `personal_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
INSERT INTO `curriculum` VALUES (13909,0,'474712-ASDFGH',13711,13772),(13910,0,'470212-ASDFGH',13712,13773),(13911,0,'170092-ASDFGH',13713,13774),(13912,0,'175412-ASDFGH',13714,13775),(13913,0,'154212-ASDFGH',13715,13776);
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_education_records`
--

DROP TABLE IF EXISTS `curriculum_education_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_education_records` (
  `curriculum` int(11) NOT NULL,
  `education_records` int(11) NOT NULL,
  UNIQUE KEY `UK_mglbvuj28iptdf2n2wliy5dne` (`education_records`),
  KEY `FK_f6du7wj59ct8k4fkxo6p6avc` (`curriculum`),
  CONSTRAINT `FK_f6du7wj59ct8k4fkxo6p6avc` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_mglbvuj28iptdf2n2wliy5dne` FOREIGN KEY (`education_records`) REFERENCES `education_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_education_records`
--

LOCK TABLES `curriculum_education_records` WRITE;
/*!40000 ALTER TABLE `curriculum_education_records` DISABLE KEYS */;
INSERT INTO `curriculum_education_records` VALUES (13909,13684),(13910,13685),(13911,13686);
/*!40000 ALTER TABLE `curriculum_education_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_endorser_records`
--

DROP TABLE IF EXISTS `curriculum_endorser_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_endorser_records` (
  `curriculum` int(11) NOT NULL,
  `endorser_records` int(11) NOT NULL,
  UNIQUE KEY `UK_pnjnunpdx94xdol4lxbq5aphh` (`endorser_records`),
  KEY `FK_lb4478p6kh3k7ps8fbdiqvds` (`curriculum`),
  CONSTRAINT `FK_lb4478p6kh3k7ps8fbdiqvds` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_pnjnunpdx94xdol4lxbq5aphh` FOREIGN KEY (`endorser_records`) REFERENCES `endorser_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_endorser_records`
--

LOCK TABLES `curriculum_endorser_records` WRITE;
/*!40000 ALTER TABLE `curriculum_endorser_records` DISABLE KEYS */;
INSERT INTO `curriculum_endorser_records` VALUES (13909,13687),(13910,13688),(13911,13689);
/*!40000 ALTER TABLE `curriculum_endorser_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneous_records`
--

DROP TABLE IF EXISTS `curriculum_miscellaneous_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneous_records` (
  `curriculum` int(11) NOT NULL,
  `miscellaneous_records` int(11) NOT NULL,
  UNIQUE KEY `UK_hbex6yqhywe93w3clw8y1od2q` (`miscellaneous_records`),
  KEY `FK_fxsf5ohw20jbm0wuny6j1nnc9` (`curriculum`),
  CONSTRAINT `FK_fxsf5ohw20jbm0wuny6j1nnc9` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_hbex6yqhywe93w3clw8y1od2q` FOREIGN KEY (`miscellaneous_records`) REFERENCES `miscellaneous_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneous_records`
--

LOCK TABLES `curriculum_miscellaneous_records` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneous_records` DISABLE KEYS */;
INSERT INTO `curriculum_miscellaneous_records` VALUES (13909,13703),(13910,13704),(13911,13705);
/*!40000 ALTER TABLE `curriculum_miscellaneous_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_professional_records`
--

DROP TABLE IF EXISTS `curriculum_professional_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_professional_records` (
  `curriculum` int(11) NOT NULL,
  `professional_records` int(11) NOT NULL,
  UNIQUE KEY `UK_7354sjhp7ih49qku2slvgb166` (`professional_records`),
  KEY `FK_amicw5c3dbgqi4vafcy7wi02i` (`curriculum`),
  CONSTRAINT `FK_amicw5c3dbgqi4vafcy7wi02i` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_7354sjhp7ih49qku2slvgb166` FOREIGN KEY (`professional_records`) REFERENCES `professional_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_professional_records`
--

LOCK TABLES `curriculum_professional_records` WRITE;
/*!40000 ALTER TABLE `curriculum_professional_records` DISABLE KEYS */;
INSERT INTO `curriculum_professional_records` VALUES (13909,13716),(13910,13717),(13911,13718);
/*!40000 ALTER TABLE `curriculum_professional_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `is_premium` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mbvdes9ypo1yu76so76owiyqx` (`user_account`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`),
  KEY `UK_16yv24j6j3um1d9w171aklkff` (`is_premium`),
  CONSTRAINT `FK_mbvdes9ypo1yu76so76owiyqx` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (13676,0,'Calle Customer 1','customer1@gmail.com','\0','','Customer1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13588,''),(13677,0,'Calle Customer 2','customer2@gmail.com','\0','','Customer2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13589,'\0'),(13678,0,'Calle Customer 3','customer3@gmail.com','\0','','Customer3','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13590,'\0'),(13679,0,'Calle Customer 4','customer4@gmail.com','\0','','Customer4','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13591,'\0'),(13680,0,'Calle Customer 5','customer5@gmail.com','\0','','Customer5','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13592,'\0'),(13681,0,'Calle Customer 6','customer6@gmail.com','\0','','Customer6','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13593,'\0'),(13682,0,'Calle Customer 7','customer7@gmail.com','\0','','Customer7','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',13594,'\0');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation`
--

DROP TABLE IF EXISTS `customisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `credit_card_makes` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `negative_words` varchar(255) DEFAULT NULL,
  `number_results` int(11) NOT NULL,
  `positive_words` varchar(255) DEFAULT NULL,
  `premium_amount` double NOT NULL,
  `priorities` varchar(255) DEFAULT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  `threshold` double NOT NULL,
  `time_results` int(11) NOT NULL,
  `welcome_message_en` varchar(255) DEFAULT NULL,
  `welcome_message_es` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation`
--

LOCK TABLES `customisation` WRITE;
/*!40000 ALTER TABLE `customisation` DISABLE KEYS */;
INSERT INTO `customisation` VALUES (13683,0,47,'https://i.ibb.co/dLs9pVS/banner.png','+34','VISA,MASTER,DINNERS,AMEX','Acme Personal Trainer','not,bad,horrible,average,disaster,no,mal,mediocre,desastre',10,'good,fantastic,excellent,great,amazing,terrific,beautiful,bien,fantastico,excelente,genial,increible,terrorifico,hermosos',200,'HIGH,NEUTRAL,LOW','sex,viagra,cialis,one million,you\'ve been selected,Nigeria,sexo,un millon,ha sido seleccionado',-0.5,1,'Welcome to Acme Personal Trainer! Price, quality, and trust in a single place','¡Bienvenidos a Acme Personal Trainer! Precio, calidad y confianza en el mismo sitio');
/*!40000 ALTER TABLE `customisation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_record`
--

DROP TABLE IF EXISTS `education_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `diploma_title` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_record`
--

LOCK TABLES `education_record` WRITE;
/*!40000 ALTER TABLE `education_record` DISABLE KEYS */;
INSERT INTO `education_record` VALUES (13684,0,'http://www.google.com','comment1','EducationRecord1','2020-04-10','institution1','2018-09-20'),(13685,0,'http://www.google.com','comment2','EducationRecord2','2022-10-14','institution2','2018-02-14'),(13686,0,'http://www.google.com','comment3','EducationRecord3','2023-06-22','institution3','2018-08-17');
/*!40000 ALTER TABLE `education_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endorsement`
--

DROP TABLE IF EXISTS `endorsement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorsement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `mark` int(11) NOT NULL,
  `trainer_to_customer` bit(1) NOT NULL,
  `written_moment` datetime DEFAULT NULL,
  `customer` int(11) NOT NULL,
  `trainer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1dn4vbq4aus6uuvxmqsmpaf2c` (`customer`,`trainer`,`trainer_to_customer`),
  KEY `UK_gftj9c1si8udifumxv4pfoo48` (`trainer`,`trainer_to_customer`),
  KEY `UK_b4u9ooiumw15j4dynxb61sikl` (`customer`,`trainer_to_customer`),
  KEY `UK_j4s0llu83uwcax7o36q5xivlt` (`trainer_to_customer`),
  CONSTRAINT `FK_hpyfk2deuy16cgvllgcyseouj` FOREIGN KEY (`trainer`) REFERENCES `trainer` (`id`),
  CONSTRAINT `FK_csalxlpkf3hcomq3b6j4lk7qi` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorsement`
--

LOCK TABLES `endorsement` WRITE;
/*!40000 ALTER TABLE `endorsement` DISABLE KEYS */;
INSERT INTO `endorsement` VALUES (13914,0,'endorsement1',8,'','2018-11-01 14:00:00',13676,13772),(13915,0,'endorsement2',7,'\0','2018-11-01 14:00:00',13676,13772);
/*!40000 ALTER TABLE `endorsement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endorser_record`
--

DROP TABLE IF EXISTS `endorser_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorser_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `linked_in_profile` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_obxl8ejjf826c3cprp00qcs8p` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorser_record`
--

LOCK TABLES `endorser_record` WRITE;
/*!40000 ALTER TABLE `endorser_record` DISABLE KEYS */;
INSERT INTO `endorser_record` VALUES (13687,0,'endorserrecord1@gmail.com','endorserRecord1','https://www.linkedin.com/e1','632014758'),(13688,0,'endorserrecord2@gmail.com','endorserRecord2','https://www.linkedin.com/e2','695410328'),(13689,0,'endorserrecord3@gmail.com','endorserRecord3','https://www.linkedin.com/e3','625401897');
/*!40000 ALTER TABLE `endorser_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `end_date` date DEFAULT NULL,
  `end_price` double DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `start_price` double DEFAULT NULL,
  `updated_moment` datetime DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `customer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i9i5l2bcvnc58jsb1fbufcowo` (`customer`),
  KEY `UK_9s8d7ftlc3fl7g34t4p42ys11` (`keyword`,`category`,`start_price`,`end_price`,`start_date`,`end_date`),
  KEY `FK_n9t1ayk0x7h5vrsfuhygo043j` (`category`),
  CONSTRAINT `FK_i9i5l2bcvnc58jsb1fbufcowo` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_n9t1ayk0x7h5vrsfuhygo043j` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (13696,0,NULL,NULL,'',NULL,NULL,'2019-05-20 00:10:00',NULL,13676),(13697,0,'2020-12-17',250.24,'workingOut8','2020-12-15',2,'2019-05-20 00:10:00',NULL,13677),(13698,0,'2020-12-17',255.24,'','2020-12-15',25.24,'2019-05-20 00:10:00',13675,13678),(13699,0,'2020-11-09',220.24,'','2020-11-04',0,'2019-05-20 00:10:00',13659,13679),(13700,0,'2020-11-09',255.24,'estoEsUnaPruebaParaQueNoMeDevuelvaNada','2020-11-04',25.24,'2019-05-20 00:10:00',NULL,13680),(13701,0,'2020-11-09',255.24,'estoEsUnaPruebaParaQueNoMeDevuelvaNada','2020-11-04',25.24,'2019-05-20 00:10:00',NULL,13681),(13702,0,'2020-11-09',255.24,'estoEsUnaPruebaParaQueNoMeDevuelvaNada','2020-11-04',25.24,'2019-05-20 00:10:00',NULL,13682);
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_working_outs`
--

DROP TABLE IF EXISTS `finder_working_outs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_working_outs` (
  `finder` int(11) NOT NULL,
  `working_outs` int(11) NOT NULL,
  KEY `FK_nn4f1ykp4g7lupkb3gfg1efg5` (`working_outs`),
  KEY `FK_8663dqfb1voyipir4a9511jx3` (`finder`),
  CONSTRAINT `FK_8663dqfb1voyipir4a9511jx3` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_nn4f1ykp4g7lupkb3gfg1efg5` FOREIGN KEY (`working_outs`) REFERENCES `working_out` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_working_outs`
--

LOCK TABLES `finder_working_outs` WRITE;
/*!40000 ALTER TABLE `finder_working_outs` DISABLE KEYS */;
INSERT INTO `finder_working_outs` VALUES (13697,13786),(13699,13787);
/*!40000 ALTER TABLE `finder_working_outs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `is_spam` bit(1) NOT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `sent_moment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_iwf26op1bvdfl6ubuvhbbp49p` (`sender`,`is_spam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (13690,0,'The next easter excelent is in April','\0','NEUTRAL','2018-09-07 15:24:00','Easter','1',13676),(13691,0,'The next easter excelent is in April','\0','NEUTRAL','2018-09-07 15:24:00','message2','1',13677),(13692,0,'The next easter excelent is in April','\0','NEUTRAL','2018-09-07 15:24:00','message3','1',13678),(13693,0,'The next easter excelent is in April','\0','NEUTRAL','2018-09-07 15:24:00','message4','1',13679),(13694,0,'The next easter excelent is in April','\0','NEUTRAL','2018-09-07 15:24:00','message5','1',13680),(13695,0,'The next easter excelent is in April','\0','NEUTRAL','2018-09-07 15:24:00','message6','1',13681);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_recipients`
--

DROP TABLE IF EXISTS `message_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_recipients` (
  `message` int(11) NOT NULL,
  `recipients` int(11) NOT NULL,
  KEY `FK_1odmg2n3n487tvhuxx5oyyya2` (`message`),
  CONSTRAINT `FK_1odmg2n3n487tvhuxx5oyyya2` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_recipients`
--

LOCK TABLES `message_recipients` WRITE;
/*!40000 ALTER TABLE `message_recipients` DISABLE KEYS */;
INSERT INTO `message_recipients` VALUES (13690,13772),(13690,13773),(13690,13774),(13690,13775),(13690,13776),(13691,13772),(13691,13773),(13691,13774),(13691,13775),(13691,13776),(13692,13772),(13692,13773),(13692,13774),(13692,13775),(13692,13776),(13693,13772),(13693,13773),(13693,13774),(13693,13775),(13693,13776),(13694,13772),(13694,13773),(13694,13774),(13694,13775),(13694,13776),(13695,13772),(13695,13773),(13695,13774),(13695,13775),(13695,13776);
/*!40000 ALTER TABLE `message_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_record`
--

DROP TABLE IF EXISTS `miscellaneous_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_record`
--

LOCK TABLES `miscellaneous_record` WRITE;
/*!40000 ALTER TABLE `miscellaneous_record` DISABLE KEYS */;
INSERT INTO `miscellaneous_record` VALUES (13703,0,'http://www.attachment.com','comment1','title1'),(13704,0,'http://www.attachment.com','comment2','title2'),(13705,0,'http://www.attachment.com','comment3','title3');
/*!40000 ALTER TABLE `miscellaneous_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nutritionist`
--

DROP TABLE IF EXISTS `nutritionist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nutritionist` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_kpmikx4ej8lt1hovnirs8vvra` (`user_account`),
  UNIQUE KEY `UK_8upru8t9sflwf9fo4r1tbqs19` (`email`),
  CONSTRAINT `FK_kpmikx4ej8lt1hovnirs8vvra` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nutritionist`
--

LOCK TABLES `nutritionist` WRITE;
/*!40000 ALTER TABLE `nutritionist` DISABLE KEYS */;
INSERT INTO `nutritionist` VALUES (13706,0,'Calle Nutritionist 1','nutritionist1@gmail.com','\0','','Nutritionist1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',13595),(13707,0,'Calle Nutritionist 2','nutritionist2@gmail.com','\0','','Nutritionist2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',13596),(13708,0,'Calle Nutritionist 3','nutritionist3@gmail.com','\0','','Nutritionist3','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',13597),(13709,0,'Calle Nutritionist 4','nutritionist4@gmail.com','\0','','Nutritionist4','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',13598),(13710,0,'Calle Nutritionist 5','nutritionist5@gmail.com','\0','','Nutritionist5','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',13599);
/*!40000 ALTER TABLE `nutritionist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_record`
--

DROP TABLE IF EXISTS `personal_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `linked_in_profile` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5iru4xbirmmtqmtd06lwgdutg` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_record`
--

LOCK TABLES `personal_record` WRITE;
/*!40000 ALTER TABLE `personal_record` DISABLE KEYS */;
INSERT INTO `personal_record` VALUES (13711,0,'personalRecord1@gmail.com','Trainer1 trainer','https://www.linkedin.com/p1','631047853','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png'),(13712,0,'personalRecord2@gmail.com','Trainer2 trainer','https://www.linkedin.com/p2','601895472','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png'),(13713,0,'personalRecord3@gmail.com','Trainer3 trainer','https://www.linkedin.com/p3','652013984','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png'),(13714,0,'personalRecord4@gmail.com','Trainer4 trainer','https://www.linkedin.com/p4','601895472','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png'),(13715,0,'personalRecord5@gmail.com','Trainer5 trainer','https://www.linkedin.com/p5','652013984','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png');
/*!40000 ALTER TABLE `personal_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professional_record`
--

DROP TABLE IF EXISTS `professional_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professional_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professional_record`
--

LOCK TABLES `professional_record` WRITE;
/*!40000 ALTER TABLE `professional_record` DISABLE KEYS */;
INSERT INTO `professional_record` VALUES (13716,0,'http://www.attachment.com','comment1','professionalRecord1','2025-04-25','role1','2016-04-25'),(13717,0,'http://www.attachment.com','comment2','professionalRecord2','2025-12-30','role2','2016-12-30'),(13718,0,'http://www.attachment.com','comment3','professionalRecord3','2025-05-26','role3','2016-05-26');
/*!40000 ALTER TABLE `professional_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_moment` datetime DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (13719,0,'Address session1','Description session11','2019-11-01 19:00:00','2019-11-01 17:00:00','session11'),(13720,0,'Address session1','Description session12','2019-11-05 19:00:00','2019-11-05 17:00:00','session12'),(13721,0,'Address session1','Description session13','2019-11-15 19:00:00','2019-11-15 17:00:00','session13'),(13722,0,'Address session1','Description session14','2019-11-30 19:00:00','2019-11-30 17:00:00','session14'),(13723,0,'Address session21','Description session21','2019-12-01 19:00:00','2019-12-01 17:00:00','session21'),(13724,0,'Address session22','Description session22','2019-12-05 19:00:00','2019-12-05 17:00:00','session22'),(13725,0,'Address session23','Description session23','2019-12-10 19:00:00','2019-12-10 17:00:00','session23'),(13726,0,'Address session24','Description session24','2019-12-20 19:00:00','2019-12-20 17:00:00','session24'),(13727,0,'Address session25','Description session25','2019-12-25 19:00:00','2019-12-25 17:00:00','session25'),(13728,0,'Address session26','Description session26','2019-12-30 19:00:00','2019-12-30 17:00:00','session26'),(13729,0,'Address session3','Description session31','2019-11-01 19:00:00','2019-11-01 17:00:00','session31'),(13730,0,'Address session3','Description session32','2019-11-05 19:00:00','2019-11-05 17:00:00','session32'),(13731,0,'Address session3','Description session33','2019-11-15 19:00:00','2019-11-15 17:00:00','session33'),(13732,0,'Address session3','Description session34','2019-11-30 19:00:00','2019-11-30 17:00:00','session34'),(13733,0,'Address session41','Description session41','2019-12-01 19:00:00','2019-12-01 17:00:00','session41'),(13734,0,'Address session42','Description session42','2019-12-05 19:00:00','2019-12-05 17:00:00','session42'),(13735,0,'Address session43','Description session43','2019-12-10 19:00:00','2019-12-10 17:00:00','session43'),(13736,0,'Address session44','Description session44','2019-12-20 19:00:00','2019-12-20 17:00:00','session44'),(13737,0,'Address session45','Description session45','2019-12-25 19:00:00','2019-12-25 17:00:00','session45'),(13738,0,'Address session46','Description session46','2019-12-30 19:00:00','2019-12-30 17:00:00','session46'),(13739,0,'Address session51','Description session51','2020-01-01 19:00:00','2020-01-01 17:00:00','session51'),(13740,0,'Address session52','Description session52','2020-01-11 19:00:00','2020-01-11 17:00:00','session52'),(13741,0,'Address session53','Description session53','2020-01-21 19:00:00','2020-01-21 17:00:00','session53'),(13742,0,'Address session54','Description session54','2020-01-30 19:00:00','2020-01-30 17:00:00','session54'),(13743,0,'Address session61','Description session61','2020-02-01 19:00:00','2020-02-01 17:00:00','session61'),(13744,0,'Address session62','Description session62','2020-03-01 19:00:00','2020-03-01 17:00:00','session62'),(13745,0,'Address session71','Description session71','2020-01-01 19:00:00','2020-01-01 17:00:00','session71'),(13746,0,'Address session72','Description session72','2020-01-05 19:00:00','2020-01-05 17:00:00','session72'),(13747,0,'Address session73','Description session73','2020-01-20 19:00:00','2020-01-20 17:00:00','session73'),(13748,0,'Address session81','Description session81','2018-12-15 19:00:00','2018-12-15 17:00:00','session81'),(13749,0,'Address session82','Description session82','2018-12-17 19:00:00','2018-12-17 17:00:00','session82'),(13750,0,'Address session91','Description session91','2019-11-04 19:00:00','2019-11-04 17:00:00','session91'),(13751,0,'Address session92','Description session92','2019-11-06 19:00:00','2019-11-06 17:00:00','session92'),(13752,0,'Address session93','Description session93','2019-11-09 19:00:00','2019-11-09 17:00:00','session93');
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link_profile` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nedqor7tomp44srq0vbui1h6b` (`link_profile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
INSERT INTO `social_profile` VALUES (13753,0,'http://www.twitter.com/nick1','nickAdministrator1','Twitter',13612),(13754,0,'http://www.instagram.com/nick2','nick1Customer1','Instagram',13676),(13755,0,'http://www.instagram.com/nick3','nick2Customer1','Instagram',13676),(13756,0,'http://www.instagram.com/nick4','nick1Customer2','Instagram',13677),(13757,0,'http://www.instagram.com/nick5','nick1Customer3','Instagram',13678),(13758,0,'http://www.instagram.com/nick6','nick1Customer4','Instagram',13679),(13759,0,'http://www.instagram.com/nick7','nick1Customer5','Instagram',13680),(13760,0,'http://www.instagram.com/nick8','nick1Auditor1','Instagram',13614),(13761,0,'http://www.instagram.com/nick9','nick2Auditor2','Instagram',13614),(13762,0,'http://www.instagram.com/nick10','nick1Auditor2','Instagram',13615),(13763,0,'http://www.instagram.com/nick11','nick1Auditor3','Instagram',13616),(13764,0,'http://www.instagram.com/nick12','nick1Auditor4','Instagram',13617),(13765,0,'http://www.instagram.com/nick13','nick1Auditor5','Instagram',13618),(13766,0,'http://www.instagram.com/nick14','nick1Nutritionist1','Instagram',13706),(13767,0,'http://www.instagram.com/nick15','nick2Nutritionist2','Instagram',13706),(13768,0,'http://www.instagram.com/nick16','nick1Nutritionist2','Instagram',13707),(13769,0,'http://www.instagram.com/nick17','nick1Nutritionist3','Instagram',13708),(13770,0,'http://www.instagram.com/nick18','nick1Nutritionist4','Instagram',13709),(13771,0,'http://www.instagram.com/nick19','nick1Nutritionist5','Instagram',13710),(13916,0,'http://www.instagram.com/nick20','nick1Trainer1','Instagram',13772),(13917,0,'http://www.instagram.com/nick21','nick2Trainer2','Instagram',13772),(13918,0,'http://www.instagram.com/nick22','nick1Trainer2','Instagram',13773),(13919,0,'http://www.instagram.com/nick23','nick1Trainer3','Instagram',13774),(13920,0,'http://www.instagram.com/nick24','nick1Trainer4','Instagram',13775),(13921,0,'http://www.instagram.com/nick25','nick1Trainer5','Instagram',13776);
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainer`
--

DROP TABLE IF EXISTS `trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `mark` double DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6vm3xjvojtigbcfhm5kq3eppg` (`user_account`),
  UNIQUE KEY `UK_4jrvips0u6okch0ktcu7xdaxw` (`email`),
  CONSTRAINT `FK_6vm3xjvojtigbcfhm5kq3eppg` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainer`
--

LOCK TABLES `trainer` WRITE;
/*!40000 ALTER TABLE `trainer` DISABLE KEYS */;
INSERT INTO `trainer` VALUES (13772,0,'Calle Trainer 1','trainer1@gmail.com','\0','','Trainer1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13605,4,0.3),(13773,0,'Calle Trainer 2','trainer2@gmail.com','\0','','Trainer2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13606,4,0.3),(13774,0,'Calle Trainer 3','trainer3@gmail.com','\0','','Trainer3','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13607,4,0.3),(13775,0,'Calle Trainer 4','trainer4@gmail.com','\0','','Trainer4','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13608,4,0.3),(13776,0,'Calle Trainer 5','trainer5@gmail.com','\0','','Trainer5','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13609,4,0.3),(13777,0,'Calle Trainer 6','trainer6@gmail.com','\0','','Trainer6','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13610,4,0.3),(13778,0,'Calle Trainer 7','trainer7@gmail.com','','','Trainer7','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',13611,4,0.3);
/*!40000 ALTER TABLE `trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_banned` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (13586,0,'\0','e00cf25ad42683b3df678c61f42c6bda','admin1'),(13587,0,'\0','54b53072540eeeb8f8e9343e71f28176','system'),(13588,0,'\0','ffbc4675f864e0e9aab8bdf7a0437010','customer1'),(13589,0,'\0','5ce4d191fd14ac85a1469fb8c29b7a7b','customer2'),(13590,0,'\0','033f7f6121501ae98285ad77f216d5e7','customer3'),(13591,0,'\0','55feb130be438e686ad6a80d12dd8f44','customer4'),(13592,0,'\0','9e8486cdd435beda9a60806dd334d964','customer5'),(13593,0,'\0','dbaa8bd25e06cc641f5406027c026e8b','customer6'),(13594,0,'\0','81162e1ef3d93f96b36d3712ca52bca5','customer7'),(13595,0,'\0','768261ab8b85bc8b04c1a5e840056108','nutritionist1'),(13596,0,'\0','ca0d82372ee05801973127ea6414b21d','nutritionist2'),(13597,0,'\0','9c360887116bf2e02328f65d4680cb16','nutritionist3'),(13598,0,'\0','31ecae7fc61efdada7986bc51aa791df','nutritionist4'),(13599,0,'\0','e5e7dfae0bf4f9c5d040db1e168833d3','nutritionist5'),(13600,0,'\0','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(13601,0,'\0','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(13602,0,'\0','fc2073dbe4f65dfd71e46602f8e6a5f3','auditor3'),(13603,0,'\0','6cc8affcba51a854192a33af68c08f49','auditor4'),(13604,0,'\0','3775bf00262284e83013c9cea5f41431','auditor5'),(13605,0,'\0','4d9a96c8e1650dc161f1adcf5c5082a0','trainer1'),(13606,0,'\0','6662f54a6c5033357408e6839a5c0a05','trainer2'),(13607,0,'\0','72977b63f9bf5a01f30bfe2ca802a3c1','trainer3'),(13608,0,'\0','06de2cced0f700b155d4d04d2ef3245c','trainer4'),(13609,0,'\0','191be98376838b92608915fb8a9e2818','trainer5'),(13610,0,'\0','a3b02d67308e7a6560e9a39f3c69860b','trainer6'),(13611,0,'\0','537d692423ad76a73eacf1c527fa35a9','trainer7');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (13586,'ADMIN'),(13587,'ADMIN'),(13588,'CUSTOMER'),(13589,'CUSTOMER'),(13590,'CUSTOMER'),(13591,'CUSTOMER'),(13592,'CUSTOMER'),(13593,'CUSTOMER'),(13594,'CUSTOMER'),(13595,'NUTRITIONIST'),(13596,'NUTRITIONIST'),(13597,'NUTRITIONIST'),(13598,'NUTRITIONIST'),(13599,'NUTRITIONIST'),(13600,'AUDITOR'),(13601,'AUDITOR'),(13602,'AUDITOR'),(13603,'AUDITOR'),(13604,'AUDITOR'),(13605,'TRAINER'),(13606,'TRAINER'),(13607,'TRAINER'),(13608,'TRAINER'),(13609,'TRAINER'),(13610,'TRAINER'),(13611,'TRAINER');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_out`
--

DROP TABLE IF EXISTS `working_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `working_out` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_moment` datetime DEFAULT NULL,
  `is_final_mode` bit(1) NOT NULL,
  `price` double NOT NULL,
  `published_moment` datetime DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `trainer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4qkr5edsh1ikhlohhdahb1juf` (`ticker`),
  KEY `UK_l50s8k4ni85y52y0c2b5qhml0` (`trainer`,`is_final_mode`),
  KEY `UK_ojohr2rkfvisb998se7dw7g8c` (`is_final_mode`,`start_moment`),
  KEY `UK_p3me5i27uksvxpyvbcvr9ul6` (`is_final_mode`,`ticker`,`description`,`start_moment`,`end_moment`,`price`),
  KEY `UK_hae47ads6l2ui9wm0dpujssdo` (`is_final_mode`),
  CONSTRAINT `FK_dl6e1bxcrdvpkio7pvxh7u20v` FOREIGN KEY (`trainer`) REFERENCES `trainer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_out`
--

LOCK TABLES `working_out` WRITE;
/*!40000 ALTER TABLE `working_out` DISABLE KEYS */;
INSERT INTO `working_out` VALUES (13779,0,'Description workingOut1','2019-11-30 19:00:00','',250.99,'2019-02-01 10:10:00','2019-11-01 17:00:00','120212-ASDFGH',13772),(13780,0,'Description workingOut2','2019-12-30 19:00:00','',235.99,'2019-02-01 10:10:00','2019-12-01 17:00:00','124712-ASDFGH',13772),(13781,0,'Description workingOut3','2019-11-30 19:00:00','',250.99,'2019-02-01 10:10:00','2019-11-01 17:00:00','120962-ASDFGH',13773),(13782,0,'Description workingOut4','2019-12-30 19:00:00','',275.99,'2019-02-01 10:10:00','2019-12-01 17:00:00','123612-ASDFGH',13774),(13783,0,'Description workingOut5','2020-01-30 19:00:00','',215.99,'2019-02-01 10:10:00','2020-01-01 17:00:00','121412-ASDFGH',13775),(13784,0,'Description workingOut6','2020-03-01 19:00:00','\0',215.99,'2019-02-01 10:10:00','2020-02-01 17:00:00','121472-ASDFGH',13775),(13785,0,'Description workingOut7','2020-01-20 19:00:00','',215.99,'2019-02-01 10:10:00','2020-01-01 17:00:00','121142-ASDFGH',13776),(13786,0,'Description workingOut8','2018-12-17 19:00:00','',215.99,'2018-02-01 10:10:00','2018-12-15 17:00:00','124142-ASDFGH',13776),(13787,0,'Description workingOut9','2019-11-09 19:00:00','',215.99,'2019-02-01 10:10:00','2019-11-04 17:00:00','114142-ASDFGH',13778);
/*!40000 ALTER TABLE `working_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_out_categories`
--

DROP TABLE IF EXISTS `working_out_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `working_out_categories` (
  `working_out` int(11) NOT NULL,
  `categories` int(11) NOT NULL,
  KEY `FK_lvacxo3b5rpuoy77sqg7on5xl` (`categories`),
  KEY `FK_9c4n3n1bpdbxe4ipa2dp3oxi5` (`working_out`),
  CONSTRAINT `FK_9c4n3n1bpdbxe4ipa2dp3oxi5` FOREIGN KEY (`working_out`) REFERENCES `working_out` (`id`),
  CONSTRAINT `FK_lvacxo3b5rpuoy77sqg7on5xl` FOREIGN KEY (`categories`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_out_categories`
--

LOCK TABLES `working_out_categories` WRITE;
/*!40000 ALTER TABLE `working_out_categories` DISABLE KEYS */;
INSERT INTO `working_out_categories` VALUES (13779,13656),(13779,13657),(13779,13658),(13780,13659),(13780,13667),(13780,13672),(13781,13673),(13781,13666),(13781,13661),(13782,13663),(13782,13657),(13782,13668),(13783,13656),(13784,13669),(13785,13666),(13786,13666),(13787,13659);
/*!40000 ALTER TABLE `working_out_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `working_out_sessions`
--

DROP TABLE IF EXISTS `working_out_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `working_out_sessions` (
  `working_out` int(11) NOT NULL,
  `sessions` int(11) NOT NULL,
  UNIQUE KEY `UK_jw6fxvr30e6bj010322ecryim` (`sessions`),
  KEY `FK_46k4vis9bked5ctt214nny7aq` (`working_out`),
  CONSTRAINT `FK_46k4vis9bked5ctt214nny7aq` FOREIGN KEY (`working_out`) REFERENCES `working_out` (`id`),
  CONSTRAINT `FK_jw6fxvr30e6bj010322ecryim` FOREIGN KEY (`sessions`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `working_out_sessions`
--

LOCK TABLES `working_out_sessions` WRITE;
/*!40000 ALTER TABLE `working_out_sessions` DISABLE KEYS */;
INSERT INTO `working_out_sessions` VALUES (13779,13719),(13779,13720),(13779,13721),(13779,13722),(13780,13723),(13780,13724),(13780,13725),(13780,13726),(13780,13727),(13780,13728),(13781,13729),(13781,13730),(13781,13731),(13781,13732),(13782,13733),(13782,13734),(13782,13735),(13782,13736),(13782,13737),(13782,13738),(13783,13739),(13783,13740),(13783,13741),(13783,13742),(13784,13743),(13784,13744),(13785,13745),(13785,13746),(13785,13747),(13786,13748),(13786,13749),(13787,13750),(13787,13751),(13787,13752);
/*!40000 ALTER TABLE `working_out_sessions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-04 20:26:29

commit;