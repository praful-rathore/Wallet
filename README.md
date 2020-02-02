# Wallet
User Wallet, Which is having functionality to do Sign-in/up, Recharge, Reverse, Check Status, Transfer, Passbook
# Project Setup
After clone/download this repository, run below sql query into your mysql server
```
create database wallet;
```
change application.properties value
```
file location = {download folder}/Wallet/src/main/resources/application.properties

#change this properties
spring.datasource.url=jdbc:mysql://localhost:{port number of your mysql server}/wallet?serverTimezone=UTC
spring.datasource.username={mysql username}
spring.datasource.password={mysql password}
```
run below command into your terminal, where you clone/download project
```
mvn clean
or
mvn clean install
```
start application jar
```
java -jar -Dspring.config.location={application properties file location of this project} {file location of this project}/target/projects-1.0-SNAPSHOT.jar
```
Verify application jar is running on port number 2705
# Api's
API Name | User SignUp
------------ | -------------
Url | http://localhost:2704/wallet/api/user/signUp
method | POST
request | {"userId":"0000000000", "password":"Praf@1234"}
response | {"headers":{},"body":{"userId":"0000000002","token":null,"walletBalance":0},"statusCode":"OK","statusCodeValue":200}

API Name | User SignIn
------------ | -------------
Url | http://localhost:2704/wallet/api/user/signIn
method | POST
request | {"userId":"0000000000","password":"Praf@1234"}
response | {"headers":{},"body":{"userId":"0000000000","token":"eyJhbGciOiJIixDtQ","walletBalance":30},"statusCode":"OK","statusCodeValue":200}

API Name | User SignOut
------------ | -------------
Url | http://localhost:2704/wallet/api/user/signOut
method | POST
header | Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMD
response | {"headers":{},"body":{"userId":"0000000002","token":null,"walletBalance":0},"statusCode":"OK","statusCodeValue":200}

API Name | Wallet Recharge
------------ | -------------
Url | http://localhost:2704/wallet/api/wallet/recharge
method | POST
header | Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMD
request | {"amount":40}
response | {"headers":{},"body":{"txnId":"02022223008","originalTxnId":null,"mode":"RECHARGE","amount":40,"txnStatus":"SUCCESS","walletLegs":[{"userId":"0000000000","sourceType":"CREDIT"}]},"statusCode":"OK","statusCodeValue":200}

API Name | Wallet Transfer
------------ | -------------
Url | http://localhost:2704/wallet/api/wallet/transfer
method | POST
request | {"amount":40}
response | {"headers":{},"body":{"txnId":"02022229010","originalTxnId":null,"mode":"TRANSFER","amount":7.01,"txnStatus":"SUCCESS","walletLegs":[{"userId":"0000000001","sourceType":"CREDIT"},{"userId":"0000000000","sourceType":"DEBIT"}]},"statusCode":"OK","statusCodeValue":200}

API Name | Wallet Check Status
------------ | -------------
Url | http://localhost:2704/wallet/api/wallet/status
method | POST
header | Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMD
request | {"txnId":"02022104001"}
response | {"headers":{},"body":{"txnId":"02022104001","originalTxnId":null,"mode":"TRANSFER","amount":1.99,"txnStatus":"SUCCESS","walletLegs":[{"userId":"0000000000","sourceType":"DEBIT"},{"userId":"0000000001","sourceType":"CREDIT"}]},"statusCode":"OK","statusCodeValue":200}

API Name | Wallet Passbook
------------ | -------------
Url | http://localhost:2704/wallet/api/wallet/passbook
method | POST
header | Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMD
request | {}
response | {"headers":{},"body":{"limit":20,"offset":0,"total":8,"results":[{"txnId":"02022229010","originalTxnId":null,"mode":"TRANSFER","amount":7.01,"txnStatus":"SUCCESS","walletLegs":[{"userId":"0000000001","sourceType":"CREDIT"},{"userId":"0000000000","sourceType":"DEBIT"}]}],"empty":false},"statusCode":"OK","statusCodeValue":200}
