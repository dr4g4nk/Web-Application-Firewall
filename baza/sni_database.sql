-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema sni_database
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sni_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sni_database` DEFAULT CHARACTER SET utf8 ;
USE `sni_database` ;

-- -----------------------------------------------------
-- Table `sni_database`.`items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sni_database`.`items` (
  `iditem` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL DEFAULT NULL,
  `price` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`iditem`))
ENGINE = InnoDB
AUTO_INCREMENT = 72
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `sni_database`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sni_database`.`users` (
  `username` VARCHAR(50) NOT NULL,
  `password` CHAR(64) NOT NULL,
  `role` TINYINT(4) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
