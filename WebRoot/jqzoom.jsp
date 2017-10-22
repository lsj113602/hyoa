<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>办公管理系统-图片放大镜</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="itcast.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link rel="stylesheet" type="text/css" href="${path}/js/jqzoom/css/jquery.jqzoom.css"/>
	<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqzoom/jquery.jqzoom-core.js"></script>
	<script type="text/javascript">
		$(function(){
			/** 图片放大镜 */
			$("#zoom").jqzoom({
	            zoomType: "standard", // 指定放大镜的类型 'standard', 'drag', 'innerzoom', 'reverse'
	            lens:true, // 是否产生蒙版
	            preloadImages: false, // 是否预加载图片
	            title : false, // 不需要标题
	            alwaysOn: false, // 是否总是显示
	            zoomWidth: 500, // 右边div的宽度
            	zoomHeight: 500, // 右边div的高度
            	xOffset:0, // 指定右边div离左边的距离
            	yOffset:0, // 指定右边div离上面的距离
            	position:"right" // 指div放的方向(left、right、bottom)
            	
	        });
		});
	</script>
</head>
<body>
	<center>
		<a href="${path}/images/Koala.jpg" id="zoom">
			<img src="${path}/images/Koala.jpg" width="200px" height="200px"/>
		</a>
	</center>
</body>
</html>