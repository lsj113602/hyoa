<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-联系组管理</title>
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
			
			
			
			
			/** 为添加联系组按钮绑定点击事件 */
			$("#addContactGroup").click(function(){
				/** 显示添加联系组的对话窗口 */
				$("#divDialog").dialog({
					title : "添加联系组", // 标题
					width : 405,  // 宽度
					height : 215, // 高度
					collapsible : true, // 可伸缩
					minimizable : true, // 最小化
					maximizable : true,  // 最大化
					modal : true, // 模态窗口
					onClose : function(){ // 监听是否关闭当前窗口
						window.location.href = "${path}/admin/addressbook/selectContactGroup.jspx?pageModel.pageIndex=${pageModel.pageIndex}";
					}
				});
				$("#iframe").prop("src", "${path}/admin/addressbook/showAddContactGroup.jspx").show();
			});
			
			
			/** 修改联系组 */
			$("#updateContactGroup").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.length == 0){
					alert("请选择要修改的联系组！");
				}else if (boxs.length == 1){
					
					/** 显示修改联系组的对话窗口 */
					$("#divDialog").dialog({
						title : "修改联系组", // 标题
						width : 405, // 宽度
						height : 215, // 高度
						collapsible : true, // 可伸缩
						minimizable : true, // 最小化
						maximizable : true,  // 最大化
						modal : true, // 模态窗口
						onClose : function(){ // 监听是否关闭当前窗口
							window.location.href = "${path}/admin/addressbook/selectContactGroup.jspx?pageModel.pageIndex=${pageModel.pageIndex}";
						}
					});
					$("#iframe").prop("src", "${path}/admin/addressbook/showUpdateContactGroup.jspx?contactGroup.id=" + boxs.val()).show();
				}else{
					alert("修改时，只能选择一个联系组！");
				}
			});
			
			/** 为删除按钮绑定点击事件 */
			$("#deleteContactGroup").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.size() == 0){
					alert("请选择要删除的联系组！");
				}else{
					if (confirm("您确定要删除吗？")){
						/** 定义数组 */
						var ids = new Array();
						/** 获取选中的checkbox中的value属性值 */
						boxs.each(function(){
							ids.push(this.value);
						});
						window.location.href = "${path}/admin/addressbook/deleteContactGroup.jspx?pageModel.pageIndex=${pageModel.pageIndex}&ids=" + ids.join(",");
					}
				}
			});
			
		});
	</script>
</head>
<body>
	<table>
		<tr>
			<td><input type="button" value="添加" id="addContactGroup"/></td>
			<td><input type="button" value="修改" id="updateContactGroup"/></td>
			<td><input type="button" value="删除" id="deleteContactGroup"/>
				&nbsp;<font color="red">${tip}</font>
			</td>
		</tr>
	</table>


	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th width="65px;"><input type="checkbox" id="checkAll"/>全部</th>
			<th>组名</th>
			<th>备注</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="contactGroups" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><input type="checkbox" id="box_${stat.index}" value="${id}"/>${stat.count}</td>
					<td><s:property value="name"/></td>
					<td><s:property value="remark"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<it:pager pageIndex="${pageModel.pageIndex}" 
			  pageSize="${pageModel.pageSize}" 
			  recordCount="${pageModel.recordCount}" 
			  submitUrl="${path}/admin/addressbook/selectContactGroup.jspx?pageModel.pageIndex={0}"/>

	
	<!-- 把div作为弹出窗口 -->
	<div id="divDialog" style="overflow: hidden;">
		<iframe width="100%" height="100%" style="display:none;" frameborder="0" id="iframe"></iframe>
	</div>
</body>
</html>