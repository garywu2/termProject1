package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This is a JUnit test class which essentially tests that the getter
 * setters of supplier work
 *
 * @author  Gary Wu
 * @version 4.10.0
 * @since April 5, 2019
 */
class SupplierTest {

	/**
	 * Creates a new supplier and tests the suppliers
	 * getteres and setters
	 */
	@Test
	void test() {
		Supplier s = new Supplier(8043, "The man", "CHHS", "your boi");
		String result = s.getName();
		assertEquals(8043, s.getId());
		assertEquals(result, s.getName());
	}

}
