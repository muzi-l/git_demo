<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>密码管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript"
	src="<c:url value="/js/cust/account/update.password.js?${jsverson}"/>"></script>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="-1">
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 
	response.setDateHeader("Expires", -1); //prevents caching at the proxy server
%>
</head>
<body>
<table height="100%" width="100%">
	<tr>
		<td align="center" width="100%">
			<div id="p">
				<form id="passwordForm" method="post">
					<table style="padding: 5px;">
						<tr>
							<td align="right">用户名：</td>
							<td width="200px" align="right"><input id="loginName"
								name="loginName" disabled="disabled"
								value="<c:out value="${sessionScope.loginName}"></c:out>"
								style="width: 170px;" /></td>
							<td></td>
						</tr>
						<tr>
							<td align="right">原始密码：</td>
							<td width="200px" align="right"><input type="password"
								maxlength="10" onpaste="return false" id="originalPassword"
								name="originalPassword" value="" style="width: 170px;" /></td>
							<td><label style="color: red">*</label></td>
						</tr>
						<tr>
							<td align="right">新密码：</td>
							<td width="200px" align="right"><input type="password"
								maxlength="10" onpaste="return false" id="newPassword"
								name="newPassword" value="" style="width: 170px;" /></td>
							<td><label style="color: red">*</label></td>
						</tr>
						<tr>
							<td align="right">确认新密码：</td>
							<td width="200px" align="right"><input type="password"
								maxlength="10" onpaste="return false" id="newPasswords" value=""
								style="width: 170px;" /></td>
							<td><label style="color: red">*</label></td>
						</tr>
						<tr height="20px">
						</tr>
						<tr>
							<td align="right"></td>
							<td width="200px" align="right"><a id="update"
								class="easyui-linkbutton" onclick="updatePassword()"></a>&nbsp;
								<a id="resets" onclick="resets()" class="easyui-linkbutton"></a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</td>
	</tr>
</table>
</body>
</html>