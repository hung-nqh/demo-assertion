package company.project.execution.reporter;

import java.io.Serializable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.testng.ITestResult;
import org.testng.Reporter;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

@Plugin(name = "AllureReport", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = false)
public class AllureReportAppender extends AbstractAppender {

	private AllureReportAppender(final String name, final Layout<? extends Serializable> layout) {
		super(name, null, layout, false, Property.EMPTY_ARRAY);
	}
	
	@PluginFactory
	public static AllureReportAppender createAppender(
			@PluginAttribute("name") @Required(message = "<AllureReport> appender: a name is required") final String name,
			@PluginElement("Layout") Layout<? extends Serializable> layout) {
		return new AllureReportAppender(name, layout);
	}

	@Override
	public void append(final LogEvent event) {
		Level level = event.getLevel();
		final Layout<? extends Serializable> layout = getLayout();
		String message;
		
		if (layout != null && layout instanceof AbstractStringLayout) {
			message = ((AbstractStringLayout) layout).toSerializable(event);
		} else {
			message = event.getMessage().getFormattedMessage();
		}
		
		if (level.intLevel() == Level.ERROR.intLevel() || level.intLevel() == Level.FATAL.intLevel()) {
			ITestResult testResult = Reporter.getCurrentTestResult();
			Throwable ex = testResult.getThrowable();
			if (ex != null && (ex instanceof AssertionError) == false) {
				// Although this won't mark test case as "Broken" but it's good to comply with Allure's conventions
				Allure.step(message, Status.BROKEN);
			} else {
				// Although this won't mark test case as "Failed" but it's good to comply with Allure's conventions
				Allure.step(message, Status.FAILED);
			}
		} else {
			Allure.step(message, Status.PASSED);
		}
	}
}
