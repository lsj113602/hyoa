<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>人事管理系统-短信验证码</title>
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
		<script type="text/javascript" src="${path}/js/jquery-timer-1.0.js"></script>
		<script type="text/javascript">
			
			$(function(){
				/** 为获取验证码按钮绑定点击事件 */
				$("#verifyBtn").click(function(){
				
					/** 发送异步请求 */
					$.ajax({
						url : "${path}/sendSMSAjax.jspx",
						type : "post",
						data : {phone : $("#phone").val()}, // 请求参数
						dataType : "text",
						async : true,
						success : function(data){
							if ($.parseJSON(data)){
								/** 使当前按钮失效 */
								$("#verifyBtn").attr("disabled", true)
												.countDown("{0}秒后重新获取验证码", 60);
							}
						},
						error : function(){
							alert("数据加载失败！");
						}
					});
				
					
					
				});
			});
		</script>
	</head>
<body>
	<br/>
	<table align="center" class="editTable" cellpadding="8" cellspacing="1">
		<s:actionerror/><s:fielderror/>
		<tbody style="background-color: #FFFFFF;">
			<s:form action="/admin/updateSelf.jspx" method="post" id="updateSelfForm" theme="simple">
				<s:token></s:token>
				<tr>
					<td width="65px;">手机号码：</td>
					<td>
						<input type="text" name="phone" size="18" id="phone"/>
					</td>
					
				</tr>
				<tr>
					<td>验证码：</td>
					<td>
						<input type="text" name="phone" size="18"/>
						<input type="button"  value="获取验证码" id="verifyBtn"/>
					</td>
					
				</tr>
				
			</s:form>
		</tbody>
	</table>
</body>
</html>

	
	