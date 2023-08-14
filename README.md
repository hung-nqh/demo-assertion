- Need to have Java 11 or above installed, and set system environment variables for Java (JAVA_HOME and Path).
- For Windows, when running "mvn allure:serve" or "mvnw allure:serve", I observe that it runs 2 "java.exe" processes. When you close the CLI window (by Ctrl+C or by X button), it only ends 1 "java.exe" process related to Maven, then you can still view Allure report. It's a weird behavior when running Allure through Maven command.
So in that case, you need to open "Task Manager" of Windows to close the other "java.exe" process in the "Details" tab (or in the "Processes" tab if you know the display name of the "java.exe" you installed in your machine).
- Depending on your usage, you can remove unused objects from static collections at the end of each test case. For example, the static collection in TestFailures class is a good candidate.
