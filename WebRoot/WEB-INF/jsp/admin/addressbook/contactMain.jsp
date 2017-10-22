<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>办公管理系统-联系人管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="itcast.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link rel="stylesheet" type="text/css" href="css/pager.css"/>
	<script type="text/javascript">
	</script>
</head>
	<!-- 框架集: 分成两列 -->
	<frameset cols="138,*" frameborder="yes" border="1">
		<!-- 左边子窗口 -->
		<frame src="${path}/admin/addressbook/contactLeft.jspx" name="contactLeftFrame"/>
		<!-- 右边子窗口 -->
		<frame src="${path}/admin/addressbook/selectContact.jspx" name="contactRightFrame"/>
	</frameset>
</html>