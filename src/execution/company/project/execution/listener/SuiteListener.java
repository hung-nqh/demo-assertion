package company.project.execution.listener;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

	private static boolean startedTestRun = false;
	
	/**
	 * Invoked once for each {@code <suite>} tag.
	 */
	@Override
	public void onStart(ISuite suite) {
		startOneTimeMethods();
	}

	/**
	 * Invoked once for each {@code <suite>} tag.
	 */
	@Override
	public void onFinish(ISuite suite) {
		// Intentionally empty
	}
	
	private synchronized void startOneTimeMethods() {
		if (startedTestRun == false) {
			startedTestRun = true;
			configTestRun();
			cleanAllureResultsFolder();
		}
	}
	
	private void configTestRun() {
		// Set system properties here
	}
	
	private void cleanAllureResultsFolder() {
		File folder = Path.of(System.getProperty("allure.results.directory")).toFile();
		FilenameFilter filter = (dir, name) -> name.equals("categories.json") == false;
		File[] allFiles = folder.listFiles(filter);
		
		if (allFiles != null) {
			for (File file : allFiles) {
				file.delete();
			}
		}
	}
}
