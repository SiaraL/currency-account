# Read Me

The following project has been done for a recruitment purposes.

# Getting Started

### Versions

- java 17.0.2
- maven 3.8.2

### Starting

1. To download project type:
   ```git copy https://github.com/SiaraL/currency-account.git```
2. **Go** to your path, eg. .../projects/currency-account
3. **Run** the project with your ide or type:
   ```mvn spring-boot:run```

# API

1. Creating account:
    - localhost:8080/api/account POST
    - request body: {firstName: string, lastName: string, balance: double}
    - response: **token**: string

2. Getting account:
    - localhost:8080/api/account?token=**token** GET
    - response: {firstName: string, lastName: string, balance: {[key: string]: string}}

3. Exchanging currencies:
    - localhost:8080/api/account?token=**token** PATCH
    - request body: {currencyFrom: string, currencyTo: string, amountFrom?: double, amountTo?: double}