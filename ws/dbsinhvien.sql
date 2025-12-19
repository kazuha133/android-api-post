-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 19, 2019 at 11:57 AM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbsinhvien`
--
CREATE DATABASE IF NOT EXISTS `dbsinhvien` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `dbsinhvien`;

-- --------------------------------------------------------

--
-- Table structure for table `sinhvien_tbl`
--

DROP TABLE IF EXISTS `sinhvien_tbl`;
CREATE TABLE IF NOT EXISTS `sinhvien_tbl` (
  `MaSV` int(11) NOT NULL,
  `TenSV` text NOT NULL,
  PRIMARY KEY (`MaSV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sinhvien_tbl`
--

INSERT INTO `sinhvien_tbl` (`MaSV`, `TenSV`) VALUES
(1, 'Nguyen Ti'),
(2, 'Tran Suu'),
(3, 'Le Dan'),
(9, 'SV9'),
(19, 'SV19'),
(29, 'SV29');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
