package utils;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class ItemTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Supplier s = new Supplier(8043, "The man", "CHHS", "your boi");
		Item i = new Item(1011, "fruit", 50, 22.70, s);
		String result = i.getToolName();
		assertEquals("fruit", result);
		assertEquals(1011, i.getToolId());
		assertEquals(50, i.getToolQuantity());
	}

}
