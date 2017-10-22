<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>人事管理系统-密码修改</title>
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
				$("#pwd_submit").click(function(){
					var oldpwd = $("#oldpwd");
					var newpwd = $("#newpwd");
					var okpwd = $("#okpwd");
					var  msg = "";
					if ($.trim(oldpwd.val()) == ""){
						msg = "旧密码不能为空!";
						oldpwd.focus();
					}else if (!/^\w{6,20}$/.test(oldpwd.val())){
						msg = "旧密码长度必须在6-20之间!";
						oldpwd.focus();
					}else if ($.trim(newpwd.val()) == ""){
						msg = "新密码不能为空!";
						newpwd.focus();
					}else if (!/^\w{6,20}$/.test(newpwd.val())){
						msg = "新密码长度必须在6-20之间!";
						newpwd.focus();
					}else if ($.trim(newpwd.val()) != $.trim(okpwd.val())){
						msg = "新密码与确认密码不一致!";
						okpwd.focus();
					}
					if (msg != ""){
						alert(msg);
					}else{
						$("#pwdForm").submit();
					}
				});
			});
		</script>
	</head>
<body>
	<div align="center">
		<s:if test="tip != null">
			<center style="color:red;">${tip}</center>
		</s:if>
		<form action="${path}/admin/updatePwd.jspx" method="post" id="pwdForm">
			<s:token></s:token>
			<table cellpadding="10px">
				<tr>
					<td>旧密码：</td>
					<td><input type="password" id="oldpwd" size="30" name="oldpwd"/></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" id="newpwd" size="30" name="newpwd"/></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input type="password" id="okpwd" size="30" name="okpwd"/></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input type="button" id="pwd_submit" value="确定"/>&nbsp;&nbsp;<input type="reset" value="重置"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>