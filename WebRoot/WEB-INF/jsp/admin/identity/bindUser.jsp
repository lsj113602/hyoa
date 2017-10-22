<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-绑定用户</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
	<link href="${path}/css/common/pager.css" type="text/css" rel="stylesheet"/>
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
			
			/** 为绑定用户按钮绑定点击事件 */
			$("#bindUser").click(function(){
				/** 获取下面选中的checkbox */
				var boxs = $("input[id^='box_']:checked");
				if (boxs.size() == 0){
					alert("请选择要绑定的用户！");
				}else{
					/** 定义数组 */
					var userIds = new Array();
					/** 获取选中的checkbox中的value属性值 */
					boxs.each(function(){
						userIds.push(this.value);
					});
					window.location.href = "${path}/admin/identity/bindUser.jspx?pageModel.pageIndex=${pageModel.pageIndex}&role.id=${role.id}&userIds=" + userIds.join(",");
				}
			});
			
		});
	</script>
</head>
<body>
	
	<table>
		<tr>
			<td><input type="button" value="绑定" id="bindUser"/>
				&nbsp;<font color="red">${tip}</font></td>
		</tr>
	</table>

	<!-- 数据展示区 -->
	<table width="100%" class="listTable" cellpadding="8" cellspacing="1">
		<tr class="listHeaderTr">
			<th><input type="checkbox" id="checkAll"/>全部</th>
			<th>编号</th>
			<th>姓名</th>
			<th>职位</th>
			<th>邮箱</th>
			<th>状态</th>
			<th>创建日期</th>
			<th>创建人</th>
		</tr>
		<tbody style="background-color: #FFFFFF;">
			<s:iterator value="users" status="stat">
				<tr id="tr_${stat.index}" class="listTr">
					<td><input type="checkbox" id="box_${stat.index}" value="${userId}"/>${stat.count}</td>
					<td><s:property value="userId"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="job.name"/></td>
					<td><s:property value="email"/></td>
					<td>
						<s:if test="status == 0"><font color="red">新建</font></s:if>
						<s:elseif test="status == 1">审核</s:elseif>
						<s:elseif test="status == 2">不通过</s:elseif>
						<s:else><font color="#2F4F4F">冻结</font></s:else>
					</td>
					<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:property value="creater.name"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	
	<!-- 分页标签区 -->
	<it:pager pageIndex="${pageModel.pageIndex}" 
			  pageSize="${pageModel.pageSize}" 
			  recordCount="${pageModel.recordCount}" 
			  submitUrl="${path}/admin/identity/showBindUser.jspx?pageModel.pageIndex={0}&role.id=${role.id}"/>
	
</body>
</html>