<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>人事管理系统-添加员工</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
		<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="Description" content="网页信息的描述" />
		<meta name="Author" content="fkjava.org" />
		<meta name="Copyright" content="All Rights Reserved." />
		<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
		<link href="${path}/css/common/admin.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript" src="${path}/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				/** 发送异步请求填充两个下拉列表 */
				$.ajax({
					url : "${path}/admin/identity/loadDeptAndJobAjax.jspx", // 请求URL
					type : "post", // 请求方式
					dataType : "json", // 服务器响应回来的数据类型
					async : true,
					success : function(data){
						// data: {"depts" : [{id : 1, name : ''},{id : 1, name : ''}], "jobs" : [{code : '', name : ''},{}]}
						/** 填充部门下拉列表 */
						$.each(data.depts, function(){
							// this : {id : 1, name : ''}
							$("<option/>").val(this.id).text(this.name).appendTo("#deptSelect");
						});
						/** 填充职位下拉列表 */
						$.each(data.jobs, function(){
							// this : {code : 1, name : ''}
							$("<option/>").val(this.code).text(this.name).appendTo("#jobSelect");
						});
					},
					error : function(){
						alert("数据加载失败！");
					}
				});
				/** 添加用户，提交表单函数 */
				$("#btn_submit").click(function(){
					$("#updateEmployeeForm").submit();
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
		<s:form id="updateEmployeeForm" action="/admin/employeeInfo/updateEmployee.jspx" method="post" theme="simple">
			<!-- 防表单重复提交需要传的参数 -->
			<s:token></s:token>
			<tr><td colspan="4"></td></tr>
			<tr>
				<td>姓名：<s:hidden name="employee.employeeId"/></td>
				<td>
					<s:textfield name="employee.name" size="18" maxlength="20" id="name"/>
				</td>
				<td>身份证号：</td>
				<td>
					<s:textfield name="employee.idcard" size="20" id="userId"/>
				</td>
			</tr>
			<tr>
				<td>性别：</td>
				<td>
					<s:select name="employee.sex" list="#{1:'男',2:'女'}"/>
				</td>
				<td>部&nbsp;&nbsp;门：</td>
				<td>
					<select id="deptSelect" name="employee.dept.id"></select>
				</td>
			</tr>

			<tr>
				<td>职&nbsp;&nbsp;位：</td>
				<td>
					<select id="jobSelect" name="employee.job.code"></select>
				</td>
				<td>邮&nbsp;&nbsp;箱：</td>
				<td>
					<s:textfield name="employee.email" size="18" maxlength="50" id="email"/>
				</td>
			</tr>
				<td>政治身份：</td>
				<td><s:select name="employee.identity" list="#{'群众':'群众','中共党员':'中共党员','其它':'其它' }"/></td>
				<td>手&nbsp;&nbsp;机：</td>
				<td>
					<s:textfield name="employee.phone" size="18" maxlength="11" id="phone"/>
				</td>
			</tr>
			<tr>
				<td>状态：&nbsp;</td>
				<td>
				    <s:select name="employee.status" list="#{0:'在职',1:'离职'}"/>
				</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>地址：&nbsp;</td>
				<td>
					<s:textfield name="employee.address" size="18" maxlength="20" id="address"/>
				</td>
				<td>入职时间：&nbsp;</td>
				<td>
				    <s:textfield name="employee.joinDate" size="18" id="endDate"
						cssClass="Wdate" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input value="提 交" type="button" id="btn_submit" />
					&nbsp;
					<input value="重 置" type="reset" />&nbsp;<font color="red">${tip}</font>
				</td>
			</tr>
		</s:form>

	</table>
</body>
</html>	