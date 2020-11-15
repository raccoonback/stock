# stock

![](assets/demo.gif)

## 📋 Features


1. Yahoo Finance API 데이터를 조회하여 180일간에 최대 수익을 낼 수 있는 날짜 구간 탐색
2. UniBits API 데이터를 조회하여 180일간에 최대 수익을 낼 수 있는 날짜 구간 탐색

- 🔍  [탐색 코드](https://github.com/raccoonback/stock/blob/94905fe8c7a17e843a4d55fce10f58a08b518728/src/main/java/com/test/stock/stock/service/StockService.java#L84-L117)


## 🤔 Verification

1. [Yahoo Finance API 이용한 검증](https://github.com/raccoonback/stock/blob/main/src/test/java/com/test/stock/stock/service/YahooStockServiceTest.java)
  - ✔ 일(day), 주(week) 단위의 최대 수익이 동일 여부 확인
  - ✔ 일(day), 월(month) 단위의 최대 수익이 동일 여부 확인

  ```
    ❗ 일(day), 주(week), 월(month)에 대한 예외 처리
    주(week), 월(month) 경우에는 시작 날짜가 각각 첫 번째 요일(월요일), 1일에 시작하기 때문에 오차가 발생한다.
    예를 들어서, 마지막 날짜가 2020-10-21 경우에는 일(day)단위로 조회하면 시작 날짜가 2020-04-24 이다.
    하지만 주(week)단위로 조회하면 2020-04-20부터의 데이터를 가져오게 되고, 윌(month)단위로는 2020-04-01부터의 데이터를 가져온다.
    결과적으로 주(week)단위에서는 20~23일에 대한 노이즈 데이터가 발생하고, 윌(month)단위에서는 01~23일에 대한 잘못된 데이터가 같이 조회되어 오차가 발생하게 된다.

    따라서 주(week)단위 경우에는 기존의 시작 날짜로 부터의 다음주 월요일로 정하고, 월(month)단위에는 다음달 1일을 시작날짜로 설정한다.
    포함되지 않은 날짜의 데이터는 일(day)단위로 추가 조회하여 오차를 제거한다.
    즉, 2020-04-20 경우에 주(week)단위는 04-27일을 시작 날짜로 설정해 조회하고 24~26일에 대한 데이터를 추가로 조회한다.
    또한 월(month)단위는 05-01일을 시작 날짜로 설정해 조회하고 24~30일에 대한 데이터를 추가로 조회한다.
  ```
  - 🔍  예외처리 코드: [일단위로 포함되지 않은 날짜를 추가로 조회](https://github.com/raccoonback/stock/blob/94905fe8c7a17e843a4d55fce10f58a08b518728/src/main/java/com/test/stock/stock/service/YahooStockService.java#L47-L60), [일(day), 주(week), 월(month)에 따른 시작 날짜 변경](https://github.com/raccoonback/stock/tree/94905fe8c7a17e843a4d55fce10f58a08b518728/src/main/java/com/test/stock/stock/service/strategy/yahoo/frequency)

2. [Yahoo Finance API와 UniBits API를 비교하여 검증](https://github.com/raccoonback/stock/blob/main/src/test/java/com/test/stock/stock/service/UniBitsServiceTest.java)
  - ✔ 일(day)단위로 최대 수익을 내기 위한 매수/매도 날짜와 최대 수익금에 대한 동일 여부 확인

## 📎 ThirdParty API

- [UniBits](https://unibit.ai/api/docs/V2.0/historical_stock_price)
- [Yahoo Finance](https://rapidapi.com/apidojo/api/yahoo-finance1?endpoint=apiendpoint_a1e0ecc6-0a3a-43fd-8133-77a66d33f68c)

### 🧨 Caution
- Yahoo Finance API는 월당 500개의 요청만으로 제한하고 있기 때문에, 많은 요청 과정에서 데이터 조회가 막힐 수 있습니다.
- 또한, Yahoo Finance API 요청 처리가 오래 걸리기 때문에 초기에 차트 렌더링이 오래 지연될 수 있습니다.

## 🕶 How to run

### Prepared

> [application.yml](/src/main/resources/application.yml) 파일에 Stock Third Party API 추가 필요 

### Run
> ./gradlew bootRun

> ./gradlew test

## 🏷 Development environment
- springboot 2.3.4
- java 8
- junit
- thymeleaf
- javascript
