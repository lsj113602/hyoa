<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>办公管理系统-权限管理</title>
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
			 d.add(0, -1, "权限管理"); 
			 /** 发异步请求加载操作树 */
			 $.ajax({
			 	url : "${path}/admin/identity/loadPopedomTreeAjax.jspx",
			 	type : "post",
			 	dataType : "json",
			 	async : true,
			 	success : function(data){
			 		// data: [{id : '', pid: '', name : ''},{},...]
			 		$.each(data, function(){
			 			// this: {id : '', pid: '', name : ''}
			 			var url = "${path}/admin/identity/selectPopedom.jspx?role.id=${role.id}&moduleCode=" + this.id;
			 			url = this.pid == "0" ? "" : url;
			 			/**
			 			 d.add(id, pid, name, url, title, target)
			 			 target: 目标写frame的name属性值
			 			 */
			 			d.add(this.id, this.pid, this.name, url, this.name, "popedomRightFrame");
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