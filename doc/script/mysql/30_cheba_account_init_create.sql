# --------------------------------------------------------
# Host:                         192.168.0.242
# Server version:               5.0.95
# Server OS:                    redhat-linux-gnu
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2014-02-28 14:28:30
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping structure for table lijialiang-test.cheba_account_permi
DROP TABLE IF EXISTS `cheba_account_permi`;
CREATE TABLE IF NOT EXISTS `cheba_account_permi` (
  `uuid` varchar(32) character set gb2312 NOT NULL,
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
  UNIQUE KEY `data` (`data`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_account_role
DROP TABLE IF EXISTS `cheba_account_role`;
CREATE TABLE IF NOT EXISTS `cheba_account_role` (
  `uuid` varchar(32) character set gb2312 NOT NULL,
  `name` varchar(256) character set gb2312 NOT NULL,
  `descr` varchar(256) character set gb2312 default NULL,
  `sort_no` varchar(256) character set gb2312 default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) character set gb2312 NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) character set gb2312 NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_account_role_permi
DROP TABLE IF EXISTS `cheba_account_role_permi`;
CREATE TABLE IF NOT EXISTS `cheba_account_role_permi` (
  `uuid` varchar(32) character set gb2312 NOT NULL,
  `role_uuid` varchar(32) character set gb2312 NOT NULL,
  `permi_uuid` varchar(32) character set gb2312 NOT NULL,
  `descr` varchar(256) character set gb2312 default NULL,
  `sort_no` varchar(256) character set gb2312 default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) character set gb2312 NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) character set gb2312 NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`),
  UNIQUE KEY `role_uuid_permi_uuid` (`role_uuid`,`permi_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_account_statusset
DROP TABLE IF EXISTS `cheba_account_statusset`;
CREATE TABLE IF NOT EXISTS `cheba_account_statusset` (
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


# Dumping structure for table lijialiang-test.cheba_account_user
DROP TABLE IF EXISTS `cheba_account_user`;
CREATE TABLE IF NOT EXISTS `cheba_account_user` (
  `uuid` varchar(32) character set gb2312 NOT NULL,
  `login_name` varchar(256) character set gb2312 NOT NULL,
  `type` char(2) character set gb2312 default NULL,
  `status` varchar(256) character set gb2312 default NULL,
  `descr` varchar(256) character set gb2312 default NULL,
  `sort_no` varchar(256) character set gb2312 default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) character set gb2312 NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) character set gb2312 NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# Data exporting was unselected.


# Dumping structure for table lijialiang-test.cheba_account_user_role
DROP TABLE IF EXISTS `cheba_account_user_role`;
CREATE TABLE IF NOT EXISTS `cheba_account_user_role` (
  `uuid` varchar(32) character set gb2312 NOT NULL,
  `user_uuid` varchar(32) character set gb2312 NOT NULL,
  `role_uuid` varchar(32) character set gb2312 NOT NULL,
  `descr` varchar(256) character set gb2312 default NULL,
  `sort_no` varchar(256) character set gb2312 default NULL,
  `created_time` datetime NOT NULL,
  `creater_uuid` varchar(32) character set gb2312 NOT NULL,
  `updated_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updater_uuid` varchar(32) character set gb2312 NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`uuid`),
  UNIQUE KEY `user_uuid_role_uuid` (`user_uuid`,`role_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
