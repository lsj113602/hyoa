<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>人事管理系统-找回密码</title>
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
				$("#find_submit").click(function(){
					var userId = $("#userId");
					var answer = $("#answer");
					var msg = "";
					if ($.trim(userId.val()) == ""){
						msg = "用户编号不能为空！";
						userId.focus();
					}else if (!/^\w{5,20}$/.test(userId.val())){
						msg = "用户编号长度必须在5-20之间！";
						userId.focus();
					}else if ($.trim(answer.val()) == ""){
						msg = "答案不能为空！";
						answer.focus();
					}
					if (msg != ""){
						alert(msg);
					}else{
						$("#findForm").submit();
					}
				});
			});
		</script>
	</head>
<body>
	<s:form action="/admin/findPwd.jspx" method="post" id="findForm" theme="simple">
		<br/><center><font color="red">${tip}</font></center>
		<table align="center">
			<tr>
				<td>用户编号：</td>
				<td><s:textfield name="userId" id="userId" size="38"/></td>
			</tr>
			<tr>
				<td>问&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
				<td>
					<s:select name="question"
						list="#{0:'您的生日？',1:'您父亲的姓名？',2:'您的出生地？',3:'您母亲的名字？'}"/>
				</td>
			</tr>
			<tr>
				<td>答&nbsp;&nbsp;&nbsp;&nbsp;案：</td>
				<td colspan="2">
					<s:textarea rows="3" cols="30" id="answer" name="answer"></s:textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="find_submit" value="确定"/>&nbsp;<input type="reset" value="重置"/>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>