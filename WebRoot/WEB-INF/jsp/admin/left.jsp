<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
    <link rel="stylesheet" type="text/css" href="${path}/css/common/global.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/js/dtree/dtree.css"/>
    <script type="text/javascript" src="${path}/js/dtree/dtree.js"></script>
    <script type="text/javascript" src="${path}/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${path}/js/jquery-migrate-1.2.1.min.js"></script>
	
	<style type="text/css">
		html,body{ height:100%;}
		a{color:#6a6f71; text-decoration:none;}
	</style>	
	<script type="text/javascript">
		
		$(function(){
			/** 为图标按点击次数绑定事件 */
			$("#ShowNav").toggle(
				function(){
					/** 获取父窗口的frameset (id为fstMain) */
					var fst = $("#fstMain", parent.document);
					$("#shumenu").hide();
					fst.attr("cols", "14,*");
					/** 更改图标 */
					$(this).attr("src", "${path}/images/system/left_xs.gif");
					$("#nav_title").attr("title", "显示菜单栏");
				},
				function(){
					/** 获取父窗口的frameset (id为fstMain) */
					var fst = $("#fstMain", parent.document);
					$("#shumenu").show();
					fst.attr("cols", "148,*");
					/** 更改图标 */
					$(this).attr("src", "${path}/images/system/left_yc.gif");
					$("#nav_title").attr("title", "隐藏菜单栏");
				}
			);
		});
	</script>
	
  </head>
 <body class="leftmenubody">
 	<div class="topdivyc">
    	<a href="javascript:void(0);" class="a_link" title="隐藏菜单栏" id="nav_title">
    	<img src="${path}/images/system/left_yc.gif" id="ShowNav"/>
    	<!--隐藏时反显示的图片<img src="images/left_xs.gif" />--></a>
    </div>
    <div class="bodytextmenu" id="shumenu">
	    <div class="shumenu" >
	    	<script type="text/javascript">
				/** 创建树对象 */
				var d = new dTree("d", "${path}/js/dtree/");
				/** 添加根节点
				 * id : 自己节点的id
				 * pid: 父节点的id
				 * name : 树节点显示的名称
				 */
				d.add(0, -1, "人事管理系统");
				
				/** 异步请求根据当前登录用户生成菜单树 */
				$.ajax({
					url : "${path}/admin/loadMenuTreeAjax.jspx",
					type : "post",
					dataType : "json",
					async : false, // 异步请求中的同步
					success : function(data){
						// data: [{id : '', pid: '', name : '', url : ''},{}]
						$.each(data, function(){
							var url = "javascript:parent.mainframe.addTab('"+ this.name +"', '"+ this.url +"')";
							url = this.pid == "0" ? "" : url;
							d.add(this.id, this.pid, this.name, url, this.name);
						});
					},
					error : function(){
						alert("数据加载失败！");
					}
				});
				
				document.write(d);
				
				/** 展开所有的树节点 */
				d.openAll();
		
			</script>
	    </div>
    </div>
  </body>
</html>
