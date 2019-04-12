package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SupplierTest {

	@Test
	void test() {
		Supplier s = new Supplier(8043, "The man", "CHHS", "your boi");
		String result = s.getName();
		assertEquals(8043, s.getId());
		assertEquals(result, s.getName());
	}

}
