

$(document).ready(function() {
	// $(".desktop2").css("left", "-100vw");
	// $(".desktop3").css("left", "-100vw");
	// $(".desktop4").css("left", "-100vw");
	$(".desktop2").animate({left: "-100vw"}, 1);
	$(".desktop3").animate({left: "-100vw"}, 1);
	$(".desktop4").animate({left: "-100vw"}, 1);

	$(".desktop1").click(function() {
		$(this).animate({left: "100vw"}, "slow", function(){
			$(this).hide();
		});
		$(".desktop2").animate({left: "0vw"}, "slow");
	});
	$(".desktop2").click(function() {
		$(this).animate({left: "100vw"}, "slow", function(){
			$(this).hide();
		});
		$(".desktop3").animate({left: "0vw"}, "slow");
	});
	$(".desktop3").click(function() {
		$(this).animate({left: "100vw"}, "slow", function(){
			$(this).hide();
		});
		$(".desktop4").animate({left: "0vw"}, "slow");
	});

});

