-- --------------------------------------------------------
-- Host:                         192.168.0.242
-- Server version:               5.0.95 - Source distribution
-- Server OS:                    redhat-linux-gnu
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2012-11-01 10:42:42
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;
-- Dumping data for table lijialiang-test.cheba_statusset: 8 rows
DELETE FROM `cheba_statusset`;
/*!40000 ALTER TABLE `cheba_statusset` DISABLE KEYS */;
INSERT INTO `cheba_statusset` (`uuid`, `type`, `data`, `value`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('000001', 'task_status', '10', '未开启', '任务状态', NULL, '2012-04-26 09:31:10', 'system', '2012-04-26 09:39:48', 'system', 0),
	('000002', 'task_status', '20', '已开启', '任务状态', NULL, '2012-04-26 09:31:10', 'system', '2012-04-26 09:39:48', 'system', 0),
	('000003', 'task_status', '30', '已结束', '任务状态', NULL, '2012-04-26 09:31:10', 'system', '2012-04-26 09:39:47', 'system', 0);
/*!40000 ALTER TABLE `cheba_statusset` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
