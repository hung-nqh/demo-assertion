set scriptPath=%~dp0
cd %scriptPath%
mvnw clean test & mvnw allure:serve
