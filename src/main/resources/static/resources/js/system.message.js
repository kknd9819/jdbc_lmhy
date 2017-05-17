$(document).ready(function() {
	$(window).scroll(function() {
		var $messageDiv = $("#messageDiv");

		var screenWidth = $(window).width();
		var screenHeight = $(window).height();
		var scrollTop = $(document).scrollTop();
		var messageDivLeft = (screenWidth - $messageDiv.width()) / 2;
		var messageDivTop = (screenHeight - $messageDiv.height()) / 2 + scrollTop - 20;
		$messageDiv.css({
			"left" : messageDivLeft + "px",
			"top" : messageDivTop + "px"
		});

		var $panelWindow = $("div[class='panel window messager-window']");

		var panelWindowTop = (screenHeight - $panelWindow.height()) / 2 + scrollTop;
		$panelWindow.css({
			"top" : panelWindowTop + "px"
		});
	});

	$(".shou_dis_i").live({
		mouseenter : function() {
			$(this).parents("div.wenhaotcc").find("div.btn_dis_box").first().show();
		},
		mouseleave : function() {
			$(this).parents("div.wenhaotcc").find("div.btn_dis_box").first().hide();
		}
	});

});

var sysMessageTimer;
var messageCloseSecordSecords = 1;
var messageCloseSecordInterval;

function showMessageCloseSecordSpan(className, url) {
	jQuery("#messageCloseSecord").html(messageCloseSecordSecords);

	if (messageCloseSecordSecords == 0) {
		clearInterval(messageCloseSecordInterval);
		jQuery("#messageDiv").hide();
		jQuery("#messageDiv").remove();
		messageCloseSecordSecords = 1;
		if (url != null) {
			location.href = url;
		}
	}
	messageCloseSecordSecords--;
}

function showMessageDiv(className, content, url) {
	jQuery("div[class='note2']").remove();
	jQuery("<div id='messageDiv' class='note2' style='display: none;'></div>").appendTo("body");
	var $messageDiv = jQuery("#messageDiv");
	$messageDiv.empty();
	var messageDivStr = "<div class='note2_title clearfix'>提示信息 <i class='note2_close' onclick='closeMessageDiv()' ></i></div>";

	messageDivStr += "<div class='note2_content clearfix'>";
	messageDivStr += "<div class='note2_ct clearfix'>";
	messageDivStr += " <p> <span class='tishi-content'> <i class='" + className + "'></i> " + content + " </span> </p>";
	messageDivStr += "</div>";
	messageDivStr += "</div>";

	messageDivStr += "<div class='note2_ctbm clearfix'>";
	messageDivStr += "<p> <span class='tishi-content'> <i class='nock'></i> <b id='messageCloseSecord'>2</b>秒后窗口关闭 </span> </p>";
	messageDivStr += "</div>";
	$messageDiv.append(messageDivStr);

	divTopCenter();

	$messageDiv.show();
	messageCloseSecordSecords = 1;
	messageCloseSecordInterval = setInterval(function() {
		showMessageCloseSecordSpan(className, url);
	}, 1000);

}

function closeMessageDiv() {
	var $messageDiv = jQuery("#messageDiv");
	$messageDiv.hide();
	$messageDiv.remove();
	messageCloseSecordSecords = 1;
}

function divTopCenter() {
	var $messageDiv = jQuery("#messageDiv");
	var screenWidth = jQuery(window).width();
	var screenHeight = jQuery(window).height();
	var scrollTop = jQuery(document).scrollTop();
	var objLeft = (screenWidth - $messageDiv.width()) / 2;
	var objTop = (screenHeight - $messageDiv.height()) / 2 + scrollTop - 20;
	$messageDiv.css({
		"left" : objLeft + "px",
		"top" : objTop + "px"
	});

}

function showMessageDivOpen(className, card, balance, type) {
	jQuery("div[class='note2']").remove();
	jQuery("<div id='messageDiv' class='note2' style='display: none;'></div>").appendTo("body");
	var $messageDiv = jQuery("#messageDiv");
	$messageDiv.empty();
	var messageDivStr = "<div class='note2_title clearfix'>提示信息 <i class='note2_close' onclick='closeMessageDiv()' ></i></div>";

	messageDivStr += "<div class='note2_content clearfix'>";
	messageDivStr += "<div class='note2_ct clearfix'>";
	messageDivStr += " <p> <span class='tishi-content'>会员卡卡号 " + card;

	if (type == 1) {
		messageDivStr += " , 余额为";
	} else {
		messageDivStr += " ,  闪豆为";
	}

	messageDivStr += "<span class='price16_nb'> " + balance + " </span> </span> </p>";

	messageDivStr += "</div>";
	messageDivStr += "</div>";

	messageDivStr += "<div class='note2_ctbm clearfix'>";
	messageDivStr += "<p> <span class='member_Inputccer'><a  onclick='closeMessageDiv()' class='mem_btn5'>确定</a>  </span> </p>";
	messageDivStr += "</div>";
	$messageDiv.append(messageDivStr);

	divTopCenter();

	$messageDiv.show();

}