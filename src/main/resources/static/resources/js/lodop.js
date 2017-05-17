	var LODOP; //声明为全局变量      

	function printShipping(strID,sn,url){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("订单"+sn+"配送单打印");
		LODOP.ADD_PRINT_URL(30,20,746,"95%",url+"/order/printReport/printOrderShipping.jhtml?orderId="+strID);
		LODOP.SET_PRINT_STYLEA(0,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(0,"VOrient",3);
//		LODOP.SET_SHOW_MODE("MESSAGE_GETING_URL",""); //该语句隐藏进度条或修改提示信息
//		LODOP.SET_SHOW_MODE("MESSAGE_PARSING_URL","");//该语句隐藏进度条或修改提示信息
		LODOP.PRINT();	
		
	};	
	
	function printView(strID,url){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("配送单预览");
		LODOP.ADD_PRINT_URL(30,20,746,"95%",url+"/order/printReport/printOrderShipping.jhtml?orderId="+strID);
		LODOP.SET_PRINT_STYLEA(0,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(0,"VOrient",3);
//		LODOP.SET_SHOW_MODE("MESSAGE_GETING_URL",""); //该语句隐藏进度条或修改提示信息
//		LODOP.SET_SHOW_MODE("MESSAGE_PARSING_URL","");//该语句隐藏进度条或修改提示信息
		LODOP.PREVIEW();			
	};	
	
	function printStockout(strID,sn,url){
	
		LODOP=getLodop();  
		LODOP.PRINT_INIT("订单"+sn+"补货单打印");
		LODOP.ADD_PRINT_URL(30,20,746,"95%",url+"/order/printReport/printStockout.jhtml?orderId="+strID);
		LODOP.SET_PRINT_STYLEA(0,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(0,"VOrient",3);
//		LODOP.SET_SHOW_MODE("MESSAGE_GETING_URL",""); //该语句隐藏进度条或修改提示信息
//		LODOP.SET_SHOW_MODE("MESSAGE_PARSING_URL","");//该语句隐藏进度条或修改提示信息
		LODOP.PRINT();	
	}
	
	function printDistribution(strID,sn,url){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("订单"+sn+"配货单打印");
		LODOP.ADD_PRINT_URL(30,20,746,"95%",url+"/order/printReport/printOrder.jhtml?orderId="+strID);
		LODOP.SET_PRINT_STYLEA(0,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(0,"VOrient",3);
//		LODOP.SET_SHOW_MODE("MESSAGE_GETING_URL",""); //该语句隐藏进度条或修改提示信息
//		LODOP.SET_SHOW_MODE("MESSAGE_PARSING_URL","");//该语句隐藏进度条或修改提示信息
		LODOP.PRINT();	
		
	};