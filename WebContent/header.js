$(document).ready(function() {
	$(".signing").hide();
	$("#menu").hide();
	
	var uname = $("#username").html();
	if (uname != "") {
		$("#signout").show();
		$("#menu").show();
	} else 
		$("#signin").show();
});
