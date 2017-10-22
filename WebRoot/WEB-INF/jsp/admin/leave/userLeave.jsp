<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-用户假期管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<link href="${path}/css/common/pager.css" type="text/css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="${path}/js/jqeasyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqeasyui/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
		$(function(){
			
			/** 为下面所有的tr绑定onmouseover与onmouseout事件 (hover)*/
			$("tr[id^='tr_']").hover(
				function(){ // onmouseover
					$(this).css({"background-color" : "#ffffbf", "cursor" : "pointer"});
				}, 
				function(){ // onmouseout
					/** 获取当前行中checkbox */
					var box = $(this).find("input[id^='box_']");
					if (!box.attr("checked")){
						$(this).css("background-color", "#ffffff");
					}
				}
			);
			
			/** 异步加假期类型 */
			$.ajax({
				url : "${path}/admin/leave/loadLeaveTypeAjax.jspx",
				type : "post",
				dataType : "json",
				async : true,
				success : function(data){
					// data: [{},{}] List<Map>
					$.each(data,function(){
						// this: {code : '', name :''}
						$("<option/>").val(this.code)
									  .text(this.name)
									  .attr("selected", this.code == "${leaveItem.leaveType.code}")
									  .appendTo("#leaveTypeSelect");
					});
				},
				error : function(){
					alert("数据加载失败！");
				}
			});		
			
			
			/** 为填写请假单按钮绑定点击事件 */
			$("#addLeaveItem").click(function(){
				/** 显示填写请假单的对话窗口 */
				$("#divDialog").dialog({
					title : "填写请假单", // 标题
					width : 410,  // 宽度
					height : 315, // 高度
					collapsible : true, // 可伸缩
					minimizable : true, // 最小化
					maximizable : true,  // 最大化
					modal : true, // 模态窗口
					onClose : function(){ // 监听是否关闭当前窗口
						window.location.href = "${path}/admin/leave/selectUserLeave.jspx?pageModel.pageIndex=${pageModel.pageIndex}&leaveItem.leaveType.code=${leaveItem.leaveType.code}";
					}
				});
				$("#iframe").prop("src", "${path}/admin/leave/showAddLeaveItem.jspx").show();
			});
			
		});
		
		/** 显示流程图 */
		var showProcessDiagramFn = function(piId){
			/** 显示流程图的对话窗口 */
			$("#divDialog").dialog({
				title : "请假-流程图", // 标题
				width : 580,  // 宽度
				height : 480, // 高度
				collapsible : true, // 可伸缩
				minimizable : true, // 最小化
				maximizable : true,  // 最大化
				modal : true // 模态窗口
			});
			$("#iframe").prop("src", "${path}/admin/workflow/showProcessDiagram.jspx?processInstanceId=" + piId).show();
		};
	</script>
</head>
<body>
	<form action="${path}/admin/leave/selectUserLeave.jspx" method="post">
		<table>
			<tr>
				<td><input type="button" value="添加" id="addLeaveItem"/></td>
				<td>假期类型：<select id="leaveTypeSelect" name="leaveItem.leaveType.code">
								<option>==请选择==</option></select></td>
				<td><input type="submit" value="查询"/>
					&nbsp;<font color="red">${tip}</font>
				</td>
			</tr>
		</table>
	</form>


	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th>请假类型</th>
			<th>请假人</th>
			<th>请假原因</th>
			<th>开始日期</th>
			<th>结束日期</th>
			<th>请假小时</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="leaveItems" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><s:property value="leaveType.name"/></td>
					<td><s:property value="creater.name"/></td>
					<td><s:property value="leaveCase"/></td>
					<td><s:date name="beginDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:date name="endDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="leaveHour"/></td>
					<td><s:if test="status == 0"><font color="blue">新建</font></s:if> 
						<s:elseif test="status == 1">
							<font>审核通过</font>
						</s:elseif>
						<s:else>
							<font color="red">不通过</font>
						</s:else>
					</td>
					<td>
						<s:if test="status == 0">
							<a href="javascript:;" onclick="showProcessDiagramFn('${procInstanceId}');">流程图</a>
						</s:if>
						<s:else>
							<a href="${path}/admin/workflow/selectHistoryTask.jspx?processInstanceId=${procInstanceId}">历史任务</a>
						</s:else>
							&nbsp;<a href="${path}/admin/leave/selectAuditResult.jspx?leaveAudit.leaveItem.id=${id}">审批意见</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<it:pager pageIndex="${pageModel.pageIndex}" 
			  pageSize="${pageModel.pageSize}" 
			  recordCount="${pageModel.recordCount}" 
			  submitUrl="${path}/admin/leave/selectUserLeave.jspx?pageModel.pageIndex={0}&leaveItem.leaveType.code=${leaveItem.leaveType.code}"/>

	
	<!-- 把div作为弹出窗口 -->
	<div id="divDialog" style="overflow: hidden;">
		<iframe width="100%" height="100%" style="display:none;" frameborder="0" id="iframe"></iframe>
	</div>
</body>
</html>