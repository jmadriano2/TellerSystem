$(document).ready(function() {
	var main_body_height = $(".main_body").height();
	var top_height = main_body_height * .15;

	$(".transaction_forms").css({
		"position" : "relative",
		"top" : top_height
	});
});