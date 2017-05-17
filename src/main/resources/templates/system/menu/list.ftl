<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>权限管理列表 -  生源闪购（shengyuan.cn）闪亮生活，购你喜欢！</title>
<meta name="author" content="生源闪购（shengyuan.cn）" />
<meta name="copyright" 生源闪购（shengyuan.cn） />
<link type="text/css" href="/resources/css/common.css" rel="stylesheet"/>
<script type="text/javascript" src="/resources/common/jquery/jquery.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/list.js"></script>
<script type="text/javascript">
	$().ready(function() {
		[@flash_message /]
	});
	
	function deleteProductCategory(pcid,valueId){	
		if(pcid != ""){					
			$.dialog({
				type: "warn",
				content: "您确定要删除吗？",
				onOk: function() {
					$.ajax({
						url: "delete.jhtml",
						type: "POST",
						data: {id: pcid,vid:valueId},
						dataType: "json",
						cache: false,
						success: function(message) {
							$.message(message);
							if (message.type == "success") {
								$("#tr_"+pcid).remove();;
							}
						}
					});
				}
			});
		}
	}
	
	function removeTr(pcid,treePathStr){
	    if(treePathStr==","){
	       $.each(
				$("table:first tbody tr.subCss"), 
				function(i ,tr){
				//清空子类
				if($(this).attr("class")=='subCss' && ($(this).attr("id")!=('tr_'+pcid))){
					$(this).closest("tr").remove();
				}
			});
	    }
	}
	
	function showProductCategory(pcid,grade,treePath) {
		if(pcid != ""){
			$.ajax({
				url: "zllist.jhtml",
			  	data: "POST",
			  	data: {id: pcid},
			  	dataType: "json",
			  	success: function(msg){
			  	
			  		if (msg.message.type == "success") {
			  		
			  		    if(msg.menus.length>0){
			  		       removeTr(pcid,treePath);		
			  		    }			  			
			  					  			  		
			  			$.each(
							$("table:first tbody tr"), 
							function(i ,tr){
							    var grades = grade+1;
							   
								if($(this).attr("id") == ("tr_" + pcid)){
								   
									var curRow = $("table:first tbody tr:eq("+i+")") ;
										
									for(var j=0;j<msg.menus.length;j++){
									
									   //处理排序显示null值的问题
									   if(msg.menus[j].orders==null){
									      msg.menus[j].orders = "";
									   }//end
									  
									   //判断当前产生的行是否已经存在，存在就删除
									   if(jQuery("#tr_"+msg.menus[j].id)){
									      jQuery("#tr_"+msg.menus[j].id).remove();
									   }//end
									  
									   if(msg.menus[j].grade==grades){
									  
										str='<tr id=tr_'+msg.menus[j].id+' class=subCss>' +
											'<td>'+
												'<span style=margin-left:'+(msg.menus[j].grade * 20)+'px;[#if '+msg.menus[j].grade+' == 0] color: #000000;[/#if]>'+ msg.menus[j].name +'</span>'+
											'</td>' +
											'<td>' + msg.menus[j].vName + '</td>' +
											'<td>' + msg.menus[j].orders + '</td>' +
											'<td>'+
												'<a href=edit.jhtml?id='+msg.menus[j].id+'>[编辑]</a>' +
												'<a href=javascript:deleteProductCategory('+msg.menus[j].id+','+msg.menus[j].menuValue+'); class=delete>[删除]</a>'+
												'<a href=javascript:showProductCategory('+msg.menus[j].id+','+msg.menus[j].grade+',\''+msg.menus[j].treePath+'\');>[子类]</a>'+
											'</td>' +
										 '</tr>';
										 curRow.after(str);
										 
										 }//end if
										 
									}//end for
									
								}//end if
								
							});//end each	
									  			
					}else{
						alert("数据加载失败！");
					}
			  	}					  
			});
		}		
	}
</script>
</head>
<body>
	<div class="path">
		<a href="/admin/common/index.jhtml">首页</a> &raquo; 权限管理列表
	</div>
	<form id="listForm" action="list.jhtml" method="get">
	<div class="bar">
		[@shiro.hasPermission name="authority:add"]
			<a href="add.jhtml" class="iconButton">
				<span class="addIcon">&nbsp;</span>添加
			</a>
		[/@shiro.hasPermission]
		<a href="javascript:;" id="refreshButton" class="iconButton">
			<span class="refreshIcon">&nbsp;</span>刷新
		</a>
	</div>
	<table id="listTable" class="list">
		<tr>
			<th><span>名称</span></th>
			<th><span>菜单值</span></th>
			<th><span>排序</span></th>
			<th><span>操作</span></th>
		</tr>
		<tbody>
		[#list page.content as menu]
			<tr id="tr_${menu.id}" class="abc" >
				<td>
					<span style="margin-left: ${menu.grade * 20}px;color: #000000;">
						${menu.name}
					</span>
				</td>
				<td>
					--
				</td>
				<td>
					${menu.orders}
				</td>
				<td>
					[@shiro.hasPermission name="authority:edit"]
						<a href="edit.jhtml?id=${menu.id}">[编辑]</a>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="authority:delete"]
						<a href="javascript:deleteProductCategory('${menu.id}','${menu.menuValue.id}');" class="delete">[删除]</a>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="authority:category"]
						<a href="javascript:showProductCategory('${menu.id}','${menu.grade}','${menu.treePath}');" >[子类]</a>
					[/@shiro.hasPermission]
				</td>
			</tr>
		[/#list]
		</tbody>
	</table>
	[@pagination pageNumber = page.currentPage totalPages = page.totalPage]
		[#include "/common/pagination.ftl"]
	[/@pagination]	
	</form>
</body>
</html>