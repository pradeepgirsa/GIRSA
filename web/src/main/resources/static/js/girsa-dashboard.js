jQuery(document).ready(function(){

	/*==Calendar==*/
	$(document).on('click', '.event-close', function () {
		$(this).closest("li").remove();
		return false;
	});
	$(function () {
		var today_date = moment().format("ddd");
		$('<span>today : ' + today_date + '</span>').appendTo('.cal-day');
		
		$('.evnt-input').keypress(function (e) {
			var p = e.which;
			var inText = $('.evnt-input').val();
			if (p == 13) {
				if (inText == "") {
					alert('Empty Field');
				} else {
					$('<li>' + inText + '<a href="#" class="event-close"> <i class="fa fa-trash-o"></i> </a> </li>').appendTo('.event-list');
					$(this).val('');
					$('.event-list').scrollTo('100%', '100%', {
						easing: 'swing'
						});
					}
				return false;
				e.epreventDefault();
				e.stopPropagation();
			}
		});
	});	
	jQuery('#calendar').fullCalendar({
		header: {
			left: 'today',
			center: 'prev title next',
			right: ''
		},
	});  	
});