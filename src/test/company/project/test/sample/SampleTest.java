package company.project.test.sample;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import company.project.core.assertion.Assert;

@Test
public class SampleTest {

	public void TestCase_01() {
		// Passed
	}
	
	public void TestCase_02() {
		org.testng.asserts.SoftAssert soft = new SoftAssert();
		
		soft.assertTrue(false, "Soft Subject"); // failed
		
		// Throw exception for Hard Assert
		org.testng.Assert.assertTrue(false, "Hard Subject");
		
		// Cannot throw exception for Soft Assert
		soft.assertAll();
	}
	
	public void TestCase_03() {
		org.testng.asserts.SoftAssert soft = new SoftAssert();
		
		soft.assertTrue(false, "Soft Subject"); // failed
		
		// Throw exception for SoftAssert
		soft.assertAll();
		
		// Cannot throw exception for Hard Assert
		org.testng.Assert.assertTrue(false, "Hard Subject");
	}
	
	public void TestCase_04() {
		Assert.soft().assertTrue(true, "Soft Subject");
		Assert.soft().assertTrue(false, "Soft Subject"); // failed
		
		Assert.hard().assertTrue(true, "Hard Subject");
		Assert.hard().assertNotNull(null, "Hard Subject"); // failed
		
		// Cannot throw non-AssertionError
		System.out.println(1 / 0);
	}
	
	public void TestCase_05() {
		Assert.soft().assertTrue(true, "Soft Subject");
		Assert.soft().assertTrue(false, "Soft Subject"); // failed
		
		// Throw non-AssertionError
		System.out.println(1 / 0);
		
		Assert.hard().assertTrue(true, "Hard Subject");
		Assert.hard().assertNotNull(null, "Hard Subject"); // failed
	}
}
