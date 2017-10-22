<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-操作管理</title>
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
			
			
			
			/** 为添加操作按钮绑定点击事件 */
			$("#addModule").click(function(){
				/** 显示添加操作的对话窗口 */
				$("#divDialog").dialog({
					title : "添加操作", // 标题
					width : 385,  // 宽度
					height : 235, // 高度
					collapsible : true, // 可伸缩
					minimizable : true, // 最小化
					maximizable : true,  // 最大化
					modal : true, // 模态窗口
					onClose : function(){ // 监听是否关闭当前窗口
						/** 刷新左边的操作树 */
						parent.moduleLeftFrame.location.reload();
						window.location.href = "${path}/admin/identity/selectModule.jspx?pageModel.pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
					}
				});
				$("#iframe").prop("src", "${path}/admin/identity/showAddModule.jspx?parentCode=${parentCode}").show();
			});
			
			
			/** 修改操作 */
			$("#updateModule").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.length == 0){
					alert("请选择要修改的操作！");
				}else if (boxs.length == 1){
					
					/** 显示修改操作的对话窗口 */
					$("#divDialog").dialog({
						title : "修改操作", // 标题
						width : 385, // 宽度
						height : 235, // 高度
						collapsible : true, // 可伸缩
						minimizable : true, // 最小化
						maximizable : true,  // 最大化
						modal : true, // 模态窗口
						onClose : function(){ // 监听是否关闭当前窗口
							/** 刷新左边的操作树 */
							parent.moduleLeftFrame.location.reload();
							window.location.href = "${path}/admin/identity/selectModule.jspx?pageModel.pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
						}
					});
					$("#iframe").prop("src", "${path}/admin/identity/showUpdateModule.jspx?module.code=" + boxs.val()).show();
				}else{
					alert("修改时，只能选择一个操作！");
				}
			});
			
			/** 为删除按钮绑定点击事件 */
			$("#deleteModule").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.size() == 0){
					alert("请选择要删除的操作！");
				}else{
					if (confirm("您确定要删除吗？")){
						/** 定义数组 */
						var codes = new Array();
						/** 获取选中的checkbox中的value属性值 */
						boxs.each(function(){
							codes.push(this.value);
						});
						window.location.href = "${path}/admin/identity/deleteModule.jspx?pageModel.pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}&codes=" + codes.join(",");
					}
				}
			});
			
			if ("${tip}" != ""){
				/** 刷新左边的操作树 */
				parent.moduleLeftFrame.location.reload();
			}
		});
	</script>
</head>
<body>
	<table>
		<tr>
			<td><input type="button" value="添加" id="addModule"/></td>
			<td><input type="button" value="修改" id="updateModule"/></td>
			<td><input type="button" value="删除" id="deleteModule"/>
				&nbsp;<font color="red">${tip}</font>
			</td>
		</tr>
	</table>


	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th width="65px;"><input type="checkbox" id="checkAll"/>全部</th>
			<th>编号</th>
			<th>名称</th>
			<th>备注</th>
			<th>链接</th>
			<th>操作</th>
			<th>创建日期</th>
			<th>创建人</th>
			<th>修改日期</th>
			<th>修改人</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="modules" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><input type="checkbox" id="box_${stat.index}" value="${code}"/>${stat.count}</td>
					<td><s:property value="code"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="remark"/></td>
					<td><s:property value="url"/></td>
					<td><a href="${path}/admin/identity/selectModule.jspx?parentCode=${code}">查看下级</a></td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="creater.name"/></td>
					<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="modifier.name"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<it:pager pageIndex="${pageModel.pageIndex}" 
			  pageSize="${pageModel.pageSize}" 
			  recordCount="${pageModel.recordCount}" 
			  submitUrl="${path}/admin/identity/selectModule.jspx?pageModel.pageIndex={0}&parentCode=${parentCode}"/>

	
	<!-- 把div作为弹出窗口 -->
	<div id="divDialog" style="overflow: hidden;">
		<iframe width="100%" height="100%" style="display:none;" frameborder="0" id="iframe"></iframe>
	</div>
</body>
</html>