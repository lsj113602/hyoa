<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<title>人事管理系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
	<meta name="Keywords" content="keyword1,keyword2,keyword3"/>
	<meta name="Description" content="网页信息的描述" />
	<meta name="Author" content="gdcct.gov.cn" />
	<meta name="Copyright" content="All Rights Reserved." />
	<link href="${path}/logo.ico" rel="shortcut icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${path}/js/jqeasyui/themes/default/easyui.css"/>
	<script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${path}/js/jqeasyui/jquery.easyui.min.js"></script>
	
  	<style type="text/css">
		html, body {
		width : 100%;
		height : 100%;
		padding : 0;
		margin : 0;
		overflow : hidden;
	</style>
	
	<script type="text/javascript">
		$(function(){
			/** 在div上面附加选项卡 */
			$("#tab").tabs({
				fit : true, // 填充父容器
				//tabWidth : 80, // 宽度
				//tabHeight: 30 // 高度
				onAdd : function(){ // 当添加了新的选项卡
					window.isAdd = true;
				},
				onSelect : function(title, index){ // 当选项卡选中时
					if (!window.isAdd){ // 防止再次请求
						/** 让当前选项卡中的面板刷新 */
						var tab = $("#tab").tabs("getSelected");
						tab.panel("refresh");
					}
					window.isAdd = false;
				}
			});
			/** 添加面板 */
			$("#tab").tabs("add", {
				title : "用户信息", // 标题
				content : "<iframe width='100%' height='100%' src='${path}/admin/self.jspx' frameborder='0'></iframe>", // 内容
				closable : false // 是否可以关闭
			});
		});
		
		/** 添加面板的函数 */
		var addTab = function(title, url){
			/** 判断面板是否存在 */
			var isExists = $("#tab").tabs("exists", title);
			/** isExists是true就代表已经存在，那就选中 */
			if (isExists){
				// select
				$("#tab").tabs("select", title);
			}else{
				$("#tab").tabs("add", {
					title : title, // 标题
					content : "<iframe width='100%' height='100%' src='${path}"+ url +"' frameborder='0'></iframe>", // 内容
					closable : true // 是否可以关闭
				});
			}
		};
		
		/** 显示修改密码的对话窗口 */
		var showUpdatePwdFn = function(){
			/** 显示找回密码的对话窗口 */
			$("#divDialog").dialog({
				title : "修改密码", // 标题
				width : 385,  // 宽度
				height : 215, // 高度
				collapsible : true, // 可伸缩
				minimizable : true, // 最小化
				maximizable : true,  // 最大化
				modal : true // 模态窗口
			});
			$("#iframe").prop("src", "${path}/admin/password.jspx").show();
		};
	</script>
</head>
<body>
	<div id="tab">
	</div>
	
	<!-- 把div作为弹出窗口 -->
	<div id="divDialog" style="overflow: hidden;">
		<iframe width="100%" height="100%" style="display:none;" frameborder="0" id="iframe"></iframe>
	</div>
	
</body>
</html>