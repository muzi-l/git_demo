<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>认证中心管理界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript" src="<c:url value="/js/cust/manager/userinfo.check.js?${jsverson}"/>"></script>
</head>
<body>
	<fieldset style="height: 30px;">
		<center>
			<form id="searchform" method="post" autocomplete="off">
			<input type="hidden" id="isdirty" value="true">
				<table>
					<tr>
						<td>登录名</td>
						<td><input id="loginName" type="text" name="loginName" onchange="aa()"></td>
						<td>昵称</td>
						<td><input id="userName" type="text" name="userName" onchange="aa()"></td>
						<td><a class="easyui-linkbutton" id="search" onclick="searchdata()"></a>
						</td>
						<td><a id="chongzhi" class="easyui-linkbutton" type="reset" onclick="chongzhi()"></a></td>
					</tr>
				</table>
			</form>
		</center>
	</fieldset>
	<div style="height: 2px;"></div>
	<a class="easyui-linkbutton" id="add" onclick="add()"></a>
	<div style="height: 2px;"></div>
	<table id="positionTable"></table>
	<div id="dialogDiv"></div>
</body>
</html>