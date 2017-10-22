<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-添加联系人</title>
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
		<script type="text/javascript" src="${path}/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn_submit").on("click", function(){
					var name = $("#name");
					var phone = $("#phone");
					var email = $("#email");
					var qqNum = $("#qqNum");
					var birthday = $("#birthday");
					var msg = "";
					if ($.trim(name.val()) == ""){
						msg = "姓名不能为空！";
						name.focus();
					}else if ($.trim(phone.val()) == ""){
						msg = "手机号码不能为空！";
						phone.focus();
					}else if (!/^1[3|4|5|7|8]\d{9}$/.test($.trim(phone.val()))){
						msg = "手机号码格式不正确！";
						phone.focus();
					}else if ($.trim(email.val()) == ""){
						msg = "邮箱不能为空！";
						email.focus();
					}else if ($.trim(qqNum.val()) == ""){
						msg = "QQ号码不能为空！";
						qqNum.focus();
					}else if ($.trim(birthday.val()) == ""){
						msg = "生日不能为空！";
						birthday.focus();
					}
					if (msg != ""){
						alert(msg);
					}else{
						/** 提交表单 */
						$("#addContactForm").submit();
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
		<s:form id="addContactForm" action="/admin/addressbook/addContact.jspx" method="post" theme="simple">
			<s:hidden name="contact.contactGroup.id"></s:hidden>
			<!-- 防表单重复提交需要传的参数 -->
			<s:token></s:token>
			<tr>
				<td>名称：</td>
				<td>
					<s:textfield name="contact.name" size="40" id="name"/>
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<s:select name="contact.sex" list="#{1:'男', 2:'女'}" cssStyle="width:80px;"/>
				</td>
			</tr>
			<tr>
				<td>手机号码：</td>
				<td>
					<s:textfield name="contact.phone" size="40" id="phone"/>
				</td>
			</tr>
			<tr>
				<td>邮箱：</td>
				<td>
					<s:textfield name="contact.email" size="40" id="email"/>
				</td>
			</tr>
			<tr>
				<td>QQ号码：</td>
				<td>
					<s:textfield name="contact.qqNum" size="40" id="qqNum"/>
				</td>
			</tr>
			<tr>
				<td>生日：</td>
				<td>
					<s:textfield name="contact.birthday" cssClass="Wdate" 
						onClick="WdatePicker({lang:'zh-cn', skin:'whyGreen', dateFmt:'yyyy-MM-dd'});" 
						size="40" id="birthday"/>
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