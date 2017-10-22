<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-用户管理</title>
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
	<style type="text/css">
		.nameDiv{
			border : 1px solid black;
			width : 120px;
			height : auto;
			position: absolute;
			left : 100px;
			top : 200px;
			background-color: #FFFFFF;
			line-height : 25px;
			font-weight : bold;
			display: none;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			/** 异步加载数据填充部门下拉列表 */
			$.ajax({
				url : "${path}/admin/identity/loadDeptAjax.jspx",
				type : "post",
				dataType : "json",
				async: true,
				success : function(data){
					// data
					// [{id : 1, name : '技术部'},{id : 1, name : '技术部'},...]
					/** 获取当前查询的部门id */
					var deptId = "${user.dept.id}";
					$.each(data, function(){
						// this: {id : 1, name : '技术部'}
						$("<option/>").val(this.id)
						              .text(this.name)
						              .attr("selected", this.id == deptId)
						              .appendTo("#deptSelect");
					});
				},
				error : function(){
					alert("数据加载失败！");
				}
			});
			
			/** 为全选checkbox绑定点击事件 */
			$("#checkAll").click(function(){
				// this : dom元素
				/** 获取下面数据迭代行中的checkbox */
				$("input[id^='box_']").attr("checked", this.checked);
				/** 获取下面所有的tr */
				$("tr[id^='tr_']").trigger(this.checked ? "mouseover" : "mouseout");
			});
			
			/** 让全选选中 */
			/** 获取下面数据迭代行中的所有checkbox */
			var boxs = $("input[id^='box_']");
			/** 为数据迭代行中的所有checkbox绑定点击事件 */
			boxs.click(function(){
				/** 获取选中的checkbox */
				$("#checkAll").attr("checked", boxs.filter(":checked").length == boxs.length);
			});
			
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
			
			
			/** #################### 姓名文本框(联想) ####################### */
			/** 确定nameDiv的left与top、width */
			/** 获取姓名文本框的位置偏移对象 */
			var offset = $("#nameText").offset();
			/** 设置nameDiv的偏离左边与上面的距离 */
			$("#nameDiv").css("left", offset.left)
			             .css("top", offset.top + $("#nameText").outerHeight());
			/** 设置宽度 */
			$("#nameDiv").width($("#nameText").innerWidth() + 1);
			/** 定义变量缓存上一次用户输入的数据 */
			var name = "";
			/** 为文本框绑定onkeyup事件 */
			$("#nameText").keyup(function(){ // onkeyup 按钮松开
				/** 判断文本框是不是输入了值 */
				if ($.trim(this.value) != ""){
					/** 判断用户输入的值与上一次是不是一样 */
					if (name != $.trim(this.value)){
						/** 异步请求查询姓名 */
						$.post("${path}/admin/identity/loadNameAjax.jspx",{name : $.trim(this.value)}, function(data, status){
							// status : success 、 error
							if (status == "success"){
								// data: ['','']
								/** 清空nameDiv中所有子元素 */
								$("#nameDiv").empty().hide();
								/** 判断数组的长度(确定是否有响应数据) */
								if (data.length > 0){
									/** 迭代数组 */
									$.each(data, function(){
										/** 创建div添加到nameDiv中 */
										$("<div/>").text(this).appendTo("#nameDiv");
									});
									/** 显示nameDiv */
									$("#nameDiv").slideDown(300);
									/** 获取nameDiv中所有的子元素，为它们绑定hover事件 */
									$("#nameDiv").children().hover(
										function(){
											/** 删除所有的div的style */
											$("#nameDiv > div").removeAttr("style");
											$(this).css({"background-color" : "#FFC0CB", "cursor" : "pointer"})
										}, 
										function(){
											$(this).removeAttr("style");
										}
									).click(function(){
										/** 把当前div中文本放到文本框 */
										$("#nameText").val($(this).text());
										/** 关闭nameDiv隐藏 */
										$("#nameDiv").slideUp(300);
									});
								}
							}else{
								alert("数据加载失败！");
							}
						}, "json");
					}
					/** 缓存用户输入的文本 */
					name = $.trim(this.value);
				}else{
					/** 关闭nameDiv隐藏 */
					$("#nameDiv").slideUp(300);
					/** 还原为空字符串 */
					name = "";
				}
			});
			/** 为document绑定点击事件 */
			$(document).click(function(){
				/** 关闭nameDiv隐藏 */
				$("#nameDiv").slideUp(300);
			});
			/** 为document绑定onkeydown事件 */
			$(document).keydown(function(event){
				if (event.keyCode === 38){ // 向上
					/** 获取当前的div(有style的属性div) */
					var currentDiv = $("#nameDiv > div[style]").removeAttr("style"); // 删除样式
					/** 代表有一个div上面有style */
					if (currentDiv.length == 1 && currentDiv.prev().length == 1){
						/** 获取相邻的上一个div */
						var prevDiv = currentDiv.prev();
						/** 为prevDiv添加style */
						prevDiv.css("background-color","#FFC0CB");
						/** 为name赋值，为了防止发送异步请求 */
						name = prevDiv.text();
						/** 把prevDiv中文本放到上面的文本框中 */
						$("#nameText").val(prevDiv.text());
					}
					/** 代表没有一个div上面有style */
					if (currentDiv.length == 0 || currentDiv.prev().length == 0){
						/** 获取最后一个div */
						var lastDiv = $("#nameDiv > div:last");
						/** 为lastDiv添加style */
						lastDiv.css("background-color","#FFC0CB");
						/** 为name赋值，为了防止发送异步请求 */
						name = lastDiv.text();
						/** 把lastDiv中文本放到上面的文本框中 */
						$("#nameText").val(lastDiv.text());
					}
				}
				if (event.keyCode === 40){ // 向下
					/** 获取当前的div(有style的属性div) */
					var currentDiv = $("#nameDiv > div[style]").removeAttr("style"); // 删除样式
					/** 代表有一个div上面有style */
					if (currentDiv.length == 1 && currentDiv.next().length == 1){
						/** 获取相邻的下一个div */
						var nextDiv = currentDiv.next();
						/** 为nextDiv添加style */
						nextDiv.css("background-color","#FFC0CB");
						/** 为name赋值，为了防止发送异步请求 */
						name = nextDiv.text();
						/** 把nextDiv中文本放到上面的文本框中 */
						$("#nameText").val(nextDiv.text());
					}
					/** 代表没有一个div上面有style */
					if (currentDiv.length == 0 || currentDiv.next().length == 0){
						/** 获取第一个div */
						var firstDiv = $("#nameDiv > div:first");
						/** 为firstDiv添加style */
						firstDiv.css("background-color","#FFC0CB");
						/** 为name赋值，为了防止发送异步请求 */
						name = firstDiv.text();
						/** 把firstDiv中文本放到上面的文本框中 */
						$("#nameText").val(firstDiv.text());
					}
				}
			});			
			/** #################### 姓名文本框(联想) ####################### */
			
			/** 为添加用户按钮绑定点击事件 */
			$("#addUser").click(function(){
				/** 显示添加用户的对话窗口 */
				$("#divDialog").dialog({
					title : "添加用户", // 标题
					width : 480, // 宽度
					height : 265, // 高度
					collapsible : true, // 可伸缩
					minimizable : true, // 最小化
					maximizable : true,  // 最大化
					modal : true, // 模态窗口
					onClose : function(){ // 监听是否关闭当前窗口
						window.location.href = "${path}/admin/identity/selectUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id}";
					}
				});
				$("#iframe").prop("src", "${path}/admin/identity/showAddUser.jspx").show();
			});
			
			/** 修改用户 */
			$("#updateUser").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.length == 0){
					alert("请选择要修改的用户！");
				}else if (boxs.length == 1){
					
					/** 显示修改用户的对话窗口 */
					$("#divDialog").dialog({
						title : "修改用户", // 标题
						width : 480, // 宽度
						height : 245, // 高度
						collapsible : true, // 可伸缩
						minimizable : true, // 最小化
						maximizable : true,  // 最大化
						modal : true, // 模态窗口
						onClose : function(){ // 监听是否关闭当前窗口
							window.location.href = "${path}/admin/identity/selectUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id}";
						}
					});
					$("#iframe").prop("src", "${path}/admin/identity/showUpdateUser.jspx?user.userId=" + boxs.val()).show();
				}else{
					alert("修改时，只能选择一个用户！");
				}
			});
			
			/** 为删除按钮绑定点击事件 */
			$("#deleteUser").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.size() == 0){
					alert("请选择要删除的用户！");
				}else{
					if (confirm("您确定要删除吗？")){
						/** 定义数组 */
						var userIds = new Array();
						/** 获取选中的checkbox中的value属性值 */
						boxs.each(function(){
							userIds.push(this.value);
						});
						window.location.href = "${path}/admin/identity/deleteUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id}&userIds=" + userIds.join(",");
					}
				}
			});
			/** 为审核按钮绑定点击事件 */
			$("#checkUser").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.size() == 0){
					alert("请选择要审批的用户！");
				}else{
					if (confirm("您确定要审批吗？")){
						/** 定义数组 */
						var userIds = new Array();
						/** 获取选中的checkbox中的value属性值 */
						boxs.each(function(){
							userIds.push(this.value);
						});
						window.location.href = "${path}/admin/identity/checkUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id}&userIds=" + userIds.join(",") + "&user.status=" + $("#status").val();
					}
				}
			});
			
			/** 获取该模块的权限 */
			var popedoms = "${user_module_popedoms}";
			// /admin/identity/selectUser.jspx, /admin/identity/updateUser.jspx
			var btnArr = ["addUser", "updateUser", "deleteUser", "checkUser"];
			$.each(btnArr, function(){
				if (popedoms.indexOf(this) == -1){
					if (this == "checkUser"){
						$("#td_" + this).hide();
					}
					$("#" + this).hide();
				}
			});
		});
	</script>
</head>
<body>
	<!-- 工具按钮区 -->
	<s:form action="/admin/identity/selectUser.jspx" method="post" theme="simple">
		<table>
			<tr>
				<td><input type="button" value="添加" id="addUser"/></td>
				<td><input type="button" value="修改" id="updateUser"/></td>
				<td><input type="button" value="删除" id="deleteUser"/></td>
				<td id="td_checkUser">状态：<select id="status">
				    <option value="1">审核</option>
				    <option value="2">不通过</option>
				    <option value="3">冻结</option></select>

				</td>
				<td><input type="button" value="审核" id="checkUser"/></td>
				<td>姓名：<s:textfield name="user.name" size="12" id="nameText"/></td>
				<td>手机号码：<s:textfield name="user.phone" size="12"/></td>
				<td>部门：<select name="user.dept.id" id="deptSelect">
							<option value="0">==请选择==</option>
						</select>
				</td>
				<td><input type="submit" value="查询"/>
						&nbsp;<font color="red">${tip}</font></td>
			</tr>
		</table>
	</s:form>


	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th><input type="checkbox" id="checkAll"/>全部</th>
			<th>编号</th>
			<th>姓名</th>
			<th>性别</th>
			<th>部门</th>
			<th>职位</th>
			<th>手机号码</th>
			<th>邮箱</th>
			<th>状态</th>
			<th>创建日期</th>
			<th>创建人</th>
			<th>审核日期</th>
			<th>审核人</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="users" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><input type="checkbox" id="box_${stat.index}" value="${userId}"/>${stat.count}</td>
					<td><s:property value="userId"/></td>
					<td><s:property value="name"/></td>
					<td>${sex == 1 ? '男' : '女'}</td>
					<td><s:property value="dept.name"/></td>
					<td><s:property value="job.name"/></td>
					<td><s:property value="phone"/></td>
					<td><s:property value="email"/></td>
					<td>
						<s:if test="status == 0"><font color="red">新建</font></s:if>
						<s:elseif test="status == 1">审核</s:elseif>
						<s:elseif test="status == 2">不通过</s:elseif>
						<s:else><font color="#2F4F4F">冻结</font></s:else>
					</td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="creater.name"/></td>
					<td><s:date name="checkDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="checker.name"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<it:pager pageIndex="${pageModel.pageIndex}" 
			  pageSize="${pageModel.pageSize}" 
			  recordCount="${pageModel.recordCount}" 
			  submitUrl="${path}/admin/identity/selectUser.jspx?pageModel.pageIndex={0}&user.name=${user.name}&user.phone=${user.phone}&user.dept.id=${user.dept.id}"/>

	<!-- 显示异步请求查询用户姓名的数据 -->
	<div id="nameDiv" class="nameDiv"></div>
	
	<!-- 把div作为弹出窗口 -->
	<div id="divDialog" style="overflow: hidden;">
		<iframe width="100%" height="100%" style="display:none;" frameborder="0" id="iframe"></iframe>
	</div>
</body>
</html>