$(document).ready(function() {
	var modal_height = $(".confirmationModal").height();
	var modal_content_height = modal_height * .2;
	var top_height = scrn_height * .4;

	$(".modal-dialog").css("height", modal_content_height);
	$(".modal-dialog").css({
		"position" : "relative",
		"top" : top_height
	});

	$(window).resize(function() {
		var modal_height = $(".confirmationModal").height();
		var modal_content_height = modal_height * .2;
		var top_height = scrn_height * .4;
		$(".modal-dialog").css("height", modal_content_height);
		$(".modal-dialog").css({
			"position" : "relative",
			"top" : top_height + " !important"
		});
	});
});