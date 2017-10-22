<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-修改角色</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
		<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="Description" content="网页信息的描述" />
		<meta name="Author" content="fkjava.org" />
		<meta name="Copyright" content="All Rights Reserved." />
		<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn_submit").on("click", function(){
					var name = $("#name");
					var remark = $("#remark");
					var msg = "";
					if ($.trim(name.val()) == ""){
						msg = "角色名称不能为空！";
						name.focus();
					}else if ($.trim(remark.val()) == ""){
						msg = "角色备注不能为空！";
						remark.focus();
					}
					if (msg != ""){
						alert(msg);
					}else{
						/** 提交表单 */
						$("#updateRoleForm").submit();
					}
				});
			});
		</script>
	</head>
<body>
	<table align="center">
		<!-- 输出防表单重复提交的提示信息 -->
		<s:actionerror cssStyle="font-size:12px;color:red;"/>
		<!-- 后台输入校验的提示信息 -->
		<s:fielderror cssStyle="font-size:12px;color:red;"/>
		<s:form id="updateRoleForm" action="/admin/identity/updateRole.jspx" method="post" theme="simple">
			<s:hidden name="role.id"></s:hidden>
			<!-- 防表单重复提交需要传的参数 -->
			<s:token></s:token>
			<tr>
				<td>角色名称：</td>
				<td>
					<s:textfield name="role.name" size="40" id="name"/>
				</td>
			</tr>
			<tr>
				<td>角色备注：</td>
				<td>
					<s:textarea name="role.remark" rows="5" cols="38" id="remark"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input value="提 交" type="button" id="btn_submit" />
					&nbsp;
					<input value="重 置" type="reset" />&nbsp;<font color="red">${tip}</font>
				</td>
			</tr>
		</s:form>

	</table>
</body>
</html>	