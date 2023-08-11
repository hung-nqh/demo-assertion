package company.project.core.assertion;

import org.testng.asserts.IAssert;

public class SoftAssert extends AssertWrapper {

	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		super.onAssertFailure(assertCommand, ex);
		// Do not throw ex;
	}
}
