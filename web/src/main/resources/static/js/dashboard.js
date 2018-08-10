jQuery(document).ready(function(){
    "use strict";
    function showTooltip(x, y, contents) {
	jQuery('<div id="tooltip" class="tooltipflot">' + contents + '</div>').css( {
	    position: 'absolute',
	    display: 'none',
	    top: y + 5,
	    left: x + 5
	}).appendTo("body").fadeIn(200);
    }
    /*****SIMPLE CHART*****/
    var newCust = [[0, 5], [1, 6], [2,7], [3, 9], [4, 12], [5, 14], [6, 15]];
    var retCust = [[0, 3], [1, 4], [2,6], [3, 8], [4, 11], [5, 13], [6,14]];
	
    var plot = jQuery.plot(jQuery("#basicflot"),
	[{
	    data: newCust,
	    label: "Portfolio Nr1",
	    color: "#03c3c4"
	},
        {
	    data: retCust,
	    label: "Portfolio Nr1",
	    color: "#905dd1"
        }
	],
	{
	    series: {
		lines: {
		    show: false
		},
		splines: {
		    show: true,
		    tension: 0.4,
		    lineWidth: 1,
		    fill: 0.4
		},
		shadowSize: 0
	    },
	    points: {
		show: true,
	    },
	    legend: {
		container: '#basicFlotLegend',
                noColumns: 0
	    },
	    grid: {
		hoverable: true,
		clickable: true,
		borderColor: '#ddd',
		borderWidth: 0,
		labelMargin: 5,
		backgroundColor: '#fff'
	    },
	    yaxis: {
		min: 0,
		max: 15,
		color: '#eee'
	    },
	    xaxis: {
		color: '#eee'
	    }
	});
		
	var previousPoint = null;
	jQuery("#basicflot").bind("plothover", function (event, pos, item) {
	jQuery("#x").text(pos.x.toFixed(2));
	jQuery("#y").text(pos.y.toFixed(2));
			
	if(item) {
	    if (previousPoint != item.dataIndex) {
		previousPoint = item.dataIndex;
						
		jQuery("#tooltip").remove();
		var x = item.datapoint[0].toFixed(2),
		y = item.datapoint[1].toFixed(2);
	 			
		showTooltip(item.pageX, item.pageY,
		item.series.label + " of " + x + " = " + y);
	    }
			
	} else {
	    jQuery("#tooltip").remove();
	    previousPoint = null;            
	}
		
    });
		
    jQuery("#basicflot").bind("plotclick", function (event, pos, item) {
	if (item) {
	    plot.highlight(item.series, item.datapoint);
	}
    });
    
    
    /*****CHART 2 *****/
    
    var visitors = [[0, 0], [1, 3], [2,2], [3, 7], [4, 4], [5, 5], [6, 6]];
    var unique = [[0, 0], [1, 2], [2,1], [3, 5], [4, 7], [5, 9], [6,8]];
	
    var plot = jQuery.plot(jQuery("#basicflot2"),
	[{
	    data: visitors,
	    label: "Portfolio Nr1",
	    color: "#394263"
	},
        {
	    data: unique,
	    label: "Portfolio Nr2",
	    color: "#b830b3"
        }
	],
	{
	    series: {
		lines: {
		    show: false
		},
		splines: {
		    show: true,
		    tension: 0.4,
		    lineWidth: 1,
		    fill: 0.5
		},
		shadowSize: 0
	    },
	    points: {
		show: true
	    },
	    legend: {
		container: '#basicFlotLegend2',
                noColumns: 0
	    },
	    grid: {
		hoverable: true,
		clickable: true,
		borderColor: '#ddd',
		borderWidth: 0,
		labelMargin: 5,
		backgroundColor: '#fff'
	    },
	    yaxis: {
		min: 0,
		max: 15,
		color: '#eee'
	    },
	    xaxis: {
		color: '#eee'
	    }
	});
		
	var previousPoint = null;
	jQuery("#basicflot2").bind("plothover", function (event, pos, item) {
	jQuery("#x").text(pos.x.toFixed(2));
	jQuery("#y").text(pos.y.toFixed(2));
			
	if(item) {
	    if (previousPoint != item.dataIndex) {
		previousPoint = item.dataIndex;
						
		jQuery("#tooltip").remove();
		var x = item.datapoint[0].toFixed(2),
		y = item.datapoint[1].toFixed(2);
	 			
		showTooltip(item.pageX, item.pageY,
		item.series.label + " of " + x + " = " + y);
	    }
			
	} else {
	    jQuery("#tooltip").remove();
	    previousPoint = null;            
	}
		
    });
		
    jQuery("#basicflot2").bind("plotclick", function (event, pos, item) {
	if (item) {
	    plot.highlight(item.series, item.datapoint);
	}
    });
    
    
    /*****CHART 3 *****/
    
    var impressions =       [[0, 0], [1, 5], [2,2], [3, 7], [4, 4], [5, 5], [6, 4]];
    var uniqueimpressions = [[0, 0], [1, 2], [2,1], [3, 6], [4, 3], [5, 6], [6,5]];
	
    var plot = jQuery.plot(jQuery("#basicflot3"),
	[{
	    data: impressions,
	    label: "Fund 1",
	    color: "#905dd1"
	},
        {
	    data: uniqueimpressions,
	    label: "Fund 2",
	    color: "#394263"
        }
	],
	{
	    series: {
		lines: {
		    show: false
		},
		splines: {
		    show: true,
		    tension: 0.4,
		    lineWidth: 1,
		    fill: 0.4
		},
		shadowSize: 0
	    },
	    points: {
		show: true
	    },
	    legend: {
		container: '#basicFlotLegend3',
                noColumns: 0
	    },
	    grid: {
		hoverable: true,
		clickable: true,
		borderColor: '#ddd',
		borderWidth: 0,
		labelMargin: 5,
		backgroundColor: '#fff'
	    },
	    yaxis: {
		min: 0,
		max: 15,
		color: '#eee'
	    },
	    xaxis: {
		color: '#eee'
	    }
	});
		
	var previousPoint = null;
	jQuery("#basicflot3").bind("plothover", function (event, pos, item) {
	jQuery("#x").text(pos.x.toFixed(2));
	jQuery("#y").text(pos.y.toFixed(2));
			
	if(item) {
	    if (previousPoint != item.dataIndex) {
		previousPoint = item.dataIndex;
						
		jQuery("#tooltip").remove();
		var x = item.datapoint[0].toFixed(2),
		y = item.datapoint[1].toFixed(2);
	 			
		showTooltip(item.pageX, item.pageY,
		item.series.label + " of " + x + " = " + y);
	    }
			
	} else {
	    jQuery("#tooltip").remove();
	    previousPoint = null;            
	}
		
    });
		
    jQuery("#basicflot3").bind("plotclick", function (event, pos, item) {
	if (item) {
	    plot.highlight(item.series, item.datapoint);
	}
    });



    /****** Sparks ******/

    
    jQuery('#sparkline').sparkline([0,12,11,13,11,13,12,11,12,13,14,13], {
	type: 'bar', 
        height:'30px',
        barColor: '#394263'
    });
    
    jQuery('#sparkline2').sparkline([0,8,8,6,9,10,6,5,5,7,8,9], {
	type: 'bar', 
	height:'30px',
        barColor: '#999'
    });
    
    jQuery('#sparkline3').sparkline([4,3,3,1,4,3,2,2,3,10,9,6], {
	type: 'bar', 
        height:'30px',
        barColor: '#394263'
    });
    
    jQuery('#sparkline4').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
	type: 'bar', 
	height:'30px',
        barColor: '#999'
    });
    
    jQuery('#sparkline5').sparkline([4,3,3,1,4,3,2,2,3,10,9,6], {
	type: 'bar', 
        height:'30px',
        barColor: '#394263'
    });
    
    jQuery('#sparkline6').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
	type: 'bar', 
	height:'30px',
        barColor: '#999'
    });


    jQuery('#sparkline7').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
    	type: 'bar',
    	height:'30px',
            barColor: '#394263'
        });
    

	 jQuery('#sparkline8').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
    	type: 'bar',
    	height:'30px',
            barColor: '#999'
        });

    jQuery('#sparkline9').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
        	type: 'bar',
        	height:'30px',
                barColor: '#394263'
            });

	 jQuery('#sparkline10').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
    	type: 'bar',
    	height:'30px',
            barColor: '#999'
        });

	jQuery('#sparkline11').sparkline([9,8,8,6,9,10,6,5,6,3,4,2], {
			type: 'bar',
			height:'30px',
				barColor: '#394263'
			});

    /***** BAR CHART *****/
    var m4 = new Morris.Bar({
        // ID of the element in which to draw the chart.
        element: 'stacked-chart',
        // Chart data records -- each entry in this array corresponds to a point on
        // the chart.
        data: [
            { y: '2006', a: 30, b: 20 },
            { y: '2007', a: 75,  b: 65 },
            { y: '2008', a: 50,  b: 40 },
            { y: '2009', a: 75,  b: 65 },
            { y: '2010', a: 50,  b: 40 },
            { y: '2011', a: 75,  b: 65 },
            { y: '2012', a: 100, b: 90 }
        ],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['Series A', 'Series B'],
        barColors: ['#1CAF9A', '#394263'],
        lineWidth: '1px',
        fillOpacity: 0.8,
        smooth: false,
        stacked: true,
        hideHover: true
    });
    
    
    
    // This will empty first option in select to enable placeholder
    jQuery('select option:first-child').text('');
    
    // Select2
    jQuery("select").select2({
        minimumResultsForSearch: -1
    });
                
    // Basic Wizard
    jQuery('#basicWizard').bootstrapWizard({
        onTabShow: function(tab, navigation, index) {
            tab.prevAll().addClass('done');
            tab.nextAll().removeClass('done');
            tab.removeClass('done');
                        
            var $total = navigation.find('li').length;
            var $current = index + 1;
                        
            if($current >= $total) {
                $('#basicWizard').find('.wizard .next').addClass('hide');
                $('#basicWizard').find('.wizard .finish').removeClass('hide');
            } else {
                $('#basicWizard').find('.wizard .next').removeClass('hide');
                $('#basicWizard').find('.wizard .finish').addClass('hide');
            }
        }
    });
    // This will submit the basicWizard form
    jQuery('.panel-wizard').submit(function() {    
        alert('This will submit the form wizard');
        return false // remove this to submit to specified action url
    });
	/*==Slim Scroll ==*/
	if ($.fn.slimScroll) {
		$('.event-list').slimscroll({
            height: '260px',
            wheelStep: 20
        });
		$('.widget-conversation-list').slimscroll({
			height: '360px',
			wheelStep: 35
		});
		$('.widget-todo ul').slimscroll({
			height: '300px',
			wheelStep: 35
		});
	}	
	/*Chat*/
	$(function () {
		$('.chat-input').keypress(function (ev) {
			var p = ev.which;
			var chatTime = moment().format("h:mm");
			var chatText = $('.chat-input').val();
			if (p == 13) {
				if (chatText == "") {
					alert('Empty Field');
				} else {
					$('<li class="clearfix"><div class="chat-avatar"><img class="img-responsive" src="images/photos/user1.png" alt="female"><i>' + chatTime + '</i></div><div class="conversation-text"><div class="ctext-wrap"><i>Salma Hayek</i><p>' + chatText + '</p></div></div></li>').appendTo('.widget-conversation-list');
					$(this).val('');
					$('.widget-conversation-list').scrollTo('100%', '100%', {
					easing: 'swing'
					});
				}
				return false;
				ev.epreventDefault();
				ev.stopPropagation();
			}
		});
		$('.chat-send .btn').click(function () {
			var chatTime = moment().format("h:mm");
			var chatText = $('.chat-input').val();
			if (chatText == "") {
				alert('Empty Field');
				$(".chat-input").focus();
			} else {
				$('<li class="clearfix"><div class="chat-avatar"><img class="img-responsive" src="images/photos/user1.png" alt="female"><i>' + chatTime + '</i></div><div class="conversation-text"><div class="ctext-wrap"><i>Salma Hayek</i><p>' + chatText + '</p></div></div></li>').appendTo('.widget-conversation-list');
				$('.chat-input').val('');
				$(".chat-input").focus();
				$('.widget-conversation-list').scrollTo('100%', '100%', {
					easing: 'swing'
				});
			}
			return false;
		});
	});
	/*==Slim Scroll ==*/
	if ($.fn.slimScroll) {
		$('.event-list').slimscroll({
            height: '260px',
            wheelStep: 20
        });
		$('.widget-conversation-list').slimscroll({
			height: '360px',
			wheelStep: 35
		});
	}
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