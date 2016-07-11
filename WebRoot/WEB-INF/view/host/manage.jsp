<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<c:url value="/js/cust/host/manage.js?${jsverson}"/>"></script>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="-1">
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 
	response.setDateHeader("Expires", -1); //prevents caching at the proxy server
%>
<style>
<!--
.programInfo{
height: 25px;
line-height: 25px;
}
/* #chats .info { */
/* margin: 3px 1px; */
/* } */

/* #chats .desc { */
/* font-weight: bold; */
/* font-size: 14px; */
/* } */

/* #chats .msg { */
/* word-wrap:break-word; */
/* font-family: courier; */
/* font-size: 14px; */
/* margin: 3px 1px; */
/* } */
.answerer-item{
	font-size:12px;
	padding:3px;
	padding-right:0px;
	cursor:default;
}
.case-answer{
	margin: 4px 0;
}
.case-answer-item{
	display:inline;
	height: 20px;
	line-height: 20px;
	font-size:12px;
	margin: 2px 0;
}
.answerer-item-hover{
	background:#99BBE8;
}
.answerer-item-selected{
	background:#FBEC88;
}
-->
</style>
<div id="progamePanel" style="width:1300px;height: 600px;">
	<div style="display:inline-block; _zoom:1;_display:inline;margin: 4px 2px 2px 4px;float: left;">
		<div id="answererPanel">
			<table>
				<tr>
					<td colspan="2"><div style="margin: 1px 0px;font-weight: bold;height: 15px;">当前节目详情：</div></td>
				</tr>
				<tr>
					<td colspan="2">
						<table style="width: 292px;height: 210px;border: 1px solid #99BBE8;">
							<tr>
								<td style="text-align: left;width: 80px;">节目名称：</td>
								<td style="text-align: left;"><div id="programName" class="programInfo"></div></td>
							</tr>
							<tr>
								<td style="text-align: left;width: 80px;">节目期数：</td>
								<td style="text-align: left;"><div id="programPeriods" class="programInfo"></div></td>
							</tr>
							<tr>
								<td style="text-align: left;width: 80px;">主持人：</td>
								<td style="text-align: left;"><div id="compereName" class="programInfo"></div></td>
							</tr>
							<tr>
								<td style="text-align: left;width: 80px;">当前答题者：</td>
								<td style="text-align: left;"><div id="currRespondents" class="programInfo"></div></td>
							</tr>
							<tr>
								<td style="text-align: left;width: 80px;">当前奖金：</td>
								<td style="text-align: left;"><div id="currBonus" class="programInfo"></div></td>
							</tr>
							<tr>
								<td style="text-align: left;width: 80px;">答题时间：</td>
								<td style="text-align: left;"><div id="answerTime" class="programInfo"></div></td>
							</tr>
							<tr>
								<td style="text-align: left;width: 80px;">已完成题目：</td>
								<td style="text-align: left;"><div id="completeNo" class="programInfo"></div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><div style="margin: 1px 0px;font-weight: bold;height: 15px;">请选择答题者：</div></td>
				</tr>
				<tr>
					<td>
						<div id="answerer" style="width: 202px;height: 84px;border: 1px solid #99BBE8;">
						</div>
					</td>
					<td>
						<button id="selectBtn" disabled="disabled" style="width: 84px;height: 84px;">选择</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="display:inline-block; _zoom:1;_display:inline;margin: 4px 2px 2px 2px;float: left;">
		<div id="answerPanel">
			<table>
				<tr style="height: 100px;">
					<td style="vertical-align: top;">
						<div style="margin: 1px 0px;font-weight: bold;height: 15px;">当前题目：</div>
					</td>
					<td style="vertical-align: top;">
						<span id="currQuestionTitle" style="margin: 0px 3px;"></span>
						<p id="currQuestionContent" style="margin: 0px 3px;"></p>
					</td>
				</tr>
				<tr style="height: 170px;">
					<td style="vertical-align: top;">
						<div style="margin: 1px 0px;font-weight: bold;padding-top: 5px;height: 15px;">答题选项：</div>
					</td>
					<td>
						<table id="answerTab">
							<tr>
								<td><button id="answer_A" style="width: 200px;height: 80px;" disabled="disabled"></button></td>
								<td><button id="answer_B" style="width: 200px;height: 80px;" disabled="disabled"></button></td>
							</tr>
							<tr>
								<td><button id="answer_C" style="width: 200px;height: 80px;" disabled="disabled"></button></td>
								<td><button id="answer_D" style="width: 200px;height: 80px;" disabled="disabled"></button></td>
							</tr>
	
						</table>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<button id="pushQuestion" style="width: 404px;height: 40px;margin: 3px;" disabled="disabled">出题</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="display:inline-block; _zoom:1;_display:inline;margin: 4px 2px 2px 2px;clear: right;">
		<div id="answerCasePanel">
			<table>
				<tr>
					<td style="vertical-align: top;">
						<div style="margin: 1px 0px;font-weight: bold;height: 15px;">答题倒计时：</div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="answerCountdown"></div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
						<div style="margin: 1px 0px;font-weight: bold;height: 15px;margin: 4px 0 0 0;">观众答题情况：</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="case-answer">
							A：<div id="answerA" class="case-answer-item"></div>
							<div id="answerA_case"></div>
						</div>
						<div class="case-answer">
							B：<div id="answerB" class="case-answer-item"></div>
							<div id="answerB_case"></div>
						</div>
						<div class="case-answer">
							C：<div id="answerC" class="case-answer-item"></div>
							<div id="answerC_case"></div>
						</div>
						<div class="case-answer">
							D：<div id="answerD" class="case-answer-item"></div>
							<div id="answerD_case"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<button id="caseRefresh" style="width: 392px;height: 100px;margin: 2px 0px;" disabled="disabled">刷新 </button>
					</td>
				</tr>
			</table>
		</div>
	</div>
<!-- 	<div style=""> -->
		<iframe src="${chatUrl}" style="width: 1206px;height: 250px;border: 1px solid #99BBE8;margin: 2px 2px 2px 4px;"></iframe>
<!-- 		<div id="chatRoom" style=""> -->
<%-- 			<input id="chatUrl" type="text" style="display: none;" value="${chatUrl}" /> --%>
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 					<td> -->
<!-- 						<div id="chats" style="width: 1198px;height: 240px;border: 1px solid #99BBE8;overflow: scroll;"> -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
