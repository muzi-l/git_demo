# --------------------------------------------------------
# Host:                         192.168.0.242
# Server version:               5.0.95
# Server OS:                    redhat-linux-gnu
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2014-02-28 14:28:59
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping data for table lijialiang-test.cheba_account_permi: ~9 rows (approximately)
/*!40000 ALTER TABLE `cheba_account_permi` DISABLE KEYS */;
INSERT INTO `cheba_account_permi` (`uuid`, `data`, `value`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('A040', 'A040', '扯吧后台管理', NULL, NULL, '2012-04-26 09:31:10', 'system', '2014-01-10 15:17:58', 'system', 0),
	('A040.B010', 'A040.B010', '节目管理', NULL, NULL, '2012-04-26 09:31:10', 'system', '2014-01-10 15:18:15', 'system', 0),
	('A040.B020', 'A040.B020', '节目主持管理', NULL, NULL, '2012-04-26 09:31:10', 'system', '2014-02-28 14:17:10', 'system', 0),
	('A050', 'A050', '系统管理', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-06-27 16:07:29', 'system', 0),
	('A050.B010', 'A050.B010', '用户管理', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-06-27 16:07:42', 'system', 0),
	('A050.B020', 'A050.B020', '角色管理', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-06-27 16:08:02', 'system', 0),
	('A050.B030', 'A050.B030', '密码修改', NULL, NULL, '2012-07-18 13:07:14', 'system', '2012-07-18 13:11:12', 'system', 0);
/*!40000 ALTER TABLE `cheba_account_permi` ENABLE KEYS */;

# Dumping data for table lijialiang-test.cheba_account_role: ~4 rows (approximately)
/*!40000 ALTER TABLE `cheba_account_role` DISABLE KEYS */;
INSERT INTO `cheba_account_role` (`uuid`, `name`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('child_sys_admin_role', '管理人员', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-10-16 10:39:08', 'cheba_admin_uuid', 10);
/*!40000 ALTER TABLE `cheba_account_role` ENABLE KEYS */;

# Dumping data for table lijialiang-test.cheba_account_role_permi: ~17 rows (approximately)
/*!40000 ALTER TABLE `cheba_account_role_permi` DISABLE KEYS */;
INSERT INTO `cheba_account_role_permi` (`uuid`, `role_uuid`, `permi_uuid`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('40288099447726a90144772909ce0000', 'child_sys_admin_role', 'A040', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0),
	('40288099447726a90144772909f10001', 'child_sys_admin_role', 'A040.B010', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0),
	('40288099447726a9014477290a060002', 'child_sys_admin_role', 'A040.B020', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0),
	('40288099447726a9014477290a110003', 'child_sys_admin_role', 'A050', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0),
	('40288099447726a9014477290a1e0004', 'child_sys_admin_role', 'A050.B010', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0),
	('40288099447726a9014477290a290005', 'child_sys_admin_role', 'A050.B020', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0),
	('40288099447726a9014477290a340006', 'child_sys_admin_role', 'A050.B030', NULL, NULL, '2014-02-28 14:23:02', 'cheba_admin_uuid', '2014-02-28 14:23:51', 'cheba_admin_uuid', 0);
/*!40000 ALTER TABLE `cheba_account_role_permi` ENABLE KEYS */;

# Dumping data for table lijialiang-test.cheba_account_statusset: ~4 rows (approximately)
/*!40000 ALTER TABLE `cheba_account_statusset` DISABLE KEYS */;
INSERT INTO `cheba_account_statusset` (`uuid`, `type`, `data`, `value`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('000013', 'user_state', '10', '生效', '用户状态', NULL, '2012-04-26 09:31:10', 'system', '2012-04-25 21:39:41', 'system', 0),
	('000014', 'user_state', '20', '终止', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-04-25 21:39:41', 'system', 0),
	('000020', 'USER_TYPE', '10', 'CP用户', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-06-15 13:09:44', 'system', 0),
	('000021', 'USER_TYPE', '20', '内部员工', NULL, NULL, '2012-04-26 09:31:10', 'system', '2012-04-25 21:39:40', 'system', 0);
/*!40000 ALTER TABLE `cheba_account_statusset` ENABLE KEYS */;

# Dumping data for table lijialiang-test.cheba_account_user: ~2 rows (approximately)
/*!40000 ALTER TABLE `cheba_account_user` DISABLE KEYS */;
INSERT INTO `cheba_account_user` (`uuid`, `login_name`, `type`, `status`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('cheba_admin_uuid', 'administrator', '20', '10', NULL, NULL, '2012-04-26 09:43:14', 'system', '2012-09-21 15:55:26', 'dict_admin_uuid', 46);
/*!40000 ALTER TABLE `cheba_account_user` ENABLE KEYS */;

# Dumping data for table lijialiang-test.cheba_account_user_role: ~2 rows (approximately)
/*!40000 ALTER TABLE `cheba_account_user_role` DISABLE KEYS */;
INSERT INTO `cheba_account_user_role` (`uuid`, `user_uuid`, `role_uuid`, `descr`, `sort_no`, `created_time`, `creater_uuid`, `updated_time`, `updater_uuid`, `version`) VALUES
	('402880a439e729d00139e7cfd16c0012', 'cheba_admin_uuid', 'child_sys_admin_role', NULL, NULL, '2012-09-21 15:52:00', 'dict_admin_uuid', '2012-09-21 15:56:08', 'dict_admin_uuid', 0);
/*!40000 ALTER TABLE `cheba_account_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
