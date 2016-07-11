// 校验
$.extend($.fn.validatebox.defaults.rules,
{
	max : {
		validator : function(value, param) {
			return value <= param[0];
		},
		message : '当前允许的最大排序位置为{0}'
	}
});

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
	$('#searchProgram').linkbutton({
		text : "查询",
		iconCls : "icon-search"
	});
	$('#resetProgramBtn').linkbutton({
		text : "重置",
		iconCls : "icon-reset"
	});
	$('#program_grid').datagrid({
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
		height : /*$('#program_gg').parent().height() - 43*/ "auto",
		sortName : 'programTime',
		sortOrder : 'desc',
		pageList : [ 30, 40, 50 ],
		remoteSort : true, // 是否以服务器数据排序
		pagination : true, // 是否显示分页工具栏
		loadMsg : '加载中，请稍后',
		toolbar : [{
			id : 'addaddProgram',
			text : '新增节目',
			iconCls : 'icon-add',
			handler : function() {
				addProgram();
			}
		}],
		rowStyler: function(index,row){
			if (row.status=="20"){
				return 'background-color:lightpink;color:#fff;';
			}
			},
		columns : [ [
		{
			field : 'uuid',
			title : 'uuid',
			hidden :true
		},
		{
			field : 'programName',
			title : '节目名称',
			align : 'center',
			sortable : true,
			width : getWidth(0.1)
		},{
			field : 'programPeriods',
			title : '节目期数',
			align : 'center',
			sortable : true,
			width : getWidth(0.1)
		},{
			field : 'programTime',
			title : '节目时间',
			align : 'center',
			sortable : true,
			width : getWidth(0.11),
			formatter : dateOf
		},
		{
			field : 'pass_1_bonus',
			title : '初始奖金(第一关)',
			align : 'center',
			sortable : true,
			width : getWidth(0.1)
		},
		{
			field : 'compereName',
			title : '节目主持人',
			align : 'center',
			sortable : true,
			width : getWidth(0.1)
		},{
			field : 'status',
			title : '节目状态',
			align : 'center',
			width : getWidth(0.1),
			formatter : function(value,rowData,rowIndex){
				if(value=="10"){
					
					return "未开启";
				}else if(value=="20"){
					return "已开启";
				}else if(value=="30"){
					return "已结束";
				}else{
					return "";
				}
			}
		},{
			field : 'op',
			title : '操作',
			align : "center",
			width : getWidth(0.1),
			formatter : formatOpt
		} ] ],
		onSortColumn : function(sort,order){
			var ps = $('#program_grid').datagrid('options').pageSize;
			var pn = $('#program_grid').datagrid('options').pageNumber;
			
			loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/query',"program_grid");
		},
		onLoadSuccess : function (data) {
			if (!data.rows||data.rows=='') {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据</td></tr>');
			}
		}
	});

	var sort = $('#program_grid').datagrid('options').sortName;
	var order = $('#program_grid').datagrid('options').sortOrder;
	var ps = $('#program_grid').datagrid('options').pageSize;
	var pn = $('#program_grid').datagrid('options').pageNumber;

	// 首次加载表格
	filters = {};

	loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/query',"program_grid");
	
	var pager = $('#program_grid').datagrid('getPager');
	if (pager) {
		$(pager).pagination(
				{
					pageList : [ 30, 40, 50 ],
					showRefresh : false,
					onSelectPage : function(pageNumber, pageSize) {
						var sort = $('#program_grid').datagrid('options').sortName;
						var order = $('#program_grid').datagrid('options').sortOrder;
						loadData(filters, new PageOrder(pageSize, pageNumber,sort, order),rootPath+'/manager/program/query',"program_grid");
					}
				});
	}
	
	$("#resetProgramBtn").click(function() {
		$("#programQueryForm").get(0).reset();
		filters = {};
	});

	// 查询
	$("#searchProgram").click(
			function() {
				$('#program_grid').datagrid('options').pageSize = 30;
				$('#program_grid').datagrid('options').pageNumber = 1;
				$('#program_grid').datagrid('options').pageList = [ 30, 40, 50 ];

				var pager = $('#program_grid').datagrid('getPager');
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
											var sort = $('#program_grid').datagrid(
													'options').sortName;
											var order = $('#program_grid').datagrid(
													'options').sortOrder;
											
											loadData(filters, new PageOrder(
													pageSize, pageNumber, sort,
													order),rootPath+'/manager/program/query',"program_grid");
										}
									});
				}

				filters = {
					programNameQuery : $.trim($("#programNameQuery").val()),
					programPeriodsQuery : $.trim($("#programPeriodsQuery").val())
				};
				var sort = $('#program_grid').datagrid('options').sortName;
				var order = $('#program_grid').datagrid('options').sortOrder;
				
				loadData(filters, new PageOrder(30, 1, sort, order),rootPath+'/manager/program/query',"program_grid");
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
	if(rowData.status=="10"){
		text.push("<a href='javascript:void(0);' onClick='taskExecute(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push("20");
		text.push("\");' title='开启'>");
		text.push("开启");
		text.push("</a> ");
		
		text.push("<a href='javascript:void(0);' onClick=updateProgram(\"");
		text.push(rowData.uuid);
		text.push("\") title='编辑'>");
		text.push("编辑");
		text.push("</a> ");
		
		text.push("<a href='javascript:void(0);' onClick=managerQuestionbank(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push(rowData.programPeriods);
		text.push("\") title='节目题库'>");
		text.push("节目题库");
		text.push("</a>");
		
		text.push(" <a href='javascript:void(0);' onClick=addQuestionbank2(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push(rowData.programPeriods);
		text.push("\") title='添加题'>");
		text.push("添加题");
		text.push("</a>");
	}
	if(rowData.status=="20"){
		text.push("<a href='javascript:void(0);' onClick='taskExecute(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push("30");
		text.push("\");' title='结束'>");
		text.push("结束");
		text.push("</a> ");
		//
		text.push("<a href='javascript:void(0);' onClick=updateProgram3(\"");
		text.push(rowData.uuid);
		text.push("\") title='查看'>");
		text.push("查看");
		text.push("</a> ");
		
		text.push("<a href='javascript:void(0);' onClick=updateProgramAllotPopulation(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push(rowData.programPeriods);
		text.push("\") title='分配人数'>");
		text.push("分配人数");
		text.push("</a>");
		
//		text.push("<a href='javascript:void(0);' onClick=updateProgram(\"");
//		text.push(rowData.uuid);
//		text.push("\") title='编辑'>");
//		text.push("编辑");
//		text.push("</a> ");
		
//		text.push("<a href='javascript:void(0);' onClick=managerQuestionbank(\"");
//		text.push(rowData.uuid);
//		text.push("\",\"");
//		text.push(rowData.programPeriods);
//		text.push("\") title='节目题库'>");
//		text.push("节目题库");
//		text.push("</a>");
		
		text.push(" <a href='javascript:void(0);' onClick=addQuestionbank2(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push(rowData.programPeriods);
		text.push("\") title='添加题'>");
		text.push("添加题");
		text.push("</a>");
	}if(rowData.status=="30"){
//		text.push("<a href='javascript:void(0);' onClick=updateProgram3(\"");
//		text.push(rowData.uuid);
//		text.push("\") title='查看'>");
//		text.push("查看");
//		text.push("</a> ");
//		
//		text.push("<a href='javascript:void(0);' onClick=managerQuestionbank3(\"");
//		text.push(rowData.uuid);
//		text.push("\",\"");
//		text.push(rowData.programPeriods);
//		text.push("\") title='节目题库'>");
//		text.push("节目题库");
//		text.push("</a>");
		
		text.push("<a href='javascript:void(0);' onClick=updateProgram(\"");
		text.push(rowData.uuid);
		text.push("\") title='编辑'>");
		text.push("编辑");
		text.push("</a> ");
		
		text.push("<a href='javascript:void(0);' onClick=managerQuestionbank(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push(rowData.programPeriods);
		text.push("\") title='节目题库'>");
		text.push("节目题库");
		text.push("</a>");
		
		text.push(" <a href='javascript:void(0);' onClick=addQuestionbank2(\"");
		text.push(rowData.uuid);
		text.push("\",\"");
		text.push(rowData.programPeriods);
		text.push("\") title='添加题'>");
		text.push("添加题");
		text.push("</a>");
	}
//	text.push("<a href='javascript:void(0);' onClick='delOne(\"");
//	text.push(rowData.uuid);
//	text.push("\");' title='删除'>");
//	text.push("删除");
//	text.push("</a> ");

	//----题目-----
	

	return text.join('');
}

function addProgram(){
	$('<div id="addProgram_dd" title="添加节目" style="width:700px;height:600px;padding:10px 20px">'
			+'<form id="addProgramForm">'
			+'<input type="hidden" name="status" value="10" />'
			+'<table align="center">'
//			+'<tr height="10px"></tr>'
//			+'<tr><td colspan="2"><input id="programStatus" type="radio" name="status" value="10" checked="checked" />不开启&nbsp;<input id="programStatus" type="radio" name="status" value="20" />立即开启&nbsp;<input id="programStatus" type="radio" name="status" value="30" />已结束</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目名称:</td><td><input type="text" id="programName" name="programName" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目期数:</td><td><input type="text" id="programPeriods" name="programPeriods" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目时间:</td><td><input type="text" id="programTime" name="programTime"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第一关奖金:</td><td><input type="text" name="pass_1_bonus" id="pass_1_bonus" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第一关奖金分配人数:</td><td><input type="text" name="pass_1_bonusAllotPopulation" id="pass_1_bonusAllotPopulation" maxlength="11"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第二关奖金:</td><td><input type="text" name="pass_2_bonus" id="pass_2_bonus" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第二关奖金分配人数:</td><td><input type="text" name="pass_2_bonusAllotPopulation" id="pass_2_bonusAllotPopulation" maxlength="11"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第三关奖金:</td><td><input type="text" name="pass_3_bonus" id="pass_3_bonus" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第三关奖金分配人数:</td><td><input type="text" name="pass_3_bonusAllotPopulation" id="pass_3_bonusAllotPopulation" maxlength="11"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目题库:</td><td>已有<font color="#FF0000">0</font>道题目</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>答题时间:</td><td><input type="text" style="width:131px;" name="answerTime" id="answerTime" maxlength="11"/>&nbsp;秒&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>节目主持人:</td><td><input type="text" name="compereName" id="compereName" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第一位答题者:</td><td><input type="text" readonly name="answer_1_Name" id="answer_1_Name" maxlength="64"/>&nbsp;<font color="#FF0000">*</font>&nbsp;<button type="button" onClick="selectUser(\'一\')">选择...</button></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第二位答题者:</td><td><input type="text" readonly name="answer_2_Name" id="answer_2_Name" maxlength="64"/>&nbsp;<font color="#FF0000">*</font>&nbsp;<button type="button" onClick="selectUser(\'二\')">选择...</button></td></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>第三位答题者:</td><td><input type="text" readonly name="answer_3_Name" id="answer_3_Name" maxlength="64"/>&nbsp;<font color="#FF0000">*</font>&nbsp;<button type="button" onClick="selectUser(\'三\')">选择...</button></td></td></tr>'
			+'<tr height="10px"><td><input type="hidden" id="answer_1_Uuid" name="answer_1_Uuid"/><input type="hidden" id="answer_2_Uuid" name="answer_2_Uuid"/><input type="hidden" id="answer_3_Uuid" name="answer_3_Uuid"/></td></tr>'
			+'</table>'
			+'</form>'
			+ '</div>').appendTo($("#dialogDiv"));
	
	$('#programName').validatebox({
		required : true
	});
	$('#programPeriods').validatebox({
		required : true
	});
	$('#programTime').datebox({
		required : true,
		editable : false,
		width : 153
	});
//	$('#bonus').validatebox({
//		
//	});
	$('#pass_1_bonus').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
//	$('#answerTime').validatebox({
//		required : true
//	});
	$('#pass_1_bonusAllotPopulation').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
	$('#pass_2_bonus').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
	$('#pass_2_bonusAllotPopulation').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
	$('#pass_3_bonus').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
	$('#pass_3_bonusAllotPopulation').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
	$('#answerTime').numberbox({   
	    min:1,
	    max:10000,
	    precision:0,
	    required : true
	});
	$('#compereName').validatebox({
		required : true
	});
	$('#answer_1_Name').validatebox({
		required : true
	});
	$('#answer_2_Name').validatebox({
		required : true
	});
	$('#answer_3_Name').validatebox({
		required : true
	});
//	$('#answer_1_Name').validatebox({
//		required : true
//	});
//	$('#answer_1_Uuid').combobox({
//		width : '153',
//		valueField : 'uuid',
//		textField : 'userName',
//		panelHeight : 'auto',
////		editable : false,
//		required : true,
//		filter:function(q,row){
//			var opts=$(this).combobox("options");
//			return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;
//		}
//	});
//	$('#answer_2_Uuid').combobox({
//		width : '153',
//		valueField : 'uuid',
//		textField : 'userName',
//		panelHeight : 'auto',
////		editable : false,
//		required : true,
//		filter:function(q,row){
//			var opts=$(this).combobox("options");
//			return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;
//		}
//	});
//	$('#answer_3_Uuid').combobox({
//		width : '153',
//		valueField : 'uuid',
//		textField : 'userName',
//		panelHeight : 'auto',
////		editable : false,
//		required : true,
//		filter:function(q,row){
//			
//		var opts=$(this).combobox("options");
////		return row[opts.textField].indexOf(q)>-1;
////		console.info(row[opts.textField].indexOf(q)==0);
////		return row[opts.textField].indexOf(q)==0;
//		return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;
//		}
//	});
//	$('#answer_1_Uuid').combobox('panel').css({'max-height':'200px'});
//	$('#answer_2_Uuid').combobox('panel').css({'max-height':'200px'});
//	$('#answer_3_Uuid').combobox('panel').css({'max-height':'200px'});
//	$.getJSON(getRootPath()+'/manager/program/getUser', {
//		"random" : Math.random()
//		}, function(result)
//		{
//			resultHandler(result, function process(data)
//			{
//				$("#answer_1_Uuid").combobox('loadData', data);
//				$("#answer_2_Uuid").combobox('loadData', data);
//				$("#answer_3_Uuid").combobox('loadData', data);
////				comboboxNullable("programUuid");
//			});
//		});

	$('#addProgram_dd').dialog({
		modal : true,
		buttons : [ {
			text : '下一步',
			iconCls : 'icon-next-step',
			handler : function() {
//				$("#answer_1_Name").val($("#answer_1_Uuid").combobox('getText'));
//				$("#answer_2_Name").val($("#answer_2_Uuid").combobox('getText'));
//				$("#answer_3_Name").val($("#answer_3_Uuid").combobox('getText'));
				$('#addProgramForm').ajaxSubmit({
							url : getRootPath() + '/manager/program/add?random=' + Math.random(),
							dataType : 'json',
							beforeSubmit:function(){
								if($('#addProgramForm').form("validate")){
//									if($("#answer_1_Uuid").combobox('getValue') == $("#answer_1_Uuid").combobox('getText')){
//										$.messager.alert('错误', '答题者甲不存在,请从下拉框中点击一行选中', 'error');
//										return false;
//									}
//									if($("#answer_2_Uuid").combobox('getValue') == $("#answer_2_Uuid").combobox('getText')){
//										$.messager.alert('错误', '答题者乙不存在,请从下拉框中点击一行选中', 'error');
//										return false;
//									}
//									if($("#answer_3_Uuid").combobox('getValue') == $("#answer_3_Uuid").combobox('getText')){
//										$.messager.alert('错误', '答题者丙不存在,请从下拉框中点击一行选中', 'error');
//										return false;
//									}
									if($("#answer_1_Uuid").val() == $("#answer_2_Uuid").val()||$("#answer_1_Uuid").val() == $("#answer_3_Uuid").val()||$("#answer_2_Uuid").val() == $("#answer_3_Uuid").val()){
										$.messager.alert('错误', '答题者不能是同一个用户', 'error');
										return false;
									}
									return true;
								}
								return false;
							},
							success : function(result) {
								resultHandler(result, function process(data) {
									//调用节目
									managerQuestionbank(data.uuid,data.programPeriods,true);
									
									$('#addProgram_dd').dialog('destroy');
//										
									var sort = $('#program_grid').datagrid('options').sortName;
									var order = $('#program_grid').datagrid('options').sortOrder;
									var ps = $('#program_grid').datagrid('options').pageSize;
									var pn = $('#program_grid').datagrid('options').pageNumber;		
									loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
											+ '/manager/program/query',"program_grid");
								});
							}
				});
				}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#addProgram_dd').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#addProgram_dd').dialog('destroy');
		}
	});
	$(".dialog-button").css("border-top-color","#C6E2FF");
	$('#addProgram_dd').dialog('open');
}

function updateProgram(uuid){
	$.getJSON(getRootPath()
			+ '/manager/program/getProgramByUuid', {"uuid":uuid},
			function(result) {
				resultHandler(result, function process(data) {
					$('<div id="updateProgram_dd" title="编辑节目" style="width:700px;height:600px;padding:10px 20px">'
							+'<form id="updateProgramForm">'
							+'<input type="hidden" name="uuid" value="'+data.uuid+'">'
							+'<table align="center">'
							+'<tr height="10px"></tr>'
//							+'<tr><td colspan="2"><input id="programStatus" type="radio" name="status" value="10" checked="checked" />不开启&nbsp;<input id="programStatus" type="radio" name="status" value="20" />立即开启&nbsp;<input id="programStatus" type="radio" name="status" value="30" />已结束</td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目名称:</td><td><input type="text" id="programName" name="programName" maxlength="64" value="'+data.programName+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目期数:</td><td><input type="text" id="programPeriods" name="programPeriods" maxlength="64" value="'+data.programPeriods+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目时间:</td><td><input type="text" name="programTime" id="programTime" value="'+dateOf(data.programTime)+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一关奖金:</td><td><input type="text" name="pass_1_bonus" id="pass_1_bonus" maxlength="64" value="'+data.pass_1_bonus+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一关奖金分配人数:</td><td><input type="text" name="pass_1_bonusAllotPopulation" id="pass_1_bonusAllotPopulation" maxlength="11" value="'+data.pass_1_bonusAllotPopulation+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二关奖金:</td><td><input type="text" name="pass_2_bonus" id="pass_2_bonus" maxlength="64" value="'+data.pass_2_bonus+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二关奖金分配人数:</td><td><input type="text" name="pass_2_bonusAllotPopulation" id="pass_2_bonusAllotPopulation" maxlength="11" value="'+data.pass_2_bonusAllotPopulation+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三关奖金:</td><td><input type="text" name="pass_3_bonus" id="pass_3_bonus" maxlength="64" value="'+data.pass_3_bonus+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三关奖金分配人数:</td><td><input type="text" name="pass_3_bonusAllotPopulation" id="pass_3_bonusAllotPopulation" maxlength="11" value="'+data.pass_3_bonusAllotPopulation+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目题库:</td><td>已有<font color="#FF0000">'+data.questionbankCount+'</font>道题目&nbsp;<a href="#" onClick="managerQuestionbank(\''+data.uuid+'\',\''+data.programPeriods+'\');">节目题库管理</a></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>答题时间:</td><td><input type="text" style="width:131px;" name="answerTime" id="answerTime" maxlength="11" value='+data.answerTime+' />&nbsp;秒&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目主持人:</td><td><input type="text" name="compereName" id="compereName" maxlength="64" value="'+data.compereName+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一位答题者:</td><td><input type="text" readonly name="answer_1_Name" id="answer_1_Name" maxlength="64" value="'+data.answer_1_Name+'" />&nbsp;<font color="#FF0000">*</font>&nbsp;<button type="button" onClick="selectUser(\'一\')">选择...</button></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二位答题者:</td><td><input type="text" readonly name="answer_2_Name" id="answer_2_Name" maxlength="64" value="'+data.answer_2_Name+'" />&nbsp;<font color="#FF0000">*</font>&nbsp;<button type="button" onClick="selectUser(\'二\')">选择...</button></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三位答题者:</td><td><input type="text" readonly name="answer_3_Name" id="answer_3_Name" maxlength="64" value="'+data.answer_3_Name+'" />&nbsp;<font color="#FF0000">*</font>&nbsp;<button type="button" onClick="selectUser(\'三\')">选择...</button></td></tr>'
							+'<tr height="10px"><td><input type="hidden" id="answer_1_Uuid" name="answer_1_Uuid" value="'+data.answer_1_Uuid+'"/><input type="hidden" id="answer_2_Uuid" name="answer_2_Uuid" value="'+data.answer_2_Uuid+'"/><input type="hidden" id="answer_3_Uuid" name="answer_3_Uuid" value="'+data.answer_3_Uuid+'"/></td></tr>'
							+'</table>'
							+'</form>'
							+ '</div>').appendTo($("#dialogDiv"));
					$('#programName').validatebox({
						required : true
					});
					$('#programPeriods').validatebox({
						required : true
					});
					$('#programTime').datebox({
						required : true,
						editable : false,
						width : 153
					});
//					$('#bonus').validatebox({
//						required : true
//					});
//					$('#answerTime').validatebox({
//						required : true
//					});
//					$('#bonus').validatebox({
//					
//					});
					$('#pass_1_bonus').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
		//				$('#answerTime').validatebox({
		//					required : true
		//				});
					$('#pass_1_bonusAllotPopulation').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_2_bonus').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_2_bonusAllotPopulation').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_3_bonus').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_3_bonusAllotPopulation').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#answerTime').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#compereName').validatebox({
						required : true
					});
//					$('#answer_1_Uuid').combobox({
//						width : '153',
//						valueField : 'uuid',
//						textField : 'userName',
//						panelHeight : 'auto',
////						editable : false,
//						required : true,
//						filter:function(q,row){
//							var opts=$(this).combobox("options");
//							return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;
//						}
//					});
//					$('#answer_2_Uuid').combobox({
//						width : '153',
//						valueField : 'uuid',
//						textField : 'userName',
//						panelHeight : 'auto',
////						editable : false,
//						required : true,
//						filter:function(q,row){
//							var opts=$(this).combobox("options");
//							return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;
//						}
//					});
//					$('#answer_3_Uuid').combobox({
//						width : '153',
//						valueField : 'uuid',
//						textField : 'userName',
//						panelHeight : 'auto',
////						editable : false,
//						required : true,
//						filter:function(q,row){
//							var opts=$(this).combobox("options");
//							return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;
//						}
//					});
//					$('#answer_1_Uuid').combobox('panel').css({'max-height':'200px'});
//					$('#answer_2_Uuid').combobox('panel').css({'max-height':'200px'});
//					$('#answer_3_Uuid').combobox('panel').css({'max-height':'200px'});
//					$.getJSON(getRootPath()+'/manager/program/queryUser', {
//						"random" : Math.random()
//						}, function(result)
//						{
//							resultHandler(result, function process(data)
//							{
//								$("#answer_1_Uuid").combobox('loadData', data);
//								$("#answer_2_Uuid").combobox('loadData', data);
//								$("#answer_3_Uuid").combobox('loadData', data);
////								comboboxNullable("programUuid");
//							});
//						});
					$('#updateProgram_dd').dialog({
						modal : true,
						buttons : [ {
							text : '提交',
							iconCls : 'icon-ok',
							handler : function() {
//								$("#answer_1_Name").val($("#answer_1_Uuid").combobox('getText'));
//								$("#answer_2_Name").val($("#answer_2_Uuid").combobox('getText'));
//								$("#answer_3_Name").val($("#answer_3_Uuid").combobox('getText'));
								$('#updateProgramForm').ajaxSubmit({
											url : getRootPath() + '/manager/program/update?random=' + Math.random(),
											dataType : 'json',
											beforeSubmit:function(){
												if($('#updateProgramForm').form("validate")){
//													if($("#answer_1_Uuid").combobox('getValue') == $("#answer_1_Uuid").combobox('getText')){
//														$.messager.alert('错误', '答题者甲不存在,请从下拉框中点击一行选中', 'error');
//														return false;
//													}
//													if($("#answer_2_Uuid").combobox('getValue') == $("#answer_2_Uuid").combobox('getText')){
//														$.messager.alert('错误', '答题者乙不存在,请从下拉框中点击一行选中', 'error');
//														return false;
//													}
//													if($("#answer_3_Uuid").combobox('getValue') == $("#answer_3_Uuid").combobox('getText')){
//														$.messager.alert('错误', '答题者丙不存在,请从下拉框中点击一行选中', 'error');
//														return false;
//													}
													if($("#answer_1_Uuid").val() == $("#answer_2_Uuid").val()||$("#answer_1_Uuid").val() == $("#answer_3_Uuid").val()||$("#answer_2_Uuid").val() == $("#answer_3_Uuid").val()){
														$.messager.alert('错误', '答题者不能是同一个用户', 'error');
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
																$('#updateProgram_dd').dialog('destroy');
														
																var sort = $('#program_grid').datagrid('options').sortName;
																var order = $('#program_grid').datagrid('options').sortOrder;
																var ps = $('#program_grid').datagrid('options').pageSize;
																var pn = $('#program_grid').datagrid('options').pageNumber;		
																loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
																		+ '/manager/program/query',"program_grid");
															});
												});
											}
								});
								}
						}, {
							text : '关闭',
							iconCls : 'icon-cancel',
							handler : function() {
								$('#updateProgram_dd').dialog('destroy');
							}
						} ],
						onClose : function() {
							$('#updateProgram_dd').dialog('destroy');
						}
					});
					$(".dialog-button").css("border-top-color","#C6E2FF");
					$('#updateProgram_dd').dialog('open');
				});
			});
}

function taskExecute(uuid,status){
	var opt="";
	if(status=="20"){
		opt="开启";
	}else if(status=="30"){
		opt="结束";
	}else{
		$.messager.alert('错误', '非法操作！', 'error');
	}
	$.messager.confirm('提示', '确认'+opt+'节目吗？', function(ok)
	{
		if (ok)
		{	
			$.ajax({
				type : "post",
				dataType : 'json',
				url : getRootPath() + '/manager/program/status/update',
				data : {
					uuid : uuid,
					status : status,
					"random" : Math.random()
				},
				success : function(result) {
					resultHandler(result, function process(data) {
						$.messager.alert('提示', ""+opt+"节目成功", 'info', function()
							{
								var sort = $('#program_grid').datagrid('options').sortName;
								var order = $('#program_grid').datagrid('options').sortOrder;
								var ps = $('#program_grid').datagrid('options').pageSize;
								var pn = $('#program_grid').datagrid('options').pageNumber;		
								loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
										+ '/manager/program/query',"program_grid");
							});
					});
				}
			});
		}
	});
}

function managerQuestionbank(uuid,title,isAdding){
	$('<div id="dd2" title="['+title+']题目管理" style="width:1000px;height:600px;">'
			+'<table align="center" id="managerQuestionbank_table">'
			+'</table>'
			+ '</div>').appendTo($("#dialogDiv"));
	
	$('#managerQuestionbank_table').datagrid({
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
		height : $('#dd2').parent().height()-70,
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
				addQuestionbank2(uuid,title);
			}
		}],
		columns : [ [
		{
			field : 'uuid',
			title : 'uuid',
			hidden :true
		},
		{
			field : 'index',
			title : '排序',
			align : 'center',
			width : getWidth(0.05),
			formatter : formatIndex
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
			width : getWidth(0.1),
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
			formatter : managerQuestionbank_table_formatOpt2
		} ] ],
		onSortColumn : function(sort,order){
			var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
			var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;
			
			loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
		},
		onLoadSuccess : function (data) {
			if (!data.rows||data.rows=='') {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据</td></tr>');
			}
		}
	});

	var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
	var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
	var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
	var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;

	// 首次加载表格
	filters = {programUuidQuery : uuid};

	loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
	
	var pager = $('#managerQuestionbank_table').datagrid('getPager');
	if (pager) {
		$(pager).pagination(
				{
					pageList : [ 30, 40, 50 ],
					showRefresh : false,
					onSelectPage : function(pageNumber, pageSize) {
						var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
						var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
						loadData(filters, new PageOrder(pageSize, pageNumber,sort, order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
					}
				});
	}
	
	$("#resetQuestionbankBtn").click(function() {
		$("#questionbankForm").get(0).reset();
		$('#programUuidQuery').combobox('setValue', null);
		filters = {programUuidQuery : uuid};
	});

	// 查询
	$("#searchQuestionbank").click(
			function() {
				$('#managerQuestionbank_table').datagrid('options').pageSize = 30;
				$('#managerQuestionbank_table').datagrid('options').pageNumber = 1;
				$('#managerQuestionbank_table').datagrid('options').pageList = [ 30, 40, 50 ];

				var pager = $('#managerQuestionbank_table').datagrid('getPager');
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
											var sort = $('#managerQuestionbank_table').datagrid(
													'options').sortName;
											var order = $('#managerQuestionbank_table').datagrid(
													'options').sortOrder;
											loadData(filters, new PageOrder(
													pageSize, pageNumber, sort,
													order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
										}
									});
				}

				filters = {
					programUuidQuery : $("#programUuidQuery").combobox('getValue'),
					questionTitleQuery : $.trim($("#questionTitleQuery").val())
				};
				var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
				var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;

				loadData(filters, new PageOrder(30, 1, sort, order),rootPath+'/manager/program/questionbank/query',"program_grid","managerQuestionbank_table");
				
	});
	var buttunValue = '';
	var icon = '';
	if(isAdding){
		buttunValue = "完成";
		icon = "icon-ok";
	}else{
		buttunValue = "关闭";
		icon = "icon-cancel";
	}
	$('#dd2').dialog({
		modal : true,
		buttons : [{
			text : buttunValue,
			iconCls : icon,
			handler : function() {
				$('#dd2').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd2').dialog('destroy');
		}
	});
	$('#dd2').dialog('open');
}

function managerQuestionbank_table_formatOpt2(value, rowData, rowIndex) {
	var text = [];
	text.push("<a href='javascript:void(0);' onClick=updateQuestionbank2(\"");
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
	
	text.push(" <a href='javascript:void(0);' onClick=sortNoQuestionbank(\"");
	text.push(rowData.uuid);
	text.push("\",\"");
	text.push(rowData.questionTitle);
	text.push("\",\"");
	text.push(rowData.index);
	text.push("\") title='排序'>");
	text.push("排序");
	text.push("</a> ");
	return text.join('');
}

//记录之前选中答案
var beforAnswerId2 = '';

function addQuestionbank2(uuid,title){
	$('<div id="dd" title="添加['+title+']题目" style="width:700px;height:500px;padding:10px 20px">'
			+'<form id="addProgramForm">'
			+'<input type="hidden" id="programUuid" name="programUuid" value="'+uuid+'"/>'
			+'<table align="center">'
			+'<tr height="10px"></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>题目名称:</td><td><input type="text" id="questionTitle" name="questionTitle" maxlength="64"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>难度系数:</td><td><input type="radio" name="difficultyType" value="10" checked="checked" />非常简单<input type="radio" name="difficultyType" value="20" />简单<input type="radio" name="difficultyType" value="30" />一般<input type="radio" name="difficultyType" value="40" />难<input type="radio" name="difficultyType" value="50" />非常难</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td valign="top">文字题面:</td><td><textarea rows="4" id="questionContent" name="questionContent" maxlength="256" style="width:200px;resize:none"></textarea>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
//			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" name="answer_A" id="answer_A" maxlength="10"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_A" onClick="clickFuction2(\'answer_td_A\',\'answer_A\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_B">B:<input type="text" name="answer_B" id="answer_B" maxlength="10"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_B" onClick="clickFuction2(\'answer_td_B\',\'answer_B\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_C">C:<input type="text" name="answer_C" id="answer_C" maxlength="10"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_C" onClick="clickFuction2(\'answer_td_C\',\'answer_C\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_D">D:<input type="text" name="answer_D" id="answer_D" maxlength="10"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_D" onClick="clickFuction2(\'answer_td_D\',\'answer_D\')"></td></tr>'
			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" name="answer_A" id="answer_A" maxlength="10"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_B">B:<input type="text" name="answer_B" id="answer_B" maxlength="10"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_C">C:<input type="text" name="answer_C" id="answer_C" maxlength="10"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_D">D:<input type="text" name="answer_D" id="answer_D" maxlength="10"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td></td></tr>'
			+'</table>'
			+'</form>'
			+ '</div>').appendTo($("#dialogDiv"));
	
//	$('#programUuid').combobox({
//		width : '205',
//		valueField : 'uuid',
//		textField : 'programPeriods',
//		panelHeight : 'auto',
//		editable : false,
//		required : true
//	});
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
	
//	$('#programUuid').combobox('panel').css({'max-height':'200px'});
//	
//	$.getJSON(getRootPath()+'/manager/program/questionbank/loadAllProgram', {
//		"random" : Math.random()
//		}, function(result)
//		{
//			resultHandler(result, function process(data)
//			{
//				$("#programUuid").combobox('loadData', data);
////				comboboxNullable("programUuid");
//			});
//		});
	
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				$('#addProgramForm').ajaxSubmit({
							url : getRootPath() + '/manager/program/questionbank/add?random=' + Math.random(),
							dataType : 'json',
							beforeSubmit:function(){
								if($('#addProgramForm').form("validate")){
									//是否需要选择正答案
//									if(!$('input:radio[name="correctAnswer"]').is(":checked")){
//										$.messager.alert('错误', '请选择正确答案！', 'error');
//										return false;
//									}
									return true;
								}
								return false;
							},
							success : function(result) {
								resultHandler(result, function process(data) {
									$.messager.alert('提示', "添加成功", 'info', function()
											{
												$('#dd').dialog('destroy');
										
												var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
												var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
												var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
												var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;		
												loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
														+ '/manager/program/questionbank/query',"managerQuestionbank_table");
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

function updateQuestionbank2(uuid,programUuid,questionTitle,difficultyType,questionContent,answer_A,answer_B,answer_C,answer_D,correctAnswer){
	$('<div id="dd" title="编辑题目" style="width:700px;height:550px;padding:10px 20px">'
			+'<form id="updateQuestionbankForm">'
			+'<input type="hidden" name="uuid" value="'+uuid+'">'
			+'<input type="hidden" id="programUuid" name="programUuid" value="'+programUuid+'"/>'
			+'<table align="center">'
			+'<tr height="10px"></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>题目名称:</td><td><input type="text" id="questionTitle" name="questionTitle" maxlength="64" value="'+questionTitle+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>难度系数:</td><td><input type="radio" name="difficultyType" id="difficultyType1" value="10" />非常简单<input type="radio" name="difficultyType" id="difficultyType2" value="20" />简单<input type="radio" name="difficultyType" id="difficultyType3" value="30" />一般<input type="radio" name="difficultyType" id="difficultyType4" value="40" />难<input type="radio" name="difficultyType" id="difficultyType5" value="50" />非常难</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td valign="top">文字题面:</td><td><textarea rows="4" id="questionContent" name="questionContent" maxlength="256" style="width:200px;resize:none">'+questionContent+'</textarea>&nbsp;<font color="#FF0000">*</font></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" name="answer_A" value="'+answer_A+'" maxlength="10" id="answer_A"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_A" onClick="clickFuction2(\'answer_td_A\',\'answer_A\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_B">B:<input type="text" name="answer_B" value="'+answer_B+'" maxlength="10" id="answer_B"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_B" onClick="clickFuction2(\'answer_td_B\',\'answer_B\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_C">C:<input type="text" name="answer_C" value="'+answer_C+'" maxlength="10" id="answer_C"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_C" onClick="clickFuction2(\'answer_td_C\',\'answer_C\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_D">D:<input type="text" name="answer_D" value="'+answer_D+'" maxlength="10" id="answer_D"/>&nbsp;<input type="radio" name="correctAnswer" value="answer_D" onClick="clickFuction2(\'answer_td_D\',\'answer_D\')"></td></tr>'
//			+'<tr height="10px"></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" name="answer_A" value="'+answer_A+'" maxlength="10" id="answer_A"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_B">B:<input type="text" name="answer_B" value="'+answer_B+'" maxlength="10" id="answer_B"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_C">C:<input type="text" name="answer_C" value="'+answer_C+'" maxlength="10" id="answer_C"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_D">D:<input type="text" name="answer_D" value="'+answer_D+'" maxlength="10" id="answer_D"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td></td></tr>'
			+'</table>'
			+'</form>'
			+ '</div>').appendTo($("#dialogDiv"));
	
//	$('#programUuid').combobox({
//		width : '205',
//		valueField : 'uuid',
//		textField : 'programPeriods',
//		panelHeight : 'auto',
//		editable : false,
//		required : true
//	});
	$('#questionTitle').validatebox({
		required : true
	});
	$("input[type=radio][value='"+difficultyType+"']").attr("checked",'checked');
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
	
//	$('#programUuid').combobox('panel').css({'max-height':'200px'});
//	
//	$.getJSON(getRootPath()+'/manager/program/questionbank/loadAllProgram', {
//		"random" : Math.random()
//		}, function(result)
//		{
//			resultHandler(result, function process(data)
//			{
//				$("#programUuid").combobox('loadData', data);
////				comboboxNullable("programUuid");
//			});
//		});
	
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				$('#updateQuestionbankForm').ajaxSubmit({
					url : getRootPath() + '/manager/program/questionbank/update?random=' + Math.random(),
					dataType : 'json',
					beforeSubmit:function(){
						if($('#updateQuestionbankForm').form("validate")){
							//是否需要选择正答案
//							if(!$('input:radio[name="correctAnswer"]').is(":checked")){
//								$.messager.alert('错误', '请选择正确答案！', 'error');
//								return false;
//							}
							return true;
						}
						return false;
					},
					success : function(result) {
						resultHandler(result, function process(data) {
							$.messager.alert('提示', "编辑成功", 'info', function()
									{
										$('#dd').dialog('destroy');
								
										var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
										var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
										var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
										var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;		
										loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
												+ '/manager/program/questionbank/query',"managerQuestionbank_table");
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

function clickFuction2(td_id){
	if(beforAnswerId2!=''){
		$("#"+beforAnswerId2).removeAttr("style");
		$('#'+beforAnswerId2+' :text').removeAttr("style");
	}
	$("#"+td_id).css("color","red");
	$('#'+td_id+' :text').css("color","red");
	
	beforAnswerId2 = td_id;
}

function getWidth(percent) {
	return $('#program_grid').parent().width() * percent;
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
function loadData(queryParams, pageOrder,url,grid) {
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
						$('#'+grid).datagrid('loadData', { total: 0, rows: [] });
					}else{
						$('#'+grid).datagrid('loadData', data);
					}
				});
			});
}

function sortNoQuestionbank(uuid, name, index) {
	var sortDialogDivContent = '<div id="sortDialogDiv">'+
	'<table width="100%" height="100%">'+
	'<tr>'+
		'<td width="100%" height="100%" align="center"><table><tr><td align="left"><form id="sortForm">'+
			'<p>将题目"'+name+'"';
	if("null" != index){
		sortDialogDivContent += '(排序号'+index+')</p>';
	}
	sortDialogDivContent +='<p>排列到第&nbsp;<input type="text" name="toPosition" id="toPosition"/>&nbsp;位</p>';
	sortDialogDivContent +='<p style="height: 20px" id="mess"></p>';
	sortDialogDivContent += '</form></td></tr></table></td></tr></table></div>';
	$(sortDialogDivContent).appendTo($("body"));
	
	// 校验
	$('#toPosition').validatebox({
		required : true,
		validType:['int']
	});
	var validNumber ="";
	$.getJSON(getRootPath() + '/manager/program/questionbank/stick/maxAvailableSortNo', {
		"ct" : new Date().getTime(),
		"fromUuid" : uuid
	}, function(result) {
		resultHandler(result, function process(data) {
			$("#mess").append('注：当前允许的最大排序位置为'+data+'');
			validNumber=data;
		});
	});
	
	$("#sortDialogDiv").dialog({
		title : name,
		width : 400,
		height : 320,
		modal : true,
		buttons : [ {
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				$("#mess").append('注：当前允许的最大排序位置为'+validNumber+'');
				if(!$('#sortForm').form('validate')){
					$("#mess").empty();
					$("#mess").append('注：当前允许的最大排序位置为'+validNumber+'');
					return;
				}
				if($("#toPosition").val()>validNumber){
					$("#mess").empty();
					$("#mess").append('<font color="red">注：当前允许的最大排序位置为'+validNumber+'</font>');
					return;
				}
				var toPosition = $("#toPosition").val();
				move(uuid,toPosition);
				// 关闭窗口
				$('#sortDialogDiv').dialog('destroy');
			}
		},{
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#sortDialogDiv').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#sortDialogDiv').dialog('destroy');
		}
	});
}

function move(uuid,toPosition){
	$.getJSON(getRootPath() + '/manager/program/questionbank/sort', {
		"ct" : new Date().getTime(),
		"fromUuid" : uuid,
		"toPosition" : toPosition
	}, function(result) {
		resultHandler(result, function process(data) {
			// 清除选择
//			$("#recom_column_grid").datagrid('clearSelections');
//			$("#recom_column_grid").datagrid('clearChecked');
			var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
			var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
			var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
			var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;
			loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
					+ '/manager/program/questionbank/query',"managerQuestionbank_table");
		});
	});
}

//排序号格式化
function formatIndex(value, rowData, rowIndex) {
	if(rowData.index){
		return rowData.index;
	}
	return '';
}
//-------2014-02-11------------
function selectUser(param){
	$('<div id="dd2" title="选择第['+param+']位答题者" style="width:1000px;height:600px;">'
			+'<fieldset><table align=center><tr><td><input type="text" name="userName" id="userName" />&nbsp;<a id="queryUser"></a>&nbsp;<a id="resetUser"></a></td></tr></table></fieldset>'
			+'<table align="center" id="user_table">'
			+'</table>'
			+ '</div>').appendTo($("#dialogDiv"));
	
	$('#queryUser').linkbutton({text : "查询",iconCls : "icon-search"});

	$('#resetUser').linkbutton({text : "重置",iconCls : "icon-reset"});
	
	$('#user_table').datagrid({
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
		height : $('#dd2').parent().height()-120,
		sortName : 'sortNo',
		sortOrder : 'desc',
		pageList : [ 30, 40, 50 ],
		remoteSort : true, // 是否以服务器数据排序
		pagination : true, // 是否显示分页工具栏
		loadMsg : '加载中，请稍后',
		columns : [ [
		{
			field : 'ck',
			align : 'center',
			checkbox : true
		},
		{
			field : 'uuid',
			title : 'uuid',
			hidden :true
		},
		{
			field : 'userName',
			title : '用户名',
			align : 'center',
			width : getWidth(0.05)
		},{
			field : 'status',
			title : '状态',
			align : 'center',
			width : getWidth(0.15)
		},{
			field : 'registTime',
			title : '注册时间',
			align : 'center',
			width : getWidth(0.1),
			formatter : dateOf2
		} ] ],
		onLoadSuccess : function (data) {
			if (!data.rows||data.rows=='') {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据</td></tr>');
			}
		}
	});

	var sort = $('#user_table').datagrid('options').sortName;
	var order = $('#user_table').datagrid('options').sortOrder;
	var ps = $('#user_table').datagrid('options').pageSize;
	var pn = $('#user_table').datagrid('options').pageNumber;

	// 首次加载表格
	filters = {userName : ""};

	loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/queryUser',"user_table");
	
	var pager = $('#user_table').datagrid('getPager');
	if (pager) {
		$(pager).pagination(
				{
					pageList : [ 30, 40, 50 ],
					showRefresh : false,
					onSelectPage : function(pageNumber, pageSize) {
						var sort = $('#user_table').datagrid('options').sortName;
						var order = $('#user_table').datagrid('options').sortOrder;
						loadData(filters, new PageOrder(pageSize, pageNumber,sort, order),rootPath+'/manager/program/queryUser',"user_table");
					}
				});
	}
	
	$("#resetUser").click(function() {
		$("#userName").val("");
		filters = {userName : ""};
	});

	// 查询
	$("#queryUser").click(
			function() {
				$('#user_table').datagrid('options').pageSize = 30;
				$('#user_table').datagrid('options').pageNumber = 1;
				$('#user_table').datagrid('options').pageList = [ 30, 40, 50 ];

				var pager = $('#user_table').datagrid('getPager');
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
											var sort = $('#user_table').datagrid(
													'options').sortName;
											var order = $('#user_table').datagrid(
													'options').sortOrder;
											loadData(filters, new PageOrder(
													pageSize, pageNumber, sort,
													order),rootPath+'/manager/program/queryUser',"user_table");
										}
									});
				}

				filters = {
					userName : $.trim($("#userName").val())
				};
				var sort = $('#user_table').datagrid('options').sortName;
				var order = $('#user_table').datagrid('options').sortOrder;

				loadData(filters, new PageOrder(30, 1, sort, order),rootPath+'/manager/program/queryUser',"user_table");
				
	});
	$('#dd2').dialog({
		modal : true,
		buttons : [{
			text : "确定",
			iconCls : "icon-ok",
			handler : function() {
				var rows = $("#user_table").datagrid('getSelected');
				if (rows==null)
				{
					$.messager.alert('未选择数据行', '您还没有选择任何一行！', 'error');
					return;
				}else{
					if(param=="一"){
						$("#answer_1_Uuid").val(rows.uuid);
						$("#answer_1_Name").val(rows.userName);
					}else if(param=="二"){
						$("#answer_2_Uuid").val(rows.uuid);
						$("#answer_2_Name").val(rows.userName);
					}else{
						$("#answer_3_Uuid").val(rows.uuid);
						$("#answer_3_Name").val(rows.userName);
					}
					$('#dd2').dialog('destroy');
				}
			}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dd2').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd2').dialog('destroy');
		}
	});
	$('#dd2').dialog('open');
}
//查看
function updateProgram3(uuid){
	$.getJSON(getRootPath()
			+ '/manager/program/getProgramByUuid', {"uuid":uuid},
			function(result) {
				resultHandler(result, function process(data) {
					$('<div id="updateProgram_dd" title="查看节目" style="width:700px;height:600px;padding:10px 20px">'
							+'<form id="updateProgramForm">'
							+'<input type="hidden" name="uuid" value="'+data.uuid+'">'
							+'<table align="center">'
							+'<tr height="10px"></tr>'
//							+'<tr><td colspan="2"><input id="programStatus" type="radio" name="status" value="10" checked="checked" />不开启&nbsp;<input id="programStatus" type="radio" name="status" value="20" />立即开启&nbsp;<input id="programStatus" type="radio" name="status" value="30" />已结束</td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目名称:</td><td><input type="text" disabled id="programName" name="programName" maxlength="64" value="'+data.programName+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目期数:</td><td><input type="text" disabled id="programPeriods" name="programPeriods" maxlength="64" value="'+data.programPeriods+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目时间:</td><td><input type="text" disabled name="programTime" id="programTime" value="'+dateOf(data.programTime)+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一关奖金:</td><td><input type="text" disabled name="pass_1_bonus" id="pass_1_bonus" value="'+data.pass_1_bonus+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一关奖金分配人数:</td><td><input type="text" disabled name="pass_1_bonusAllotPopulation" id="pass_1_bonusAllotPopulation" value="'+data.pass_1_bonusAllotPopulation+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二关奖金:</td><td><input type="text" disabled name="pass_2_bonus" id="pass_2_bonus" value="'+data.pass_2_bonus+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二关奖金分配人数:</td><td><input type="text" disabled name="pass_2_bonusAllotPopulation" id="pass_2_bonusAllotPopulation" value="'+data.pass_2_bonusAllotPopulation+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三关奖金:</td><td><input type="text" disabled name="pass_3_bonus" id="pass_3_bonus" value="'+data.pass_3_bonus+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三关奖金分配人数:</td><td><input type="text" disabled name="pass_3_bonusAllotPopulation" id="pass_3_bonusAllotPopulation" value="'+data.pass_3_bonusAllotPopulation+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目题库:</td><td>已有<font color="#FF0000">'+data.questionbankCount+'</font>道题目&nbsp;<a href="#" onClick="managerQuestionbank3(\''+data.uuid+'\',\''+data.programPeriods+'\');">节目题库管理</a></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>答题时间:</td><td><input type="text" disabled style="width:131px;" name="answerTime" id="answerTime" maxlength="11" value='+data.answerTime+' />&nbsp;秒&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>节目主持人:</td><td><input type="text" disabled name="compereName" id="compereName" maxlength="64" value="'+data.compereName+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一位答题者:</td><td><input type="text" disabled name="answer_1_Name" id="answer_1_Name" maxlength="64" value="'+data.answer_1_Name+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二位答题者:</td><td><input type="text" disabled name="answer_2_Name" id="answer_2_Name" maxlength="64" value="'+data.answer_2_Name+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三位答题者:</td><td><input type="text" disabled name="answer_3_Name" id="answer_3_Name" maxlength="64" value="'+data.answer_3_Name+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"><td><input type="hidden" id="answer_1_Uuid" name="answer_1_Uuid"/><input type="hidden" id="answer_2_Uuid" name="answer_2_Uuid"/><input type="hidden" id="answer_3_Uuid" name="answer_3_Uuid"/></td></tr>'
							+'<tr height="10px"></tr>'
							+'</table>'
							+'</form>'
							+ '</div>').appendTo($("#dialogDiv"));
					$('#programName').validatebox({
						required : true
					});
					$('#programPeriods').validatebox({
						required : true
					});
					$('#programTime').datebox({
						required : true,
						editable : false,
						width : 153
					});
					$('#answerTime').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#compereName').validatebox({
						required : true
					});
					$('#updateProgram_dd').dialog({
						modal : true,
						buttons : [{
							text : '关闭',
							iconCls : 'icon-cancel',
							handler : function() {
								$('#updateProgram_dd').dialog('destroy');
							}
						} ],
						onClose : function() {
							$('#updateProgram_dd').dialog('destroy');
						}
					});
					$(".dialog-button").css("border-top-color","#C6E2FF");
					$('#updateProgram_dd').dialog('open');
				});
			});
}

//只能查看题目列表
function managerQuestionbank3(uuid,title,isAdding){
	$('<div id="dd2" title="['+title+']题目管理" style="width:1000px;height:600px;">'
			+'<table align="center" id="managerQuestionbank_table">'
			+'</table>'
			+ '</div>').appendTo($("#dialogDiv"));
	
	$('#managerQuestionbank_table').datagrid({
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
		height : $('#dd2').parent().height()-70,
		sortName : 'sortNo',
		sortOrder : 'desc',
		pageList : [ 30, 40, 50 ],
		remoteSort : true, // 是否以服务器数据排序
		pagination : true, // 是否显示分页工具栏
		loadMsg : '加载中，请稍后',
/*		toolbar : [{
			id : 'addQuestionbank',
			text : '新增题目',
			iconCls : 'icon-add',
			handler : function() {
				addQuestionbank2(uuid,title);
			}
		}],*/
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
			width : getWidth(0.1),
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
			formatter : managerQuestionbank_table_formatOpt3
		} ] ],
		onSortColumn : function(sort,order){
			var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
			var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;
			
			loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
		},
		onLoadSuccess : function (data) {
			if (!data.rows||data.rows=='') {
				var body = $(this).data().datagrid.dc.body2;
				body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据</td></tr>');
			}
		}
	});

	var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
	var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
	var ps = $('#managerQuestionbank_table').datagrid('options').pageSize;
	var pn = $('#managerQuestionbank_table').datagrid('options').pageNumber;

	// 首次加载表格
	filters = {programUuidQuery : uuid};

	loadData(filters, new PageOrder(ps, pn, sort, order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
	
	var pager = $('#managerQuestionbank_table').datagrid('getPager');
	if (pager) {
		$(pager).pagination(
				{
					pageList : [ 30, 40, 50 ],
					showRefresh : false,
					onSelectPage : function(pageNumber, pageSize) {
						var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
						var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;
						loadData(filters, new PageOrder(pageSize, pageNumber,sort, order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
					}
				});
	}
	
	$("#resetQuestionbankBtn").click(function() {
		$("#questionbankForm").get(0).reset();
		$('#programUuidQuery').combobox('setValue', null);
		filters = {programUuidQuery : uuid};
	});

	// 查询
	$("#searchQuestionbank").click(
			function() {
				$('#managerQuestionbank_table').datagrid('options').pageSize = 30;
				$('#managerQuestionbank_table').datagrid('options').pageNumber = 1;
				$('#managerQuestionbank_table').datagrid('options').pageList = [ 30, 40, 50 ];

				var pager = $('#managerQuestionbank_table').datagrid('getPager');
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
											var sort = $('#managerQuestionbank_table').datagrid(
													'options').sortName;
											var order = $('#managerQuestionbank_table').datagrid(
													'options').sortOrder;
											loadData(filters, new PageOrder(
													pageSize, pageNumber, sort,
													order),rootPath+'/manager/program/questionbank/query',"managerQuestionbank_table");
										}
									});
				}

				filters = {
					programUuidQuery : $("#programUuidQuery").combobox('getValue'),
					questionTitleQuery : $.trim($("#questionTitleQuery").val())
				};
				var sort = $('#managerQuestionbank_table').datagrid('options').sortName;
				var order = $('#managerQuestionbank_table').datagrid('options').sortOrder;

				loadData(filters, new PageOrder(30, 1, sort, order),rootPath+'/manager/program/questionbank/query',"program_grid","managerQuestionbank_table");
				
	});
	var buttunValue = '';
	var icon = '';
	if(isAdding){
		buttunValue = "完成";
		icon = "icon-ok";
	}else{
		buttunValue = "关闭";
		icon = "icon-cancel";
	}
	$('#dd2').dialog({
		modal : true,
		buttons : [{
			text : buttunValue,
			iconCls : icon,
			handler : function() {
				$('#dd2').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd2').dialog('destroy');
		}
	});
	$('#dd2').dialog('open');
}

function managerQuestionbank_table_formatOpt3(value, rowData, rowIndex) {
	var text = [];
	text.push("<a href='javascript:void(0);' onClick=updateQuestionbank3(\"");
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
	text.push("\") title='查看'>");
	text.push("查看");
	text.push("</a> ");
	return text.join('');
}

function updateQuestionbank3(uuid,programUuid,questionTitle,difficultyType,questionContent,answer_A,answer_B,answer_C,answer_D,correctAnswer){
	$('<div id="dd" title="查看题目" style="width:700px;height:550px;padding:10px 20px">'
			+'<form id="updateQuestionbankForm">'
			+'<input type="hidden" name="uuid" value="'+uuid+'">'
			+'<input type="hidden" id="programUuid" name="programUuid" value="'+programUuid+'"/>'
			+'<table align="center">'
			+'<tr height="10px"></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>题目名称:</td><td><input type="text" disabled id="questionTitle" name="questionTitle" maxlength="64" value="'+questionTitle+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>难度系数:</td><td><input type="radio" disabled name="difficultyType" id="difficultyType1" value="10" />非常简单<input type="radio" disabled name="difficultyType" id="difficultyType2" value="20" />简单<input type="radio" disabled name="difficultyType" id="difficultyType3" value="30" />一般<input type="radio" disabled name="difficultyType" id="difficultyType4" value="40" />难<input type="radio" disabled name="difficultyType" id="difficultyType5" value="50" />非常难</td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td valign="top">文字题面:</td><td><textarea rows="4" id="questionContent" disabled name="questionContent" maxlength="256" style="width:200px;resize:none">'+questionContent+'</textarea>&nbsp;<font color="#FF0000">*</font></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" disabled name="answer_A" value="'+answer_A+'" maxlength="10" id="answer_A"/>&nbsp;<input type="radio" disabled name="correctAnswer" value="answer_A" onClick="clickFuction2(\'answer_td_A\',\'answer_A\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_B">B:<input type="text" disabled name="answer_B" value="'+answer_B+'" maxlength="10" id="answer_B"/>&nbsp;<input type="radio" disabled name="correctAnswer" value="answer_B" onClick="clickFuction2(\'answer_td_B\',\'answer_B\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_C">C:<input type="text" disabled name="answer_C" value="'+answer_C+'" maxlength="10" id="answer_C"/>&nbsp;<input type="radio" disabled name="correctAnswer" value="answer_C" onClick="clickFuction2(\'answer_td_C\',\'answer_C\')"></td></tr>'
//			+'<tr height="10px"></tr>'
//			+'<tr><td></td><td id="answer_td_D">D:<input type="text" disabled name="answer_D" value="'+answer_D+'" maxlength="10" id="answer_D"/>&nbsp;<input type="radio" disabled name="correctAnswer" value="answer_D" onClick="clickFuction2(\'answer_td_D\',\'answer_D\')"></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td>答题选项:</td><td id="answer_td_A">A:<input type="text" disabled name="answer_A" value="'+answer_A+'" maxlength="10" id="answer_A"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_B">B:<input type="text" disabled name="answer_B" value="'+answer_B+'" maxlength="10" id="answer_B"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_C">C:<input type="text" disabled name="answer_C" value="'+answer_C+'" maxlength="10" id="answer_C"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td id="answer_td_D">D:<input type="text" disabled name="answer_D" value="'+answer_D+'" maxlength="10" id="answer_D"/></td></tr>'
			+'<tr height="10px"></tr>'
			+'<tr height="10px"></tr>'
			+'<tr><td></td><td></td></tr>'
			+'</table>'
			+'</form>'
			+ '</div>').appendTo($("#dialogDiv"));
	
//	$('#programUuid').combobox({
//		width : '205',
//		valueField : 'uuid',
//		textField : 'programPeriods',
//		panelHeight : 'auto',
//		editable : false,
//		required : true
//	});
	
//	$("form[id='updateQuestionbankForm'] :text").attr("disabled","disabled");
	
	$('#questionTitle').validatebox({
		required : true
	});
	$("input[type=radio][value='"+difficultyType+"']").attr("checked",'checked');
//	$("input[type=radio][value='"+correctAnswer+"']").trigger("click");
//	$("input[type=radio][value='"+correctAnswer+"']").attr("checked",'checked');
	
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
	
//	$('#programUuid').combobox('panel').css({'max-height':'200px'});
//	
//	$.getJSON(getRootPath()+'/manager/program/questionbank/loadAllProgram', {
//		"random" : Math.random()
//		}, function(result)
//		{
//			resultHandler(result, function process(data)
//			{
//				$("#programUuid").combobox('loadData', data);
////				comboboxNullable("programUuid");
//			});
//		});
	
	$('#dd').dialog({
		modal : true,
		buttons : [{
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

//查看
function updateProgramAllotPopulation(uuid,programPeriods){
	$.getJSON(getRootPath()
			+ '/manager/program/getProgramByUuid', {"uuid":uuid},
			function(result) {
				resultHandler(result, function process(data) {
					$('<div id="updateProgram_dd" title="'+programPeriods+'" style="width:500px;height:330px;padding:10px 20px">'
							+'<form id="updateProgramAllotPopulationForm">'
							+'<input type="hidden" name="uuid" value="'+data.uuid+'">'
							+'<table align="center">'
							+'<tr><td>第一关奖金:</td><td><input type="text" name="pass_1_bonus" id="pass_1_bonus" value="'+data.pass_1_bonus+'"/>&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第一关奖金分配人数:</td><td><input type="text" name="pass_1_bonusAllotPopulation" id="pass_1_bonusAllotPopulation" value="'+data.pass_1_bonusAllotPopulation+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二关奖金:</td><td><input type="text" name="pass_2_bonus" id="pass_2_bonus" value="'+data.pass_2_bonus+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第二关奖金分配人数:</td><td><input type="text" name="pass_2_bonusAllotPopulation" id="pass_2_bonusAllotPopulation" value="'+data.pass_2_bonusAllotPopulation+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三关奖金:</td><td><input type="text" name="pass_3_bonus" id="pass_3_bonus" value="'+data.pass_3_bonus+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'<tr><td>第三关奖金分配人数:</td><td><input type="text" name="pass_3_bonusAllotPopulation" id="pass_3_bonusAllotPopulation" value="'+data.pass_3_bonusAllotPopulation+'" />&nbsp;<font color="#FF0000">*</font></td></tr>'
							+'<tr height="10px"></tr>'
							+'</table>'
							+'</form>'
							+ '</div>').appendTo($("#dialogDiv"));
					$('#pass_1_bonus').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_1_bonusAllotPopulation').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_2_bonus').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_2_bonusAllotPopulation').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_3_bonus').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#pass_3_bonusAllotPopulation').numberbox({   
					    min:1,
					    max:10000,
					    precision:0,
					    required : true
					});
					$('#updateProgram_dd').dialog({
						modal : true,
						buttons : [{
							text : '提交',
							iconCls : 'icon-ok',
							handler : function() {
								$('#updateProgramAllotPopulationForm').ajaxSubmit({
											url : getRootPath() + '/manager/program/update?random=' + Math.random(),
											dataType : 'json',
											beforeSubmit:function(){
												if($('#updateProgramAllotPopulationForm').form("validate")){
													return true;
												}
												return false;
											},
											success : function(result) {
												resultHandler(result, function process(data) {
													$.messager.alert('提示', "编辑成功", 'info', function()
															{
																$('#updateProgram_dd').dialog('destroy');
														
																var sort = $('#program_grid').datagrid('options').sortName;
																var order = $('#program_grid').datagrid('options').sortOrder;
																var ps = $('#program_grid').datagrid('options').pageSize;
																var pn = $('#program_grid').datagrid('options').pageNumber;		
																loadData(filters, new PageOrder(ps, pn, sort, order), getRootPath()
																		+ '/manager/program/query',"program_grid");
															});
												});
											}
								});
								}
						}, {
							text : '关闭',
							iconCls : 'icon-cancel',
							handler : function() {
								$('#updateProgram_dd').dialog('destroy');
							}
						} ],
						onClose : function() {
							$('#updateProgram_dd').dialog('destroy');
						}
					});
					$(".dialog-button").css("border-top-color","#C6E2FF");
					$('#updateProgram_dd').dialog('open');
				});
			});
}