package gone;

import static org.junit.Assert.assertEquals; 
import org.junit.Test;

public class LocationTest {
	
	/*
	 * Method: offsetBy
	 * 
	 * Bad data: params are null
	 */
	@SuppressWarnings("null")
	@Test (expected = NullPointerException.class) 
	public void nullOffsetBy() {
		new Location(0, 0).offsetBy((Integer)null, (Integer)null);
	}

	/*
	 * Method: offsetBy
	 * 
	 * Structured basis: nominal case, offseting by 1 from location
	 * Good data: nominal case: expected value
	 * Boundary analysis: offset by 1 to the left
	 */
	@Test  
	public void offsetByOneToLeft() {
		Location result = new Location(2, 3).offsetBy(-1, 0);
		assertEquals(result.getRow(), 1);
		assertEquals(result.getColumn(), 3);
	}
	
	/*
	 * Method: offsetBy
	 * 
	 * Structured basis: nominal case, offseting by 1 from location
	 * Good data: nominal case: expected value
	 * Boundary analysis: offset by 1 to the right
	 */
	@Test  
	public void offsetByOneToRight() {
		Location result = new Location(2, 3).offsetBy(1, 0);
		assertEquals(result.getRow(), 3);
		assertEquals(result.getColumn(), 3);
	}
	
	/*
	 * Method: offsetBy
	 * 
	 * Structured basis: nominal case, offseting by 1 from location
	 * Good data: nominal case: expected value
	 * Boundary analysis: offset by 1 above
	 */
	@Test  
	public void offsetByOneAbove() {
		Location result = new Location(2, 3).offsetBy(0, -1);
		assertEquals(result.getRow(), 2);
		assertEquals(result.getColumn(), 2);
	}
	
	/*
	 * Method: offsetBy
	 * 
	 * Structured basis: nominal case, offseting by 1 from location
	 * Good data: nominal case: expected value
	 * Boundary analysis: offset by 1 below
	 */
	@Test  
	public void offsetByOneBelow() {
		Location result = new Location(2, 3).offsetBy(0, 1);
		assertEquals(result.getRow(), 2);
		assertEquals(result.getColumn(), 4);
	}
	
}