-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2019 at 06:09 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+08:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE IF EXISTS teller_db;
CREATE DATABASE IF NOT EXISTS teller_db;
USE teller_db;
--
-- Database: `teller_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `teller`
--

CREATE TABLE `teller` (
  `teller_id` int(11) NOT NULL,
  `teller_first_name` varchar(50) NOT NULL,
  `teller_middle_name` varchar(50) DEFAULT NULL,
  `teller_last_name` varchar(50) NOT NULL,
  `teller_username` varchar(20) NOT NULL,
  `teller_password` varchar(50) NOT NULL,
  `teller_access` int(3) NOT NULL DEFAULT '1',
  `teller_date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `teller_date_updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_first_name` varchar(50) NOT NULL,
  `customer_middle_name` varchar(50) DEFAULT NULL,
  `customer_last_name` varchar(50) NOT NULL,
  `customer_address` varchar(100) NOT NULL,
  `customer_occupation` varchar(50),
  `customer_description` varchar(200),
  `customer_date_joined` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_date_updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `teller_id` int(11) NOT NULL
  
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` varchar(12) NOT NULL,
  `account_type` varchar(1) NOT NULL,
  `account_currency` varchar(3) NOT NULL,
  `account_sequence` int(7) NOT NULL,
  `account_overdraft` DECIMAL(26,5) DEFAULT 0,
  `account_date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

-- --------------------------------------------------------

--
-- Table structure for table `ledger_entry`
--

CREATE TABLE `ledger_entry` (
  `entry_id` int(11) NOT NULL,
  `entry_type` varchar(3) NOT NULL,
  `entry_amount` DECIMAL(26,5) NOT NULL,
  `entry_balance` DECIMAL(26,5) NOT NULL,
  `entry_balance_status` varchar(6) NOT NULL,
  `entry_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `recipient_entry_id` int(11),
  `account_id` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `customer_id_fk` (`customer_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `ledger_entry`
--
ALTER TABLE `ledger_entry`
  ADD PRIMARY KEY (`entry_id`),
  ADD KEY `account_id_fk` (`account_id`);

--
-- Indexes for table `teller`
--
ALTER TABLE `teller`
  ADD PRIMARY KEY (`teller_id`),
  ADD UNIQUE KEY `teller_username` (`teller_username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` varchar(12) NOT NULL;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ledger_entry`
--
ALTER TABLE `ledger_entry`
  MODIFY `entry_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `teller`
--
ALTER TABLE `teller`
  MODIFY `teller_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `teller_id_fk` FOREIGN KEY (`teller_id`) REFERENCES `teller` (`teller_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ledger_entry`
--
ALTER TABLE `ledger_entry`
  ADD CONSTRAINT `account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO teller 
	( teller_first_name, 
	  teller_middle_name, 
	  teller_last_name,
	  teller_username, 
	  teller_password, 
	  teller_access)
   VALUES
   (  'Jade Ericson', 
	  'Malaki',
	  'Adriano',
	  'jadriano', 
	  'pass', 
	  3);  

INSERT INTO teller 
	( teller_first_name, 
	  teller_middle_name, 
	  teller_last_name,
	  teller_username, 
	  teller_password, 
	  teller_access)
   VALUES
   (  'Ez Pz', 
	  'Lemon',
	  'Squeezy',
	  'lemon', 
	  'ade', 
	  2);  

INSERT INTO teller 
	( teller_first_name, 
	  teller_middle_name, 
	  teller_last_name,
	  teller_username, 
	  teller_password, 
	  teller_access)
   VALUES
   (  'Michael', 
	  'John',
	  'Mananghaya',
	  'mjmanang', 
	  'pass', 
	  1);  

INSERT INTO customer 
   VALUES
   (  1, 
	  'Jade Ericson', '',
	  'Adriano',
	  'Cannes Bldg. Kassel Residences, Parañaque City', 
	  'Software Engineer', 
	  '',
	  CURRENT_TIMESTAMP,
	  CURRENT_TIMESTAMP,
	  2);  

INSERT INTO customer 
   VALUES
   (  2, 
	  'John', '',
	  'Mananghaya',
	  '123 Kalsada Street, Brgy Village, Syudad City', 
	  'ML Expert', 
	  '',
	  CURRENT_TIMESTAMP,
	  CURRENT_TIMESTAMP,
	  2);  

COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
