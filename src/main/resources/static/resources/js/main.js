$().ready(function() {
		var $nav = $("#nav a:not(:last)");
		var $menu = $("#menu dl");
		var $menuItem = $("#menu a");
		
		$nav.click(function() {
			var $this = $(this);
			$nav.removeClass("current");
			$this.addClass("current");
			var $currentMenu = $($this.attr("href"));
			$menu.hide();
			$currentMenu.show();
			return false;
		});
		
		$menuItem.click(function() {
			var $this = $(this);
			$menuItem.removeClass("current");
			$this.addClass("current");
		});
	});
	
	var CODER_ALAW = 0;
	var CODER_PCM = 1;
	var CODER_G729 = 3;
	var CODER_SPEEX = 20;
	var CODER_ULAW = 100;
	
	var UBOX_MODE_RECORD = 0; //录音模式， 通常使用的模式
	var UBOX_MODE_DIAG = 1; //诊断模式， 用于捕获线路信息，供信号分析之用，支持的语音编码方式是CODER_PCM
	var UBOX_MODE_CONFIG = 2; //配置模式，
	var hdl = -1;
	
	var UBOX_STATE_RESET = 1; //复位态，表示既非振铃也非摘机的状态。如果此前为振铃态，则此状态表示振铃已停止，如果此前为摘机态，则此状态表示已挂机。
	var UBOX_STATE_RINGING = 2; //振铃态，表示已检测到线路振铃信号，如果振铃停止，则将触发 UBOX_EVENT_RESET 事件，并汇报 UBOX_STATE_RESET 状态。
	var UBOX_STATE_HOOK_OFF = 3; //摘机态，
	var UBOX_STATE_HANG = 4; //悬空态，
	var UBOX_STATE_IDLE = 5;
	var UBOX_STATE_REVERSE_HOOKOFF = 6; //反向摘机，指软件摘机
	var UBOX_STATE_POSITIVIE_HOOKOFF = 7; //正向摘机，指软件摘机
	
	var  ubox={lines:[],linenum:0};//全局声明
	
	function AppendStatus(szStatus){
		document.getElementById("StatusArea").value +=szStatus;
		document.getElementById("StatusArea").value +="\r\n";
		var d = document.getElementById("StatusArea").scrollHeight;
		document.getElementById("StatusArea").scrollTop = d;
	}

	function Ubox_Plug_In(uboxhdl){
		var outdata = "设备插入 句柄号:"+uboxhdl;
   		if(ubox.linenum == 2) {
      		return;
		}
   		ubox.linenum++;
   		if( ubox.lines[uboxhdl] == undefined ){
			ubox.lines[uboxhdl] = {};
		}
		ubox.lines[uboxhdl].handle = uboxhdl;
		ubox.lines[uboxhdl].callernumber = "";
	    ubox.lines[uboxhdl].dtmfkyes = "";
		ubox.lines[uboxhdl].lineid = ubox.linenum;
		if(ubox.linenum == 1) {
		    hdl = ubox.lines[uboxhdl].handle;
		}
   		AppendStatus(outdata);
	}

	function Ubox_hookoff(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 摘机");
	}

	function Ubox_hookon(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 挂机");
	}

	function Ubox_CallId(uboxHandle,callerNumber,callerTime,callerName) {
	   var outdata = "设备"+uboxHandle+"号码："+callerNumber+"时间："+callerTime ;
	   AppendStatus(outdata);
	}

	function ubox_Ring(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 振铃");
	}

	function ubox_RingCancel(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 振铃取消");
	}

	function ubox_RingStop(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 停振");
	}

	function ubox_ToneBusy(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 忙音");
	}

	function ubox_DeviceAlarm(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 警告，重启软件");
	}

	function ubox_DeviceError(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 错误，重启软件");
	}

	function ubox_DtmfKey(uboxHandle,dtmfCode) {
	   // var outdata = "设备"+uboxHandle+"按键:"+(dtmfCode-48);
	   // AppendStatus(outdata);
	}

	function ubox_HangIng(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 悬空");
	}

	function ubox_PlugOut(uboxhdl) {
	   var outdata = "设备"+uboxhdl;
	   ubox.lines[uboxhdl].handle = -1;
	   ubox.lines[uboxhdl] = undefined;
	   ubox.linenum -= 1;
	   AppendStatus(outdata+" 拨出");
	}

	function ubox_PlayError(uboxhdl) {
	    var outdata = "设备"+uboxhdl;
	    AppendStatus(outdata+" 放音错误");
	}

	function ubox_openDevice(id) {
		try {
		 	var uChannelNum=Phonic_usb.OpenDevice(UBOX_MODE_RECORD);
		 	if(uChannelNum== 0) {
	 			AppendStatus("打开设备成功");
	 		} else {
		 		AppendStatus("打开设备失败");		
	 		}
		}catch(e) { }
	}

	function ubox_CloseDevice() {
		Phonic_usb.CloseDevice();
		AppendStatus("关闭设备完成.");
	}

	function ubox_dialnum(szCode) {
		var szCode = szCode;
		if (szCode.substr(0,1) != 0) {
			szCode = '0' + szCode;
		}
	    var state = Phonic_usb.GetLineState(hdl);
		if((state== UBOX_STATE_HOOK_OFF )||(state== UBOX_STATE_REVERSE_HOOKOFF )||(state== UBOX_STATE_POSITIVIE_HOOKOFF )) {   
			if(Phonic_usb.SendDtmf(hdl,szCode) == 0) {
				AppendStatus("拨号:"+szCode.substr(1));
		  	}else {
				AppendStatus("拨号失败:"+szCode.substr(1));
		  	}
		} else {
			AppendStatus("请摘起电话机");
		}
	}

	function ubox_hook_off() {
		if(Phonic_usb.SoftHookoff(hdl)==0) {
			AppendStatus("摘机成功");
		} else {
			AppendStatus("摘机失败");
		}
	}

	function ubox_hook_on() {
		if(Phonic_usb.SoftHookon(hdl)==0) {
			AppendStatus("挂机成功");
		} else {
			AppendStatus("挂机失败");
		}
	}

	function ubox_handle_streamVoice(uboxHandle, pVoice, size) {
		var outdata = "流式录音 句柄号:"+hdl +"size:"+size;
		AppendStatus(outdata);
	}

	function changelinenum(This) {
		if(ubox.lines[This.value] == undefined) {
			alert("USB盒子未插入");
		} else {
			hdl = ubox.lines[This.value].handle; 
		}
	}

	function OCXRegistered(strID) {  
  		var ocx = null;  
  		try {  
			ocx = new ActiveXObject(strID);  
		} catch(e) {  
     		return false;  
    	}  
  		if(ocx != null) {  
    		ocx = null;  
			return true;  
  		} else  
     		return false;  
		}

	window.onbeforeunload = function() {
		if(event.clientX>document.body.clientWidth&&event.clientY<0||event.altKey||event.ctrlKey) {
			ubox_CloseDevice();
			Phonic_usb.CloseLogFile();
			window.event.returnValue="";
		}
	}

	function init() {
		Phonic_usb.OpenLogFile(0);
		ubox_CloseDevice();
		ubox_openDevice();
	}

	window.callback_hello = function(){
		alert("abc");
	}