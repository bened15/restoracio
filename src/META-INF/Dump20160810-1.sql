-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: restoracio
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `adm_customer`
--

DROP TABLE IF EXISTS `adm_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adm_customer` (
  `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME` varchar(50) NOT NULL,
  `CUSTOMER_LASTNAME` varchar(50) DEFAULT NULL,
  `CUSTOMER_ADDRESS1` varchar(500) NOT NULL,
  `CUSTOMER_ADDRESS2` varchar(500) DEFAULT NULL,
  `CUSTOMER_PHONE1` varchar(45) NOT NULL,
  `CUSTOMER_PHONE2` varchar(45) DEFAULT NULL,
  `CUSTOMER_EMAIL` varchar(100) DEFAULT NULL,
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `adm_reservation`
--

DROP TABLE IF EXISTS `adm_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adm_reservation` (
  `RESERVATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` int(11) NOT NULL,
  `RESERVATION_DATE` date NOT NULL,
  `ADVANCE_PAYMENT_AMMOUNT` float DEFAULT '0',
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PAYMENT_METHOD_ID` int(11) DEFAULT NULL,
  `COMMENTS` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`RESERVATION_ID`),
  KEY `FK_RESERVATION_CUSTOMER_idx` (`CUSTOMER_ID`),
  KEY `FK_RESERVATION_PAYMENT_METHOD_idx` (`PAYMENT_METHOD_ID`),
  CONSTRAINT `FK_RESERVATION_CUSTOMER` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `adm_customer` (`CUSTOMER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RESERVATION_PAYMENT_METHOD` FOREIGN KEY (`PAYMENT_METHOD_ID`) REFERENCES `ctg_payment_method` (`PAYMENT_METHOD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_discount`
--

DROP TABLE IF EXISTS `ctg_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_discount` (
  `DISCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DISCOUNT_NAME` varchar(75) NOT NULL,
  `DISCOUNT_DESCRIPTION` varchar(500) DEFAULT NULL,
  `DISCOUNT_VALID_SINCE` datetime NOT NULL,
  `DISCOUNT_VALID_SINCE_TIME` varchar(4) DEFAULT NULL,
  `DISCOUNT_VALID_UNTIL` datetime NOT NULL,
  `DISCOUNT_VALID_UNTIL_TIME` varchar(4) DEFAULT NULL,
  `DISCOUNT_PERCENTAGE` int(2) DEFAULT NULL,
  `ENTRY_DATE` datetime NOT NULL,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  `MENU_TYPE_ID` int(11) DEFAULT NULL,
  `TOTAL_PRODUCTS` int(4) DEFAULT '0',
  PRIMARY KEY (`DISCOUNT_ID`),
  KEY `FK_MENU_TYPE_ID_FROM_DISCOUNT_idx` (`MENU_TYPE_ID`),
  CONSTRAINT `FK_MENU_TYPE_ID_FROM_DISCOUNT` FOREIGN KEY (`MENU_TYPE_ID`) REFERENCES `ctg_menu_type` (`MENU_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`ctg_discount_BEFORE_UPDATE` BEFORE UPDATE ON `ctg_discount` FOR EACH ROW
BEGIN
    SET NEW.TOTAL_PRODUCTS = (SELECT COUNT(1) FROM ctg_discount_menu where discount_id = NEW.discount_id);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ctg_discount_menu`
--

DROP TABLE IF EXISTS `ctg_discount_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_discount_menu` (
  `MENU_ITEM_DISCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `DISCOUNT_ID` int(11) NOT NULL,
  PRIMARY KEY (`MENU_ITEM_DISCOUNT_ID`),
  KEY `FK_DISCOUNT_PRODUCT_1_idx` (`DISCOUNT_ID`),
  KEY `FK_DISCOUNT_MENU_ITEM_idx` (`MENU_ITEM_ID`),
  CONSTRAINT `FK_DISCOUNT_MENU_ITEM` FOREIGN KEY (`MENU_ITEM_ID`) REFERENCES `rest_menu_item` (`MENU_ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_DISCOUNT_PRODUCT_1` FOREIGN KEY (`DISCOUNT_ID`) REFERENCES `ctg_discount` (`DISCOUNT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`ctg_discount_menu_BEFORE_INSERT` BEFORE INSERT ON `ctg_discount_menu` FOR EACH ROW
BEGIN
	UPDATE CTG_DISCOUNT
    SET TOTAL_PRODUCTS = TOTAL_PRODUCTS + 1
    WHERE DISCOUNT_ID = NEW.DISCOUNT_ID; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`ctg_discount_menu_AFTER_DELETE` AFTER DELETE ON `ctg_discount_menu` FOR EACH ROW
BEGIN
	UPDATE CTG_DISCOUNT
    SET TOTAL_PRODUCTS = TOTAL_PRODUCTS -1
    WHERE DISCOUNT_ID = OLD.DISCOUNT_ID; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ctg_measure_unit`
--

DROP TABLE IF EXISTS `ctg_measure_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_measure_unit` (
  `MEASURE_UNIT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MEASURE_NAME` varchar(50) NOT NULL,
  `MEASURE_UNI` varchar(2) NOT NULL,
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`MEASURE_UNIT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_menu_language`
--

DROP TABLE IF EXISTS `ctg_menu_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_menu_language` (
  `MENU_LANGUAE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LANGUAGE_NAME` varchar(50) NOT NULL,
  `LANGUAGE_ISO2` varchar(2) DEFAULT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) NOT NULL,
  PRIMARY KEY (`MENU_LANGUAE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_menu_type`
--

DROP TABLE IF EXISTS `ctg_menu_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_menu_type` (
  `MENU_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_TYPE_NAME` varchar(50) NOT NULL,
  `MENU_TYPE_DESCRIPTION` varchar(256) DEFAULT NULL,
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PRIORITY` int(3) DEFAULT NULL,
  PRIMARY KEY (`MENU_TYPE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_payment_method`
--

DROP TABLE IF EXISTS `ctg_payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_payment_method` (
  `PAYMENT_METHOD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `ENTRY_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`PAYMENT_METHOD_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_product_log`
--

DROP TABLE IF EXISTS `ctg_product_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_product_log` (
  `PRODUCT_LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) DEFAULT NULL,
  `PRODUCT_NAME` varchar(100) NOT NULL,
  `PRODUCT_DESCRIPTION` varchar(500) DEFAULT NULL,
  `PRODUCT_QTY_AVAILABLE` float NOT NULL DEFAULT '0',
  `PRODUCT_QTY_TRESHOLD` float NOT NULL DEFAULT '0',
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(45) NOT NULL,
  `MEASURE_UNIT` int(11) DEFAULT NULL,
  `SUPPLIER_ID` varchar(45) NOT NULL,
  PRIMARY KEY (`PRODUCT_LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_product_type`
--

DROP TABLE IF EXISTS `ctg_product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_product_type` (
  `PRODUCT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE_NAME` varchar(100) DEFAULT NULL,
  `TYPE_DESCRIPTION` varchar(1000) DEFAULT NULL,
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PRODUCT_TYPE_ID`),
  UNIQUE KEY `TYPE_NAME_UNIQUE` (`TYPE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_supplier`
--

DROP TABLE IF EXISTS `ctg_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_supplier` (
  `SUPPLIER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SUPPLIER_NAME` varchar(45) DEFAULT NULL,
  `CONTACT_NAME` varchar(45) NOT NULL,
  `CONTACT_LASTNAME` varchar(45) NOT NULL,
  `ADDRESS` varchar(500) DEFAULT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `SUPPLIER_PHONE_NUMBER` varchar(45) DEFAULT NULL,
  `SUPPLIER_EMAIL` varchar(45) DEFAULT NULL,
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CONTACT_EMAIL` varchar(45) DEFAULT NULL,
  `CONTACT_PHONE_NUMBER` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SUPPLIER_ID`),
  UNIQUE KEY `SUPPLIER_NAME_UNIQUE` (`SUPPLIER_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ctg_transaction_type`
--

DROP TABLE IF EXISTS `ctg_transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ctg_transaction_type` (
  `TRANSACTION_TYPE_ID` varchar(3) NOT NULL,
  `TRANSACTION_TYPE_NAME` varchar(50) NOT NULL,
  `TRANSACTION_TYPE_DESC` varchar(256) NOT NULL,
  `TRANSACTION_ADDITION` int(1) DEFAULT '1',
  PRIMARY KEY (`TRANSACTION_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inv_inventory_availability`
--

DROP TABLE IF EXISTS `inv_inventory_availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inv_inventory_availability` (
  `ID_INVENTORY_AVAILABILITY` int(11) NOT NULL,
  PRIMARY KEY (`ID_INVENTORY_AVAILABILITY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inv_inventory_waste`
--

DROP TABLE IF EXISTS `inv_inventory_waste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inv_inventory_waste` (
  `ID_INVENTORY_WASTE` int(11) NOT NULL AUTO_INCREMENT,
  `INV_PRODUCT_ID` int(11) NOT NULL,
  `PRODUCT_QTY_WASTE` int(11) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) NOT NULL,
  `STATE` varchar(1) DEFAULT 'A',
  PRIMARY KEY (`ID_INVENTORY_WASTE`),
  KEY `FK_INV_PRODUCT_ID_idx` (`INV_PRODUCT_ID`),
  CONSTRAINT `FK_INV_PRODUCT_ID` FOREIGN KEY (`INV_PRODUCT_ID`) REFERENCES `inv_product_item` (`INV_PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`inv_inventory_waste_BEFORE_INSERT` BEFORE INSERT ON `inv_inventory_waste` FOR EACH ROW
BEGIN
  DECLARE VAR_PRODUCT_AVAILABILITY INT DEFAULT 0;
  SET VAR_PRODUCT_AVAILABILITY = (SELECT PRODUCT_QTY_AVAILABILITY  FROM INV_PRODUCT_ITEM WHERE INV_PRODUCT_ID=NEW.INV_PRODUCT_ID);
  IF (VAR_PRODUCT_AVAILABILITY = 0 OR VAR_PRODUCT_AVAILABILITY < NEW.PRODUCT_QTY_WASTE)
  THEN
    SET NEW= concat('Error: Cannot insert record. The product waste is greather than quantity available = ',VAR_PRODUCT_AVAILABILITY) ;
  END IF;
  
  update INV_PRODUCT_ITEM 
  set product_qty_availability = product_qty_availability - new.product_qty_waste
  WHERE INV_PRODUCT_ID=NEW.INV_PRODUCT_ID;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `inv_product_item`
--

DROP TABLE IF EXISTS `inv_product_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inv_product_item` (
  `INV_PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) NOT NULL,
  `PRODUCT_QTY` int(11) NOT NULL,
  `PRODUCT_PRICE` float NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) NOT NULL,
  `TRANSACTION_TYPE_ID` varchar(3) NOT NULL,
  `STATE` varchar(10) NOT NULL COMMENT 'OPEN\nCLOSECANCELED\nRESERVED',
  `PRODUCT_QTY_AVAILABILITY` int(11) NOT NULL,
  `EXPIRATION_DATE` date DEFAULT NULL,
  PRIMARY KEY (`INV_PRODUCT_ID`),
  KEY `FK_CTG_PRODUCT_idx` (`PRODUCT_ID`),
  CONSTRAINT `FK_CTG_PRODUCT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `rest_product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`inv_product_item_BEFORE_INSERT` BEFORE INSERT ON `inv_product_item` FOR EACH ROW
BEGIN
	SET NEW.PRODUCT_QTY_AVAILABILITY = NEW.PRODUCT_QTY;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`inv_product_item_AFTER_INSERT` AFTER INSERT ON `inv_product_item` FOR EACH ROW
BEGIN
   IF NEW.TRANSACTION_TYPE_ID = 'PRC' OR NEW.TRANSACTION_TYPE_ID = 'PRV'   THEN
		UPDATE rest_product pro
			SET pro.PRODUCT_QTY_AVAILABILITY = IFNULL(pro.PRODUCT_QTY_AVAILABILITY,0)+ NEW.PRODUCT_QTY
		WHERE pro.Product_id = NEW.PRODUCT_ID;
   END IF;

   IF NEW.TRANSACTION_TYPE_ID = 'SLD' OR NEW.TRANSACTION_TYPE_ID = 'WST' THEN
		UPDATE rest_product pro
			SET pro.PRODUCT_QTY_AVAILABILITY = IFNULL(pro.PRODUCT_QTY_AVAILABILITY,0)- NEW.PRODUCT_QTY
		WHERE pro.Product_id = NEW.PRODUCT_ID;
   END IF;

 CALL inv_update_automatic(NEW.PRODUCT_ID, NEW.PRODUCT_QTY, NEW.INV_PRODUCT_ID );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `inv_product_transaction_log`
--

DROP TABLE IF EXISTS `inv_product_transaction_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inv_product_transaction_log` (
  `INV_PRODUCT_TRNS_ID` int(22) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` int(11) NOT NULL,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `PRODUCT_ID` int(11) NOT NULL,
  `PRODUCT_QTY` float NOT NULL,
  `INV_PRODUCT_ID` int(11) DEFAULT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  `TRANSACTION_TYPE_ID` varchar(3) NOT NULL,
  `STATE` varchar(10) NOT NULL COMMENT 'OPEN\nCLOSECANCELED\nRESERVED\nSOLD\nLOCKED',
  `ENTRY_DATE_CNL` datetime DEFAULT NULL,
  `ENTRY_USER_CNL` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`INV_PRODUCT_TRNS_ID`),
  KEY `FK_CTG_PRODUCT_idx` (`PRODUCT_ID`),
  CONSTRAINT `FK_PRD_TRNS_LOG` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `rest_product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_area`
--

DROP TABLE IF EXISTS `rest_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_area` (
  `AREA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AREA_NAME` varchar(100) NOT NULL,
  `IS_SMOKER_AREA` tinyint(4) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) NOT NULL,
  PRIMARY KEY (`AREA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_bill`
--

DROP TABLE IF EXISTS `rest_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_bill` (
  `BILL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TABLE_ACCOUNT_ID` int(11) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) NOT NULL,
  `BILL_SUBTOTAL` float NOT NULL,
  `BILL_TIP` float NOT NULL,
  `BILL_TOTAL` float NOT NULL,
  `PAYMENT_METHOD_ID` int(11) DEFAULT NULL,
  `SHIFT_ID` int(11) DEFAULT NULL,
  `BILL_NAME` varchar(85) DEFAULT NULL,
  `ID_DISCOUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`BILL_ID`),
  KEY `FK_BILL_PAYMENTMETHOD_idx` (`PAYMENT_METHOD_ID`),
  KEY `FK_BILL_TABLEACCOUNT_idx` (`TABLE_ACCOUNT_ID`),
  KEY `FK_CTG_DISCOUNT_idx_idx` (`ID_DISCOUNT`),
  CONSTRAINT `FK_BILL_PAYMENTMETHOD` FOREIGN KEY (`PAYMENT_METHOD_ID`) REFERENCES `ctg_payment_method` (`PAYMENT_METHOD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BILL_TABLEACCOUNT` FOREIGN KEY (`TABLE_ACCOUNT_ID`) REFERENCES `rest_table_account` (`TABLE_ACCOUNT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_CTG_DISCOUNT_idx` FOREIGN KEY (`ID_DISCOUNT`) REFERENCES `ctg_discount` (`DISCOUNT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_bill_detail`
--

DROP TABLE IF EXISTS `rest_bill_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_bill_detail` (
  `BILL_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BILL_ID` int(11) NOT NULL,
  `BILL_DETAIL_SUBTOTAL` float NOT NULL,
  `BILL_DETAIL_TOTAL` float NOT NULL,
  `ORDER_ID` int(11) NOT NULL,
  PRIMARY KEY (`BILL_DETAIL_ID`),
  KEY `FK_BILLDETAIL_BILL_idx` (`BILL_ID`),
  KEY `FK_BILLDETAIL_ORDER_idx` (`ORDER_ID`),
  CONSTRAINT `FK_BILLDETAIL_BILL` FOREIGN KEY (`BILL_ID`) REFERENCES `rest_bill` (`BILL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BILLDETAIL_ORDER` FOREIGN KEY (`ORDER_ID`) REFERENCES `rest_order` (`ORDER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_bill_detail_x_discount`
--

DROP TABLE IF EXISTS `rest_bill_detail_x_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_bill_detail_x_discount` (
  `bill_id` int(11) NOT NULL,
  `bill_detail_id` int(11) NOT NULL,
  `discount_id` int(11) NOT NULL,
  `discount_value` float NOT NULL,
  `bill_discount_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`bill_discount_id`),
  KEY `FK_BILL_DISCOUNT_DISCOUNT_idx` (`discount_id`),
  KEY `FK_BILL_DISCOUNT_BILL_DETAIL_idx` (`bill_detail_id`),
  KEY `FK_BILL_DISCOUNT_BILL_idx` (`bill_id`),
  CONSTRAINT `FK_BILL_DISCOUNT_BILL` FOREIGN KEY (`bill_id`) REFERENCES `rest_bill` (`BILL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BILL_DISCOUNT_BILL_DETAIL` FOREIGN KEY (`bill_detail_id`) REFERENCES `rest_bill_detail` (`BILL_DETAIL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BILL_DISCOUNT_DISCOUNT` FOREIGN KEY (`discount_id`) REFERENCES `ctg_discount` (`DISCOUNT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_bill_payment`
--

DROP TABLE IF EXISTS `rest_bill_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_bill_payment` (
  `ID_BILL_PAYMENT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PAYMENT_METHOD` int(11) NOT NULL,
  `AMOUNT` float NOT NULL,
  `ID_BILL` int(11) DEFAULT NULL,
  `COMMENTS` varchar(85) DEFAULT NULL,
  PRIMARY KEY (`ID_BILL_PAYMENT`),
  KEY `FK_BILLPAY_PAYMETH_idx` (`ID_PAYMENT_METHOD`),
  KEY `FK_BILLPAY_BILL_idx` (`ID_BILL`),
  CONSTRAINT `FK_BILLPAY_BILL` FOREIGN KEY (`ID_BILL`) REFERENCES `rest_bill` (`BILL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BILLPAY_PAYMETH` FOREIGN KEY (`ID_PAYMENT_METHOD`) REFERENCES `ctg_payment_method` (`PAYMENT_METHOD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_information`
--

DROP TABLE IF EXISTS `rest_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_information` (
  `INFORMATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REST_NAME` varchar(100) NOT NULL,
  `REST_LOGO_IMAGE` blob,
  `INF_ACTIVE` tinyint(4) NOT NULL,
  `REST_ADDRESS` varchar(500) DEFAULT NULL,
  `REST_PHONE1` varchar(45) DEFAULT NULL,
  `REST_PHONE2` varchar(45) DEFAULT NULL,
  `REST_EMAIL` varchar(100) DEFAULT NULL,
  `REST_NIT` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`INFORMATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_kitchen`
--

DROP TABLE IF EXISTS `rest_kitchen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_kitchen` (
  `KITCHEN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `KITCHEN_NAME` varchar(45) DEFAULT NULL,
  `KITCHEN_DESCRIPTION` varchar(256) DEFAULT NULL,
  `KITCHEN_PRINTER` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`KITCHEN_ID`),
  UNIQUE KEY `KITCHEN_NAME_UNIQUE` (`KITCHEN_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_menu_item`
--

DROP TABLE IF EXISTS `rest_menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_menu_item` (
  `MENU_ITEM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_NAME` varchar(100) NOT NULL,
  `MENU_ITEM_DESCRIPTION` varchar(500) NOT NULL,
  `MENU_ITEM_PRICE` float NOT NULL,
  `AVAILABLE` tinyint(4) DEFAULT '1',
  `MENU_TYPE_ID` int(11) NOT NULL,
  `MENU_IMAGE` longblob,
  `KITCHEN_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`MENU_ITEM_ID`),
  KEY `FK_MENUITEM_MENUTYPE_idx` (`MENU_TYPE_ID`),
  KEY `FK_MENUITEM_KITCHEN_idx` (`KITCHEN_ID`),
  CONSTRAINT `FK_MENUITEM_KITCHEN` FOREIGN KEY (`KITCHEN_ID`) REFERENCES `rest_kitchen` (`KITCHEN_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_MENUITEM_MENUTYPE` FOREIGN KEY (`MENU_TYPE_ID`) REFERENCES `ctg_menu_type` (`MENU_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_menu_item_comments`
--

DROP TABLE IF EXISTS `rest_menu_item_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_menu_item_comments` (
  `MENU_ITEM_COMMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `COMMENT` varchar(100) NOT NULL,
  `COMMENT_DESCRIPTION` varchar(256) NOT NULL,
  PRIMARY KEY (`MENU_ITEM_COMMENT_ID`),
  KEY `FK_ITEMPROD_MENUITEM_idx` (`MENU_ITEM_ID`),
  CONSTRAINT `FK_ITEMCOMM_MENUITEM` FOREIGN KEY (`MENU_ITEM_ID`) REFERENCES `rest_menu_item` (`MENU_ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_menu_item_product`
--

DROP TABLE IF EXISTS `rest_menu_item_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_menu_item_product` (
  `MENU_ITEM_PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `PRODUCT_ID` int(11) NOT NULL,
  `MEASURE_UNIT_ID` int(11) NOT NULL,
  `QUANTITY` float NOT NULL,
  PRIMARY KEY (`MENU_ITEM_PRODUCT_ID`),
  KEY `FK_ITEMPROD_MEASURE_idx` (`MEASURE_UNIT_ID`),
  KEY `FK_ITEMPROD_MENUITEM_idx` (`MENU_ITEM_ID`),
  KEY `FK_ITEMPROD_PRODUCT_idx` (`PRODUCT_ID`),
  CONSTRAINT `FK_ITEMPROD_MEASURE` FOREIGN KEY (`MEASURE_UNIT_ID`) REFERENCES `ctg_measure_unit` (`MEASURE_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ITEMPROD_MENUITEM` FOREIGN KEY (`MENU_ITEM_ID`) REFERENCES `rest_menu_item` (`MENU_ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ITEMPROD_PRODUCT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `rest_product` (`PRODUCT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_menu_item_type`
--

DROP TABLE IF EXISTS `rest_menu_item_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_menu_item_type` (
  `MENU_ITEM_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `MENU_TYPE_ID` int(11) NOT NULL,
  `AVAILABLE` tinyint(4) NOT NULL,
  PRIMARY KEY (`MENU_ITEM_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_order`
--

DROP TABLE IF EXISTS `rest_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_order` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `ORDER_COMMENT` varchar(500) DEFAULT NULL,
  `ORDER_STATUS` varchar(45) NOT NULL COMMENT 'CREATED\nIN PROGRESS\nREADY\nCOMPLETED',
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ATTENDANT` varchar(25) DEFAULT NULL,
  `TABLE_ACCOUNT_ID` int(11) NOT NULL,
  `ID_SHIFT` int(11) NOT NULL,
  `PRIORITY` int(3) DEFAULT NULL,
  `DISCOUNT_ID` int(11) DEFAULT NULL COMMENT 'Automatic Discount ID\n',
  `DISCOUNT_VALUE` float DEFAULT '0' COMMENT 'If Automatic Discount ID is different from 0 then this field will be calculated with after insert trigger',
  PRIMARY KEY (`ORDER_ID`),
  KEY `FK_ORDER_TABLEACCOUNT_idx` (`TABLE_ACCOUNT_ID`),
  KEY `FK_ORDER_MENUITEM_idx` (`MENU_ITEM_ID`),
  KEY `FK_ORDER_SHIFT_idx` (`ID_SHIFT`),
  KEY `FK_ORDER_DISCOUNT_idx` (`DISCOUNT_ID`),
  CONSTRAINT `FK_ORDER_DISCOUNT` FOREIGN KEY (`DISCOUNT_ID`) REFERENCES `ctg_discount` (`DISCOUNT_ID`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_ORDER_MENUITM` FOREIGN KEY (`MENU_ITEM_ID`) REFERENCES `rest_menu_item` (`MENU_ITEM_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_SHIFT_` FOREIGN KEY (`ID_SHIFT`) REFERENCES `rest_shift` (`ID_SHIFT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_TABLEACCOUNT` FOREIGN KEY (`TABLE_ACCOUNT_ID`) REFERENCES `rest_table_account` (`TABLE_ACCOUNT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`rest_order_BEFORE_INSERT` BEFORE INSERT ON `rest_order` FOR EACH ROW
BEGIN
    CALL apply_automatic_discount(NEW.MENU_ITEM_ID,NEW.ORDER_ID,CURRENT_TIMESTAMP,@DISCOUNT_ID,@DISCOUNT_VALUE);
	IF  @DISCOUNT_ID != 0 THEN
		SET NEW.DISCOUNT_ID = @DISCOUNT_ID;
		SET NEW.DISCOUNT_VALUE = ROUND (@DISCOUNT_VALUE,2);
    END IF;
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`rest_order_AFTER_INSERT` AFTER INSERT ON `rest_order` FOR EACH ROW
BEGIN
    CALL inv_update(NEW.MENU_ITEM_ID,NEW.ORDER_ID,NEW.ATTENDANT,'SLD');


	INSERT INTO REST_ORDER_LOG
			(ORDER_ID,MENU_ITEM_ID,ORDER_COMMENT,ORDER_STATUS,ENTRY_DATE,ATTENDANT,TABLE_ACCOUNT_ID,ID_SHIFT)
	VALUES
			(NEW.ORDER_ID,NEW.MENU_ITEM_ID,NEW.ORDER_COMMENT,NEW.ORDER_STATUS,NEW.ENTRY_DATE,NEW.ATTENDANT,NEW.TABLE_ACCOUNT_ID,NEW.ID_SHIFT);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`rest_order_AFTER_UPDATE` AFTER UPDATE ON `rest_order` FOR EACH ROW
BEGIN

	IF NEW.ORDER_STATUS = 'CANCELED' AND OLD.ORDER_STATUS != 'CANCELED' THEN
		CALL inv_update(NEW.MENU_ITEM_ID,NEW.ORDER_ID,NEW.ATTENDANT,'CNL');
	END IF;
    INSERT INTO REST_ORDER_LOG
			(ORDER_ID,MENU_ITEM_ID,ORDER_COMMENT,ORDER_STATUS,ENTRY_DATE,ATTENDANT,TABLE_ACCOUNT_ID,ID_SHIFT)
	VALUES
			(NEW.ORDER_ID,NEW.MENU_ITEM_ID,NEW.ORDER_COMMENT,NEW.ORDER_STATUS,NEW.ENTRY_DATE,NEW.ATTENDANT,NEW.TABLE_ACCOUNT_ID,NEW.ID_SHIFT);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rest_order_log`
--

DROP TABLE IF EXISTS `rest_order_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_order_log` (
  `ORDER_LOG_ID` int(25) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` int(11) NOT NULL,
  `TABLE_ID` int(11) DEFAULT NULL,
  `MENU_ITEM_ID` int(11) NOT NULL,
  `ORDER_COMMENT` varchar(500) DEFAULT NULL,
  `ORDER_STATUS` varchar(45) NOT NULL COMMENT 'CREATED\nIN PROGRESS\nREADY\nCOMPLETED',
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ATTENDANT` varchar(25) DEFAULT NULL,
  `TABLE_ACCOUNT_ID` int(11) DEFAULT NULL,
  `ID_SHIFT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ORDER_LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_product`
--

DROP TABLE IF EXISTS `rest_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_product` (
  `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_NAME` varchar(100) NOT NULL,
  `PRODUCT_DESCRIPTION` varchar(500) DEFAULT NULL,
  `PRODUCT_QTY_TRESHOLD` float NOT NULL DEFAULT '0',
  `PRODUCT_QTY_AVAILABILITY` float NOT NULL DEFAULT '0',
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(45) DEFAULT NULL,
  `PRODUCT_TYPE_ID` int(11) NOT NULL,
  `MEASURE_UNIT_ID` int(11) NOT NULL,
  `SUPPLIER_ID` int(11) NOT NULL,
  `PRODUCT_WASTE` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`PRODUCT_ID`),
  KEY `FK_PRODENTRY_MEASURE_idx` (`MEASURE_UNIT_ID`),
  KEY `FK_PRODENTRY_PRODTYPE_idx` (`PRODUCT_TYPE_ID`),
  KEY `FK_PRODENTRY_SUPPLIER_idx` (`SUPPLIER_ID`),
  CONSTRAINT `FK_PRODENTRY_MEASURE` FOREIGN KEY (`MEASURE_UNIT_ID`) REFERENCES `ctg_measure_unit` (`MEASURE_UNIT_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRODENTRY_PRODTYPE` FOREIGN KEY (`PRODUCT_TYPE_ID`) REFERENCES `ctg_product_type` (`PRODUCT_TYPE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRODENTRY_SUPPLIER` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `ctg_supplier` (`SUPPLIER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_shift`
--

DROP TABLE IF EXISTS `rest_shift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_shift` (
  `ID_SHIFT` int(11) NOT NULL AUTO_INCREMENT,
  `OPENING_DATETIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OPENED_BY` varchar(25) NOT NULL,
  `STATUS` varchar(25) NOT NULL COMMENT 'CREATED\nOPENED\nCLOSED',
  `CLOSING_DATETIME` datetime DEFAULT NULL,
  `CLOSED_BY` varchar(25) DEFAULT NULL,
  `SERVED_TABLES` int(11) DEFAULT '0',
  `ORDERS_TAKEN` int(11) DEFAULT '0',
  `INITIAL_MONEY` float DEFAULT '0',
  `RECEIVED_TABLES` int(11) DEFAULT '0',
  `PENDING_TABLES` int(11) DEFAULT '0' COMMENT 'Mesas pendiente de pago al cierre de caja',
  PRIMARY KEY (`ID_SHIFT`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Turnos en restaurante (Supervisor)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_shift_detail`
--

DROP TABLE IF EXISTS `rest_shift_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_shift_detail` (
  `ID_SHIFT_DETAIL` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PAYMENT_METHOD` int(11) DEFAULT NULL,
  `AMOUNT` float DEFAULT NULL,
  `ID_SHIFT` int(11) NOT NULL,
  PRIMARY KEY (`ID_SHIFT_DETAIL`),
  KEY `FK_REST_SHIF_idx_idx` (`ID_SHIFT`),
  CONSTRAINT `FK_REST_SHIF_idx` FOREIGN KEY (`ID_SHIFT`) REFERENCES `rest_shift` (`ID_SHIFT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_shift_log`
--

DROP TABLE IF EXISTS `rest_shift_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_shift_log` (
  `ID_SHIFT_LOG` int(11) NOT NULL AUTO_INCREMENT,
  `ID_SHIFT` int(11) DEFAULT NULL,
  `OPENING_DATETIME` datetime DEFAULT NULL,
  `OPENED_BY` varchar(25) DEFAULT NULL,
  `STATUS` varchar(25) DEFAULT NULL COMMENT 'CREATED\nOPENED\nCLOSED',
  `CLOSING_DATETIME` datetime DEFAULT NULL,
  `CLOSED_BY` varchar(25) DEFAULT NULL,
  `SERVED_TABLES` int(11) DEFAULT NULL,
  `ORDERS_TAKEN` int(11) DEFAULT NULL,
  `INITIAL_MONEY` float DEFAULT NULL,
  `PENDING_TABLES` int(11) DEFAULT NULL COMMENT 'Mesas pendiente de pago al cierre de caja',
  `RECEIVED_TABLES` int(11) DEFAULT NULL COMMENT 'Mesas aún abiertas del turno anterior',
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) NOT NULL,
  `ENTRY_DATE_LOG` datetime NOT NULL,
  PRIMARY KEY (`ID_SHIFT_LOG`),
  KEY `FK_SHIFTLOG_SHIFT_idx` (`ID_SHIFT`),
  CONSTRAINT `FK_SHIFTLOG_SHIFT` FOREIGN KEY (`ID_SHIFT`) REFERENCES `rest_shift` (`ID_SHIFT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rest_table`
--

DROP TABLE IF EXISTS `rest_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_table` (
  `TABLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AREA_ID` varchar(45) NOT NULL,
  `TABLE_NAME` varchar(45) NOT NULL,
  `CHAIR_AMMOUNT` int(11) NOT NULL,
  `STATUS` varchar(45) DEFAULT 'DESOCUPADO',
  PRIMARY KEY (`TABLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`rest_table_AFTER_UPDATE` AFTER UPDATE ON `rest_table` FOR EACH ROW
BEGIN
	IF  NEW.STATUS = 'DESOCUPADO' AND OLD.STATUS='OCUPADO' THEN
		INSERT INTO REST_TABLE_SHIFT (TABLE_ID,ID_SHIFT,ENTRY_DATE) 
        VALUES (NEW.TABLE_ID, (select id_shift from restoracio.rest_shift where status = 'OPEN'), DEFAULT);
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rest_table_account`
--

DROP TABLE IF EXISTS `rest_table_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_table_account` (
  `TABLE_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TABLE_ID` int(11) NOT NULL,
  `CREATED_DATETIME` datetime DEFAULT NULL,
  `CREATED_BY` varchar(25) DEFAULT NULL,
  `ACCOUNT_STATUS` varchar(25) NOT NULL DEFAULT 'CREATED' COMMENT 'CREATED\nIN PROGRESS\nCOMPLETED',
  `ID_SHIFT_OPENED` int(11) DEFAULT NULL,
  `CLOSED_DATETIME` datetime DEFAULT NULL,
  `ID_SHIFT_CLOSED` int(11) DEFAULT NULL,
  PRIMARY KEY (`TABLE_ACCOUNT_ID`),
  KEY `FK_SHIFT_idx` (`ID_SHIFT_OPENED`),
  KEY `FK_ORDER_TABLE_idx` (`TABLE_ID`),
  KEY `FK_ORDER_SHIFT_CLOSE_idx` (`ID_SHIFT_CLOSED`),
  CONSTRAINT `FK_ORDER_SHIFT_CLOSE` FOREIGN KEY (`ID_SHIFT_CLOSED`) REFERENCES `rest_shift` (`ID_SHIFT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ORDER_SHIFT_OPEN` FOREIGN KEY (`ID_SHIFT_OPENED`) REFERENCES `rest_shift` (`ID_SHIFT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TABLEACCOUNT_TABLE` FOREIGN KEY (`TABLE_ID`) REFERENCES `rest_table` (`TABLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `restoracio`.`rest_table_account_AFTER_UPDATE` AFTER UPDATE ON `rest_table_account` FOR EACH ROW
BEGIN
	IF NEW.ID_SHIFT_CLOSED IS NOT NULL AND OLD.ID_SHIFT_CLOSED IS NULL THEN
		CALL UPDATE_TABLE_STATUS(NEW.TABLE_ID);
	END IF;
    
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `rest_table_shift`
--

DROP TABLE IF EXISTS `rest_table_shift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rest_table_shift` (
  `TABLE_SHIFT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TABLE_ID` int(11) NOT NULL,
  `ID_SHIFT` int(11) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TABLE_SHIFT_ID`),
  KEY `FK_TABLE_SHIFT_TABLE_idx` (`TABLE_ID`),
  KEY `FK_TABLE_SHIFT_SHIFT_idx` (`ID_SHIFT`),
  CONSTRAINT `FK_TABLE_SHIFT_SHIFT` FOREIGN KEY (`ID_SHIFT`) REFERENCES `rest_shift` (`ID_SHIFT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TABLE_SHIFT_TABLE` FOREIGN KEY (`TABLE_ID`) REFERENCES `rest_table` (`TABLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ROL_CODE` varchar(25) NOT NULL,
  `ROL_NAME` varchar(100) NOT NULL,
  `ROL_DESCRIPTION` varchar(500) DEFAULT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ROL_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `USER_CODE` varchar(25) NOT NULL,
  `USER_PASSWORD` varchar(45) NOT NULL COMMENT 'Crypted password',
  `USER_NAME` varchar(45) NOT NULL,
  `USER_LASTNAME` varchar(45) NOT NULL,
  `USER_ADDRESS` varchar(500) DEFAULT NULL,
  `USER_PHONE1` varchar(45) DEFAULT NULL,
  `USER_PHONE2` varchar(45) DEFAULT NULL,
  `USER_EMAIL` varchar(45) DEFAULT NULL,
  `EMPLOYMENT_BEGIN` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EMPLOYMENT_END` date DEFAULT NULL,
  `USER_BIRTH_DATE` date DEFAULT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`USER_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_rol`
--

DROP TABLE IF EXISTS `sys_user_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_rol` (
  `USER_ROL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_CODE` varchar(25) NOT NULL,
  `ROL_CODE` varchar(25) NOT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`USER_ROL_ID`),
  KEY `FK_USERROL_USER_idx` (`USER_CODE`),
  KEY `FK_USERROL_ROL_idx` (`ROL_CODE`),
  CONSTRAINT `FK_USERROL_ROL` FOREIGN KEY (`ROL_CODE`) REFERENCES `sys_role` (`ROL_CODE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USERROL_USER` FOREIGN KEY (`USER_CODE`) REFERENCES `sys_user` (`USER_CODE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_user_rol_log`
--

DROP TABLE IF EXISTS `sys_user_rol_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_rol_log` (
  `USER_ROL_LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ROL_ID` int(11) NOT NULL,
  `USER_CODE` varchar(25) DEFAULT NULL,
  `ROL_CODE` varchar(25) DEFAULT NULL,
  `ENTRY_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ENTRY_USER` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`USER_ROL_LOG_ID`),
  KEY `FK_USRROLLOG_USRROL_idx` (`USER_ROL_ID`),
  CONSTRAINT `FK_USRROLLOG_USRROL` FOREIGN KEY (`USER_ROL_ID`) REFERENCES `sys_user_rol` (`USER_ROL_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'restoracio'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `CHECK_EXPIRATION_DATE` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`skip-grants user`@`skip-grants host`*/ /*!50106 EVENT `CHECK_EXPIRATION_DATE` ON SCHEDULE EVERY 1 DAY STARTS '2016-08-10 06:00:00' ON COMPLETION NOT PRESERVE ENABLE COMMENT 'EVENTO QUE VERIFICA SI UN PRODUCTO SE ENCUENTRA VENCIDO' DO UPDATE inv_product_item SET STATE = 'CLOSED' WHERE EXPIRATION_DATE < CURRENT_DATE() */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'restoracio'
--
/*!50003 DROP PROCEDURE IF EXISTS `apply_automatic_discount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `apply_automatic_discount`(PRM_MENU_ITEM_ID int,PRM_ORDER_ID int,PRM_DATE datetime,OUT PRM_DISCOUNT_ID INT, OUT PRM_DISCOUNT_VALUE FLOAT)
BEGIN
		DECLARE VAR_DISCOUNT_ID INT ;
		DECLARE VAR_MENU_ITEM_DISCOUNT FLOAT ;
		DECLARE v_finished INTEGER DEFAULT 0;
		DECLARE v_discount_applied INTEGER DEFAULT 0;
		DECLARE v_apply_discount INTEGER DEFAULT 0;

		DECLARE DISCOUNT_LIST_CURSOR CURSOR FOR
		SELECT DIS.DISCOUNT_ID,ROUND((item.menu_item_price*(DIS.DISCOUNT_PERCENTAGE/100)),2)
		FROM ctg_discount DIS , ctg_discount_menu dis_menu, rest_menu_item item
		WHERE PRM_DATE between DIS.DISCOUNT_VALID_SINCE and DIS.DISCOUNT_VALID_UNTIL
		AND DIS.DISCOUNT_ID = dis_menu.DISCOUNT_ID
		AND dis_menu.MENU_ITEM_ID = PRM_MENU_ITEM_ID
		AND dis_menu.menu_item_id = item.menu_item_id
		order by DIS.DISCOUNT_VALID_SINCE, DIS.ENTRY_DATE ;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
		SET PRM_ORDER_ID =0;
		SET PRM_DISCOUNT_VALUE = 0;
		OPEN DISCOUNT_LIST_CURSOR;

		get_discount_list: LOOP

				FETCH DISCOUNT_LIST_CURSOR INTO VAR_DISCOUNT_ID,VAR_MENU_ITEM_DISCOUNT;
				IF v_finished = 1 THEN
					LEAVE get_discount_list;
				END IF;
                if v_apply_discount = 0 then
					SET v_apply_discount = 1;
					SET PRM_DISCOUNT_ID =VAR_DISCOUNT_ID;
					SET PRM_DISCOUNT_VALUE = ROUND(VAR_MENU_ITEM_DISCOUNT,2);
					
					LEAVE get_discount_list;
                end if;
		END LOOP get_discount_list;

		-- Cerramos el cursor
		CLOSE DISCOUNT_LIST_CURSOR;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `apply_discount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `apply_discount`(PRM_MENU_ITEM_ID int,PRM_ORDER_ID int,PRM_DATE datetime)
BEGIN

		DECLARE VAR_DISCOUNT_ID INT ;
		DECLARE VAR_DISCOUNT_PERCENTAGE INT ;
		DECLARE v_finished INTEGER DEFAULT 0;
		DECLARE v_discount_applied INTEGER DEFAULT 0;
		DECLARE v_apply_discount INTEGER DEFAULT 0;

		DECLARE DISCOUNT_LIST_CURSOR CURSOR FOR
		SELECT DISCOUNT_ID,DISCOUNT_PERCENTAGE
		FROM ctg_discount WHERE PRM_DATE between DISCOUNT_VALID_SINCE and DISCOUNT_VALID_UNTIL order by DISCOUNT_VALID_SINCE ;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
		OPEN DISCOUNT_LIST_CURSOR;

		get_discount_list: LOOP

				FETCH DISCOUNT_LIST_CURSOR INTO VAR_DISCOUNT_ID, VAR_DISCOUNT_PERCENTAGE;
				IF v_finished = 1 THEN
					LEAVE get_discount_list;
				END IF;
                if v_apply_discount = 0 then
					SET v_apply_discount := (SELECT COUNT(*) FROM ctg_discount_menu where discount_id = VAR_DISCOUNT_ID and menu_item_id =PRM_MENU_ITEM_ID  );
                end if;
                IF v_apply_discount = 1 THEN
					UPDATE REST_ORDER SET AUTOMATIC_DISCOUNT_ID = VAR_DISCOUNT_ID
                    WHERE ORDER_ID= PRM_ORDER_ID;
					LEAVE get_discount_list;
				END IF;
		END LOOP get_discount_list;

		-- Cerramos el cursor
		CLOSE DISCOUNT_LIST_CURSOR;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CLOSE_SHIFT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CLOSE_SHIFT`(IN PRM_SHIFT_ID INT, IN PRM_USER VARCHAR(25))
BEGIN

DECLARE VAR_PAYMENTH_METHOD_ID INT ;
		DECLARE VAR_AMOUNT FLOAT;
		DECLARE v_finished INTEGER DEFAULT 0;

		DECLARE PAYMENTHMETHOD_X_AMOUNT_CURSOR CURSOR FOR
		SELECT  PAYMENTH_METHOD_ID , round(sum(bill.BILL_TOTAL),2)
		FROM restoracio.rest_bill bill
		where bill.shift_id = PRM_SHIFT_ID
		group by PAYMENT_METHOD_ID;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
		OPEN PAYMENTHMETHOD_X_AMOUNT_CURSOR;
		
        DELETE FROM REST_SHIFT_DETAIL WHERE SHIFT_ID = PRM_SHIFT_ID;
        
		get_amount_list: LOOP

				FETCH PAYMENTHMETHOD_X_AMOUNT_CURSOR INTO VAR_PAYMENTH_METHOD_ID, VAR_AMOUNT;
				IF v_finished = 1 THEN
				LEAVE get_amount_list;
				END IF;

				INSERT INTO REST_SHIFT_DETAIL (ID_PAYMENTH_METHOD,AMOUNT, ID_SHIFT)
                VALUES (VAR_PAYMENTH_METHOD_ID,VAR_AMOUNT,PRM_SHIFT_ID);

		END LOOP get_amount_list;

		-- Cerramos el cursor
		CLOSE PAYMENTHMETHOD_X_AMOUNT_CURSOR;
		
        UPDATE REST_SHIFT
        SET SERVED_TABLES = (SELECT COUNT(1) FROM REST_TABLE_SHIFT WHERE ID_SHIFT = PRM_SHIFT_ID),
			PENDING_TABLES = (SELECT COUNT(1) FROM ( SELECT distinct table_id FROM restoracio.rest_table_account where ID_SHIFT_OPENED = PRM_SHIFT_ID and ID_SHIFT_CLOSED is null) mesas_pendientes) 
        WHERE ID_SHIFT = PRM_SHIFT_ID;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inv_PRODUCT_AVAILABILITY` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inv_PRODUCT_AVAILABILITY`(in PRM_ORDER_id INT,IN PRM_menu_item_id INT,IN PRM_PRODUCT_ID INT,IN PRM_PRODUCT_QTY_NEEDED INT, IN PRM_ATTENDANT VARCHAR(25), IN PRM_TRANSACTION_TYPE_ID VARCHAR(3))
BEGIN
		DECLARE VAR_INV_PRODUCT_ID INT;
		DECLARE VAR_PRODUCT_QTY_AVAILABILITY INT;
		DECLARE TEMP_PRODUCT_QTY_NEEDED INT;
		DECLARE TEMP_PRODUCT_QTY_AVAILABILITY INT;
		DECLARE v_finished INTEGER DEFAULT 0;

		DECLARE PRODUCT_AVAILABILITY_CURSOR CURSOR FOR
		SELECT INV_PRODUCT_ID,PRODUCT_QTY_AVAILABILITY
		FROM inv_product_item 
        WHERE PRODUCT_ID = PRM_PRODUCT_ID AND  STATE ='OPEN' 
        ORDER BY ENTRY_DATE;
		
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
		SET TEMP_PRODUCT_QTY_NEEDED  =PRM_PRODUCT_QTY_NEEDED;
		OPEN PRODUCT_AVAILABILITY_CURSOR;
		get_product_availability: LOOP
				FETCH PRODUCT_AVAILABILITY_CURSOR INTO VAR_INV_PRODUCT_ID, VAR_PRODUCT_QTY_AVAILABILITY;
				
              
                IF v_finished = 1 THEN
					LEAVE get_product_availability;
				END IF;
				IF TEMP_PRODUCT_QTY_NEEDED = 0  THEN
					LEAVE get_product_availability;
				END IF;
				IF VAR_PRODUCT_QTY_AVAILABILITY <= TEMP_PRODUCT_QTY_NEEDED THEN 
               	SET TEMP_PRODUCT_QTY_AVAILABILITY = 0;
				
					INSERT INTO inv_product_transaction_log
						   (ORDER_ID,MENU_ITEM_ID,PRODUCT_ID,PRODUCT_QTY,INV_PRODUCT_ID,ENTRY_DATE,ENTRY_USER,TRANSACTION_TYPE_ID,STATE)
					VALUES (PRM_ORDER_id,PRM_menu_item_id,PRM_PRODUCT_ID,VAR_PRODUCT_QTY_AVAILABILITY,VAR_INV_PRODUCT_ID,SYSDATE(),PRM_ATTENDANT,PRM_TRANSACTION_TYPE_ID,'OPEN');
					
					UPDATE inv_product_item 
					SET PRODUCT_QTY_AVAILABILITY = TEMP_PRODUCT_QTY_AVAILABILITY,
						STATE = 'CLOSED'
					WHERE INV_PRODUCT_ID = VAR_INV_PRODUCT_ID;
					
                    UPDATE rest_product 
					SET  PRODUCT_QTY_AVAILABILITY = IFNULL(PRODUCT_QTY_AVAILABILITY,0) - VAR_PRODUCT_QTY_AVAILABILITY
					WHERE PRODUCT_ID = VAR_INV_PRODUCT_ID;

					SET TEMP_PRODUCT_QTY_NEEDED = TEMP_PRODUCT_QTY_NEEDED-VAR_PRODUCT_QTY_AVAILABILITY;

				ELSE -- QUANTITY NEEDED IS LESS THAN ACTUAL PURCHASE
					SET TEMP_PRODUCT_QTY_AVAILABILITY = VAR_PRODUCT_QTY_AVAILABILITY-TEMP_PRODUCT_QTY_NEEDED;

					INSERT INTO inv_product_transaction_log
						   (ORDER_ID,MENU_ITEM_ID,PRODUCT_ID,PRODUCT_QTY,INV_PRODUCT_ID,ENTRY_DATE,ENTRY_USER,TRANSACTION_TYPE_ID,STATE)
					VALUES (PRM_ORDER_id,PRM_menu_item_id,PRM_PRODUCT_ID,TEMP_PRODUCT_QTY_NEEDED,VAR_INV_PRODUCT_ID,SYSDATE(),PRM_ATTENDANT,PRM_TRANSACTION_TYPE_ID,'OPEN');
					
					UPDATE inv_product_item 
					SET  PRODUCT_QTY_AVAILABILITY = TEMP_PRODUCT_QTY_AVAILABILITY
					WHERE INV_PRODUCT_ID = VAR_INV_PRODUCT_ID;
                    
                    UPDATE rest_product 
					SET  PRODUCT_QTY_AVAILABILITY = IFNULL(PRODUCT_QTY_AVAILABILITY,0) - TEMP_PRODUCT_QTY_NEEDED
					WHERE PRODUCT_ID = VAR_INV_PRODUCT_ID;
                    
					SET TEMP_PRODUCT_QTY_NEEDED = 0;

				END IF;
				
				
		END LOOP get_product_availability;
				

		IF TEMP_PRODUCT_QTY_NEEDED > 0 THEN 
			-- INSERT RECORD WITHOUT INV_PRODUCT REFERENCE. THIS VALUE WILL BE UPDATED WHEN X ACTION OCURRS.
			-- X WILL BE AN ACTION THAT WILL BE EXECUTED AFTER UPDATING INVENTORY 
			--  TRANSACTION_TYPE_ID varchar(3) NOT NULL,
			--  STATE varchar(10) NOT NULL COMMENT 'OPEN\nCLOSECANCELED\nRESERVED\nSOLD\nLOCKED',
			INSERT INTO inv_product_transaction_LOG
						   (ORDER_ID,MENU_ITEM_ID,PRODUCT_ID,PRODUCT_QTY,INV_PRODUCT_ID,ENTRY_DATE,ENTRY_USER,TRANSACTION_TYPE_ID,STATE)
			VALUES (PRM_ORDER_id,PRM_menu_item_id,PRM_PRODUCT_ID,TEMP_PRODUCT_QTY_NEEDED,NULL,SYSDATE(),PRM_ATTENDANT,PRM_TRANSACTION_TYPE_ID,'OPEN');
		END IF;
            
		
		-- Cerramos el cursor
		CLOSE PRODUCT_AVAILABILITY_CURSOR;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inv_PRODUCT_AVAILABILITY_CNL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inv_PRODUCT_AVAILABILITY_CNL`(in PRM_ORDER_id INT,IN PRM_menu_item_id INT,IN PRM_PRODUCT_ID INT, IN PRM_ATTENDANT VARCHAR(25), PRM_TRANSACTION_TYPE_ID VARCHAR (3))
BEGIN
		DECLARE VAR_INV_PRODUCT_TRNS_ID INT;
		DECLARE VAR_INV_PRODUCT_ID INT;
		DECLARE VAR_PRODUCT_INV_AVAILABILITY INT;
		DECLARE v_finished INTEGER DEFAULT 0;

		DECLARE PRODUCT_INVT_AVAILABILITY_CURSOR CURSOR FOR
        SELECT INV_PRODUCT_TRNS_ID,INV_PRODUCT_ID, PRODUCT_QTY
		FROM inv_product_transaction_log 
        WHERE ORDER_ID = PRM_ORDER_ID AND MENU_ITEM_ID = PRM_MENU_ITEM_ID AND PRODUCT_ID = PRM_PRODUCT_ID
        ORDER BY INV_PRODUCT_ID;		
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
		OPEN PRODUCT_INVT_AVAILABILITY_CURSOR;
		get_product_availability: LOOP
				FETCH PRODUCT_INVT_AVAILABILITY_CURSOR INTO VAR_INV_PRODUCT_TRNS_ID,VAR_INV_PRODUCT_ID, VAR_PRODUCT_INV_AVAILABILITY;
				
              
                IF v_finished = 1 THEN
					LEAVE get_product_availability;
				END IF;
                
				UPDATE inv_product_item 
				SET PRODUCT_QTY_AVAILABILITY = IFNULL(PRODUCT_QTY_AVAILABILITY,0)+VAR_PRODUCT_INV_AVAILABILITY,
					STATE = 'OPEN'
				WHERE INV_PRODUCT_ID = VAR_INV_PRODUCT_ID;
                
                UPDATE inv_product_transaction_log
                SET TRANSACTION_TYPE_ID = PRM_TRANSACTION_TYPE_ID,
					ENTRY_USER_CNL = PRM_ATTENDANT, ENTRY_DATE_CNL = CURRENT_TIMESTAMP,
                    STATE = 'CANCELED'
				WHERE INV_PRODUCT_TRNS_ID = VAR_INV_PRODUCT_TRNS_ID;
				
		END LOOP get_product_availability;				  
		
		-- Cerramos el cursor
		CLOSE PRODUCT_INVT_AVAILABILITY_CURSOR;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inv_update` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inv_update`(PRM_menu_item_id int,PRM_ORDER_id int,PRM_ATTENDANT varchar(25), prm_ACTION VARCHAR(3))
BEGIN

		DECLARE VAR_PRODUCT_ID INT ;
		DECLARE VAR_QUANTITY INT;
		DECLARE v_finished INTEGER DEFAULT 0;

		DECLARE PRODUCT_LIST_CURSOR CURSOR FOR
		SELECT PRODUCT_ID,QUANTITY
		FROM rest_menu_item_product WHERE MENU_ITEM_ID =PRM_menu_item_id;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
		
		OPEN PRODUCT_LIST_CURSOR;

		get_product_list: LOOP

				FETCH PRODUCT_LIST_CURSOR INTO VAR_PRODUCT_ID, VAR_QUANTITY;
				IF v_finished = 1 THEN
				LEAVE get_product_list;
				END IF;
                IF PRM_ACTION = 'SLD' THEN
					CALL inv_PRODUCT_AVAILABILITY(PRM_ORDER_id,PRM_menu_item_id,VAR_PRODUCT_ID,VAR_QUANTITY,PRM_ATTENDANT, 'SLD');
				ELSE
					CALL inv_PRODUCT_AVAILABILITY_CNL(PRM_ORDER_id,PRM_menu_item_id,VAR_PRODUCT_ID,PRM_ATTENDANT,'CNL');
                END IF;
		END LOOP get_product_list;

		-- Cerramos el cursor
		CLOSE PRODUCT_LIST_CURSOR;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `inv_update_automatic` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `inv_update_automatic`(PRM_PRODUCT_ID int, PRM_QTY_AVAILABLE int, PRM_INV_PRODUCT_ID INT)
BEGIN

		DECLARE VAR_INV_PRODUCT_TRNS_ID INT ;
		DECLARE VAR_QUANTITY_NEEDED INT;
		DECLARE VAR_ORDER_ID INT;
		DECLARE VAR_MENU_ITEM_ID INT;
		DECLARE VAR_ENTRY_USER VARCHAR(30);
		DECLARE VAR_TRANSACTION_TYPE_ID VARCHAR(30);
		DECLARE TEMP_PRODUCT_QTY_AVAILABILITY INT;
		DECLARE VAR_QUANTITY_NEEDED_OPEN INT;
		
		DECLARE v_finished INTEGER DEFAULT 0;

		DECLARE INVENTORY_LIST_CURSOR CURSOR FOR
		SELECT INV_PRODUCT_TRNS_ID , PRODUCT_QTY,ORDER_ID,MENU_ITEM_ID,ENTRY_USER,TRANSACTION_TYPE_ID
		FROM restoracio.inv_product_transaction_log
		where product_id = PRM_PRODUCT_ID 
		 and inv_product_id is null
		 and TRANSACTION_TYPE_ID = 'SLD' 
		order by  entry_date asc;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

		SET TEMP_PRODUCT_QTY_AVAILABILITY  =PRM_QTY_AVAILABLE;		
		OPEN INVENTORY_LIST_CURSOR;

		get_inventory_list: LOOP

				FETCH INVENTORY_LIST_CURSOR INTO VAR_INV_PRODUCT_TRNS_ID, VAR_QUANTITY_NEEDED,VAR_ORDER_ID,VAR_MENU_ITEM_ID,VAR_ENTRY_USER,VAR_TRANSACTION_TYPE_ID;
				IF v_finished = 1 THEN
				LEAVE get_inventory_list;
				END IF;

				
				IF TEMP_PRODUCT_QTY_AVAILABILITY = 0  THEN
					LEAVE get_inventory_list;
				END IF;
				IF TEMP_PRODUCT_QTY_AVAILABILITY < VAR_QUANTITY_NEEDED THEN 

					UPDATE inv_product_transaction_log 
					SET  INV_PRODUCT_ID = PRM_INV_PRODUCT_ID
					WHERE INV_PRODUCT_TRNS_ID = VAR_INV_PRODUCT_TRNS_ID;
					
				
					SET VAR_QUANTITY_NEEDED_OPEN = VAR_QUANTITY_NEEDED - TEMP_PRODUCT_QTY_AVAILABILITY;
					
					INSERT INTO inv_product_transaction_log
						   (ORDER_ID,MENU_ITEM_ID,PRODUCT_ID,PRODUCT_QTY,INV_PRODUCT_ID,ENTRY_DATE,ENTRY_USER,TRANSACTION_TYPE_ID,STATE)
					VALUES (VAR_ORDER_ID,VAR_MENU_ITEM_ID,PRM_PRODUCT_ID,VAR_QUANTITY_NEEDED_OPEN,NULL,SYSDATE(),VAR_ENTRY_USER,VAR_TRANSACTION_TYPE_ID,'OPEN');
				

                    UPDATE rest_product 
					SET  PRODUCT_QTY_AVAILABILITY = IFNULL(PRODUCT_QTY_AVAILABILITY,0) - TEMP_PRODUCT_QTY_AVAILABILITY
					WHERE PRODUCT_ID = PRM_PRODUCT_ID;
					
					SET TEMP_PRODUCT_QTY_AVAILABILITY = 0;

					UPDATE inv_product_item 
					SET PRODUCT_QTY_AVAILABILITY = TEMP_PRODUCT_QTY_AVAILABILITY,
						STATE = 'CLOSED'
					WHERE INV_PRODUCT_ID = VAR_INV_PRODUCT_ID;


				ELSE -- QUANTITY NEEDED IS LESS THAN ACTUAL PURCHASE
					SET TEMP_PRODUCT_QTY_AVAILABILITY = TEMP_PRODUCT_QTY_AVAILABILITY-VAR_QUANTITY_NEEDED;

					UPDATE inv_product_transaction_log 
					SET  INV_PRODUCT_ID = PRM_INV_PRODUCT_ID
					WHERE INV_PRODUCT_TRNS_ID = VAR_INV_PRODUCT_TRNS_ID;

					
					UPDATE inv_product_item 
					SET  PRODUCT_QTY_AVAILABILITY = TEMP_PRODUCT_QTY_AVAILABILITY
					WHERE INV_PRODUCT_ID = PRM_INV_PRODUCT_ID;
                    
                    UPDATE rest_product 
					SET  PRODUCT_QTY_AVAILABILITY = IFNULL(PRODUCT_QTY_AVAILABILITY,0) - VAR_QUANTITY_NEEDED
					WHERE PRODUCT_ID = PRM_PRODUCT_ID;
                    

				END IF;
				
				
				
				
				
				
				
				
		END LOOP get_inventory_list;

		-- Cerramos el cursor
		CLOSE INVENTORY_LIST_CURSOR;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_TABLE_STATUS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UPDATE_TABLE_STATUS`(IN PRM_TABLE_ID INT)
BEGIN
	DECLARE VAR_TOTAL_ACCOUNTS INT default 0  ;
	
    SET VAR_TOTAL_ACCOUNTS =  (SELECT COUNT(*) FROM REST_TABLE_ACCOUNT WHERE TABLE_ID = PRM_TABLE_ID AND ID_SHIFT_CLOSED IS NULL);
	IF VAR_TOTAL_ACCOUNTS = 0 THEN
			UPDATE REST_TABLE
			SET STATUS  = 'DESOCUPADO'
			WHERE TABLE_ID = PRM_TABLE_ID;
	END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-10 15:25:53
