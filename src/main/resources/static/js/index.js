import {getTimeSerialAndProfit, initialize} from './request.js';
import {drow, updateChart} from './chart.js';

window.addEventListener('DOMContentLoaded', async () => {
    const yahooId = 'yahoo';
    const unibitsId = 'unibits';
    try {
        const [yahooData, unibitsData] = await initialize();
        const yahooChart = drow(yahooId, yahooData);
        const unibitsChart = drow(unibitsId, unibitsData);

        updateProfit(yahooId, yahooData.profit);
        updateProfit(unibitsId, unibitsData.profit);

        registerHandler(yahooChart, yahooId);
        registerHandler(unibitsChart, unibitsId);
    } catch (error) {
        alert(error.message);
    }
});

function registerHandler(chart, id) {
    const button = document.querySelector(`#${id} > .card > button`);
    const input = document.querySelector(`#${id} > .card input`);

    const successCallback = (symbol, statisticsData) => {
        const {profit, ...prices} = statisticsData;
        updateChart(chart, prices);
        updateSymnbol(id, symbol);
        updateProfit(id, profit);

        input.value = '';
        button.disabled = false;
    };

    const failCallback = () => {
        input.value = '';
        button.disabled = false;
    };

    const debouncedTimeSerialAndProfitFunc = debounce(getTimeSerialAndProfit, successCallback, failCallback);
    const update = async (event) => {
        event.stopPropagation();
        button.disabled = true;
        debouncedTimeSerialAndProfitFunc(id, input.value);
    };

    button.addEventListener('click', update);
    input.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            update(event);
        }
    });
}

function updateSymnbol(id, symbol) {
    const element = document.querySelector(`#${id}Symbol`);
    element.innerText = symbol;
}

function updateProfit(id, {startDate, endDate, profit}) {
    const element = document.querySelector(`#${id}Profit`);
    element.innerHTML = `
            <span class="startDate">${startDate}</span> ~
            <span class="endDate">${endDate}</span> :
            <span class="amount">${profit}</span> USD
    `;
}

function debounce(func, success, fail) {
    let setTimeoutId = null;
    return function (id, symbol) {
        if (!setTimeoutId) {
            setTimeoutId = setTimeout(async () => {
                try {
                    const statisticsData = await func(id, symbol);
                    setTimeoutId = null;
                    success(symbol, statisticsData);
                } catch (error) {
                    alert(error.message);
                    fail();
                }
            }, 0);
        }
    }
}