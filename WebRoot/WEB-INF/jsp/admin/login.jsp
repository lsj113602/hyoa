<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>和毅·人事管理系统-登录页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="${path}/js/jqeasyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqeasyui/jquery.easyui.min.js"></script>
   
	<style type="text/css">
		body{ 
			background-repeat: repeat; 
			background-positon: 100%, 100%; 
		} 
		li{
			margin-left:20px;
		}
	</style>
	<script type="text/javascript">
	
		/** 当前页面不能被其它的窗口包含 */
		if (window != parent){
			parent.location = window.location;
		}
		/** 如果用户已登录就直接跳转 */
		if ("${session_user}" != ""){
			window.location.href = "${path}/admin/main.jspx";
		}
	
		$(function(){
			/** 为验证码看不清楚a标签绑定点击事件 */
			$("#see").click(function(){
				/** 获取验证码图片，重新设置它的src属性值 */
				$("#vimg").attr("src", "${path}/verify.jspx?random=" + Math.random());
			});
			
			/** 为验证码图片绑定onmouseover */
			$("#vimg").mouseover(function(){
				/** 让光标变成手状 */
				$(this).css("cursor", "pointer");
			}).click(function(){
				/** 触发a标签的点击事件 */
				$("#see").trigger("click");
			});
			
			/** 为登录按钮绑定点击事件 */
			$("#login_btn").on("click", function(){
				/** 获取input元素 */
				var userId = $("#userId");
				var password = $("#password");
				var vcode = $("#vcode");
				var msg = "";
				if ($.trim(userId.val()) == ""){
					msg = "用户名不能为空！";
					userId.focus(); // 获取焦点
				}else if (!/^\w{5,20}$/.test($.trim(userId.val()))){
					msg = "用户名格式不正确！";
					userId.focus(); // 获取焦点
				}else if ($.trim(password.val()) == ""){
					msg = "密码不能为空！";
					password.focus(); // 获取焦点
				}else if (!/^\w{6,20}$/.test($.trim(password.val()))){
					msg = "密码格式不正确！";
					password.focus(); // 获取焦点
				}else if ($.trim(vcode.val()) == ""){
					msg = "验证码不能为空！";
					vcode.focus(); // 获取焦点
				}else if (!/^[A-Za-z0-9]{4}$/.test($.trim(vcode.val()))){
					msg = "验证码格式不正确！";
					vcode.focus(); // 获取焦点
				}
				if (msg != ""){
					alert(msg);
				}else{
					/** 把表单中input元素直接序列成get请求字符串 */
					var params = $("#loginForm").serialize();
					/** 异步请求登录 */
					$.ajax({
						url : "${path}/admin/loginAjax.jspx", // 请求URL
						data : params, // 请求参数
						type : "post", // 请求方式
						dataType : "json", // 指定服务器响应回来的数据类型
						async : true, // 异步请求
						success : function(data){ // 请求成功
							// data: 响应数据
							// {key : value, key : value} {"tip" : "登录成功!", "status" : 0}
							if (data.status == 0){ // 登录成功
								window.location.href = "${path}/admin/main.jspx";
							}else{ // 登录失败
								alert(data.tip);
								/** 让验证码变一下 */
								$("#see").trigger("click");
							}					
						},
						error : function(){ // 请求出错
							alert("数据加载失败！");
						}
					});
				}
			});
			
			/** 监听用户是否按了回车键, 按了回车键也可以登录 */
			$(document).keydown(function(event){
				// event.keyCode 13 回车键
				if (event.keyCode === 13){
					$("#login_btn").trigger("click");
				}
			});
			
			/** 找回密码 */
			$("#findpwd").click(function(){
				alert("123");
				/** 显示找回密码的对话窗口 */
				$("#divDialog").dialog({
					title : "找回密码", // 标题
					width : 405,  // 宽度
					height : 215, // 高度
					collapsible : true, // 可伸缩
					minimizable : true, // 最小化
					maximizable : true,  // 最大化
					modal : true // 模态窗口
				});
				$("#iframe").prop("src", "${path}/admin/showFindpwd.jspx").show();
			});
		});
		
		
	</script>
</head>
<body background="${path}/images/login/9.png">
	<div align="center" style="height:100%">
		<form id="loginForm">
			<table border="0" cellpadding="0" cellspacing="0" style="margin-top:120px;">
				<tr>
					<td colspan="2" width="447" height="104" style="background-image: url('${path}/images/login/1_01.jpg');"></td>
				</tr>
				<tr>
					<td width="92" height="200" style="background-image: url('${path}/images/login/1_02.gif');">&nbsp;</td>
					<td width="355" height="200" style="background-image: url('${path}/images/login/1_03.gif');">
						<table style="font-size:13px;margin:0 0 0 30px;" >
							<tr align="left">
								<td>用户名：</td>
								<td colspan="2">
									<input type="text" name="userId" size="15" id="userId"/>
								</td>
								
							</tr>
							<tr align="left">
								<td>密&nbsp;&nbsp;码：</td>
								<td>
									<input type="password" name="password" size="15" id="password"/>
								</td>
								<td>
									<a href="javascript:void(0)" id="findpwd" style="font-size:12px;color:white;">忘了密码?</a>
								</td>
							</tr>
						   <tr align="left">
								<td>验证码：</td>
								<td>
									<div style="float:left;">
										<input type="text" name="vcode" size="4" maxlength="4" id="vcode"/>
									</div>
									<div style="float:left;padding:0 0 0 5px;">
										<img src="${path}/verify.jspx" width="60" height="22" title="验证码" id="vimg"/>
									</div>
								</td>
								<td align="left">
									<a href="javascript:void(0);" id="see" style="font-size:12px;color:white;">看不清楚</a>
								</td>
						   </tr>
						   <tr align="left">
						   		<td>&nbsp;</td>
								<td colspan="2"><input type="checkbox" name="key" value="1" id="key"/>记住用户</td>
						   </tr>
						 
						  <tr align="center">
							<td colspan="3">
								<input type="button" value="登 录" width="67" height="22" id="login_btn"/>&nbsp;
								<input type="reset" value="重 置"/>
							</td>
						  </tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="3" width="447" height="34" style="background-image: url('${path}/images/login/1_04.gif');"></td>
				</tr>
			</table>
		</form>
	</div>
	
	
	<!-- 把div作为弹出窗口 -->
	<div id="divDialog" style="overflow: hidden;">
		<iframe width="100%" height="100%" style="display:none;" frameborder="0" id="iframe"></iframe>
	</div>
</body>
</html>