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
------------ | -------------

API Name | User SignIn
------------ | -------------
Url | http://localhost:2704/wallet/api/user/signIn
method | POST

### SignIn
### SignOut
## Wallet
### Recharge
### Transfer
### Check Status
### Passbook
