function drow(id, data) {
    const {dates, high, low, min, max} = data;
    const ctx = document.getElementById(`${id}Chart`);
    return new Chart(ctx, {
        type: 'line',
        data: {
            labels: dates,
            datasets: [{
                label: 'high',
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                ],
                data: high,
                borderWidth: 1,
                order: 1
            }, {
                label: 'low',
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(255, 99, 132, 0.2)'
                ],
                data: low,
                borderWidth: 1,
                order: 3
            },
                {
                    label: 'profit',
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.0)',
                        'rgba(75, 192, 192, 0.0)',
                    ],
                    borderColor: [
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    data: [{x: min.date, y: min.minPrice}, {x: max.date, y: max.maxPrice}],
                    borderWidth: 5,
                    order: 1
                }
            ]
        },
        options: {}
    });
}

function updateChart(chart, data) {
    const {dates, high, low, min, max} = data;
    chart.data.labels = dates;
    chart.data.datasets[0].data = high;
    chart.data.datasets[1].data = low;
    chart.data.datasets[2].data = [{x: min.date, y: min.minPrice}, {x: max.date, y: max.maxPrice}];
    chart.update();
}

export {
    drow, updateChart
}