-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: ejercicio1
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acomodador`
--

DROP TABLE IF EXISTS `acomodador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acomodador` (
  `id` bigint NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre_completo` varchar(255) NOT NULL,
  `telefono_movil` varchar(13) NOT NULL,
  `antiguedad` int DEFAULT NULL,
  `edad` int NOT NULL,
  `valoracion_media` double(4,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_htis380spisxdokkaj6h9id2d` (`nombre_completo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acomodador`
--

LOCK TABLES `acomodador` WRITE;
/*!40000 ALTER TABLE `acomodador` DISABLE KEYS */;
INSERT INTO `acomodador` VALUES (52,'juanfer@email.com','Juan Fernando El Movidas','699123321',0,16,5.77),(53,'tipo@email.com','Tipo de Incognito','611123456',5,20,5.00),(54,'senor@email.com','Jimmy McNulty','600123456',5,33,5.00),(103,'senor2@email.com','Omar Little','612123321',0,33,5.00),(202,'senor3@email.com','Cedric Daniels','632123321',0,33,5.00),(203,'senor4@email.com','Cristopher Moltisanti','632123421',0,33,5.00),(303,'senor42@email.com','Furio Giunta','632134212',0,33,5.00);
/*!40000 ALTER TABLE `acomodador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `director`
--

DROP TABLE IF EXISTS `director`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `director` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `numero_peliculas` int DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `director`
--

LOCK TABLES `director` WRITE;
/*!40000 ALTER TABLE `director` DISABLE KEYS */;
INSERT INTO `director` VALUES (1,'Antoine Fuqua',22,'1965-05-30'),(2,'Bryan Singer',13,'1966-10-17'),(3,'David Fincher',100,'1965-08-28'),(4,'Alejandro Amenábar',19,'1972-03-31'),(5,'Brian De Palma',46,'1940-09-11'),(6,'Daniel Monzón',7,'1968-08-24'),(7,'Francis',22,'1939-04-07'),(8,'Quentin Tarantino',9,'1963-03-27'),(9,'Alberto Rodriguez Librero',14,'1971-05-11'),(10,'Christopher Nolan',50,'1970-07-30'),(11,'John McTiernan',13,'1951-03-08'),(12,'Hayao Miyazaki',11,'1941-01-05'),(13,'Pete Docter',5,'1968-10-09'),(14,'Martin Scorsese',26,'1942-11-17'),(15,'Guy Ritchie',2,'1968-09-10'),(16,'Los hermanos Coen',18,'1954-11-29'),(17,'Francis Veber',13,'1937-07-28'),(19,'Juanma Bajo Ulloa',15,'1967-01-01'),(20,'Jose Luis Cuerda',17,'1947-02-18'),(21,'Terry Jones',8,'1942-02-01'),(24,'Director prueba1',50,'1984-01-05'),(25,'Director prueba1424',100,'1984-01-05'),(26,'Guille El Movidas',0,'2023-03-15'),(27,'Carlos CapriconioTV',0,'2001-05-25');
/*!40000 ALTER TABLE `director` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emision`
--

DROP TABLE IF EXISTS `emision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emision` (
  `fecha` datetime(6) NOT NULL,
  `id_pelicula` bigint NOT NULL,
  `idSala` bigint NOT NULL,
  `palomitas` double(6,2) DEFAULT NULL,
  `visitantes` int DEFAULT NULL,
  `id_acomodador` bigint DEFAULT NULL,
  `id_limpiador` bigint DEFAULT NULL,
  PRIMARY KEY (`fecha`,`id_pelicula`,`idSala`),
  KEY `FKaa1u30wkl1qmypydmhn7ujl2r` (`id_acomodador`),
  KEY `FK3jw82dbdpecsqe3je62ixlssv` (`id_limpiador`),
  KEY `index_emision` (`id_pelicula`,`idSala`,`fecha`),
  KEY `FKquqrn51w97k5v17u06ms48rb9` (`idSala`),
  CONSTRAINT `FK3jw82dbdpecsqe3je62ixlssv` FOREIGN KEY (`id_limpiador`) REFERENCES `limpiador` (`id`),
  CONSTRAINT `FKaa1u30wkl1qmypydmhn7ujl2r` FOREIGN KEY (`id_acomodador`) REFERENCES `acomodador` (`id`),
  CONSTRAINT `FKquqrn51w97k5v17u06ms48rb9` FOREIGN KEY (`idSala`) REFERENCES `sala` (`id`),
  CONSTRAINT `FKssvbbus48upfwy8m27cd1802v` FOREIGN KEY (`id_pelicula`) REFERENCES `pelicula` (`id`),
  CONSTRAINT `emision_chk_1` CHECK ((`visitantes` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emision`
--

LOCK TABLES `emision` WRITE;
/*!40000 ALTER TABLE `emision` DISABLE KEYS */;
INSERT INTO `emision` VALUES ('2002-05-20 16:30:40.000000',9,8,NULL,0,103,4),('2015-06-20 03:03:00.000000',4,5,NULL,0,52,3),('2018-05-03 00:00:00.000000',15,1,NULL,0,52,3),('2018-05-19 00:00:00.000000',19,2,NULL,0,52,1),('2018-07-12 00:00:00.000000',32,4,16.60,2,54,3),('2018-09-15 00:00:00.000000',15,1,0.00,1,52,5),('2018-11-02 00:00:00.000000',9,2,NULL,0,52,4),('2018-12-23 00:00:00.000000',19,4,30.00,3,53,1),('2019-01-19 00:00:00.000000',32,3,60.00,2,52,1),('2019-02-15 00:00:00.000000',33,1,NULL,0,52,4),('2019-02-15 00:00:00.000000',33,2,NULL,0,52,4),('2019-03-22 00:00:00.000000',19,2,48.00,2,53,3),('2019-04-05 00:00:00.000000',1,4,NULL,0,52,4),('2019-05-13 00:00:00.000000',19,3,18.00,1,52,1),('2019-07-21 00:00:00.000000',10,3,NULL,0,NULL,4),('2019-08-08 00:00:00.000000',32,6,NULL,0,NULL,1),('2019-08-23 00:00:00.000000',10,1,NULL,0,NULL,3),('2019-09-20 00:00:00.000000',19,2,19.00,1,NULL,1),('2019-10-01 00:00:00.000000',10,4,NULL,0,54,5),('2019-12-17 00:00:00.000000',33,3,NULL,0,52,4),('2020-01-29 00:00:00.000000',1,5,74.50,8,52,4),('2020-02-09 00:00:00.000000',11,6,NULL,0,52,4),('2020-03-16 00:00:00.000000',12,7,500.42,1,53,5),('2020-03-29 00:00:00.000000',13,8,16.00,1,52,4),('2020-04-18 00:00:00.000000',11,9,NULL,0,52,4),('2020-05-23 00:00:00.000000',12,1,NULL,0,54,5),('2020-06-16 00:00:00.000000',11,2,NULL,0,53,1),('2020-07-26 00:00:00.000000',12,4,2075.97,12,NULL,5),('2020-09-05 00:00:00.000000',36,3,NULL,0,NULL,5),('2020-10-13 00:00:00.000000',12,5,NULL,0,NULL,5),('2020-11-12 00:00:00.000000',8,6,NULL,0,NULL,3),('2020-12-24 00:00:00.000000',13,7,NULL,0,NULL,4),('2021-01-01 00:00:00.000000',6,8,10.32,1,NULL,1),('2021-01-01 22:00:00.000000',6,9,NULL,0,NULL,1),('2021-01-19 00:00:00.000000',12,9,NULL,0,NULL,5),('2021-02-07 00:00:00.000000',13,10,NULL,0,NULL,5),('2021-03-15 00:00:00.000000',16,11,NULL,0,52,4),('2021-04-02 00:00:00.000000',17,1,NULL,0,54,5),('2021-05-17 00:00:00.000000',13,2,22.00,1,52,3),('2021-05-25 00:00:00.000000',16,4,29.00,1,54,1),('2021-06-12 00:00:00.000000',36,3,NULL,0,52,4),('2021-07-25 00:00:00.000000',16,5,NULL,0,52,3),('2021-09-05 00:00:00.000000',3,7,NULL,0,52,4),('2021-10-14 00:00:00.000000',35,8,9.00,1,NULL,4),('2021-11-10 00:00:00.000000',17,9,NULL,0,NULL,5),('2021-12-05 00:00:00.000000',35,10,14.00,1,NULL,3),('2021-12-15 00:00:00.000000',14,1,NULL,0,NULL,3),('2022-01-13 00:00:00.000000',2,11,NULL,0,NULL,3),('2022-01-28 00:00:00.000000',36,1,NULL,0,NULL,1),('2022-02-03 00:00:00.000000',20,4,NULL,0,NULL,3),('2022-03-17 00:00:00.000000',35,3,4.00,1,NULL,4),('2022-04-26 00:00:00.000000',20,6,10.00,1,NULL,5),('2022-05-04 00:00:00.000000',30,5,NULL,0,54,3),('2022-05-09 00:00:00.000000',7,7,NULL,0,54,1),('2022-05-17 00:00:00.000000',30,8,5.00,1,54,1),('2022-06-09 00:00:00.000000',31,1,NULL,0,54,5),('2022-06-13 00:00:00.000000',33,9,23.00,1,52,4),('2022-07-20 00:00:00.000000',20,10,NULL,0,52,5),('2022-08-25 00:00:00.000000',33,11,NULL,0,54,4),('2022-10-17 00:00:00.000000',18,2,NULL,0,52,3),('2022-10-28 00:00:00.000000',30,4,NULL,0,53,3),('2022-11-12 00:00:00.000000',20,3,NULL,0,53,3),('2022-12-17 00:00:00.000000',18,5,69.00,3,52,1),('2023-01-05 00:00:00.000000',33,6,NULL,0,NULL,3),('2023-02-23 00:00:00.000000',29,8,NULL,0,NULL,1),('2023-03-24 00:00:00.000000',5,10,NULL,0,NULL,4),('2023-04-01 00:00:00.000000',29,11,22.00,1,NULL,1),('2023-04-03 00:00:00.000000',34,12,NULL,0,NULL,3),('2023-04-10 00:00:00.000000',34,9,NULL,0,NULL,4),('2023-05-10 16:30:40.000000',9,6,NULL,1,NULL,4),('2023-05-12 16:30:40.000000',4,6,2000.32,1,52,4),('2023-05-12 16:30:40.000000',13,6,NULL,0,52,3),('2023-05-14 18:30:40.000000',8,3,3.23,1,52,4),('2023-05-15 16:30:40.000000',9,6,NULL,0,52,4),('2023-05-16 16:30:40.000000',9,6,4.23,1,52,4),('2023-05-16 18:30:40.000000',6,10,NULL,0,52,1),('2023-05-19 16:30:40.000000',9,6,NULL,0,103,4),('2023-05-19 16:30:40.000000',9,7,NULL,0,103,4),('2023-05-20 16:30:40.000000',9,7,NULL,0,103,4),('2023-05-20 16:30:40.000000',9,8,NULL,0,103,4),('2024-07-29 16:30:40.000000',13,10,NULL,NULL,NULL,4),('2024-07-29 17:30:40.000000',2,3,NULL,NULL,NULL,4),('2024-07-29 18:30:40.000000',2,4,NULL,NULL,NULL,4),('2024-07-29 18:30:40.000000',8,9,NULL,NULL,NULL,4),('2024-07-29 18:30:40.000000',16,9,NULL,NULL,NULL,4),('2025-07-29 16:30:40.000000',7,4,NULL,NULL,NULL,4),('2025-07-29 16:30:40.000000',7,10,NULL,NULL,52,4),('2025-07-29 16:30:40.000000',8,10,NULL,NULL,52,4),('2025-07-29 16:30:40.000000',13,1,NULL,NULL,52,4),('2025-07-29 17:30:40.000000',2,11,NULL,NULL,52,4),('2025-07-29 18:30:40.000000',16,7,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',3,12,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',4,6,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',4,10,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',7,1,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',8,2,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',19,2,NULL,NULL,NULL,4),('2026-07-29 16:30:40.000000',19,4,NULL,NULL,NULL,4),('2026-07-29 17:30:40.000000',1,11,NULL,NULL,52,4),('2026-07-29 17:30:40.000000',6,3,NULL,NULL,52,4),('2026-07-29 17:30:40.000000',7,9,NULL,NULL,NULL,4),('2026-07-29 17:30:40.000000',8,1,NULL,NULL,NULL,4),('2026-07-29 17:30:40.000000',11,11,NULL,NULL,NULL,4),('2026-07-29 18:30:40.000000',4,5,NULL,NULL,NULL,4),('2026-07-29 18:30:40.000000',5,2,NULL,NULL,NULL,4),('2026-07-29 18:30:40.000000',17,12,NULL,NULL,52,4),('2030-07-10 17:30:40.000000',1,9,NULL,NULL,NULL,4),('2030-08-08 00:00:00.000000',32,5,NULL,NULL,54,1);
/*!40000 ALTER TABLE `emision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `espectador`
--

DROP TABLE IF EXISTS `espectador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espectador` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dinero_gastado` double DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `genero_favorito` varchar(255) NOT NULL,
  `nombre_completo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l00nvotbt9lm9g233jvy82vji` (`nombre_completo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espectador`
--

LOCK TABLES `espectador` WRITE;
/*!40000 ALTER TABLE `espectador` DISABLE KEYS */;
INSERT INTO `espectador` VALUES (2,14.4,'2017-01-13','ANIMACION','Dana Scully'),(3,NULL,'2006-01-01','COMEDIA','El Fumador'),(4,543.03,'1985-07-23','THRILLER','Fox Mulder'),(5,NULL,'2015-04-30','ACCION','Richard Langly'),(6,NULL,'1967-07-14','COMEDIA','Melvin Frohike'),(7,NULL,'1953-07-14','COMEDIA','Dale Cooper'),(12,NULL,'1954-07-14','POLICIACA','Bob'),(13,NULL,'1990-02-10','COMEDIA','El Movidas');
/*!40000 ALTER TABLE `espectador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `espectador_emision`
--

DROP TABLE IF EXISTS `espectador_emision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `espectador_emision` (
  `id` int NOT NULL AUTO_INCREMENT,
  `columna` int DEFAULT NULL,
  `fila` int DEFAULT NULL,
  `gasto_palomitas` double(6,2) DEFAULT NULL,
  `valoracion_servicio` double(4,2) DEFAULT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `id_pelicula` bigint NOT NULL,
  `id_sala` bigint NOT NULL,
  `id_espectador` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7jadobpfif29sobvo725hd4uf` (`fecha`,`id_pelicula`,`id_sala`),
  KEY `FK7tlbhd2qcgdor5xypv1vnivjc` (`id_espectador`),
  CONSTRAINT `FK7jadobpfif29sobvo725hd4uf` FOREIGN KEY (`fecha`, `id_pelicula`, `id_sala`) REFERENCES `emision` (`fecha`, `id_pelicula`, `idSala`),
  CONSTRAINT `FK7tlbhd2qcgdor5xypv1vnivjc` FOREIGN KEY (`id_espectador`) REFERENCES `espectador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `espectador_emision`
--

LOCK TABLES `espectador_emision` WRITE;
/*!40000 ALTER TABLE `espectador_emision` DISABLE KEYS */;
INSERT INTO `espectador_emision` VALUES (2,8,8,0.00,9.40,'2020-01-29 00:00:00.000000',1,5,3),(3,7,14,0.00,6.00,'2018-09-15 00:00:00.000000',15,1,4),(4,6,1,14.00,9.00,'2021-12-05 00:00:00.000000',35,10,4),(5,5,1,23.00,5.00,'2022-06-13 00:00:00.000000',33,9,4),(6,4,1,8.30,5.40,'2020-01-29 00:00:00.000000',1,5,3),(7,3,1,500.42,NULL,'2020-03-16 00:00:00.000000',12,7,4),(8,2,1,22.00,NULL,'2023-04-01 00:00:00.000000',29,11,4),(9,1,1,10.32,NULL,'2021-01-01 00:00:00.000000',6,8,4),(10,9,2,29.00,1.00,'2019-03-22 00:00:00.000000',19,2,3),(11,8,2,22.00,1.00,'2021-05-17 00:00:00.000000',13,2,4),(12,7,2,16.00,7.00,'2020-01-29 00:00:00.000000',1,5,3),(13,6,2,4.00,4.00,'2022-03-17 00:00:00.000000',35,3,4),(14,5,2,16.00,7.00,'2020-03-29 00:00:00.000000',13,8,3),(15,4,2,19.00,3.00,'2019-09-20 00:00:00.000000',19,2,3),(16,3,2,10.00,6.00,'2022-04-26 00:00:00.000000',20,6,3),(17,2,2,9.00,1.89,'2021-10-14 00:00:00.000000',35,8,4),(19,15,4,23.00,3.23,'2022-12-17 00:00:00.000000',18,5,4),(20,3,4,17.00,2.96,'2020-01-29 00:00:00.000000',1,5,4),(22,5,4,5.00,5.46,'2022-05-17 00:00:00.000000',30,8,4),(23,20,4,19.00,3.59,'2019-03-22 00:00:00.000000',19,2,4),(24,4,4,18.00,7.64,'2019-05-13 00:00:00.000000',19,3,4),(27,7,4,8.30,5.40,'2020-01-29 00:00:00.000000',1,5,3),(29,1,4,8.30,7.30,'2020-01-29 00:00:00.000000',1,5,5),(30,8,6,8.30,6.40,'2020-01-29 00:00:00.000000',1,5,2),(31,7,5,8.30,5.40,'2020-01-29 00:00:00.000000',1,5,6),(35,6,5,500.42,5.40,'2020-07-26 00:00:00.000000',12,4,4),(36,5,5,1000.43,5.40,'2020-07-26 00:00:00.000000',12,4,4),(37,4,5,500.42,5.40,'2020-07-26 00:00:00.000000',12,4,4),(38,3,5,8.30,5.40,'2020-07-26 00:00:00.000000',12,4,4),(39,2,5,8.30,5.40,'2020-07-26 00:00:00.000000',12,4,4),(40,1,5,8.30,5.40,'2020-07-26 00:00:00.000000',12,4,4),(41,9,11,8.30,5.40,'2020-07-26 00:00:00.000000',12,4,4),(42,8,10,8.30,2.32,'2020-07-26 00:00:00.000000',12,4,4),(43,7,10,8.30,2.32,'2020-07-26 00:00:00.000000',12,4,4),(44,6,10,8.30,2.32,'2020-07-26 00:00:00.000000',12,4,4),(45,5,10,8.30,2.32,'2020-07-26 00:00:00.000000',12,4,4),(46,4,10,8.30,2.32,'2020-07-26 00:00:00.000000',12,4,4),(47,3,11,8.30,9.40,'2018-07-12 00:00:00.000000',32,4,4),(48,2,11,8.30,9.40,'2018-07-12 00:00:00.000000',32,4,3),(49,1,11,23.00,3.23,'2022-12-17 00:00:00.000000',18,5,4),(50,10,7,23.00,3.23,'2022-12-17 00:00:00.000000',18,5,4),(53,1,10,29.00,1.32,'2021-05-25 00:00:00.000000',16,4,4),(54,10,7,NULL,NULL,'2026-07-29 16:30:40.000000',4,6,4),(57,10,7,NULL,5.23,'2023-05-10 16:30:40.000000',9,6,4),(58,12,5,2000.32,4.10,'2023-05-12 16:30:40.000000',4,6,4),(69,12,5,NULL,5.23,'2026-07-29 17:30:40.000000',11,11,4),(70,2,1,NULL,3.00,'2025-07-29 18:30:40.000000',16,7,7),(71,5,5,3.23,10.00,'2023-05-14 18:30:40.000000',8,3,7),(72,5,5,4.23,5.32,'2023-05-16 16:30:40.000000',9,6,7),(73,1,1,10.00,5.32,'2018-12-23 00:00:00.000000',19,4,7),(74,1,2,10.00,5.32,'2018-12-23 00:00:00.000000',19,4,7),(75,1,6,10.00,5.32,'2018-12-23 00:00:00.000000',19,4,7),(76,1,6,30.00,10.00,'2019-01-19 00:00:00.000000',32,3,7),(77,1,8,30.00,10.00,'2019-01-19 00:00:00.000000',32,3,7);
/*!40000 ALTER TABLE `espectador_emision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) NOT NULL,
  `next_val` bigint DEFAULT NULL,
  PRIMARY KEY (`sequence_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('default',400);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `limpiador`
--

DROP TABLE IF EXISTS `limpiador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `limpiador` (
  `id` bigint NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre_completo` varchar(255) NOT NULL,
  `telefono_movil` varchar(13) NOT NULL,
  `salario` double(6,2) NOT NULL,
  `turno` smallint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_plpi29khxo75uuywibbnohsnt` (`nombre_completo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `limpiador`
--

LOCK TABLES `limpiador` WRITE;
/*!40000 ALTER TABLE `limpiador` DISABLE KEYS */;
INSERT INTO `limpiador` VALUES (1,'brujo@polonia.com','Gerardo El Magias','685122456',500.45,2),(3,'will@taxi.com','Will Smith','625122456',500.45,2),(4,'carlton@belair.com','Carlton Banks','635122456',1500.45,1),(5,'philip@email.com','Philip Banks','645122456',2000.45,1),(152,'sayid@perdidos.com','Sayid Jarrah','605122456',2000.45,0),(302,'rick@zombis.com','Rick Grimes','605429456',2000.45,0);
/*!40000 ALTER TABLE `limpiador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula`
--

DROP TABLE IF EXISTS `pelicula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duracion` int DEFAULT NULL,
  `fecha_fin_emision` date DEFAULT NULL,
  `precio` double(6,2) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `id_director` bigint DEFAULT NULL,
  `genero` varchar(50) NOT NULL,
  `calificacion_edad` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_94jwam3f1prexp7hod5vg6dpv` (`titulo`),
  KEY `FKlrv1wwr9cdtef3qucaub1c6bj` (`id_director`),
  CONSTRAINT `FKlrv1wwr9cdtef3qucaub1c6bj` FOREIGN KEY (`id_director`) REFERENCES `director` (`id`),
  CONSTRAINT `pelicula_chk_1` CHECK ((`duracion` >= 0)),
  CONSTRAINT `pelicula_chk_2` CHECK ((`fecha_fin_emision` <= sysdate())),
  CONSTRAINT `pelicula_chk_3` CHECK ((length(`titulo`) >= 5))
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula`
--

LOCK TABLES `pelicula` WRITE;
/*!40000 ALTER TABLE `pelicula` DISABLE KEYS */;
INSERT INTO `pelicula` VALUES (1,122,NULL,6.10,'Training Day',1,'POLICIACA',NULL),(2,135,NULL,5.00,'Sospechosos Habituales',2,'POLICIACA',7),(3,140,NULL,7.20,'Seven',3,'THRILLER',7),(4,107,'2021-02-07',4.70,'Tesis',4,'THRILLER',7),(5,127,NULL,5.90,'Scarface',5,'GANGSTERS',13),(6,130,NULL,5.20,'El club de la lucha',3,'THRILLER',13),(7,117,'2018-09-15',4.20,'wadwd',5,'POLICIACA',18),(8,130,NULL,5.10,'La Isla Minima',9,'THRILLER',18),(9,117,NULL,6.20,'Memento',10,'THRILLER',18),(10,112,NULL,4.20,'Celda 211',6,'THRILLER',18),(11,133,'2019-09-20',5.60,'El Padrino',7,'GANGSTERS',18),(12,126,'2022-10-23',5.10,'Reservoir Dogs',8,'GANGSTERS',18),(13,177,NULL,7.20,'El Irlandes',14,'GANGSTERS',18),(14,124,NULL,5.20,'Snatch: cerdos y diamantes',15,'GANGSTERS',18),(15,125,NULL,5.20,'Lock And Stock',15,'GANGSTERS',18),(16,120,NULL,4.90,'Kill Bill',8,'ACCION',18),(17,163,NULL,6.20,'Los Odiosos Ocho',8,'ACCION',18),(18,107,'2021-10-14',4.50,'La Jungla de Cristal',11,'ACCION',18),(19,105,NULL,4.00,'Airbag',19,'COMEDIA',18),(20,115,NULL,4.40,'Amanece, que no es poco',20,'COMEDIA',18),(29,126,'2019-05-28',5.20,'La vida de Bryan',21,'COMEDIA',18),(30,118,NULL,4.70,'El Gran Lebowsky',16,'COMEDIA',18),(31,114,NULL,3.90,'La Cena de los Idiotas',17,'COMEDIA',13),(32,110,NULL,5.00,'La Princesa Mononoke',12,'ANIMACION',13),(33,111,'2021-12-05',5.20,'El Viaje de Chihiro',12,'ANIMACION',13),(34,109,NULL,5.70,'Inside Out',14,'ANIMACION',13),(35,119,NULL,5.80,'Souls',13,'ANIMACION',13),(36,106,'2019-04-13',4.00,'El Rey Leon',8,'ANIMACION',18),(37,122,NULL,6.10,'La Movida 2',1,'POLICIACA',NULL),(38,122,NULL,6.10,'La Movida 3',1,'POLICIACA',NULL),(39,122,NULL,6.10,'La Movida 4',1,'POLICIACA',NULL),(40,125,NULL,5.20,'La Movida : El Retorno del Huevo de Dragon Parte 1',15,'GANGSTERS',NULL);
/*!40000 ALTER TABLE `pelicula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sala` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `numero` int NOT NULL,
  `aforo` int DEFAULT NULL,
  `premium` char(1) NOT NULL,
  `numero_filas` int NOT NULL,
  `numero_columnas` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `sala_chk_1` CHECK (((`premium` = _utf8mb4'S') or (`premium` = _utf8mb4'N')))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
INSERT INTO `sala` VALUES (1,1,100,'N',20,30),(2,2,120,'N',20,30),(3,4,80,'S',15,30),(4,3,160,'N',15,30),(5,5,8,'S',10,10),(6,6,70,'N',20,50),(7,7,100,'N',20,50),(8,8,60,'S',20,50),(9,9,50,'S',20,50),(10,10,140,'N',10,30),(11,11,135,'N',10,40),(12,12,45,'S',10,40),(13,33,11,'S',10,40),(14,22,123,'S',20,30);
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ejercicio1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 10:57:04
