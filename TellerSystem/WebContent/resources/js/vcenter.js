$(document).ready(function() {
	var scrn_height = $(window).height();
	var wanted_height = scrn_height * .9;
	var top_height = scrn_height * .05;

	$(".main").css("height", wanted_height);
	$(".main_row").css({
		"position" : "relative",
		"top" : top_height
	});

	$(window).resize(function() {
		var scrn_height = $(window).height();
		var wanted_height = scrn_height * .9;
		var top_height = scrn_height * .05;
		$(".main").css("height", wanted_height);
		$(".main_row").css({
			"position" : "relative",
			"top" : top_height
		});
	});
});