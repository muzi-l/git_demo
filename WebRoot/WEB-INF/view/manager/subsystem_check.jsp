<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>可登录系统管理界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript" src="<c:url value="/js/cust/manager/subsystem.check.js?${jsverson}"/>"></script>
</head>
<body>
	<span style="height: 30px;">
	</span>
	<div style="height: 2px;"></div>
	<a class="easyui-linkbutton" id="add" onclick="opendialog('add')"></a>
	<div style="height: 2px;"></div>
	<div id="gg">
		<table id="positionTable"></table>
	</div>
	<div id="dialogDiv"></div>
</body>
</html>