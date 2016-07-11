//全局变量，保存查询参数
var filters = {};
var rootPath = getRootPath();

$(function() {
//	$('#releaseStartTime').datebox({
//		editable : false
//	});
//	$('#releaseEndTime').datebox({
//		editable : false
//	});
	$('#programUuidQuery').combobox({
		width : '152',
		valueField : 'uuid',
		textField : 'programPeriods',
		panelHeight : 'auto',
		editable : false
	});
	
	$('#programUuidQuery').combobox('panel').css({'max-height':'200px'});
	
	$.getJSON(getRootPath()+'/manager/program/questionbank/loadAllProgram', {
		"random" : Math.random()
		}, function(result)
		{
			resultHandler(result, function process(data)
			{
				$("#programUuidQuery").combobox('loadData', data);
				comboboxNullable("programUuidQuery");
			});
		});
	
	$('#searchQuestionbank').linkbutton({
		text : "查询",
		iconCls : "icon-search"
	});
	$('#resetQuestionbankBtn').linkbutton({
		text : "重置",
		iconCls : "icon-reset"
	});
	$('#questionbank_grid').datagrid({
		noHeader : true,
		singleSelect : true,
		checkOnSelect : false,
		selectOnCheck : false,
		striped : true, // 斑马线
		nowrap : false,
		idField : 'uuid', // 该字段为标识符
		rownumbers : true, // 是否显示行号
		autoWidth : false,
		// autoHeight : true,
		fitColumns : true,
		height : $('#questionbank_gg').parent().height() - 43,
		sortName : 'sortNo',
		sortOrder : 'desc',
		pageList : [ 30, 40, 50 ],
		remoteSort : true, // 是否以服务器数据排序
		pagination : true, // 是否显示分页工具栏
		loadMsg : '加载中，请稍后',
		toolbar : [{
			id : 'addQuestionbank',
			text : '新增题目',
			iconCls : 'icon-add',
			handler : function() {
				addQuestionbank();
			}
		}],
		columns : [ [
		{
			field : 'uuid',
			title : 'uuid',
			hidden :true
		},
		{
			field : 'programNameStr',
			title : '期数',
			align : 'center',
			width : getWidth(0.05)
		},{
			field : 'questionTitle',
			title : '题目名称',
			align : 'center',
			width : getWidth(0.15)
		},{
			field : 'difficultyType',
			title : '难度系数',
			align : 'center',
			width : getWidth(0.11),
			formatter : function(value,rowData,rowIndex){
				if(value=="10"){
					return "非常简单";
				}else if(value=="20"){
					return "简单";
				}else if(value=="30"){
					return "一般";
				}else if(value=="40"){
					return "难";
				}else if(value=="50"){
					return "非常难";
				}else{
					return "";
				}
			}
		},
		{
			field : 'questionContent',
			title : '文字题面',
			align : 'center',
			width : getWidth(0.1)
		}, {
			field : 'answer_A',
			title : '选项A',
			align : 'center',
			width : getWidth(0.12)
		}, 
		{
			field : 'answer_B',
			title : '选项B',
			align : 'center',
			width : getWidth(0.12)
		}, 
		{
			field : 'answer_C',
			title : '选项C',
			align : 'center',
			width : getWidth(0.12)
		}, 
		{
			field : 'answer_D',
			title : '选项D',
			align : 'center',
			width : getWidth(0.12)
		}, {
			field : 'op',
			title : '操作',
			align : "center",
			width : getWidth(0.1),
			formatter : formatOpt
		} ] ],
		onSortColumn : function(sort,order){
			var ps = $('#questionbank_grid').datagrid('options').pageSize;
			var pn = $('#questionbank_grid').datagrid('options').pageNumber;
			
			loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/questionbank/query');
		}
	});

	var sort = $('#questionbank_grid').datagrid('options').sortName;
	var order = $('#questionbank_grid').datagrid('options').sortOrder;
	var ps = $('#questionbank_grid').datagrid('options').pageSize;
	var pn = $('#questionbank_grid').datagrid('options').pageNumber;

	// 首次加载表格
	filters = {};

	loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/questionbank/query');
	
	var pager = $('#questionbank_grid').datagrid('getPager');
	if (pager) {
		$(pager).pagination(
				{
					pageList : [ 30, 40, 50 ],
					showRefresh : false,
					onSelectPage : function(pageNumber, pageSize) {
						var sort = $('#questionbank_grid').datagrid('options').sortName;
						var order = $('#questionbank_grid').datagrid('options').sortOrder;
						loadData(filters, new PageOrder(pageSize, pageNumber,sort, order),rootPath+'/manager/program/questionbank/query');
					}
				});
	}
	
	$("#resetQuestionbankBtn").click(function() {
		$("#questionbankForm").get(0).reset();
		$('#programUuidQuery').combobox('setValue', null);
		filters = {};
	});

	// 查询
	$("#searchQuestionbank").click(
			function() {
				$('#questionbank_grid').datagrid('options').pageSize = 30;
				$('#questionbank_grid').datagrid('options').pageNumber = 1;
				$('#questionbank_grid').datagrid('options').pageList = [ 30, 40, 50 ];

				var pager = $('#questionbank_grid').datagrid('getPager');
				if (pager) {
					$(pager)
							.pagination(
									{
										pageNumber : 1,
										pageSize : 30,
										pageList : [ 30, 40, 50 ],
										showRefresh : false,
										onSelectPage : function(pageNumber,
												pageSize) {
											var sort = $('#questionbank_grid').datagrid(
													'options').sortName;
											var order = $('#questionbank_grid').datagrid(
													'options').sortOrder;
											loadData(filters, new PageOrder(
													pageSize, pageNumber, sort,
													order),rootPath+'/manager/program/questionbank/query');
										}
									});
				}

				filters = {
					programUuidQuery : $("#programUuidQuery").combobox('getValue'),
					questionTitleQuery : $.trim($("#questionTitleQuery").val())
				};
				var sort = $('#questionbank_grid').datagrid('options').sortName;
				var order = $('#questionbank_grid').datagrid('options').sortOrder;

				loadData(filters, new PageOrder(30, 1, sort, order),rootPath+'/manager/program/questionbank/query');
				
			});

});

// 封装排序参数
function PageOrder(pageSize, pageNumber, sort, order) {
	this.pageSize = pageSize;
	this.pageNumber = pageNumber;
	this.sort = sort;
	this.order = order;
}

function formatOpt(value, rowData, rowIndex) {
	var text = [];
//	text.push("<a href='javascript:void(0);' onClick='delOne(\"");
//	text.push(rowData.uuid);
//	text.push("\");' title='删除'>");
//	text.push("删除");
//	text.push("</a> ");
//	style='cursor:pointer;' src='./css/lib/jeasyui/themes/icons/pencil.png'
	text.push("<a href='javascript:void(0);' onClick=updateQuestionbank(\"");
	text.push(rowData.uuid);
	text.push("\",\"");
	text.push(rowData.programUuid);
	text.push("\",\"");
	text.push(rowData.questionTitle);
	text.push("\",\"");
	text.push(rowData.difficultyType);
	text.push("\",\"");
	text.push(rowData.questionContent);
	text.push("\",\"");
	text.push(rowData.answer_A);
	text.push("\",\"");
	text.push(rowData.answer_B);
	text.push("\",\"");
	text.push(rowData.answer_C);
	text.push("\",\"");
	text.push(rowData.answer_D);
	text.push("\",\"");
	text.push(rowData.correctAnswer);
	text.push("\") title='编辑'>");
	text.push("编辑");
	text.push("</a> ");
	return text.join('');
}
//记录之前选中答案
var beforAnswerId = '';

function addQuestionbank(){
	$('<div id="dd" title="添加题目" style="width:700px;height:500px;padding:10px 20px">'
			+'<form id="addProgramForm">'
			+'<table align="center">'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目期数:</td><td><input type="text" id="programUuid" name="programUuid" />&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>题目名称:</td><td><input type="text" id="questionTitle" name="questionTitle" />&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>难度系数:</td><td><input type="radio" name="difficultyType" value="10" checked="checked" />非常简单<input type="radio" name="difficultyType" value="20" />简单<input type="radio" name="difficultyType" value="30" />一般<input type="radio" name="difficultyType" value="40" />难<input type="radio" name="difficultyType" value="50" />非常难</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td valign="top">文字题面:</td><td><textarea rows="4" id="questionContent" name="questionContent" maxlength="256" style="width:200px;resize:none"></textarea>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" name="answer_A" id="answer_A"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_A" onClick="clickFuction(\'answer_td_A\',\'answer_A\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_B">B:<input type="text" name="answer_B" id="answer_B"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_B" onClick="clickFuction(\'answer_td_B\',\'answer_B\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_C">C:<input type="text" name="answer_C" id="answer_C"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_C" onClick="clickFuction(\'answer_td_C\',\'answer_C\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_D">D:<input type="text" name="answer_D" id="answer_D"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_D" onClick="clickFuction(\'answer_td_D\',\'answer_D\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td></td></tr>'
			+'</table>'
			+'</form>'
			+ '</div>').appendTo($("#dialogDiv"));
	
	$('#programUuid').combobox({
		width : '205',
		valueField : 'uuid',
		textField : 'programPeriods',
		panelHeight : 'auto',
		editable : false,
		required : true
	});
	$('#questionTitle').validatebox({
		required : true
	});
	$('#questionContent').validatebox({
		required : true
	});
	$('#answer_A').validatebox({
		required : true
	});
	$('#answer_B').validatebox({
		required : true
	});
	$('#answer_C').validatebox({
		required : true
	});
	$('#answer_D').validatebox({
		required : true
	});
	
	$('#programUuid').combobox('panel').css({'max-height':'200px'});
	
	$.getJSON(getRootPath()+'/manager/program/questionbank/loadAllProgram', {
		"random" : Math.random()
		}, function(result)
		{
			resultHandler(result, function process(data)
			{
				$("#programUuid").combobox('loadData', data);
//				comboboxNullable("programUuid");
			});
		});
	
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				$('#addProgramForm').ajaxSubmit({
							url : getRootPath() + '/manager/program/questionbank/add?random=' + Math.random(),
							beforeSubmit:function(){
								if($('#addProgramForm').form("validate")){
									if(!$('input:radio[name="correctAnswer"]').is(":checked")){
										$.messager.alert('错误', '请选择正确答案！', 'error');
										return false;
									}
									return true;
								}
								return false;
							},
							success : function(result) {
								resultHandler(result, function process(data) {
									$.messager.alert('提示', "添加成功", 'info', function()
											{
												$('#dd').dialog('destroy');
										
												var sort = $('#questionbank_grid').datagrid('options').sortName;
												var order = $('#questionbank_grid').datagrid('options').sortOrder;
												var ps = $('#questionbank_grid').datagrid('options').pageSize;
												var pn = $('#questionbank_grid').datagrid('options').pageNumber;		
												loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
														+ '/manager/program/questionbank/query');
											});
								});
							}
				});
				}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dd').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd').dialog('destroy');
		}
	});
	$(".dialog-button").css("border-top-color","#C6E2FF");
	$('#dd').dialog('open');
}

function updateQuestionbank(uuid,programUuid,questionTitle,difficultyType,questionContent,answer_A,answer_B,answer_C,answer_D,correctAnswer){
	$('<div id="dd" title="添加题目" style="width:700px;height:500px;padding:10px 20px">'
			+'<form id="updateProgramForm">'
			+'<input type="hidden" name="uuid" value="'+uuid+'">'
			+'<table align="center">'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目期数:</td><td><input type="text" id="programUuid" name="programUuid" value="'+programUuid+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>题目名称:</td><td><input type="text" id="questionTitle" name="questionTitle" value="'+questionTitle+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>难度系数:</td><td><input type="radio" name="difficultyType" id="difficultyType1" value="10" />非常简单<input type="radio" name="difficultyType" id="difficultyType2" value="20" />简单<input type="radio" name="difficultyType" id="difficultyType3" value="30" />一般<input type="radio" name="difficultyType" id="difficultyType4" value="40" />难<input type="radio" name="difficultyType" id="difficultyType5" value="50" />非常难</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td valign="top">文字题面:</td><td><textarea rows="4" id="questionContent" name="questionContent" maxlength="256" style="width:200px;resize:none">'+questionContent+'</textarea>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" name="answer_A" value="'+answer_A+'" id="answer_A"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_A" onClick="clickFuction(\'answer_td_A\',\'answer_A\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_B">B:<input type="text" name="answer_B" value="'+answer_B+'" id="answer_B"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_B" onClick="clickFuction(\'answer_td_B\',\'answer_B\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_C">C:<input type="text" name="answer_C" value="'+answer_C+'" id="answer_C"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_C" onClick="clickFuction(\'answer_td_C\',\'answer_C\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_D">D:<input type="text" name="answer_D" value="'+answer_D+'" id="answer_D"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_D" onClick="clickFuction(\'answer_td_D\',\'answer_D\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td></td></tr>'
			+'</table>'
			+'</form>'
			+ '</div>').appendTo($("#dialogDiv"));
	
	$('#programUuid').combobox({
		width : '205',
		valueField : 'uuid',
		textField : 'programPeriods',
		panelHeight : 'auto',
		editable : false,
		required : true
	});
	$('#questionTitle').validatebox({
		required : true
	});
	
//	if(difficultyType=='10'){
//		$("#difficultyType1").attr("checked","checked");
//	}else if(difficultyType=='20'){
//		$("#difficultyType2").attr("checked","checked");
//	}else if(difficultyType=='30'){
//		$("#difficultyType3").attr("checked","checked");
//	}else if(difficultyType=='40'){
//		$("#difficultyType4").attr("checked","checked");
//	}else{
//		$("#difficultyType5").attr("checked","checked");
//	}
	$("input[type=radio][value='"+difficultyType+"']").attr("checked",'checked');
//	clickFuction(correctAnswer);
	$("input[type=radio][value='"+correctAnswer+"']").trigger("click");
	$("input[type=radio][value='"+correctAnswer+"']").attr("checked",'checked');
	
	$('#questionContent').validatebox({
		required : true
	});
	$('#answer_A').validatebox({
		required : true
	});
	$('#answer_B').validatebox({
		required : true
	});
	$('#answer_C').validatebox({
		required : true
	});
	$('#answer_D').validatebox({
		required : true
	});
	
	$('#programUuid').combobox('panel').css({'max-height':'200px'});
	
	$.getJSON(getRootPath()+'/manager/program/questionbank/loadAllProgram', {
		"random" : Math.random()
		}, function(result)
		{
			resultHandler(result, function process(data)
			{
				$("#programUuid").combobox('loadData', data);
//				comboboxNullable("programUuid");
			});
		});
	
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				$('#updateProgramForm').ajaxSubmit({
					url : getRootPath() + '/manager/program/questionbank/update?random=' + Math.random(),
					beforeSubmit:function(){
						if($('#updateProgramForm').form("validate")){
							if(!$('input:radio[name="correctAnswer"]').is(":checked")){
								$.messager.alert('错误', '请选择正确答案！', 'error');
								return false;
							}
							return true;
						}
						return false;
					},
					success : function(result) {
						resultHandler(result, function process(data) {
							$.messager.alert('提示', "编辑成功", 'info', function()
									{
										$('#dd').dialog('destroy');
								
										var sort = $('#questionbank_grid').datagrid('options').sortName;
										var order = $('#questionbank_grid').datagrid('options').sortOrder;
										var ps = $('#questionbank_grid').datagrid('options').pageSize;
										var pn = $('#questionbank_grid').datagrid('options').pageNumber;		
										loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
												+ '/manager/program/questionbank/query');
									});
						});
					}
				});
				}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dd').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd').dialog('destroy');
		}
	});
	$(".dialog-button").css("border-top-color","#C6E2FF");
	$('#dd').dialog('open');
}

function clickFuction(td_id){
	if(beforAnswerId!=''){
		$("#"+beforAnswerId).removeAttr("style");
		$('#'+beforAnswerId+' :text').removeAttr("style");
	}
	$("#"+td_id).css("color","red");
	$('#'+td_id+' :text').css("color","red");
	
	beforAnswerId = td_id;
}

function getWidth(percent) {
	return $('#questionbank_grid').parent().width() * percent;
}

function dateOf(value)
{
	if (value)
	{
		return (new Date(value)).format("yyyy-MM-dd");	
	}
	return;
}

function dateOf2(value)
{
	if (value)
	{
		return (new Date(value)).format("yyyy-MM-dd hh:mm:ss");	
	}
	return;
}

//加载数据到表格
function loadData(queryParams, pageOrder,url) {
	// 参数
	var params = {};
	if (queryParams) {
		if (pageOrder) {
			queryParams.pageSize = pageOrder.pageSize;
			queryParams.pageNumber = pageOrder.pageNumber;
			queryParams.sort = pageOrder.sort;
			queryParams.order = pageOrder.order;
		}
		params = queryParams;
	} else {
		params = pageOrder;
	}
	params.ct = new Date().getTime();

	$.getJSON(url, params,
		function(result) {
			resultHandler(result, function process(data) {
				if(data==null){
					//无数据
					$('#questionbank_grid').datagrid('loadData', { total: 0, rows: [] });
				}else{
					$('#questionbank_grid').datagrid('loadData', data);
				}
			});
		});
}