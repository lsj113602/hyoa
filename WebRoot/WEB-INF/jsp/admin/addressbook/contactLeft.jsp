<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>办公管理系统-联系组树</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="itcast.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	
    <link rel="stylesheet" type="text/css" href="${path}/js/dtree/dtree.css"/>
    <script type="text/javascript" src="${path}/js/dtree/dtree.js"></script>
    <script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript">
	
		$(function(){
			/** 创建树对象 */
			window.d = new dTree("d", "${path}/js/dtree/");
			/** 添加根节点
			 * id : 自己节点的id
			 * pid: 父节点的id
			 * name : 树节点显示的名称
			 */
			 d.add(-2, -1, "联系组"); 
			 d.add(0, -2, "全部","${path}/admin/addressbook/selectContact.jspx", "全部", "contactRightFrame");
			 /** 发异步请求加载操作树 */
			 $.ajax({
			 	url : "${path}/admin/addressbook/loadContactGroupTreeAjax.jspx",
			 	type : "post",
			 	dataType : "json",
			 	async : true,
			 	success : function(data){
			 		// data: [{id : '', pid: '', name : ''},{},...]
			 		$.each(data, function(){
			 			// this : {id : '', pid: '', name : ''}
			 			d.add(this.id, this.pid, this.name, "${path}/admin/addressbook/selectContact.jspx?contact.contactGroup.id=" + this.id, this.name, "contactRightFrame");
			 		});
			 		
			 		$(document.body).html(d.toString());
			 	},
			 	error : function(){
			 		alert("数据加载失败！");
			 	}
			 });
			 
		});
	</script>
</head>
<body>
	
</body>
</html>