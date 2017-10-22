<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-权限管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	
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
			
			/** 获取该角色在该模块中已经绑定的权限, 把字符串解析成js中数组 */
			var operaArr = $.parseJSON('${operaCodes}');
			/** 让下面的checkbox选中 (已经绑定权限的checkbox就选中) */
			boxs.each(function(){
				/** 判断当前checkbox中的value属性值是不是在operaArr数组是否存在 */
				if (operaArr.indexOf(this.value) != -1){
					/** 让当前checkbox选中 */
					$(this).prop("checked", true)
						   .parent().parent().trigger("mouseover");
				}
			});
			/** 让全选选中 */
			$("#checkAll").attr("checked", operaArr.length == boxs.length);
			
			
			
			/** 为绑定操作按钮绑定点击事件 */
			$("#bindModule").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				/** 定义标记 */
				var isRequest = false;
				/** 等于就不发请求 */
				if (operaArr.length == boxs.length){
					/** 也可能发请求 */
					boxs.each(function(){
						if (operaArr.indexOf(this.value) == -1){
							isRequest = true;
							return;
						}
					});
				}else{
					isRequest = true;
				}
				if (isRequest){
					/** 定义数组 */
					var codes = new Array();
					/** 获取选中的checkbox中的value属性值 */
					boxs.each(function(){
						codes.push(this.value);
					});
					window.location.href = "${path}/admin/identity/bindModule.jspx?role.id=${role.id}&moduleCode=${moduleCode}&codes=" + codes.join(",");
				}
			});
			
			/** 为返回按钮绑定点击事件 */
			$("#backBtn").click(function(){
				parent.window.location.href = "${path}/admin/identity/selectRole.jspx";
			});
			
		});
	</script>
</head>
<body>
	<table>
		<tr>
			<td><input type="button" value="绑定操作" id="bindModule"/></td>
			<td><input type="button" value="返回" id="backBtn"/>
				&nbsp;当前角色：【<font color="red">${role.name}</font>】
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
			<th>链接</th>
			<th>备注</th>
			
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="modules" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><input type="checkbox" id="box_${stat.index}" value="${code}"/>${stat.count}</td>
					<td><s:property value="code"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="url"/></td>
					<td><s:property value="remark"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
</body>
</html>