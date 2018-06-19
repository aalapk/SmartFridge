package sandbox.ak;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The class provides some basic tests for the functionality of SmartFridgeImpl class methods
 * It uses JUnit framework 
 *
 */
public class SmartFridgeImplTest {
	
	private SmartFridgeImpl smartFridge;
	
	@Before
	public void Initialize() {
		smartFridge = new SmartFridgeImpl();
	}
	
	@After
    public void tearDown() {
		smartFridge = null;
    }
	
	@Test
	public void handleItemAdded_itemAdded_correctFillFactor() {
		
		smartFridge.handleItemAdded(9, "Container1", "Lucrene Whole Milk", 0.1);
		smartFridge.handleItemAdded(9, "Container2", "Lucrene Whole Milk", 0.3);
		assertTrue(Math.abs(0.2 - smartFridge.getFillFactor(9)) < 0.0001);				
	}
	
	@Test
	public void handleItemAdded_itemAddedToSameContainer_fillFactorUpdated() {
		
		smartFridge.handleItemAdded(9, "Container1", "Lucrene Whole Milk", 0.1);
		smartFridge.handleItemAdded(9, "Container1", "Lucrene Whole Milk", 0.2);		
		assertEquals(Double.valueOf(0.2), smartFridge.getFillFactor(9));		
	}
	
	@Test
	public void getItems_itemsAddedWithDifferentFillFactors_correctItemsReturned() {
		
		smartFridge.handleItemAdded(9, "Container1", "Lucrene Whole Milk", 0.1);
		smartFridge.handleItemAdded(9, "Container2", "Lucrene Whole Milk", 0.7);
		smartFridge.handleItemAdded(2, "Container3", "Cage Free Brown Eggs", 0.2);
		smartFridge.handleItemAdded(3, "Container4", "Organic Chicken", 0.7);
		
		String resultItms = "";
		for (ItemQuantity iq : smartFridge.getItems(0.6)) {
			resultItms = resultItms + iq.getItemType();			
		}
		assertEquals("29" , resultItms); //String "29" derived as concatenation of itemType IDs 2 and 9 		
	}
	
	@Test
	public void removeItems_itemsAddedAndremoved_correctFillFactors() {
		
		smartFridge.handleItemAdded(9, "Container1", "Lucrene Whole Milk", 0.1);
		smartFridge.handleItemAdded(9, "Container2", "Lucrene Whole Milk", 0.3);
		smartFridge.handleItemAdded(9, "Container3", "Lucrene Whole Milk", 0.7);
		smartFridge.handleItemAdded(2, "Container4", "Cage Free Brown Eggs", 0.2);		
		smartFridge.handleItemRemoved("Container2");
		assertTrue(Math.abs(0.4 - smartFridge.getFillFactor(9)) < 0.0001);		
	}
	
	@Test
	public void forgetItem_activeAndForgottenItemsAdded_correctFillFactors() {
		
		smartFridge.handleItemAdded(9, "Container1", "Lucrene Whole Milk", 0.1);
		smartFridge.handleItemAdded(9, "Container2", "Lucrene Whole Milk", 0.3);		
		smartFridge.handleItemAdded(2, "Container3", "Cage Free Brown Eggs", 0.2);
		smartFridge.handleItemAdded(3, "Container4", "Organic Chicken", 0.7);
		smartFridge.forgetItem(2);
		
		assertEquals(1, smartFridge.getItems(0.5).length);		
	}
}
