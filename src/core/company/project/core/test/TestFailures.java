package company.project.core.test;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestResult;
import org.testng.Reporter;

public class TestFailures {

	private static ConcurrentHashMap<ITestResult, LinkedList<Throwable>> allTestFailures = new ConcurrentHashMap<>();
	
	public static void addFailureToTestResult(Throwable ex) {
		ITestResult testResult = Reporter.getCurrentTestResult();
		LinkedList<Throwable> testFailures = allTestFailures.get(testResult);
		if (testFailures == null) {
			testFailures = new LinkedList<>();
			allTestFailures.put(testResult, testFailures);
		}
		testFailures.add(ex);
	}
	
	public static LinkedList<Throwable> getFailures(ITestResult testResult) {
		return allTestFailures.get(testResult);
	}
}
