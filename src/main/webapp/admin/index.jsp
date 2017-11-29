<%@page contentType="text/html" pageEncoding="utf-8" session="false"
%><%@include file="/WEB-INF/jspf/import.jspf"
%><%
/* User user = AdminSecurity.getCurrentUser(request);
AdminAuthFacade authFacade = EnvUtils.getEnv().getBean(AdminAuthFacade.class);
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}  */

/* pageContext.setAttribute("isAdmin", authFacade.isAdmin());
pageContext.setAttribute("_USER_", user); */

%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>首页 - 快点点餐系统</title>

<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="themes/css/flow.css" rel="stylesheet" type="text/css" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="js/speedup.js" type="text/javascript"></script>
<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>

<script src="js/dwz.core.js" type="text/javascript"></script>
<script src="js/dwz.util.date.js" type="text/javascript"></script>
<script src="js/dwz.validate.method.js" type="text/javascript"></script>
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="js/dwz.barDrag.js" type="text/javascript"></script>
<script src="js/dwz.drag.js" type="text/javascript"></script>
<script src="js/dwz.tree.js" type="text/javascript"></script>
<script src="js/dwz.accordion.js" type="text/javascript"></script>
<script src="js/dwz.ui.js" type="text/javascript"></script>
<script src="js/dwz.theme.js" type="text/javascript"></script>
<script src="js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="js/dwz.navTab.js" type="text/javascript"></script>
<script src="js/dwz.tab.js" type="text/javascript"></script>
<script src="js/dwz.resize.js" type="text/javascript"></script>
<script src="js/dwz.dialog.js" type="text/javascript"></script>
<script src="js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="js/dwz.cssTable.js" type="text/javascript"></script>
<script src="js/dwz.stable.js" type="text/javascript"></script>
<script src="js/dwz.taskBar.js" type="text/javascript"></script>
<script src="js/dwz.ajax.js" type="text/javascript"></script>
<script src="js/dwz.pagination.js" type="text/javascript"></script>
<script src="js/dwz.database.js" type="text/javascript"></script>
<script src="js/dwz.datepicker.js" type="text/javascript"></script>
<script src="js/dwz.effects.js" type="text/javascript"></script>
<script src="js/dwz.panel.js" type="text/javascript"></script>
<script src="js/dwz.checkbox.js" type="text/javascript"></script>
<script src="js/dwz.history.js" type="text/javascript"></script>
<script src="js/dwz.combox.js" type="text/javascript"></script>
<script src="js/dwz.print.js" type="text/javascript"></script>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<!--
选择月份的时间控件
-->
<link href="js/dateForYearMonth/simpleCanleder.css" rel="stylesheet" type="text/css" charset="UTF-8"/>
<script src="js/dateForYearMonth/simpleCanleder.js" type="text/javascript" charset="UTF-8"></script>

<script type="text/javascript" src="${ctx }/admin/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("dwz.frag.xml", {
		loginUrl:"login.jsp",
		statusCode:{ok:200, error:300, timeout:301},
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
		debug:false,
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"});
		}
	});
});

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
                <a class="logo" href="${ctx}/admin/index.jsp" style="width: 100px;">logo</a>
                <div style="float: left; color: white; font-size: 24px; padding-top: 15px;">快点点餐系统</div>
				<ul class="nav">
					<li><a href="javascript:;">welcome, ${_USER_.name}!</a></li>
					<li><a href="${ctx}/admin/updatePwd.do" target="navTab" rel="updatePwd">修改密码</a></li>
					<li><a href="${ctx}/admin/logout.do">logout</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected"></div></li>
					<li theme="green"><div></div></li>
					<li theme="purple"><div></div></li>
					<li theme="silver"><div></div></li>
					<li theme="azure"><div></div></li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>-</div></div>

				<div class="accordion" fillSpace="sidebar">
				    <div class="accordionHeader">
						<h2><span>Folder</span>菜单1</h2>
					</div>
                    <div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${ctx}/admin/serialgroup/list.do" target="navTab" rel="list-serialgroup">菜单列表</a></li>
						</ul>
					</div>
                    <%--<div class="accordionHeader">
						<h2><span>Folder</span>媒体资讯管理</h2>
					</div>
                    <div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${ctx}/admin/medianews/list.do" target="navTab" rel="list-medianews">媒体资讯列表</a></li>
							<li><a href="${ctx}/admin/ad/manufac/list.do" target="navTab" rel="list-ad-manufac">厂商广告列表</a></li>
							<li><a href="${ctx}/admin/ad/dealer/list.do" target="navTab" rel="list-ad-dealer">经销商广告列表</a></li>
							<li><a href="${ctx}/admin/ad/template/list.do" target="navTab" rel="list-ad-template">模板广告列表</a></li>
						</ul>
					</div>
                    <div class="accordionHeader">
						<h2><span>Folder</span>线索量管理</h2>
					</div>
                    <div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${ctx}/admin/ordercode/list.do" target="navTab" rel="list-ordercode">名单报名授权列表</a></li>
							<li><a href="${ctx}/admin/order/list.do" target="navTab" rel="list-order">名单线索量列表</a></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>经销商管理</h2>
					</div>
                    <div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${ctx}/admin/dealer/list.do" target="navTab" rel="list-dealer">经销商管理</a></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>数据统计报表</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${ctx}/admin/datastat/dayOrderStat" target="navTab" rel="stat-order">线索量统计</a></li>
							<li><a href="${ctx}/admin/datastat/dealerActive" target="navTab" rel="dealer-active" title="经销商活跃度">经销商活跃度统计</a></li>
						</ul>
					</div> --%>
					<div class="accordionHeader">
						<h2><span>Folder</span>权限设置</h2>
					</div>
                    <div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a href="${ctx}/admin/function/list.do" target="navTab" rel="list-function">功能列表</a></li>
							<li><a href="${ctx}/admin/role/list.do" target="navTab" rel="list-role">角色列表</a></li>
							<li><a href="${ctx}/admin/rolefunction/list.do" target="navTab" rel="list-rolefunction">角色功能</a></li>
							<%-- <li><a href="${ctx}/admin/userrole/list.do" target="navTab" rel="list-userrole">访问控制</a></li>
							<li><a href="${ctx}/admin/user/list.do" target="navTab" rel="list-user">用户列表</a></li>
							<li><a href="${ctx}/admin/rolecity/list.do" target="navTab" rel="list-rolecity">角色城市列表</a></li>
							<li><a href="${ctx}/admin/userlog/list.do?orderField=id&orderDirection=desc" 
                                   target="navTab" rel="list-userlog">操作日志</a></li>
							<li><a href="${ctx}/admin/userlogdetail/list.do?orderField=id&orderDirection=desc" 
                                   target="navTab" rel="list-userlogdetail">日志数据</a></li>
							<li><a href="${ctx}/admin/gelitool/list.do" target="navTab" rel="list-gelitool">代码定制</a></li> --%>
						</ul>
					</div>
                    
					<!-- <div class="accordionHeader">
						<h2><span>Folder</span>基础数据</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
						</ul>
					</div> -->
				</div>
			</div>
		</div>

		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<div class="tabsRight">right</div>
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">more</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
                            这里缺应用的个人主页内容，请补充。
                        </div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
