package company.project.core.assertion;

public class Assert {

	private static SoftAssert soft = new SoftAssert();
	private static HardAssert hard = new HardAssert();
	
	public static SoftAssert soft() {
		return soft;
	}
	
	public static HardAssert hard() {
		return hard;
	}
}
