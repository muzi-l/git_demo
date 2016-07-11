<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>角色管理</title>
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript"
	src="<c:url value="/js/cust/account/role.check.js?${jsverson}"/>"></script>
</head>
<body>
<fieldset style="height: 30px;">
	<center>
		<form id="searchform" method="post" autocomplete="off">
			<table align="center">
				<tr>
					<td>角色名称 <input type="text" name="name" id="name"
						onkeydown="if(event.keyCode==13) return false;" onchange="aa()"></td>
					<td><a class="easyui-linkbutton" id="searchRole"
						onclick="searchdata()"></a> <a onclick="chongzhi()"
						class="easyui-linkbutton" id="chongzhiRole" type="reset"></a></td>
				</tr>
			</table>
		</form>
	</center>
</fieldset>
<div style="height: 2px;"></div>
<a class="easyui-linkbutton" id="addRole" onclick="add()"></a>
<div style="height: 2px;"></div>
<table id="roleTable"></table>
<div id="dialogDiv"></div>
</body>
</html>