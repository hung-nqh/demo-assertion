package company.project.execution.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	private static final int MESSAGE_MAX_LENGTH = 80;
	
	private static String cutDownMessage(String message) {
		int messageLength = message.length();
		
		if (messageLength > MESSAGE_MAX_LENGTH) {
			messageLength = MESSAGE_MAX_LENGTH;
			return message.substring(0, messageLength) + "...";
		}
		else {
			return message;
		}
	}
	
	  /////////////////
	 // GET LOGGERS //
	/////////////////
	
	public static StepLogger step() {
		return new StepLogger();
	}
	
	public static EventLogger event() {
		return new EventLogger();
	}
	
	public static AssertLogger assertion() {
		return new AssertLogger();
	}
	
	public static ExceptionLogger exception() {
		return new ExceptionLogger();
	}
	
	  ////////////////////
	 // DEFINE LOGGERS //
	////////////////////
	
	public static class StepLogger {
		private static Logger log = LogManager.getLogger("StepLogger");
		
		public void info(String message) {
			log.info(message);
		}
	}
	
	public static class EventLogger {
		private static Logger log = LogManager.getLogger("EventLogger");
		
		public void info(String message) {
			log.info(message);
		}
	}
	
	public static class AssertLogger {
		private static Logger log = LogManager.getLogger("AssertLogger");
		
		public void info(String message) {
			log.info(message);
		}
		
		public void error(String message) {
			log.error(cutDownMessage(message));
		}
	}
	
	public static class ExceptionLogger {
		private static Logger log = LogManager.getLogger("ExceptionLogger");
		
		public void error(String message) {
			log.error(cutDownMessage(message));
		}
	}
}
