var data;

$.get("StatUserServlet", {}, function(responseText) {
	data = responseText;
	setData();

});

window.chartColors = {
	red : 'rgb(255, 99, 132)',
	orange : 'rgb(255, 159, 64)',
	yellow : 'rgb(255, 205, 86)',
	green : 'rgb(75, 192, 192)',
	blue : 'rgb(54, 162, 235)',
	purple : 'rgb(153, 102, 255)',
	grey : 'rgb(201, 203, 207)',
	silver : 'rgb(192,192,192)'
};

var colorNames = Object.keys(window.chartColors);

var chartColors = window.chartColors;
var color = Chart.helpers.color;
var config = {
	data : {
		datasets : [ {
			data : [],
			backgroundColor : [],
			label : 'My dataset' // for legend
		} ],
		labels : []
	},
	options : {
		responsive : true,
		legend : {
			position : 'right',
		},
		title : {
			display : true,
			text : ''
		},
		scale : {
			ticks : {
				beginAtZero : true
			},
			reverse : false
		},
		animation : {
			animateRotate : true,
			animateScale : true
		}
	}
};

function setData() {
	for ( var key in data) {
		if (key.toString() != 'Total') {
			config.data.labels.push('' + key);
			config.data.datasets.forEach(function(dataset) {
				var colorName = colorNames[config.data.labels.length
						% colorNames.length];
				dataset.backgroundColor.push(color(
						window.chartColors[colorName]).alpha(0.5).rgbString());
				dataset.data.push(data[key]);
			});
			// window.myPolarArea.update();
		}
	}
	var ctx = document.getElementById("chart-area");
	window.myPolarArea = Chart.PolarArea(ctx, config);

}