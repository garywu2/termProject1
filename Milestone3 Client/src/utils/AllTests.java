package utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Class which runs all the tests those being item test and 
 * supplier test
 *
 * @author  Gary Wu
 * @version 4.10.0
 * @since April 5, 2019
 */
@RunWith(Suite.class)
@SuiteClasses({ItemTest.class, SupplierTest.class})
public class AllTests {

}
