set scriptPath=%~dp0
set resultsPath=%scriptPath%target\allure-results
set targetPath=%scriptPath%test-output\allure-report

:: Use "--clean" to force Allure to overwrite old files.
:: Use "&" in the command below to chain 2 different commands together
:: because "allure generate" auto closes window without executing the next command.
allure generate --clean "%resultsPath%" -o "%targetPath%" & allure open "%targetPath%"
