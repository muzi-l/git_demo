# --------------------------------------------------------
# Host:                         192.168.0.242
# Server version:               5.0.95
# Server OS:                    redhat-linux-gnu
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2014-02-28 14:27:58
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for lijialiang-test
DROP DATABASE IF EXISTS `lijialiang-test`;
CREATE DATABASE IF NOT EXISTS `lijialiang-test` /*!40100 DEFAULT CHARACTER SET gbk */;
USE `lijialiang-test`;


# Dumping structure for table lijialiang-test.cheba_answer_history
DROP TABLE IF EXISTS `cheba_answer_history`;
CREATE TABLE IF NOT EXISTS `cheba_answer_history` (
  `uuid` varchar(32) NOT NULL,
  `program_uuid` varchar(32) NOT NULL,
  `question_uuid` varchar(64) NOT NULL,
  `answer_a_count` varchar(64) default NULL,
  `answer_b_count` varchar(64) default NULL,
  `answer_c_count` varchar(64) default NULL,
  `answer_d_count` varchar(64) default NULL,
  `correct_answer` varchar(64) default NULL,
  `descr` varchar(256) default NULL,
  `sort_no` varchar(32) default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk ROW_FORMAT=DYNAMIC;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_program
DROP TABLE IF EXISTS `cheba_program`;
CREATE TABLE IF NOT EXISTS `cheba_program` (
  `uuid` varchar(32) NOT NULL,
  `status` char(2) default NULL,
  `program_name` varchar(64) default NULL,
  `program_periods` varchar(64) default NULL,
  `program_time` datetime default NULL,
  `pass_1_bonus` double default NULL,
  `pass_1_bonus_allot_population` int(11) default NULL,
  `pass_2_bonus` double default NULL,
  `pass_2_bonus_allot_population` int(11) default NULL,
  `pass_3_bonus` double default NULL,
  `pass_3_bonus_allot_population` int(11) default NULL,
  `answer_time` int(11) NOT NULL,
  `compere_name` varchar(64) default NULL,
  `answer_1_uuid` varchar(32) default NULL,
  `answer_1_name` varchar(64) default NULL,
  `answer_2_uuid` varchar(32) default NULL,
  `answer_2_name` varchar(64) default NULL,
  `answer_3_uuid` varchar(32) default NULL,
  `answer_3_name` varchar(64) default NULL,
  `descr` varchar(256) default NULL,
  `sort_no` varchar(32) default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_question_bank
DROP TABLE IF EXISTS `cheba_question_bank`;
CREATE TABLE IF NOT EXISTS `cheba_question_bank` (
  `uuid` varchar(32) NOT NULL,
  `program_uuid` varchar(32) NOT NULL,
  `question_title` varchar(64) NOT NULL,
  `question_bonus` double default NULL,
  `difficulty_type` char(2) default NULL,
  `question_content` varchar(256) default NULL,
  `answer_a` varchar(64) default NULL,
  `answer_b` varchar(64) default NULL,
  `answer_c` varchar(64) default NULL,
  `answer_d` varchar(64) default NULL,
  `correct_answer` varchar(64) default NULL,
  `is_deleted` tinyint(1) default NULL,
  `descr` varchar(256) default NULL,
  `sort_no` varchar(32) default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_statusset
DROP TABLE IF EXISTS `cheba_statusset`;
CREATE TABLE IF NOT EXISTS `cheba_statusset` (
  `uuid` varchar(32) character set gb2312 NOT NULL,
  `type` varchar(256) character set gb2312 NOT NULL,
  `data` varchar(256) character set gb2312 NOT NULL,
  `value` varchar(256) character set gb2312 default NULL,
  `descr` varchar(256) character set gb2312 default NULL,
  `sort_no` varchar(256) character set gb2312 default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) character set gb2312 NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) character set gb2312 NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`),
  UNIQUE KEY `type_data` (`type`,`data`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
