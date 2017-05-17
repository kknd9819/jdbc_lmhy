<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>权限管理添加 -  生源闪购（shengyuan.cn）闪亮生活，购你喜欢！</title>
<meta name="author" content="生源闪购（shengyuan.cn）" />
<meta name="copyright" 生源闪购（shengyuan.cn） />
<link href="/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="/resources/common/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/common/jquery/jquery.js"></script>
<script type="text/javascript" src="/resources/common/jquery/jquery.tools.js"></script>
<script type="text/javascript" src="/resources/common/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript" src="/resources/js/input.js"></script>
<script type="text/javascript" src="/resources/common/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<style type="text/css">
.ztree li {
	background-color:#fff;
}
</style>
<script type="text/javascript">
	var zNodes=${menuTree};
	var setting = {
		treeId:'resTree',
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onCheck: onCheck
		}
	};
	
	function onCheck(e, treeId, treeNode) {
		var nodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
		if(nodes.length == 0){
			$('#vName').hide();
			$("#parentId").val("");
		}else{
			$("#parentId").val(nodes[0].id);
			$('#vName').show();
		}
	}
		
	$().ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var $inputForm = $("#inputForm");
		var $parentId = $("#inputForm#parentId");
		[@flash_message /]
		$.validator.addMethod("validValue", function(value, element) {
			if($('#vName').is(':hidden')){
				return true;
			}else{
				return !this.optional(element) || false;
			}
		}, $.validator.format("必填"));
		// 表单验证
		$inputForm.validate({
			rules: {
				name: {
					required: true
				},
				vName: {
					validValue: true,
					remote: {
						url: "/system/menuValue/checkName.jhtml",
						cache: false
					}
				}
			},
			messages: {
				vName: {
					remote: "已存在"
				}
			}
		});
	});
</script>
</head>
<body>
	<div class="path">
		<a href="/admin/common/index.jhtml">首页</a> &raquo; 权限管理添加
	</div>
	<form id="inputForm" action="save.jhtml" method="post">
		<div id="sp" style="display:none;"><ul id="ztrees" class="ztree"></ul></div>
		<table class="input">
			<tr>
				<th>
					<span class="requiredField">*</span>名称:
				</th>
				<td>
					<input type="text" id="name" name="name" class="text" maxlength="200" />
				</td>
			</tr>
			<tr>
				<th>
					上级分类:
				</th>
				<td>
					<input type="hidden" name="parentId" id="parentId" value="" />
					<ul id="treeDemo" class="ztree"></ul>
				</td>
			</tr>
			<tr class="hidden brands" id="vName">
				<th>
					<span class="requiredField">*</span>菜单值:
				</th>
				<td>
					<input type="text" name="vName" class="text" maxlength="100" />
				</td>
			</tr>
			<tr>
				<th>
					排序:
				</th>
				<td>
					<input type="text" name="orders" class="text" maxlength="9" />
				</td>
			</tr>
			<tr>
				<th>
					&nbsp;
				</th>
				<td>
					<input type="submit" class="button" value="提交" />
					<input type="button" class="button" value="返回" onclick="history.back()" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>