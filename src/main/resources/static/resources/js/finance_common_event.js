function IsNum(obj) { 
   //定义正则表达式部分
   var value = jQuery(obj).val();
   var reg = /^\d+$/;
   if( value.constructor === String ){
       var re = reg.test(value);
       if(!re){
       	jQuery(obj).val(1);
       }
   }
}

//收款分页查询及显示数据
function paymentForPage(currentPage,flag,url){
		var method = jQuery("#method option:selected").val();
		var financeStatus = jQuery("#financeStatus option:selected").val();
		var beginDate = jQuery("#beginDate").val();
		var endDate = jQuery("#endDate").val();
		var minAmount = jQuery("#minAmount").val();
		var maxAmount = jQuery("#maxAmount").val();
		var adminMid = jQuery("#adminMid").val();
		var sn = jQuery("#sn").val();
		var getCouponForPage = url;
		jQuery.getJSON(getCouponForPage, {paymentMethod:method,financeStatus:financeStatus,beginDate:beginDate,endDate:endDate,minAmount:minAmount,maxAmount:maxAmount,adminMid:adminMid,sn:sn,currentPage:currentPage},function(data){
			jQuery("#paymentList").empty();
			var paymentDetail = "";
			if(data.items.length>0){
				jQuery.each(data.items, function(i, item) {
					paymentDetail += "<tr>";
					paymentDetail += "<td>";
					paymentDetail += "	<span>"+item.ordersn+" / "+item.sn+"</span>";
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					if(item.method!=null && item.method==0){
					    paymentDetail += "<span>在线支付</span>";
					}else if(item.method!=null && item.method==1){
					    paymentDetail += "<span>线下支付</span>";
					}
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					if(item.paymentMethod!=null){
					   paymentDetail += item.paymentMethod;
					}
					
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					
					if(item.memberName!=null){
					   paymentDetail += item.memberName;
					}
					
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					paymentDetail += item.amount;
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					paymentDetail += item.amountPaid;
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					if(item.fee!=null){
					   paymentDetail +=  item.fee;
					}
					paymentDetail += "</td>";
					paymentDetail += "<td class='center'>";
					if(item.financeStatus!=null && item.financeStatus==0){
					   paymentDetail += "待确认";
					}else if(item.financeStatus!=null && item.financeStatus==1){
					   paymentDetail += "确认驳回";
					}else if(item.financeStatus!=null && item.financeStatus==2){
					   paymentDetail += "待复核确认";
					}else if(item.financeStatus!=null && item.financeStatus==3){
					   paymentDetail += "确认完成";
					}
					
					paymentDetail += "</td>";
					
					paymentDetail += "<td class='center'>";
					paymentDetail += "<span title='"+item.modifyDate+"'>"+item.modifyDate+"</span>"; 
					paymentDetail += "</td>";
					paymentDetail += "<td>";
					if(item.memo!=null){
					  paymentDetail += item.memo;
					}
					paymentDetail += "</td>";
					paymentDetail += "<td>";
					paymentDetail += "<a href=javascript:viewOrderItem('"+item.ordersn+"','"+item.orders+"','"+item.tax+"','"+item.freight+"','"+item.couponDiscount+"')>[查看订单明细]</a>";
					
					if((flag!=null && flag==0)  && (item.financeStatus!=null && item.financeStatus==0)){
				       paymentDetail += "<a href=javascript:sure('"+item.sn+"','"+item.orders+"')>&nbsp;&nbsp;[确认]</a>";
				    }else if((flag!=null && flag==2) && (item.financeStatus!=null && item.financeStatus==2)){
				       paymentDetail += "<a href=javascript:sure('"+item.sn+"','"+item.orders+"')>&nbsp;&nbsp;[确认复核]</a>";
				    }
				    
					paymentDetail +="</td>";
					paymentDetail +="</tr>";
				});
			}else{
				jQuery("#paymentList").append("<tr><td colspan='12' align='center'>暂无收款信息</td></tr>");
			}
			jQuery("#paymentList").append(paymentDetail);
			if(data.pageCount>0){
				financePageInit("paymentPageDiv",data.currentPage,data.pageCount,"paymentForPage",flag,url);
			}else{
				jQuery("#paymentPageDiv").empty();
			}
		});
	}
//end


function toPrintPage(url){
	var method = jQuery("#method option:selected").val();
	var financeStatus = jQuery("#financeStatus option:selected").val();
	var beginDate = jQuery("#beginDate").val();
	var endDate = jQuery("#endDate").val();
	var minAmount = jQuery("#minAmount").val();
	var maxAmount = jQuery("#maxAmount").val();
	var adminMid = jQuery("#adminMid").val();
	var sn = jQuery("#sn").val();
	var status = jQuery("#status").val();
	var param = "paymentMethod="+method+"&financeStatus="+financeStatus+"&beginDate="+beginDate+"&endDate="+endDate+"&minAmount="+minAmount+"&maxAmount="+maxAmount+"&adminMid="+adminMid+"&sn="+sn+"&status="+status;
	//alert(url+param);
	window.location.href=url+"?"+param;
}
//退款分页查询及显示数据
function refundsForPage(currentPage,flag,url){
		var method = jQuery("#method option:selected").val();
		var financeStatus = jQuery("#financeStatus option:selected").val();
		var beginDate = jQuery("#beginDate").val();
		var endDate = jQuery("#endDate").val();
		var minAmount = jQuery("#minAmount").val();
		var maxAmount = jQuery("#maxAmount").val();
		var adminMid = jQuery("#adminMid").val();
		var sn = jQuery("#sn").val();
		var status = jQuery("#status").val();
		
		var getCouponForPage = url;
		jQuery.getJSON(getCouponForPage, {paymentMethod:method,financeStatus:financeStatus,beginDate:beginDate,endDate:endDate,minAmount:minAmount,maxAmount:maxAmount,adminMid:adminMid,sn:sn,status:status,currentPage:currentPage},function(data){
			jQuery("#refundsList").empty();
			var refundsDetail = "";
			if(data.items.length>0){
				jQuery.each(data.items, function(i, item) {
					refundsDetail += "<tr>";
					refundsDetail += "<td>";
					refundsDetail += "	<span>"+item.ordersn+" / "+item.sn+"</span>";
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.method!=null && item.method==0){
					    refundsDetail += "<span>在线支付</span>";
					}else if(item.method!=null && item.method==1){
					    refundsDetail += "<span>线下支付</span>";
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.paymentMethodName!=null){
					   refundsDetail += item.paymentMethodName;
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.paymentPluginId!=null && item.paymentPluginId=='alipayDirectPlugin'){
						  refundsDetail += "支付宝即时到账";
					}else if(item.paymentPluginId!=null && item.paymentPluginId=='unionpayPlugin'){
						  refundsDetail += "银联在线支付";
					}else if(item.paymentPluginId!=null && item.paymentPluginId=='mobileAlipayWap'){
						  refundsDetail += "支付宝网页快捷支付";
					}else if(item.paymentPluginId!=null && item.paymentPluginId=='mobileAlipaySecure'){
						  refundsDetail += "支付宝无线快捷支付";
					}else if(item.paymentPluginId!=null && item.paymentPluginId=='mobileUnionPay'){
						  refundsDetail += "银联移动支付";
					}else if(item.paymentPluginId!=null && item.paymentPluginId=='weixinPayPlugin'){
						  refundsDetail += "微信移动支付";
					}else{
						  refundsDetail += "未知的支付方式["+item.paymentPluginId+"]";
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
						
				    if(item.amount!=null){
				      	refundsDetail += item.amount;
				    }
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.memberName!=null){
					   refundsDetail += item.memberName;
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.memberName!=null){
					   refundsDetail += item.operator;
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.financeStatus!=null && item.status==0){
					   refundsDetail += "待退款";
					}else if(item.financeStatus!=null && item.status==1){
					   refundsDetail += "退款失败";
					}else if(item.financeStatus!=null && item.status==2){
					   refundsDetail += "退款处理中";
					}else if(item.financeStatus!=null && item.status==3){
					   refundsDetail += "退款成功";
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					if(item.financeStatus!=null && item.financeStatus==0){
					   refundsDetail += "待确认";
					}else if(item.financeStatus!=null && item.financeStatus==1){
					   refundsDetail += "确认驳回";
					}else if(item.financeStatus!=null && item.financeStatus==2){
					   refundsDetail += "待复核确认";
					}else if(item.financeStatus!=null && item.financeStatus==3){
					   refundsDetail += "确认完成";
					}
					refundsDetail += "</td>";
					refundsDetail += "<td class='center'>";
					refundsDetail += "<span title='"+item.modifyDate+"'>"+item.modifyDate+"</span>"; 
					refundsDetail += "</td>";
					refundsDetail += "<td >";
					if(item.memo!=null){
					  refundsDetail += item.memo;
					}
					refundsDetail += "</td>";
					refundsDetail += "<td>";
					refundsDetail +="<a href=javascript:viewOrderItem('"+item.sn+"','"+item.orders+"','"+item.tax+"','"+item.freight+"','"+item.couponDiscount+"')>[查看订单明细]</a>";
					
					if((flag!=null && flag==0) && (item.financeStatus!=null && item.financeStatus==0)){
				       refundsDetail += "<a href=javascript:sure('"+item.sn+"','"+item.orders+"','"+item.amountPaid+"')>&nbsp;&nbsp;[确认]</a>";
				    }else if((flag!=null && flag==2) && (item.financeStatus!=null && item.financeStatus==2)){
				       refundsDetail += "<a href=javascript:sure('"+item.sn+"','"+item.orders+"','"+item.amountPaid+"')>&nbsp;&nbsp;[确认复核]</a>";
				    }
				    
					refundsDetail +="</td>";
					refundsDetail +="</tr>";
				});
			}else{
				jQuery("#refundsList").append("<tr><td colspan='12' align='center'>暂无退款信息</td></tr>");
			}
			jQuery("#refundsList").append(refundsDetail);
			if(data.pageCount>0){
				financePageInit("refundsPageDiv",data.currentPage,data.pageCount,"refundsForPage",flag,url);
			}else{
				jQuery("#refundsPageDiv").empty();
			}
		});
	}
//end

function joinOrderDetailHtml(data,tax,freight,returnCash){
	var html = "<div style='height: 380px; overflow-x: hidden; overflow-y: auto;'><table id='moreTable' class='moreTable' style='width:100%;height: 380px; overflow-x: hidden; overflow-y: auto;'>"
							+"<tr><th style='width:35%'>商品名称</th><th>单价</th><th>数量<\/th><th>退货<\/th>"
							+"<th>赠品<\/th><th>订单小计</th><\/tr>";
				var pMoney = 0;	
				for(var i=0;i<data.orderItemList.length;i++){
					     html += "<tr><td style='text-align:left'>"+data.orderItemList[i].name+"</td><td style='text-align:center'>"+parseFloat(data.orderItemList[i].price).toFixed(2)+"</td><td style='text-align:center'>"+data.orderItemList[i].quantity+"</td>"
						 +"<td style='text-align:center'>"+data.orderItemList[i].returnQuantity+"</td><td style='text-align:center'>";
						 if(data.orderItemList[i].isGift==true){
						    html += "是";
						  }else{
						     html += "否";
						  }
						  html +="</td><td style='text-align:center'>"+parseFloat(data.orderItemList[i].quantity*data.orderItemList[i].price).toFixed(2)+"</td></tr>";
						  pMoney += data.orderItemList[i].quantity*data.orderItemList[i].price;
				}//end 
				var tMoney = parseFloat(parseFloat(pMoney)+parseFloat(tax)+parseFloat(freight)-parseFloat(returnCash)).toFixed(2);		
				html +="<tr><td colspan='6' style='text-align:right;padding-right: 30px;'>商品合计金额：<font class='red'>"+parseFloat(pMoney).toFixed(2)+"</font></td></tr>";
				html +="<tr><td colspan='6' style='text-align:right;color:#008000;padding-right: 30px;'>+税金：<font class='red'>"+parseFloat(tax).toFixed(2)+"</font></td></tr>";
				html +="<tr><td colspan='6' style='text-align:right;color:#2F97FF;padding-right: 30px;'>+运费：<font class='red'>"+parseFloat(freight).toFixed(2)+"</font></td></tr>";
				html +="<tr><td colspan='6' style='text-align:right;color:#F26521;padding-right: 30px;'>-优惠券：<font class='red'>"+parseFloat(returnCash).toFixed(2)+"</font></td></tr>";
				if (data.lastRefundAmount) {
					html +="<tr><td colspan='6' style='text-align:right;color:#F26521;padding-right: 30px;'>-退款金额：<font class='red'>"+parseFloat(data.lastRefundAmount).toFixed(2)+"</font></td></tr>";
					html +="<tr><td colspan='6' style='text-align:right;padding-right: 30px;'>最终收款：<font class='red'>"+parseFloat(tMoney-data.lastRefundAmount).toFixed(2)+"</font></td></tr>";
				} else {
					html +="<tr><td colspan='6' style='text-align:right;padding-right: 30px;'>最终收款：<font class='red'>"+tMoney+"</font></td></tr>";
				}
				html +="</table></div>";
				
  return html;
}


function refundsOrderItemHtml(data,tax,freight,returnCash){
	var html = "<div style='height: 400px; overflow-x: hidden; overflow-y: auto;''><table id='moreTable' class='moreTable' style='width:100%;height: 310px; overflow-x: hidden; overflow-y: auto;'>"
							+"<tr><th style='width:35%;line-height:20px;'>商品名称</th><th style='line-height:20px;'>单价</th><th style='line-height:20px;'>退货数量<\/th><th style='line-height:20px;'>是否为赠品<\/th><th width='15%;line-height:20px;'>退款小计</th><\/tr>";
				var pMoney = 0;	
				for(var i=0;i<data.returnsItemList.length;i++){
					     html += "<tr><td style='text-align:left'>"+data.returnsItemList[i].name+"</td><td style='text-align:center'>"+parseFloat(data.returnsItemList[i].price).toFixed(2)+"</td><td style='text-align:center'>"+data.returnsItemList[i].quantity+"</td>";
					     html +="<td style='text-align:center'>";
						 if(data.returnsItemList[i].isGift==true){
						    html += "是";
						  }else{
						     html += "否";
						  }
						  html += "<td style='text-align:center'>"+parseFloat(data.returnsItemList[i].quantity*data.returnsItemList[i].price).toFixed(2)+"</td></tr>";
						  pMoney += data.returnsItemList[i].quantity*data.returnsItemList[i].price;
				}
				var tMoney = parseFloat(parseFloat(pMoney)+parseFloat(tax)+parseFloat(freight)-parseFloat(returnCash)).toFixed(2);
				var amountJson = eval("("+data.amountJson+")");
				
				html +="<tr><td style='text-align:right;padding-right: 10px; ' colspan='5'> "+amountJson.items[0].key+"：<font class='red'>"+amountJson.items[0].value+"</font></td></tr>";
				html +="<tr><td style='text-align:right;padding-right: 10px;color:#008000; ' colspan='5'>"+amountJson.items[1].key+"：<font class='red'>"+amountJson.items[1].value+"</font></td></tr>";
				html +="<tr><td  style='text-align:right; padding-right: 10px;color:#2F97FF' colspan='5'>"+amountJson.items[2].key+"：<font class='red'>"+amountJson.items[2].value+"</font></td></tr>";
				html +="<tr><td style='text-align:right;padding-right: 10px; color:#F26521;' colspan='5'>"+amountJson.items[3].key+"：<font class='red'>"+amountJson.items[3].value+"</font></td></tr>";
				html +="<tr><td style='text-align:right;padding-right: 10px; 'colspan='5'>最终退款合计：<font class='red'>"+parseFloat(data.lastRefundAmount).toFixed(2)+"</font></td></tr>";
				html +="</table></div>";
        return html;
}

//初始化财务状态下拉框的值
function initialValue(flag){
	 if(flag!=null && flag==0){//确认
	    jQuery("#financeStatus").val(0);
	 }else if(flag!=null && flag==2){//确认复核
	    jQuery("#financeStatus").val(2);
	 }
}

//复核
function checkOrReCheck(url){
   $.ajax({
		url: url,
		type: "GET",
		dataType: "json",
		cache: false,
		success: function(data) {
		   if(data.type=="success"){
		       $.message(data.type, data.content);
		       window.onload();
		   }else{
		       $.message(data.type, data.content);
		   }//end if
		}//end success
   });//end ajax
}

//收款确认
function paymentOnOk(paymentId,paymentSn,memo,paymentAmount,needCheck,msg){
	if(confirm(msg)){
	       var  memo = jQuery("#memo").val();
	       checkOrReCheck("ajaxCheck.jhtml?id="+paymentId+"&sn="+paymentSn+"&memo="+memo+"&paymentAmount="+paymentAmount+"&needCheck="+needCheck);
	  }//end if 
}

//退款
function refundsOnOk(data,msg){
	if(confirm(msg)){
	       var  memo = jQuery("#memo").val();
	       checkOrReCheck("ajaxCheck.jhtml?id="+data.id+"&sn="+data.refundSn+"&memo="+memo+"&refundsAmount="+data.amount+"&needCheck="+data.needCheck);
	  }//end if 
}

//打回
function onCancel(id,sn,msg){//确定打回此笔收款信息吗？
    var memo = jQuery("#memo").val();
    if(memo==null || memo==''){
       alert("请填写此单驳回原因!");
       return false;
    }
    if(confirm(msg)){
       $.ajax({
			url: "ajaxRollback.jhtml?id="+id+"&sn="+sn+"&memo="+memo,
			type: "GET",
			dataType: "json",
			cache: false,
			success: function(data) { 
			    if(data.type="success"){
			       $.message(data.type, data.content);
			       window.onload();
			    }else{
			       $.message(data.type, data.content);
			    }
			}
		});//end ajax
    }//end if
}


//join sure
function joinPaymentSure(data){
	 var html = "<table id='moreTable' class='moreTable' style='width:100%'>"
			+"<tr><th>订单编号</th><th >收款编号</th><th>订单金额</th><th>收款金额</th>"
			+"<th>订单支付方式<\/th><th>收款支付方式</th>"
			+"<th>支付人</th><th>收款日期</th>"
			+"<\/tr>";
	
	html += "<tr><td>"+data[0].orderSn+"</td><td>"+data[0].paymentSn+"</td> <td>"+data[0].orderAmount+"</td><td>"+data[0].amount+"</td>"
	+"<td>"+data[0].orderPaymentName+"</td><td>"+data[0].paymentMethod+"</td><td>";
	
	if(data[0].username!=null){
	 html += data[0].username;
	}
	html += "<td>"+data[0].paymentDateStr+"</td></tr>";
	
	html += "<tr><td colspan='2' style='text-align:right;'><font color='red'>确认或驳回收款单原因：</font></td><td colspan='6' style='text-align:left;'><input type='text' name='memo' id='memo' size='80'/></td></tr>";
	
	html +="</table>";
	return html;
}

//拆单情况下的 网上支付 确认信息
function joinOnLinePaymentSure(data){
	 var html = "<table id='moreTable' class='moreTable' style='width:100%'>"
			+"<tr><th >收款编号</th><th>订单编号</th><th>收款金额</th><th>订单金额</th><th>配送方式</th>"
			+"<th>订单支付方式<\/th><th>收款支付方式</th>"
			+"<th>支付人</th><th>收款日期</th>"
			+"<\/tr>";
	     if(data.length>1){
	    	 html += "<tr><td rowspan='2'>"+data[0].paymentSn+"</td><td>"+data[0].orderSn+"</td><td rowspan='2'>"+data[0].amount+"</td><td>"+data[0].orderAmount+"</td>"
				+"<td>"+data[0].shippingMethodName+"</td><td rowspan='2'>"+data[0].orderPaymentName+"</td><td rowspan='2'>"+data[0].paymentMethod+"</td><td rowspan='2'>";
				if(data[0].username!=null){
				  html += data[0].username;
				}
				html += "<td rowspan='2'>"+data[0].paymentDateStr+"</td></tr>";
			   html += "<tr><td>"+data[1].orderSn+"</td><td>"+data[1].orderAmount+"</td><td>"+data[1].shippingMethodName+"</td></tr>";
	     }
	html += "<tr><td colspan='2' style='text-align:right;'><font color='red'>确认或驳回收款单原因：</font></td><td colspan='6' style='text-align:left;'><input type='text' name='memo' id='memo' size='80'/></td></tr>";
	html +="</table>";
	return html;
}

function joinRefundsSure(data){
	var html = "<table id='moreTable' class='moreTable' style='width:100%'>"
		+"<tr><th>订单编号</th><th >退款编号</th><th>订单金额</th><th>退款金额</th><th>应收金额(税金+运费+未退款的商品金额)</th>"
		+"<th>订单支付方式<\/th><th>退款支付方式</th>"
		+"<th>支付人</th><th>退款日期</th>"
		+"<\/tr>";

	html += "<tr><td>"+data.orderSn+"</td><td>"+data.refundSn+"</td> <td>"+data.orderAmount+"</td><td>"+data.amount+"</td><td>"+data.amountPaid+"</td>"
	+"<td>"+data.orderPaymentName+"</td><td>"+data.refundPaymentName+"</td><td>";
	
	if(data.username!=null){
	   html += data.username;
	}
	html += "<td>"+data.refundDateStr+"</td></tr>";
	
	html += "<tr><td colspan='2' style='text-align:right;'><font color='red'>确认或驳回退款单原因：</font></td><td colspan='6' style='text-align:left;'><input type='text' name='memo' id='memo' size='80'/></td></tr>";
	
	html +="</table>";
    return html;
}


function closeDiv(){
	 jQuery("#pay_popup").hide();
}

