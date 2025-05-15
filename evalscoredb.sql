-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: evalscoredb
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity_bulletin`
--

DROP TABLE IF EXISTS `activity_bulletin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_bulletin` (
  `bulletin_ptr_id` bigint NOT NULL,
  `extra_activity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`bulletin_ptr_id`),
  KEY `activity_bulletin_extra_activity_id_0d3449e7_fk_extra_act` (`extra_activity_id`),
  CONSTRAINT `activity_bulletin_bulletin_ptr_id_2552fc3f_fk_bulletin_id` FOREIGN KEY (`bulletin_ptr_id`) REFERENCES `bulletin` (`id`),
  CONSTRAINT `activity_bulletin_extra_activity_id_0d3449e7_fk_extra_act` FOREIGN KEY (`extra_activity_id`) REFERENCES `extra_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_bulletin`
--

LOCK TABLES `activity_bulletin` WRITE;
/*!40000 ALTER TABLE `activity_bulletin` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_bulletin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_confirmed_attendance`
--

DROP TABLE IF EXISTS `activity_confirmed_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_confirmed_attendance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `proofPicture` varchar(255) NOT NULL,
  `approved` tinyint(1) NOT NULL,
  `activity_registry_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `activity_registry_id` (`activity_registry_id`),
  CONSTRAINT `activity_confirmed_a_activity_registry_id_bbeb038e_fk_activity_` FOREIGN KEY (`activity_registry_id`) REFERENCES `activity_registry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_confirmed_attendance`
--

LOCK TABLES `activity_confirmed_attendance` WRITE;
/*!40000 ALTER TABLE `activity_confirmed_attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_confirmed_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_registry`
--

DROP TABLE IF EXISTS `activity_registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_registry` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `student_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `activity_registry_student_id_ad346a4a_fk_student_user_ptr_id` (`student_id`),
  CONSTRAINT `activity_registry_student_id_ad346a4a_fk_student_user_ptr_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_ptr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_registry`
--

LOCK TABLES `activity_registry` WRITE;
/*!40000 ALTER TABLE `activity_registry` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_registry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bulletin`
--

DROP TABLE IF EXISTS `bulletin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bulletin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext,
  `duration` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bulletin`
--

LOCK TABLES `bulletin` WRITE;
/*!40000 ALTER TABLE `bulletin` DISABLE KEYS */;
/*!40000 ALTER TABLE `bulletin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancel_activity_requirement`
--

DROP TABLE IF EXISTS `cancel_activity_requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancel_activity_requirement` (
  `cancelrequirement_ptr_id` bigint NOT NULL,
  `extra_activity_id` bigint NOT NULL,
  PRIMARY KEY (`cancelrequirement_ptr_id`),
  KEY `cancel_activity_requ_extra_activity_id_57b97f1f_fk_extra_act` (`extra_activity_id`),
  CONSTRAINT `cancel_activity_requ_cancelrequirement_pt_61460d50_fk_cancel_re` FOREIGN KEY (`cancelrequirement_ptr_id`) REFERENCES `cancel_requirement` (`id`),
  CONSTRAINT `cancel_activity_requ_extra_activity_id_57b97f1f_fk_extra_act` FOREIGN KEY (`extra_activity_id`) REFERENCES `extra_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancel_activity_requirement`
--

LOCK TABLES `cancel_activity_requirement` WRITE;
/*!40000 ALTER TABLE `cancel_activity_requirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `cancel_activity_requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancel_bulletin_requirement`
--

DROP TABLE IF EXISTS `cancel_bulletin_requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancel_bulletin_requirement` (
  `cancelrequirement_ptr_id` bigint NOT NULL,
  `bulletin_id` bigint NOT NULL,
  PRIMARY KEY (`cancelrequirement_ptr_id`),
  KEY `cancel_bulletin_requirement_bulletin_id_290db467_fk_bulletin_id` (`bulletin_id`),
  CONSTRAINT `cancel_bulletin_requ_cancelrequirement_pt_0dbbbf3b_fk_cancel_re` FOREIGN KEY (`cancelrequirement_ptr_id`) REFERENCES `cancel_requirement` (`id`),
  CONSTRAINT `cancel_bulletin_requirement_bulletin_id_290db467_fk_bulletin_id` FOREIGN KEY (`bulletin_id`) REFERENCES `bulletin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancel_bulletin_requirement`
--

LOCK TABLES `cancel_bulletin_requirement` WRITE;
/*!40000 ALTER TABLE `cancel_bulletin_requirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `cancel_bulletin_requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cancel_requirement`
--

DROP TABLE IF EXISTS `cancel_requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cancel_requirement` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `reason_detail` longtext,
  `executed_status` varchar(50) NOT NULL,
  `student_affairs_officer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cancel_requirement_student_affairs_offi_9ea4bf5d_fk_student_a` (`student_affairs_officer_id`),
  CONSTRAINT `cancel_requirement_student_affairs_offi_9ea4bf5d_fk_student_a` FOREIGN KEY (`student_affairs_officer_id`) REFERENCES `student_affairs_officer` (`user_ptr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cancel_requirement`
--

LOCK TABLES `cancel_requirement` WRITE;
/*!40000 ALTER TABLE `cancel_requirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `cancel_requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `interaction_ptr_id` bigint NOT NULL,
  `content` longtext NOT NULL,
  PRIMARY KEY (`interaction_ptr_id`),
  CONSTRAINT `comment_interaction_ptr_id_1ac02613_fk_interaction_id` FOREIGN KEY (`interaction_ptr_id`) REFERENCES `interaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extra_activity`
--

DROP TABLE IF EXISTS `extra_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `extra_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `bonus_score` int NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` longtext,
  `semester_id` bigint NOT NULL,
  `term_id` bigint NOT NULL,
  `student_assistant_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `extra_activity_semester_id_9c47264b_fk_semester_id` (`semester_id`),
  KEY `extra_activity_term_id_6966ca2b_fk_term_id` (`term_id`),
  KEY `extra_activity_student_assistant_id_127f73b8_fk_student_a` (`student_assistant_id`),
  CONSTRAINT `extra_activity_semester_id_9c47264b_fk_semester_id` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`),
  CONSTRAINT `extra_activity_student_assistant_id_127f73b8_fk_student_a` FOREIGN KEY (`student_assistant_id`) REFERENCES `student_assistant` (`user_ptr_id`),
  CONSTRAINT `extra_activity_term_id_6966ca2b_fk_term_id` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extra_activity`
--

LOCK TABLES `extra_activity` WRITE;
/*!40000 ALTER TABLE `extra_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `extra_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interaction`
--

DROP TABLE IF EXISTS `interaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `bulletin_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `interaction_student_id_a81d11fb_fk_student_user_ptr_id` (`student_id`),
  KEY `interaction_bulletin_id_ee048c0f_fk_bulletin_id` (`bulletin_id`),
  CONSTRAINT `interaction_bulletin_id_ee048c0f_fk_bulletin_id` FOREIGN KEY (`bulletin_id`) REFERENCES `bulletin` (`id`),
  CONSTRAINT `interaction_student_id_a81d11fb_fk_student_user_ptr_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_ptr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interaction`
--

LOCK TABLES `interaction` WRITE;
/*!40000 ALTER TABLE `interaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `interaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `missing_activity`
--

DROP TABLE IF EXISTS `missing_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `missing_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `proof_content` longtext,
  `proof_picture` varchar(255) DEFAULT NULL,
  `executed_status` varchar(50) NOT NULL,
  `extra_activity_id` bigint NOT NULL,
  `missing_activity_id` bigint DEFAULT NULL,
  `summary_bulletin_id` bigint NOT NULL,
  `student_id` bigint NOT NULL,
  `student_assistant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `missing_activity_id` (`missing_activity_id`),
  KEY `missing_activity_extra_activity_id_c3e34014_fk_extra_activity_id` (`extra_activity_id`),
  KEY `missing_activity_summary_bulletin_id_ddc76d4f_fk_summary_b` (`summary_bulletin_id`),
  KEY `missing_activity_student_id_0a38a5ed_fk_student_user_ptr_id` (`student_id`),
  KEY `missing_activity_student_assistant_id_31f91247_fk_student_a` (`student_assistant_id`),
  CONSTRAINT `missing_activity_extra_activity_id_c3e34014_fk_extra_activity_id` FOREIGN KEY (`extra_activity_id`) REFERENCES `extra_activity` (`id`),
  CONSTRAINT `missing_activity_missing_activity_id_73dbef72_fk_activity_` FOREIGN KEY (`missing_activity_id`) REFERENCES `activity_confirmed_attendance` (`id`),
  CONSTRAINT `missing_activity_student_assistant_id_31f91247_fk_student_a` FOREIGN KEY (`student_assistant_id`) REFERENCES `student_assistant` (`user_ptr_id`),
  CONSTRAINT `missing_activity_student_id_0a38a5ed_fk_student_user_ptr_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`user_ptr_id`),
  CONSTRAINT `missing_activity_summary_bulletin_id_ddc76d4f_fk_summary_b` FOREIGN KEY (`summary_bulletin_id`) REFERENCES `summary_bulletin` (`bulletin_ptr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `missing_activity`
--

LOCK TABLES `missing_activity` WRITE;
/*!40000 ALTER TABLE `missing_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `missing_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reactions`
--

DROP TABLE IF EXISTS `reactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reactions` (
  `interaction_ptr_id` bigint NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`interaction_ptr_id`),
  CONSTRAINT `reactions_interaction_ptr_id_601b4570_fk_interaction_id` FOREIGN KEY (`interaction_ptr_id`) REFERENCES `interaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reactions`
--

LOCK TABLES `reactions` WRITE;
/*!40000 ALTER TABLE `reactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `reactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester`
--

DROP TABLE IF EXISTS `semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semester` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `year` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester`
--

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `user_ptr_id` bigint NOT NULL,
  `achievement` varchar(50) NOT NULL,
  `mssv` varchar(11) NOT NULL,
  PRIMARY KEY (`user_ptr_id`),
  CONSTRAINT `student_user_ptr_id_44865c21_fk_user_info_id` FOREIGN KEY (`user_ptr_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_affairs_officer`
--

DROP TABLE IF EXISTS `student_affairs_officer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_affairs_officer` (
  `user_ptr_id` bigint NOT NULL,
  PRIMARY KEY (`user_ptr_id`),
  CONSTRAINT `student_affairs_officer_user_ptr_id_ee9d7ca7_fk_user_info_id` FOREIGN KEY (`user_ptr_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_affairs_officer`
--

LOCK TABLES `student_affairs_officer` WRITE;
/*!40000 ALTER TABLE `student_affairs_officer` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_affairs_officer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_assistant`
--

DROP TABLE IF EXISTS `student_assistant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_assistant` (
  `user_ptr_id` bigint NOT NULL,
  PRIMARY KEY (`user_ptr_id`),
  CONSTRAINT `student_assistant_user_ptr_id_3045b2f8_fk_user_info_id` FOREIGN KEY (`user_ptr_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_assistant`
--

LOCK TABLES `student_assistant` WRITE;
/*!40000 ALTER TABLE `student_assistant` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_assistant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `summary_bulletin`
--

DROP TABLE IF EXISTS `summary_bulletin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `summary_bulletin` (
  `bulletin_ptr_id` bigint NOT NULL,
  PRIMARY KEY (`bulletin_ptr_id`),
  CONSTRAINT `summary_bulletin_bulletin_ptr_id_d9ffe62d_fk_bulletin_id` FOREIGN KEY (`bulletin_ptr_id`) REFERENCES `bulletin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `summary_bulletin`
--

LOCK TABLES `summary_bulletin` WRITE;
/*!40000 ALTER TABLE `summary_bulletin` DISABLE KEYS */;
/*!40000 ALTER TABLE `summary_bulletin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `term` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `max_value` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `updated_date` datetime(6) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(60) NOT NULL,
  `password` varchar(124) NOT NULL,
  `avatar` varchar(124) NOT NULL,
  `email` varchar(124) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `user_role` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15 17:56:23
