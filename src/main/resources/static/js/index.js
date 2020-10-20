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
        updateSymbol(id, symbol);
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

function updateSymbol(id, symbol) {
    const element = document.querySelector(`#${id}Symbol`);
    element.innerText = symbol;
}

function updateProfit(id, stockProfit) {
    const element = document.querySelector(`#${id}Profit`);
    if (!!stockProfit) {
        const {startDate, endDate, profit} = stockProfit;
        element.innerHTML = `
            <span>${startDate}</span> ~
            <span>${endDate}</span> :
            <span>${profit}</span> USD
        `;
    } else {
        element.innerHTML = `
            <span>최대 수익 구간이 존재하지 않습니다.</span> 
        `;
    }
}

function debounce(func, success, fail) {
    let setTimeoutId = null;
    return function (id, symbol) {
        if (!setTimeoutId) {
            setTimeoutId = setTimeout(async () => {
                try {
                    const statisticsData = await func(id, symbol);
                    success(symbol, statisticsData);
                } catch (error) {
                    alert(error.message);
                    fail();
                } finally {
                    setTimeoutId = null;
                }
            }, 0);
        }
    }
}