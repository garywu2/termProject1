package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ItemTest {

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
