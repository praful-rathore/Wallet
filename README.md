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
## User
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column
### SignUp
### SignIn
### SignOut
## Wallet
### Recharge
### Transfer
### Check Status
### Passbook
