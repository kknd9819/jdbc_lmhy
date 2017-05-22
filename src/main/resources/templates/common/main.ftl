<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心 -  云生源（shengyuan.cn）你的消费很值钱！</title>
<meta name="author" content="云生源后台管理系统" />
<meta name="copyright" content="云生源后台管理系统" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" ></meta>

<link rel="stylesheet" href="/resources/common/jqueryeasyui/themes/default/easyui.css">
<link rel="stylesheet" href="/resources/common/jqueryeasyui/themes/icon.css">
<link rel="stylesheet" href="/resources/css/default.css">
<link rel="stylesheet" href="/resources/css/index.css">

<script type="text/javascript" src="/resources/common/jquery/jquery.js"></script>
<script type="text/javascript" src="/resources/common/jqueryeasyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/common/jqueryeasyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/resources/js/easyuicommon.js"></script>
 

</head>
 <body class="easyui-layout">
	<div data-options="region:'north',border:false" style="overflow: hidden; height: 30px;
        background: url(../../resources/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
	 	<span style="float:right; padding-right:20px;padding-top: 3px;" class="head">欢迎 [@shiro.principal/] <a href="javascript:;" style="color:#fff" id="userset">账号设置</a> <a style="color:#fff" href="/system/logout/logout.jhtml" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; "><img src="../../resources/images/header_logo.png" width="170" height="30" align="absmiddle" /></span>
	</div>
	<div data-options="region:'west',split:true,title:'导航菜单'" style="width:180px;padding:0px;">
	 	<div id="nav" class="easyui-accordion" fit="true" border="false">
	 		<#list ["admin:merchant"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="商家" data-options="iconCls:'tu0408'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
					<@shiro.hasPermission name="admin:merchant">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/admin/merchant/list.jhtml" icon="tu0001">商家管理</a>
						</li>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:category">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/admin/category/list.jhtml" icon="tu0001">商家分类</a>
						</li>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:brand">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/merchant/brand/list.jhtml" icon="tu0001">品牌管理</a>
						</li>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:tag">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/merchant/tag/tagList.jhtml" icon="tu0001">标签管理</a>
						</li>
					</@shiro.hasPermission>
				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>
				 	
	 		<#list ["admin:member","admin:message","admin:auth"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="会员" data-options="iconCls:'tu0625'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
					<@shiro.hasPermission name="admin:member">
                        <li data-options="iconCls:'tu0001'">
                            <a href="javascript:;" rel="/member/member/list.jhtml" icon="tu0001">会员管理</a>
                        </li>
					</@shiro.hasPermission>

					<@shiro.hasPermission name="admin:message">
                        <li data-options="iconCls:'tu0001'">
                            <a href="javascript:;" rel="/member/message/list.jhtml" icon="tu0001">消息列表</a>
                        </li>
					</@shiro.hasPermission>

				    <@shiro.hasPermission name="admin:auth">
					 	<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/member/auth/list.jhtml" icon="tu0001">实名制信息</a>
						</li>
				 	</@shiro.hasPermission>
				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>
			
			<#list ["admin:salary","admin:recharge","admin:drawCash","admin:withdrawal","admin:finance_settle","admin:finance_contract"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="财务" data-options="iconCls:'tu0306'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
				<@shiro.hasPermission name="admin:salary">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/finance/salary/list.jhtml" icon="tu0001">薪资计划</a>
					</li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:recharge">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/finance/recharge/list.jhtml" icon="tu0001">充值订单</a>
					</li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:drawCash">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/finance/withdrawal/drawCash.jhtml" icon="tu0001">提现处理</a>
					</li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:withdrawal">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/finance/withdrawal/list.jhtml" icon="tu0001">提款申请</a>
					</li>
				</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:finance_settle">
					<#if Session.adminMid=='0000'||Session.adminMid==''>
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/finance/settlement_manager/list.jhtml" icon="tu0001">商家结算管理</a>
						</li>
					</#if>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:finance_contract">
					<#if Session.adminMid=='0000'||Session.adminMid==''>
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/finance/settlement_contract_manager/list.jhtml" icon="tu0001">商家合同管理</a>
						</li>
					</#if>
					</@shiro.hasPermission>
				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>

			<#list ["admin:article", "admin:articleCategory", "admin:adPosition", "admin:ad"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="内容" data-options="iconCls:'tu1013'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
				<@shiro.hasPermission name="admin:article">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/content/article/list.jhtml" icon="tu0001">文章管理</a>
					</li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:articleCategory">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/content/article_category/list.jhtml" icon="tu0001">文章分类</a>
					</li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:adPosition">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/content/ad_position/list.jhtml" icon="tu0001">广告位</a>
					</li>
				</@shiro.hasPermission>
				<@shiro.hasPermission name="admin:ad">
					<li data-options="iconCls:'tu0001'">
						<a href="javascript:;" rel="/content/ad/list.jhtml" icon="tu0001">广告管理</a>
					</li>
				</@shiro.hasPermission>
				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>
				
			<#list ["fund:task","fund:balance", "fund:finance"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="资金" data-options="iconCls:'tu1814'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
					<@shiro.hasPermission name="fund:task">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/fund/task/list.jhtml" icon="tu0001">定时任务管理</a>
						</li>
					</@shiro.hasPermission>
					
					<@shiro.hasPermission name="fund:balance">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/fund/balance/set.jhtml" icon="tu0001">消费余额利率设置</a>
						</li>
					</@shiro.hasPermission>
					
					<@shiro.hasPermission name="fund:finance">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/fund/finance/set.jhtml" icon="tu0001">理财余额利率设置</a>
						</li>
					</@shiro.hasPermission>

				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>
			
			
			<#list ["admin:paymentException","admin:paymentTrade","admin:paymentDeal","admin:refundTrade"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="支付" data-options="iconCls:'tu0916'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
					<@shiro.hasPermission name="admin:paymentTrade">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/admin/payment/paymentTrade/list.jhtml" icon="tu0001">支付流水管理</a>
						</li>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:paymentException">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/admin/payment/paymentTrade/failList.jhtml" icon="tu0001">支付异常处理</a>
						</li>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:paymentDeal">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/admin/payment/paymentTrade/dealList.jhtml" icon="tu0001">支付交易查询</a>
						</li>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="admin:refundTrade">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/admin/payment/paymentTrade/refundTradeList.jhtml" icon="tu0001">退款流水管理</a>
						</li>
					</@shiro.hasPermission>
				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>

			<#list ["system:admin", "system:role", "system:authority"] as permission>
			<@shiro.hasPermission name = permission>
			<div title="系统" data-options="iconCls:'tu0206'" style="width: 153px; height: 193px;">
				<ul class="easyui-tree tree">
					<@shiro.hasPermission name="system:admin">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/system/admin/list.jhtml" icon="tu0001">管理员</a>
						</li>
					</@shiro.hasPermission>
					
					<@shiro.hasPermission name="system:role">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/system/role/list.jhtml" icon="tu0001">角色管理</a>
						</li>
					</@shiro.hasPermission>
					
					<@shiro.hasPermission name="system:authority">
						<li data-options="iconCls:'tu0001'">
							<a href="javascript:;" rel="/system/menu/list.jhtml" icon="tu0001">权限管理</a>
						</li>
					</@shiro.hasPermission>

				</ul>
			</div>
			<#break />
			</@shiro.hasPermission>
			</#list>
			
			   
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height: 29px; background: #D2E0F2;"> 
		<div style="padding: 0px; margin-left: 50%;">By ShengYuanKeJi @2017生源科技 </div>
	</div>
	
	<div data-options="region:'center',title:''">
		<div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			 
		</div>
	</div>
	
	<div id="mm" class="easyui-menu" style="width: 150px;">
        <div id="mm-tabupdate">刷新</div>
        <div class="menu-sep">
        </div>
        <div id="mm-tabclose">关闭</div>
        <div id="mm-tabAllclose">全部关闭</div>
    </div>
</body>
</html>