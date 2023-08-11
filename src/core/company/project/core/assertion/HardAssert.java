package company.project.core.assertion;

import org.testng.asserts.IAssert;

public class HardAssert extends AssertWrapper {

	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		super.onAssertFailure(assertCommand, ex);
		throw ex;
	}
}
