$(function() {
	initLeftMenu();
	tabClose();
	tabCloseEven();
	
	$('#tabs').tabs('add',{
		title:'首页',
		content:createFrame('index.jhtml'),
		icon:'tu1112'
	});
	
	function initLeftMenu(){
		$("#nav").accordion({animate:false});
		
		$('.easyui-accordion li').click(function(){
			var _this = $(this).find("a");
			var tabTitle = _this.text();

			var url = _this.attr("rel");
			var menuid = _this.attr("ref");
			var icon = _this.attr("icon"); //获取图标
			addTab(tabTitle,url,icon);
			$('.easyui-accordion li div').removeClass("selected");
			_this.parent().addClass("selected");
		}).hover(function(){
			$(this).parent().addClass("hover");
		},function(){
			$(this).parent().removeClass("hover");
		});
	}
	
	function addTab(subtitle,url,icon){
		if(!$('#tabs').tabs('exists',subtitle)){
			$('#tabs').tabs('add',{
				title:subtitle,
				content:createFrame(url),
				closable:true,
				icon: null
			});
		}else{
			$('#tabs').tabs('select',subtitle);
			tabUpdate();
		}
		tabClose();
	}
	
	function createFrame(url)
	{
		var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return s;
	}
	
	function tabClose(){
		/*双击关闭TAB选项卡*/
		$(".tabs-inner").dblclick(function(){
			var subtitle = $(this).children(".tabs-closable").text();
			$('#tabs').tabs('close',subtitle);
		})
		
		/*为选项卡绑定右键*/
        $(".tabs-inner").bind('contextmenu', function (e) {
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });

            var subtitle = $(this).children(".tabs-closable").text();

            $('#mm').data("currtab", subtitle);
            $('#tabs').tabs('select', subtitle);
            return false;
        });
	}

	function tabUpdate(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	}
	  
	$('#userset').bind('click',function(){
		if(!$('#tabs').tabs('exists','账号设置')){
			$('#tabs').tabs('add',{
				title:'账号设置',
				content:createFrame('/system/profile/edit.jhtml'),
				closable:true,
			});
		}else{
			$('#tabs').tabs('select','账号设置');
			tabUpdate();
		}
		
	});
	
	//绑定右键菜单事件
    function tabCloseEven() {
        //刷新
        $('#mm-tabupdate').click(function () {
            var currTab = $('#tabs').tabs('getSelected');
            var url = $(currTab.panel('options').content).attr('src');
            var id = $(currTab.panel('options').content).attr('id'); ; //获取id

            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url, id)
                }
            })
        })
        //关闭
        $('#mm-tabclose').click(function () {
            var currtab_title = $('#mm').data("currtab");
            $('#tabs').tabs('close', currtab_title);
        })

        //全部关闭
        $('#mm-tabAllclose').click(function () {
            //所有所有tab对象
            var allTabs = $('#tabs').tabs('tabs');
            for (var i = (allTabs.length - 1); i >= 0; i--) {
                var tab = allTabs[i];
                var opt = tab.panel('options');
                //获取标题
                var title = opt.title;
                //是否可关闭 ture:会显示一个关闭按钮，点击该按钮将关闭选项卡
                var closable = opt.closable;
                if (closable) {
                    //alert('title' + title + '  curTabTitle:' + curTabTitle);
                    $('#tabs').tabs('close', title);
                }

            }
        })
    }
	 
});

