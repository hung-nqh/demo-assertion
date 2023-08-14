package company.project.execution.listener;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import company.project.core.test.TestFailures;
import company.project.execution.logger.Log;
import company.project.execution.stacktrace.StackTrace;

public class InvokedMethodListener implements IInvokedMethodListener {

	/**
	 * Run after ITestListener.onTestStart(...)
	 */
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// Intentionally empty
	}

	/**
	 * Run before ITestListener.onTestFailure(...)
	 */
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			/**
			 * There're 4 cases :
			 * Failed by soft assert only.
			 * Failed by hard assert only.
			 * Failed by non-AssertionError only.
			 * Failed by soft assert and { hard assert || non-AssertionError }.
			 */
			handleFailedTestMethod(testResult);
			
			// Clean temporary objects
			TestFailures.getCollection().remove(testResult);
		}
	}
	
	private void handleFailedTestMethod(ITestResult testResult) {
		List<Throwable> testFailures = TestFailures.getFailures(testResult);
		logAndTrackNonAssertionError_TestMethod(testResult, testFailures);
		
		testFailures = TestFailures.getFailures(testResult);
		updateStatusOfTestResult(testResult, testFailures);
		setThrowableForTestResult(testResult, testFailures);
	}
	
	private void logAndTrackNonAssertionError_TestMethod(ITestResult testResult, List<Throwable> testFailures) {
		Throwable ex = testResult.getThrowable();
		
		Throwable lastAssertionError = (testFailures != null)
										? testFailures.get(testFailures.size() - 1)
										: null;
		if (ex != null && (ex.equals(lastAssertionError) == false)) {
			Log.exception().error(ex.getMessage());
			// Set non-AssertionError to be tracked by TestFailures
			TestFailures.addFailureToTestResult(ex);
		}
	}
	
	private void updateStatusOfTestResult(ITestResult testResult, List<Throwable> testFailures) {
		// Beware that soft assert does not throw exception,
		// need to proactively check and set status for test result
		if (testFailures != null) {
			testResult.setStatus(ITestResult.FAILURE);
		}
	}
	
	private void setThrowableForTestResult(ITestResult testResult, List<Throwable> testFailures) {
		if (testFailures != null) {
			testResult.setThrowable(StackTrace.combine(testFailures));
		}
	}
}
