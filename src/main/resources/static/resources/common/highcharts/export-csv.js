/**
 * A small plugin for getting the CSV of a categorized chart
 */
(function (Highcharts) {
    
    
    var each = Highcharts.each;
    Highcharts.Chart.prototype.getCSV = function () {
        var columns = [],
            line,
            tempLine,
            csv = "", 
            row,
            col,
            options = (this.options.exporting || {}).csv || {},

            // Options
            dateFormat = options.dateFormat || '%Y-%m-%d %H:%M:%S',
            itemDelimiter = options.itemDelimiter || ',', // use ';' for direct import to Excel
            lineDelimiter = options.lineDelimeter || '\n';

        each (this.series, function (series) {
            if (series.options.includeInCSVExport !== false) {
                if (series.xAxis) {
                    var xData = series.xData.slice(),
                        xTitle = 'X values';
                    if (series.xAxis.isDatetimeAxis) {
                        xData = Highcharts.map(xData, function (x) {
                            return Highcharts.dateFormat(dateFormat, x)
                        });
                        xTitle = 'DateTime';
                    } else if (series.xAxis.categories) {
                        xData = Highcharts.map(xData, function (x) {
                            return Highcharts.pick(series.xAxis.categories[x], x);
                        });
                        xTitle = 'Category';
                    }
                    columns.push(xData);
                    columns[columns.length - 1].unshift(xTitle);
                }
                columns.push(series.yData.slice());
                columns[columns.length - 1].unshift(series.name);
            }
        });

        // Transform the columns to CSV
        for (row = 0; row < columns[0].length; row++) {
            line = [];
            for (col = 0; col < columns.length; col++) {
                line.push(columns[col][row]);
            }
            csv += line.join(itemDelimiter) + lineDelimiter;
        }

        return csv;
    };

    // Now we want to add "Download CSV" to the exporting menu. We post the CSV
    // to a simple PHP script that returns it with a content-type header as a 
    // downloadable file.
    // The source code for the PHP script can be viewed at 
    // https://raw.github.com/highslide-software/highcharts.com/master/studies/csv-export/csv.php
    if (Highcharts.getOptions().exporting) {
        Highcharts.getOptions().exporting.buttons.contextButton.menuItems.push({
            text: Highcharts.getOptions().lang.downloadCSV || "Download CSV",
            onclick: function () {
                Highcharts.post('http://www.highcharts.com/studies/csv-export/csv.php', {
                    csv: this.getCSV()
                });
            }
        });
    }
}(Highcharts));

Highcharts.theme = {
    colors: ['#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
        backgroundColor: {
        linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
        stops: [
            [0, 'rgb(255, 255, 255)'],
            [1, 'rgb(240, 240, 255)']
        ]},
        borderWidth: 2,
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1
    },
    title: {
        style: {
            color: '#000',
            font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
        }
    },
    subtitle: {
        style: {
            color: '#666666',
            font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
        }
    },
    xAxis: {
        gridLineWidth: 1,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '14px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
	            color: '#333',
	            fontWeight: 'bold',
	            fontSize: '12px',
	            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
            }
        }
    },
    yAxis: {
        minorTickInterval: 'auto',
        lineColor: '#000',
        lineWidth: 1,
        tickWidth: 1,
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '14px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
	            color: '#333',
	            fontWeight: 'bold',
	            fontSize: '12px',
	            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
            }
        }
    },
    legend: {
	      itemStyle: {
	         font: '9pt Trebuchet MS, Verdana, sans-serif',
	         color: 'black'
	      },
	      itemHoverStyle: {
	         color: '#039'
	      },
	      itemHiddenStyle: {
	         color: 'gray'
	      }
   }
};

jQuery.fn.extend({
	getForm:function (c) {
		var d = jQuery.extend({prefix:"", data:{}}, c);
		var b = [];
		var a = this;
		if(!this.is("form")){
			a = this.find("form");
		}
		a.each(function () {
			var e = $(this);
			var f = {};
			e.find(":text,:hidden,select:selected,:radio:checked,textarea").each(function () {
				var h = $(this);
				var g = h.attr("name");
				if(h.attr("disabled")){return;}
				if(!g){return;}
				var i = g.replace(d.prefix, "");
				if (d.data[i]) {
					f[i] = d.data[i];
					return;
				}
				if (d.prefix && g.indexOf(d.prefix) < 0) {
					return;
				}
				f[i] = h.val();
			});
			b.push(f);
		});
		if (b.length === 1) {
			return b[0];
		}
		return b;
	}
});