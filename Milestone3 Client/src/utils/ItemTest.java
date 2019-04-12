package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This is a JUnit test class which essentially tests that the getter
 * setters of item work
 *
 * @author  Gary Wu
 * @version 4.10.0
 * @since April 5, 2019
 */
class ItemTest {

	/**
	 * Creates a new supplier and items and tests the items
	 * getteres and setters
	 */
	@Test
	public void test() {
		Supplier s = new Supplier(8043, "The man", "CHHS", "your boi");
		Item i = new Item(1011, "fruit", 50, 22.70, s);
		String result = i.getToolName();
		assertEquals("fruit", result);
		assertEquals(1011, i.getToolId());
		assertEquals(50, i.getToolQuantity());
		assertEquals(22.70, i.getToolPrice());
	}

}
