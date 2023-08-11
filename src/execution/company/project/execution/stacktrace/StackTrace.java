package company.project.execution.stacktrace;

import java.util.LinkedList;
import java.util.List;

public class StackTrace {

	public static Throwable combine(List<Throwable> list) {
		return combine(list.toArray(new Throwable[list.size()]));
	}
	
	public static Throwable combine(Throwable... list) {
		if (list == null || list.length == 0) {
			return null;
		}
		
		Throwable newEx;
		LinkedList<StackTraceElement> newStackTrace = new LinkedList<>();
		Throwable lastEx = list[list.length - 1];
		
		// Note that Allure report only marks a test case as "failed" (not "broken") if the Throwable is AssertionError
		if (lastEx instanceof AssertionError) {
			newEx = new AssertionError("Summary of Failures"); // for "failed" test case 
		}
		else {
			newEx = new Throwable("Summary of Failures");  // for "broken" test case
		}
		
		for (int i = 0; i < list.length; i++) {
			Throwable ex = list[i];
			String subject = (String.format("Failure %s of %s : %s",
											i + 1,
											list.length,
											ex.toString()));
			// Minor issue : The line which contains "subject" may not be displayed in TestNG window inside Eclipse
			newStackTrace.add(new StackTraceElement(subject, "*", "*", 0));
			for (StackTraceElement item : ex.getStackTrace()) {
				newStackTrace.add(item);
			}
		}
		newEx.setStackTrace(newStackTrace.toArray(new StackTraceElement[newStackTrace.size()]));
		return newEx;
	}
}
