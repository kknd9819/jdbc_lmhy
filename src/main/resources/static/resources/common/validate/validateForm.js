/*清除提示信息和样式*/
function clearPromptMsg(id,cssName,isRemove){ 
   var valObj = jQuery("#"+id);
   if(isRemove){
	   jQuery("#"+id+"_label").remove();
   }else{
	   jQuery("#"+id+"_label").removeClass(cssName);
   }
   valObj.removeClass(cssName);
}
/*品牌管理 -- 名称首字母验证*/
function validateLetter(){
    var regExp = /[a-z|A-Z]$/;
    var initialObj = jQuery("#initial");
	var initial = initialObj.val();
	if(!regExp.test(initial)){ 
	   jQuery("#initialSpan").show();  
	   jQuery("#initial").addClass("fieldError");
	}else{
	   jQuery("#initialSpan").hide();
	   jQuery("#initial").removeClass("fieldError");
	} 
}

