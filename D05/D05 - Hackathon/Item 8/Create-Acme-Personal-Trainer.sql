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
INSERT INTO `administrator` VALUES (1154,0,'Calle Admin 1','administrator1@gmail.com','\0','','Admin1','630187654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','administrator',1144),(1155,0,'Calle Admin 2','administrator2@gmail.com','\0','','Admin2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','administrator',1145);
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
INSERT INTO `auditor` VALUES (1156,0,'Calle Auditor 1','auditor1@gmail.com','\0','','Auditor1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',1150),(1157,0,'Calle Auditor 2','auditor2@gmail.com','\0','','Auditor2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','auditor',1151);
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
INSERT INTO `box` VALUES (1158,0,'','in box',1154,NULL),(1159,0,'','out box',1154,NULL),(1160,0,'','trash box',1154,NULL),(1161,0,'','spam box',1154,NULL),(1162,0,'','notification box',1154,NULL),(1163,0,'','in box',1155,NULL),(1164,0,'','out box',1155,NULL),(1165,0,'','trash box',1155,NULL),(1166,0,'','spam box',1155,NULL),(1167,0,'','notification box',1155,NULL),(1168,0,'','in box',1156,NULL),(1169,0,'','out box',1156,NULL),(1170,0,'','trash box',1156,NULL),(1171,0,'','spam box',1156,NULL),(1172,0,'','notification box',1156,NULL),(1173,0,'','in box',1157,NULL),(1174,0,'','out box',1157,NULL),(1175,0,'','trash box',1157,NULL),(1176,0,'','spam box',1157,NULL),(1177,0,'','notification box',1157,NULL),(1207,0,'','in box',1198,NULL),(1208,0,'','out box',1198,NULL),(1209,0,'','trash box',1198,NULL),(1210,0,'','spam box',1198,NULL),(1211,0,'','notification box',1198,NULL),(1212,0,'','in box',1199,NULL),(1213,0,'','out box',1199,NULL),(1214,0,'','trash box',1199,NULL),(1215,0,'','spam box',1199,NULL),(1216,0,'','notification box',1199,NULL),(1217,0,'','in box',1203,NULL),(1218,0,'','out box',1203,NULL),(1219,0,'','trash box',1203,NULL),(1220,0,'','spam box',1203,NULL),(1221,0,'','notification box',1203,NULL),(1222,0,'','in box',1204,NULL),(1223,0,'','out box',1204,NULL),(1224,0,'','trash box',1204,NULL),(1225,0,'','spam box',1204,NULL),(1226,0,'','notification box',1204,NULL),(1227,0,'','in box',1205,NULL),(1228,0,'','out box',1205,NULL),(1229,0,'','trash box',1205,NULL),(1230,0,'','spam box',1205,NULL),(1231,0,'','notification box',1205,NULL),(1232,0,'','in box',1206,NULL),(1233,0,'','out box',1206,NULL),(1234,0,'','trash box',1206,NULL),(1235,0,'','spam box',1206,NULL),(1236,0,'','notification box',1206,NULL);
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
INSERT INTO `category` VALUES (1178,0,'Active Recovery, '),(1179,0,'Aerobic Exercise,'),(1180,0,'Anaerobic Exercise, '),(1181,0,'Boot Camp, '),(1182,0,'Circuit,'),(1183,0,'Compound Exercises, '),(1184,0,'Cool-Down, '),(1185,0,'Cross-Training,  '),(1186,0,'DOMS,'),(1187,0,'Dynamic Warm-Up, '),(1188,0,'Foam Rolling,  '),(1189,0,'Functional Moves,'),(1190,0,'Heart Rate Zones,  '),(1191,0,'HIIT, '),(1192,0,'Interval Training,  '),(1193,0,'Isometrics,   '),(1194,0,'Plyometrics, '),(1195,0,'Resistance,  '),(1196,0,'Strength Training,  '),(1197,0,'Tabata,');
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
INSERT INTO `customer` VALUES (1198,0,'Calle Customer 1','customer1@gmail.com','\0','','Customer1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',1146,'\0'),(1199,0,'Calle Customer 2','customer2@gmail.com','\0','','Customer2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','customer',1147,'\0');
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
INSERT INTO `customisation` VALUES (1200,0,47,'https://i.ibb.co/dLs9pVS/banner.png','+34','VISA,MASTER,DINNERS,AMEX','Acme Personal Trainer','not,bad,horrible,average,disaster,no,mal,mediocre,desastre',10,'good,fantastic,excellent,great,amazing,terrific,beautiful,bien,fantastico,excelente,genial,increible,terrorifico,hermosos',200,'HIGH,NEUTRAL,LOW','sex,viagra,cialis,one million,you\'ve been selected,Nigeria,sexo,un millon,ha sido seleccionado',-0.5,1,'Welcome to Acme Personal Trainer! Price, quality, and trust in a single place','¡Bienvenidos a Acme Personal Trainer! Precio, calidad y confianza en el mismo sitio');
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
INSERT INTO `finder` VALUES (1201,0,'2020-01-01',75.24,'estoEsUnaPruebaParaQueNoMeDevuelvaNada','2019-11-05',25.24,'2019-05-20 00:10:00',NULL,1198),(1202,0,'2020-12-17',50.24,'workingOut8','2020-12-15',2,'2019-05-20 00:10:00',NULL,1199);
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
INSERT INTO `nutritionist` VALUES (1203,0,'Calle Nutritionist 1','nutritionist1@gmail.com','\0','','Nutritionist1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',1148),(1204,0,'Calle Nutritionist 2','nutritionist2@gmail.com','\0','','Nutritionist2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','nutritionist',1149);
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
INSERT INTO `trainer` VALUES (1205,0,'Calle Trainer 1','trainer1@gmail.com','\0','','Trainer1','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',1152,4,0.3),(1206,0,'Calle Trainer 2','trainer2@gmail.com','\0','','Trainer2','630417654','https://deca.upc.edu/ca/persones/pdi-template-shared/profile/@@images/bac720ab-ea23-4696-9bdd-15f87f706868.png','trainer',1153,4,0.3);
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
INSERT INTO `user_account` VALUES (1144,0,'\0','e00cf25ad42683b3df678c61f42c6bda','admin1'),(1145,0,'\0','54b53072540eeeb8f8e9343e71f28176','system'),(1146,0,'\0','ffbc4675f864e0e9aab8bdf7a0437010','customer1'),(1147,0,'\0','5ce4d191fd14ac85a1469fb8c29b7a7b','customer2'),(1148,0,'\0','768261ab8b85bc8b04c1a5e840056108','nutritionist1'),(1149,0,'\0','ca0d82372ee05801973127ea6414b21d','nutritionist2'),(1150,0,'\0','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(1151,0,'\0','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(1152,0,'\0','4d9a96c8e1650dc161f1adcf5c5082a0','trainer1'),(1153,0,'\0','6662f54a6c5033357408e6839a5c0a05','trainer2');
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
INSERT INTO `user_account_authorities` VALUES (1144,'ADMIN'),(1145,'ADMIN'),(1146,'CUSTOMER'),(1147,'CUSTOMER'),(1148,'NUTRITIONIST'),(1149,'NUTRITIONIST'),(1150,'AUDITOR'),(1151,'AUDITOR'),(1152,'TRAINER'),(1153,'TRAINER');
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

-- Dump completed on 2019-06-04 20:27:02

commit;