(function($) {

	"use strict";

	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();
	$(document).ready(function() {

		// target the link
		$(".toggle_hide_password").on('click', function(e) {
		  e.preventDefault()
	  
		  // get input group of clicked link
		  var input_group = $(this).closest('.input-group')
	  
		  // find the input, within the input group
		  var input = input_group.find('input.form-control')
	  
		  // find the icon, within the input group
		  var icon = input_group.find('i')
	  
		  // toggle field type
		  input.attr('type', input.attr("type") === "text" ? 'password' : 'text')
	  
		  // toggle icon class
		  icon.toggleClass('fa-eye-slash fa-eye')
		})
	  });

})(jQuery);
