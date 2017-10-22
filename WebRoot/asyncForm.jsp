<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>办公管理系统-表单异步上传</title>
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
		<script type="text/javascript" src="${path}/js/jquery.form.js"></script>
		<script type="text/javascript">
			
			$(function(){
				// 第一种方式(v1.7-)
				/**$("#fileForm").on("submit", function(e) {
					// 取消事件的默认行为 
		            e.preventDefault(); 
		            // 表单异步提交 
		            $(this).ajaxSubmit({
		                url : "${path}/asyncUpload.jspx",
		                type : "post",
		                dataType : "text",
		                async : true,
		                success : function(imgUrl){
		                	$("<img/>").attr("src", "${path}" + imgUrl)
		                	           .width(200)
		                	           .height(200)
		                	           .appendTo("#imgs");
		                },
		                error : function(){
		                	alert("数据加载失败！");
		                }
		            });
		        });*/
		        
		        // 第二种方式: (v1.7+)
		         /** 表单异步提交 */
	            $("#fileForm").ajaxForm({
	                url : "${path}/asyncUpload.jspx",
	                type : "post",
	                dataType : "text",
	                async : true,
	                success : function(imgUrl){
	                	$("<img/>").attr("src", "${path}" + imgUrl)
	                	           .width(200)
	                	           .height(200)
	                	           .appendTo("#imgs");
	                },
	                error : function(){
	                	alert("数据加载失败！");
	                }
	            });
			});
		</script>
	</head>
<body>
	<br/>
	<table align="center" class="editTable" cellpadding="8" cellspacing="1">
		<tbody style="background-color: #FFFFFF;">
			<s:form method="post" enctype="multipart/form-data" id="fileForm" theme="simple">
				<tr>
					<td width="65px;">文件说明：</td>
					<td>
						<input type="text" name="remark" size="18"/>
					</td>
				</tr>
				<tr>
					<td>请选择上传的文件：</td>
					<td>
						<input type="file" name="pic"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交"/>
					</td>
				</tr>
			</s:form>
		</tbody>
	</table>
	
	<div id="imgs">
		
	</div>
</body>
</html>

	
	