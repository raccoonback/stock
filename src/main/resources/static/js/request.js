function initialize() {
    const yahooSymbol = document.querySelector('#yahooSymbol').textContent;
    const unibitsSymbol = document.querySelector('#unibitsSymbol').textContent;
    return Promise.all([getTimeSerialAndProfit('yahoo', yahooSymbol), getTimeSerialAndProfit('unibits', unibitsSymbol)])
        .then(result => result.flat());
}

function validate(symbol) {
    return !symbol && !symbol.length;
}

function feedback(api, symbol, response) {
    if (response.status === 400) {
        throw new Error(`${symbol}은 존재하지 않는 종목입니다. 다시 확인해주세요.`);
    } else if (response.status === 500) {
        throw new Error(`${api}에서 데이터를 조회하는 과정에서 예기치 않은 에러가 발생했습니다. 다시 시도해주세요.`);
    }
}

async function getTimeSerialAndProfit(api, symbol) {
    if (validate(symbol)) {
        throw new Error("잘못된 형식의 종목입니다");
    }

    const response = await requestApi(`/api/stock/${api}/${symbol}`);
    feedback(api, symbol, response);
    const {stockProfit, stockPrices} = await response.json();
    return {
        dates: stockPrices.map(it => it.date),
        high: stockPrices.map(it => it.maxPrice),
        low: stockPrices.map(it => it.minPrice),
        max: stockPrices.find(it => it.date === stockProfit.endDate),
        min: stockPrices.find(it => it.date === stockProfit.startDate),
        profit: stockProfit
    }
}

function requestApi(uri) {
    const maximumTryCount = 3;
    let tryCount = 1;
    return (async function request() {
        try {
            return fetch(uri, {method: 'GET'});
        } catch (error) {
            if (tryCount >= maximumTryCount) {
                throw new Error("확인되지 않은 에러가 발생하였습니다. 잠시후 다시 시도해주세요.");
            } else {
                tryCount += 1;
                return request();
            }
        }
    })();
}

export {
    initialize, getTimeSerialAndProfit
};