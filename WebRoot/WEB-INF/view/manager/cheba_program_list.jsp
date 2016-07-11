<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>题目列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 样式所需导入的链接 -->
		<%@ include file="../common/css.jsp"%>
<!-- 样式所需导入的链接 -->
<script type="text/javascript"
	src="<c:url value="/js/cust/manager/cheba.program.list.js?${jsverson}"/>"></script>

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
<table width="100%" height="100%">
	<tr>
		<td valign="top">
			<div>
				<fieldset style="height: 25px">
					<form id="programQueryForm">
						<table align="center">
							<tr>
								<td>节目名称：</td>
								<td><input type="text" id="programNameQuery" maxlength="32" /></td>
								<td>节目期数：</td>
								<td><input type="text" id="programPeriodsQuery"
									maxlength="32" /></td>
								<td colspan="2" align="right"><a href="#" id="searchProgram">查询</a>
									<a href="#" id="resetProgramBtn">重置</a></td>
								<!-- 									<td>标题：</td>
									<td><input type="text" id="infoTitle" maxlength="32" /></td>
									<td>内容关键字：</td>
									<td><input type="text" id="infoContent" maxlength="32" /></td> -->
							</tr>
							<!-- 								<tr>
									<td>发布开始时间：</td>
									<td><input type="text" id="releaseStartTime"
										maxlength="32" /></td>
									<td>发布结束时间：</td>
									<td><input type="text" id="releaseEndTime" maxlength="32" /></td>
									<td></td>
									<td></td>
								</tr>-->
							<tr>
								<!-- 									<td></td>
									<td></td>
									<td></td> 
								<td></td>
 -->
							</tr>
						</table>
					</form>
				</fieldset>
			</div> <!-- 表格 -->
			<div id="program_gg">
				<table id="program_grid"></table>
			</div>
		</td>
	</tr>
</table>
<div id="dialogDiv"></div>
</body>
</html>