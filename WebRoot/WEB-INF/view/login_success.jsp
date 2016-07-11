<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link href="favicon.ico" rel="Shorcut Icon" />
<link href="<c:url value="/css/lib/jeasyui/themes/default/easyui.css"/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/lib/jeasyui/themes/icon.css"/>"
	rel="stylesheet" type="text/css" />
<link href="<c:url value="/css/lib/ztree/zTreeStyle/zTreeStyle.css"/>"
	rel="stylesheet" type="text/css" />
<link
	href="<c:url value="/css/lib/jqueryui/themes/base/jquery.ui.all.css" />"
	rel="stylesheet" type="text/css" />

<script src="<c:url value="/js/lib/jquery/jquery-1.7.1.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/jeasyui/jquery.easyui.min.js"/>"
	type="text/javascript"></script>
<script
	src="<c:url value="/js/lib/jeasyui/plugins/datagrid-detailview.js"/>"
	type="text/javascript"></script>
<script
	src="<c:url value="/js/lib/jeasyui/locale/easyui-lang-zh_CN.js"/>"
	type="text/javascript"></script>
<script
	src="<c:url value="/js/lib/date/My97DatePicker/WdatePicker.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/json/jquery.json-2.2.min.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/base/base64.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/tools/message.handler.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/tools/date.formate.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/form/jquery.form.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/ztree/jquery.ztree.all-3.1.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/jqueryui/ui/jquery.ui.core.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/jqueryui/ui/jquery.ui.widget.js" />"
	type="text/javascript"></script>
<script
	src="<c:url value="/js/lib/jqueryui/ui/jquery.ui.position.js" />"
	type="text/javascript"></script>
<script
	src="<c:url value="/js/lib/jqueryui/ui/jquery.ui.autocomplete.js" />"
	type="text/javascript"></script>
<script src="<c:url value="/js/lib/cookie/jquery.cookie.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/js/cust/main.js?${jsverson}"/>"
	type="text/javascript"></script>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<title>扯吧程序管理</title>
</head>
<body class="easyui-layout">
	<div region="north"
		style="height:50px;text-align:center;background-image: url(<c:url value="/img/titlebackground.png" />);">
		<input type="hidden" id="systemName"
			value="${sessionScope.systemName}"> <input type="hidden"
			id="systemversion" value="${sessionScope.systemversion}">
		<table align="center" width="100%" height="100%">
			<tr>
				<td style="width: 30%; text-align: left;"><img height="100%"
					src="<c:url value="/img/title.png" />"></td>
				<td style="width: 40%; text-align: center;"><strong><font
						size="5">牛逼后台管理</font></strong></td>
				<td style="width: 30%; text-align: right;"><c:out
						value="${sessionScope.loginName}"></c:out>,欢迎您！<a href="#"
					onclick="signout()">退出</a>&nbsp;&nbsp;<a href="#"
					onclick="systeminformation()">关于</a></td>
			</tr>
		</table>
	</div>
	<div id="dialogDiv"></div>
	<div region="west" style="width: 200px; text-align: left;">
		<div id="menu" style="width: 150px;"></div>
	</div>
	<div region="center" id="center">
		<div id="tt" class="easyui-tabs" fit="true" border="false"
			plain="true">
			<div title="欢迎页">
				<embed src="flash/timeFlash2.swf" quality="high" width="99%" height="99%"      
              wmode="transparent" type='application/x-shockwave-flash'>
			 
			</div>
		</div>
	</div>
</body>
</html>