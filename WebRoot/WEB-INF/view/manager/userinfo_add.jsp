<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>认证中心新增界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript"  src="<c:url value="/js/cust/manager/userinfo.add.js?${jsverson}"/>"></script>
</head>
<body>
	<table height="100%" width="100%">
		<tr>
			<td align="center" width="100%">
				<div>
					<form id="form" method="post" autocomplete="off">
						<table align="center">
							<tr>
								<td>登录名称:</td>
								<td><input id="loginName" name="loginName" maxlength="30"/>&nbsp;<font color="#FF0000">*</font></td>
								<td>可登录系统:<br></td>
								<td rowspan="6"><select multiple="multiple"
									id="subsystemUuid" name="subsystemUuid"
									style="width: 150px; height: 150px;"></select></td>
								<td><font color="#FF0000">*</font><label style="color: red"><按Ctrl键可多选></label></td>
							</tr>
							<tr>
								<td>登录密码:</td>
								<td><input id="password" type="password" name="password"  maxlength="16" style="width: 150px;"
									onpaste="return false" />&nbsp;<font color="#FF0000">*</font></td>
							</tr>
							<tr>
								<td>昵称:</td>
								<td><input id="userName" name="userName" maxlength="30"></input>&nbsp;<font
									color="#FF0000">*</font></td>
							</tr>
							<tr>
								<td>认证邮箱:</td>
								<td><input id="email" name="email" onpaste="return false" maxlength="60"
									style="ime-mode: disabled"></input>&nbsp;<font
									color="#FF0000">*</font></td>
							</tr>
							<tr>
								<td>用户类型:</td>
								<td><input id="type" name="type" 
									></input>&nbsp;<font
									color="#FF0000">*</font></td>
							</tr>
							<tr>
								<td>用户状态:</td>
								<td><input id="status" name="status" 
									></input>&nbsp;<font
									color="#FF0000">*</font></td>
							</tr>
							<tr style="height: 150px"></tr>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td align="right">
								<a class="easyui-linkbutton" id="tijiao" onclick="addbutton()"></a> 
								<a class="easyui-linkbutton" id="roback"></a></td>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
	<div id="dialogDiv"></div>
</body>
</html>