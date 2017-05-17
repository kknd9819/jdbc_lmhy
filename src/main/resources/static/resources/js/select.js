$().ready(function() {	
	var orderPromotionTypeVal = $('select[name="orderPromotionType"]').val();
	showTable(orderPromotionTypeVal);
	function showTable(opType){
		 if(opType == "0"){
		 	subTableShow();		 	
		 	giveGiftTableHide();
		 	giveCouponTableHide();
		 	giveQuantityGiftTableHide();
		 	discountTableHide();
		 }else if(opType == "1"){
		 	subTableHide();		 	
		  	giveGiftTableShow();
		 	giveCouponTableHide();
		 	giveQuantityGiftTableHide();
		 	discountTableHide();
		 }else if(opType == "2"){
		 	subTableHide();		 	
		 	giveGiftTableHide();
		 	giveCouponTableShow();
		 	giveQuantityGiftTableHide();
		 	discountTableHide();
		 }else if(opType == "3"){
			 subTableHide();			 
			 giveGiftTableHide();
			 giveCouponTableHide();
			 giveQuantityGiftTableShow();
			 discountTableHide();
		 }else if(opType == "4"){
			 subTableHide();			 
			 giveGiftTableHide();
			 giveCouponTableHide();
			 giveQuantityGiftTableHide();
			 discountTableShow();
		 }
	 }
	
	function discountTableHide(){
    	$('#discountTable').hide();
    	$('#discountTbody').empty();
    	$('#discountIndex').val(0);
    }
	
	function discountTableShow(){
    	$('#discountTable').show();
    }
	 
	function subTableHide(){
    	$('#subTable').hide();
    	$('#subTbody').empty();
    	$('#subIndex').val(0);
    }
    function subTableShow(){
    	$('#subTable').show();
    } 
    
    function giveGiftTableHide(){
    	$('#giveGiftTable').hide();
    	$('#giveGiftTbody').empty();
    	$('#giveGiftIndex').val(0);
    }
    function giveGiftTableShow(){
    	$('#giveGiftTable').show();
    } 
	 
	function giveCouponTableHide(){
    	$('#giveCouponTable').hide();
    	$('#giveCouponTbody').empty();
    	$('#giveCouponIndex').val(0);
    }
    function giveCouponTableShow(){
    	$('#giveCouponTable').show();
    } 
    
    function giveQuantityGiftTableHide(){
    	$('#giveQuantityGiftTable').hide();
    	$('#giveQuantityGiftTbody').empty();
    	$('#giveQuantityGiftIndex').val(0);
    }
    function giveQuantityGiftTableShow(){
    	$('#giveQuantityGiftTable').show();
    } 
	 
	 
	$("select[name='orderPromotionType']").bind("click",function(){
 		var $val = $(this).val();
 		showTable($val);
 	}); 
	 
	$('#addCondition').bind('click',function(){
		var $orderPromotionType = $('select[name="orderPromotionType"]').val();		
		if($orderPromotionType == "0"){
			var subIndex = parseInt($('#subIndex').val());
			var htmlStr = '<tr>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+subIndex+'].conditionName" class="text" maxlength="50" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+subIndex+'].conditionAmount" class="text" maxlength="5" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+subIndex+'].subAmount" class="text" maxlength="5" /></td>';
			htmlStr += '</tr>';
			$('#subIndex').val(subIndex+1);
			$('#subTbody').append(htmlStr);
		}else if($orderPromotionType == "1"){
			var giveGiftIndex = parseInt($('#giveGiftIndex').val());
			 
			var htmlStr = '<tr>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveGiftIndex+'].conditionName" class="text" maxlength="50" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveGiftIndex+'].conditionAmount" class="text" maxlength="5" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveGiftIndex+'].giveIds" class="text giveIds" maxlength="100" readonly/>';
			htmlStr += '<input type="button" class="button btnGiveGift"  value="选择赠品1" />';
			htmlStr += '</td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveGiftIndex+'].giveCount" value="1" class="text giveCount" maxlength="2" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveGiftIndex+'].giveName" class="text giveName" maxlength="255" readonly/></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveGiftIndex+'].giveCost" class="text giveCost" maxlength="16" /></td>';
			htmlStr += '</tr>';
			$('#giveGiftIndex').val(giveGiftIndex+1);
			$('#giveGiftTbody').append(htmlStr);
		}else if($orderPromotionType == "2"){
			var giveCouponIndex  = parseInt($('#giveCouponIndex').val());
			var htmlStr = '<tr>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveCouponIndex+'].conditionName" class="text" maxlength="50" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveCouponIndex+'].conditionAmount" class="text" maxlength="5" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveCouponIndex+'].giveIds" class="text giveIds" maxlength="100" readonly/>';
			htmlStr += '<input type="button" class="button btnGiveCoupon"  value="选择优惠券" />';
			htmlStr += '</td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveCouponIndex+'].giveCount" value="1" class="text giveCount" maxlength="2" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveCouponIndex+'].giveName" class="text giveName" maxlength="255" readonly/></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveCouponIndex+'].giveCost" class="text giveCost" maxlength="16" /></td>';
			htmlStr += '</tr>';
			 
			$('#giveCouponIndex').val(giveCouponIndex+1);
			$('#giveCouponTbody').append(htmlStr);
		}else if($orderPromotionType == "3"){
			var giveQuantityGiftIndex = parseInt($('#giveQuantityGiftIndex').val());
			 
			var htmlStr = '<tr>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveQuantityGiftIndex+'].conditionName" class="text" maxlength="50" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveQuantityGiftIndex+'].conditionQuantity" class="text" maxlength="5" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveQuantityGiftIndex+'].giveIds" class="text giveIds" maxlength="100" readonly/>';
			htmlStr += '<input type="button" class="button btnGiveGift"  value="选择赠品" />';
			htmlStr += '</td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveQuantityGiftIndex+'].giveCount" value="1" class="text giveCount" maxlength="2" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveQuantityGiftIndex+'].giveName" class="text giveName" maxlength="255" readonly/></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+giveQuantityGiftIndex+'].giveCost" class="text giveCost" maxlength="16" /></td>';
			htmlStr += '</tr>';
			$('#giveQuantityGiftIndex').val(giveQuantityGiftIndex+1);
			$('#giveQuantityGiftTbody').append(htmlStr);
		}else if($orderPromotionType == "4"){
			var discountIndex = parseInt($('#discountIndex').val());
			var htmlStr = '<tr>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+discountIndex+'].conditionName" class="text" maxlength="50" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+discountIndex+'].conditionAmount" class="text" maxlength="5" /></td>';
			htmlStr += '<td><input type="text"   name="listOrderPromotionCondition['+discountIndex+'].subAmount" class="text" maxlength="5" /></td>';
			htmlStr += '</tr>';
			$('#discountIndex').val(discountIndex+1);
			$('#discountTbody').append(htmlStr);
		}
	});
	
	$('#deleteLastCondition').bind('click',function(){
		var $orderPromotionType = $('select[name="orderPromotionType"]').val();
		if($orderPromotionType == "0"){
			var subIndex = parseInt($('#subIndex').val());
			if(subIndex > 0){
				$('#subIndex').val(subIndex-1);
			}
			$('#subTbody').find("tr").last().remove();
		}else if($orderPromotionType == "1"){
			var giveGiftIndex = parseInt($('#giveGiftIndex').val());
			if(giveGiftIndex > 0){
				$('#giveGiftIndex').val(giveGiftIndex-1);
			}
			$('#giveGiftTbody').find("tr").last().remove();
		}else if($orderPromotionType == "2"){
			var giveCouponIndex  = parseInt($('#giveCouponIndex').val());
			if(giveCouponIndex > 0){
				$('#giveCouponIndex').val(giveCouponIndex-1);
			}
			$('#giveCouponTbody').find("tr").last().remove();
		}else if($orderPromotionType == "3"){
			var giveQuantityGiftIndex  = parseInt($('#giveQuantityGiftIndex').val());
			if(giveQuantityGiftIndex > 0){
				$('#giveQuantityGiftIndex').val(giveQuantityGiftIndex-1);
			}
			$('#giveQuantityGiftTbody').find("tr").last().remove();
		}else if($orderPromotionType == "4"){
			var discountIndex = parseInt($('#discountIndex').val());
			if(discountIndex > 0){
				$('#discountIndex').val(discountIndex-1);
			}
			$('#discountTbody').find("tr").last().remove();
		}
	});
	
	$('.btnGiveCoupon').live('click',function(){
		var $this = $(this);
		var $giveIds = $this.closest("tr").find(".giveIds");
		var $giveName = $this.closest("tr").find(".giveName");
		var $giveCost = $this.closest("tr").find(".giveCost");
		$.ajax({
		      url: "selectCoupons.jhtml",
		      type: "GET",
		      dataType: "json",
		      cache: false,
		      success: function(data) {
			      if (data.message.type == "success") {
					  $.dialog({
							title: "选择优惠券",
							content: '<table id="couponsTable" class="list">'+
							'<tr class="title">'+
							'<th style="width:80px"></th>'+
							'<th style="width:200px">优惠券名称</th>'+
							'<th style="width:80px">优惠券价值</th>'+
							'<th style="width:80px">优惠券到期时间</th>'+
							'</tr>'+
							'</table>',
							width: 900,
							modal: true,
							ok: "确认",
							cancel: "取消",
							onShow: function() {
								var trHtml="";
								var giveIds = $giveIds.val();
								var idsArr = new Array();
								if(giveIds != ""){
									idsArr = giveIds.split(",");
								}
								$.each(data.coupons, function(i, item) {
									var checkflag = false;
									
									for(var i=0;i<idsArr.length;i++){
										if(idsArr[i] == item.id){
											checkflag = true;
										}
									} 
									
									trHtml += "<tr><input type='hidden' name='id' value='"+item.id+"'/><input type='hidden' name='name' value='"+item.name+"'/><input type='hidden' name='priceExpression' value='"+item.priceExpression+"'/> <input type='hidden' name='couponType' value='"+item.couponType+"'/>  ";
									
									if(checkflag){
										trHtml += "<td><input type='checkbox' name='ids' value='"+item.id+"' checked/></td>";
									}else{
										trHtml += "<td><input type='checkbox' name='ids' value='"+item.id+"' /></td>";
									}
									trHtml += "<td>"+item.name+"</td>";
									trHtml += "<td>"+item.priceExpression+"</td>";
									trHtml += "<td>"+item.endDate+"</td></tr>";
								});
				
					 			$("#couponsTable").append(trHtml);
							},
							onOk: function() {
								var giveIds="";
								var giveName="";
								var giveCost=0;
								
								var $enabledIds = $("#couponsTable input[name='ids']:checked");
								$enabledIds.each(function(){
									var $this = $(this);
									var $id = $this.closest("tr").find("input[name='id']").val();
									var $name = $this.closest("tr").find("input[name='name']").val();
									var $price = $this.closest("tr").find("input[name='priceExpression']").val();
									giveIds += $id+",";
									giveName += $name+",";
									giveCost += parseFloat($price);
								});
								
								$giveIds.val(giveIds);
								$giveName.val(giveName);
								$giveCost.val(giveCost);
								 
							}
					  });
			 		}
				}
	   		});
	});
	
	$('.btnGiveGift').live('click',function(){
		var id = $("#id").val();
		var $this = $(this);
		var $giveIds = $this.closest("tr").find(".giveIds");
		var $giveName = $this.closest("tr").find(".giveName");
		var $giveCost = $this.closest("tr").find(".giveCost");
		$.ajax({
		      url: "selectGifts.jhtml",
		      type: "GET",
		      data: {id:id},
		      dataType: "json",
		      cache: false,
		      success: function(data) {
			      if (data.message.type == "success") {
					  $.dialog({
							title: "选择赠品",
							content: '<table id="giftsTable" class="list">'+
							'<tr class="title">'+
							'<th style="width:20px"></th>'+
							'<th style="width:40px">赠品名称</th>'+
							'<th style="width:20px">赠品数量</th>'+
							'<th style="width:40px">赠品价值</th>'+
							'</tr>'+
							'</table>',
							width: 900,
							modal: true,
							ok: "确认",
							cancel: "取消",
							onShow: function() {
								 
								var trHtml="";
								var giveIds = $giveIds.val();
								var idsArr = new Array();
								if(giveIds != ""){
									idsArr = giveIds.split(",");
								}
								if(data.gifts.length > 0){
									$.each(data.gifts, function(i, item) {
										var checkflag = false;
										
										for(var i=0;i<idsArr.length;i++){
											if(idsArr[i] == item.id){
												checkflag = true;
											}
										} 
										trHtml += "<tr><input type='hidden' name='id' value='"+item.id+"'/><input type='hidden' name='name' value='"+item.giftName+"'/><input type='hidden' name='price' value='"+item.giftPrice+"'/>";
										if(checkflag){
											trHtml += "<td><input type='checkbox' name='ids' value='"+item.id+"' checked/></td>";
										}else{
											trHtml += "<td><input type='checkbox' name='ids' value='"+item.id+"' /></td>";
										}
										trHtml += "<td>"+item.giftName+"</td>";
										trHtml += "<td>"+item.giftQuanity+"</td>";
										trHtml += "<td>"+currency(item.giftPrice,true,false)+"</td></tr>";
									});
								}else{
									trHtml += "<tr><td colspan='4'><font color='red'>没有赠品信息，速速去配置</font></td></tr>";
								}
				
					 			$("#giftsTable").append(trHtml);
							},
							onOk: function() {
								var giveIds="";
								var giveName="";
								var giveCost=0;
								
								var $enabledIds = $("#giftsTable input[name='ids']:checked");
								$enabledIds.each(function(){
									var $this = $(this);
									var $id = $this.closest("tr").find("input[name='id']").val();
									var $name = $this.closest("tr").find("input[name='name']").val();
									var $price = $this.closest("tr").find("input[name='price']").val();
									 
									giveIds += $id+",";
									giveName += $name+",";
									giveCost += parseFloat($price);
								});
								
								$giveIds.val(giveIds);
								$giveName.val(giveName);
								$giveCost.val(giveCost);
								 
							}
					  });
			 		}
				}
	   		});
	});
 
});