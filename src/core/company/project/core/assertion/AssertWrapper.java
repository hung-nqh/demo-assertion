package company.project.core.assertion;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import company.project.core.test.TestFailures;
import company.project.execution.logger.Log;

public abstract class AssertWrapper extends Assertion {

	@Override
	protected void doAssert(IAssert<?> assertCommand) {
		try {
			assertCommand.doAssert();
			onAssertSuccess(assertCommand);
		} catch (AssertionError ex) {
			onAssertFailure(assertCommand, ex);
		}
	}
	
	@Override
	public void onAssertSuccess(IAssert<?> assertCommand) {
		String assertType = getAssertType();
		String subject = (assertCommand.getMessage() != null)
							? (assertCommand.getMessage() + " ")
							: "";
		Log.assertion().info(String.format("%s Assert : %s[Expected] %s [Actual] %s",
				assertType,
				subject,
				assertCommand.getExpected(),
				assertCommand.getActual()));
	}

	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		String assertType = getAssertType();
		Log.assertion().error(assertType + " Assert : " + ex.getMessage());
		
		TestFailures.addFailureToTestResult(ex);
	}
	
	private String getAssertType() {
		if (this instanceof SoftAssert) {
			return "Soft";
		}
		else {
			return "Hard";
		}
	}
}
