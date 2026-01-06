-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: koperasi
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'Yanto','Yanto123'),(2,'Yanti','Yanti098');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_transaksi`
--

DROP TABLE IF EXISTS `detail_transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_transaksi` (
  `id_detail_transaksi` int NOT NULL AUTO_INCREMENT,
  `jumlah_pembelian` int NOT NULL,
  `harga_saat_transaksi` double NOT NULL,
  `id_transaksi` int DEFAULT NULL,
  `id_panen` int DEFAULT NULL,
  PRIMARY KEY (`id_detail_transaksi`),
  KEY `id_transaksi` (`id_transaksi`),
  KEY `id_panen` (`id_panen`),
  CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`),
  CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`id_panen`) REFERENCES `hasil_panen` (`id_panen`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_transaksi`
--

LOCK TABLES `detail_transaksi` WRITE;
/*!40000 ALTER TABLE `detail_transaksi` DISABLE KEYS */;
INSERT INTO `detail_transaksi` VALUES (2,2,15000,2,1);
/*!40000 ALTER TABLE `detail_transaksi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hasil_panen`
--

DROP TABLE IF EXISTS `hasil_panen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hasil_panen` (
  `id_panen` int NOT NULL AUTO_INCREMENT,
  `nama_hasil_panen` varchar(100) NOT NULL,
  `jenis_hasil_panen` varchar(50) DEFAULT NULL,
  `harga_jual` double NOT NULL,
  `stok` int DEFAULT '0',
  `id_petani` int DEFAULT NULL,
  PRIMARY KEY (`id_panen`),
  KEY `id_petani` (`id_petani`),
  CONSTRAINT `hasil_panen_ibfk_1` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hasil_panen`
--

LOCK TABLES `hasil_panen` WRITE;
/*!40000 ALTER TABLE `hasil_panen` DISABLE KEYS */;
INSERT INTO `hasil_panen` VALUES (1,'Tomat','Sayur',15000,98,1);
/*!40000 ALTER TABLE `hasil_panen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pembeli`
--

DROP TABLE IF EXISTS `pembeli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pembeli` (
  `id_pembeli` int NOT NULL AUTO_INCREMENT,
  `nama_pembeli` varchar(255) NOT NULL,
  `tipe_pembeli` enum('reseller','umum') NOT NULL,
  `nomor_hp_pembeli` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_pembeli`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pembeli`
--

LOCK TABLES `pembeli` WRITE;
/*!40000 ALTER TABLE `pembeli` DISABLE KEYS */;
INSERT INTO `pembeli` VALUES (1,'Aang','umum','04852169522');
/*!40000 ALTER TABLE `pembeli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petani`
--

DROP TABLE IF EXISTS `petani`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `petani` (
  `id_petani` int NOT NULL AUTO_INCREMENT,
  `nama_petani` varchar(255) NOT NULL,
  `alamat_petani` varchar(255) DEFAULT NULL,
  `nomor_hp_petani` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_petani`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petani`
--

LOCK TABLES `petani` WRITE;
/*!40000 ALTER TABLE `petani` DISABLE KEYS */;
INSERT INTO `petani` VALUES (1,'Yanto','Br. Kaje, Desa Arah','0258741693');
/*!40000 ALTER TABLE `petani` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi` (
  `id_transaksi` int NOT NULL AUTO_INCREMENT,
  `tanggal_waktu_transaksi` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_harga_transaksi` double DEFAULT NULL,
  `id_pembeli` int DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`),
  KEY `id_pembeli` (`id_pembeli`),
  CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_pembeli`) REFERENCES `pembeli` (`id_pembeli`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi`
--

LOCK TABLES `transaksi` WRITE;
/*!40000 ALTER TABLE `transaksi` DISABLE KEYS */;
INSERT INTO `transaksi` VALUES (2,'2025-11-09 12:54:15',150000,1);
/*!40000 ALTER TABLE `transaksi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-05 16:15:24
