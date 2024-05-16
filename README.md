1) The main goal of this Maven based framework is test Spotify API:

a) app: https://open.spotify.com/

b)  API documentation: https://developer.spotify.com/documentation/web-api


2) Credentials 
The project uses Authorization Code Flow as a part of OAuth2 concept. With the token manager class and their methods If the access token expired methods autmatically generate new access token by means refresh token.

To obtain required credentials go to: 
https://developer.spotify.com/documentation/web-api/tutorials/code-flow

and follow instructions over there. Then put obtained credentials in: src/test/resources/config.properties file.

For the presentation purpose you can just ping me to show how tests works with my private credentials. I don't storage credentials in GitHub due to safety rules.

3) Used Tools and Technologies:
   
a) Rest Assured: https://rest-assured.io/

b) TestNG: https://testng.org/

c) Java: https://www.java.com/pl/

d) Allure Reports: https://allurereport.org/docs/

e) Hamcrest: https://hamcrest.org/JavaHamcrest/

f) Lombok: https://projectlombok.org/


4) Running tests
   
a) single: using Maven (install maven-compiler-plugin and maven-surefire-plugin in advance): with cmd: Go to project root directory type mvn clean test and press enter

b) in parallel: in pom.xml in configuration section in maven-surefire-plugin section paste:

	<parallel>methods</parallel>
 	<threadCount>10</threadCount> //optional entry - without this default is 5

then use mvn test

c) to run in terminal / cmd with choosen environments (QA or production stage) run with: 
mvn test -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com"

Edit in com/spotify/oauth2/api/SpecBuilder.java enable lines 17 and 28, disable lines 18 and 29. 

d) to run both way (cmd/terminal and play button(run) in IDE yo have to go to:
in InteliJ: Run -> Edit Configurations -> Edit Configuration Templates ->select TestNG in JDK Settings section in VM options paste:
-ea Dtestng.dtd.http=true -DBASE_URI="https://api.spotify.com" -DACCOUNT_BASE_URI="https://accounts.spotify.com"


5) Allure tests results reporting:
   
a) install:
https://allurereport.org/docs/install-for-windows/

b) configuration with TestNG:
https://allurereport.org/docs/testng/

c) generate a report: 
Run tests, then in terminal use: allure serve target/allure-results
This command automatically open a report in Browser. You can edit target folder in src/test/resources/allure.properties in line 1.
