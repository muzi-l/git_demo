<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>题目列表</title>
<style type="text/css">
.ztree li button.switch {
	visibility: hidden;
	width: 1px;
}
</style>
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript"
	src="<c:url value="/js/cust/account/show.user.js?${jsverson}"/>"></script>
</head>
<body>
<fieldset style="height: 30px;">
	<div>
		<form id="ff" method="post" autocomplete="off">
			<input type="hidden" id="pageSize" value="15" /> <input
				type="hidden" id="pageNumber" value="1" /> <input type="hidden"
				id="sort" value="loginName" /> <input type="hidden" id="order"
				value="asc" /> <input type="hidden" id="isdirty" value="true">

			<table align="center">
				<tr>
					<td>登录名：</td>
					<td><input type="text" id="loginName" name="loginName"
						style="width: 130px; height: 20px; background: none repeat scroll 0 0 #FFFFFF; border: 1px solid #A4BED4;"
						onchange="change()" /></td>
					<td>角色：</td>
					<td><input id="role1" name="role1" /> <input type="hidden"
						id="role" name="role" /></td>
					<td></td>
					<td><a class="easyui-linkbutton" id="search"></a></td>
					<td><a class="easyui-linkbutton" type="reset" id="resetUser"></a></td>
				</tr>
			</table>

		</form>
	</div>
</fieldset>
<div style="margin-bottom: 2px; margin-top: 2px;">
	<a class="easyui-linkbutton" id="addUser"></a>
</div>
<div id="gg">
	<table id="positionTable"></table>
</div>
<div id="dialogDiv"></div>
</body>
</html>