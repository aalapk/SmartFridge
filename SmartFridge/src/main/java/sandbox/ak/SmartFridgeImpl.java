package sandbox.ak;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of SmartFridgeManager interface
 *
 */
public class SmartFridgeImpl implements SmartFridgeManager {	
	
	/**
	 * A private inner class that represents an item
	 *
	 */
	private class Item {
		
		long itemType;
		String itemUUID;
		String name;
		double fillFactor;
		
		Item(long itemType,	String itemUUID, String name, double fillFactor) {
			
			this.itemType = itemType;
			this.itemUUID = itemUUID;
			this.name = name;
			this.fillFactor = fillFactor;
		}		
	}
	
	private List<Item> inventory;	
	private List<Long> itemsToIgnore;
	
	public SmartFridgeImpl() {
		
		inventory = new ArrayList<Item>();	 
		itemsToIgnore = new ArrayList<Long>();
	}
	
	
	/**
     * This method is called every time an item is removed from the fridge
     * Removes an item from the inventory, if found
     *
     * @param itemUUID
     */
	public void handleItemRemoved(String itemUUID) {
		
		Optional<Item> item = inventory
								.stream()
								.filter(i -> i.itemUUID == itemUUID)
								.findFirst();
								
		if(item.isPresent()) {			
			inventory.remove(item.get());
		}			
	}
	

	/**
     * This method is called every time an item is stored in the fridge
     * If an item is already present on the same container (itemUUID), it just updates the fillFactor with the one provided 
     *
     * @param itemType
     * @param itemUUID
     * @param name
     * @param fillFactor
     */
	public void handleItemAdded(long itemType, String itemUUID, String name, Double fillFactor) {
		
		if(fillFactor > 1.0) {
			throw new IllegalArgumentException();
		}
		
		Optional<Item> item = inventory
								.stream()
								.filter(i -> i.itemType == itemType && i.itemUUID == itemUUID)
								.findFirst();
		
		if(item.isPresent()) {
			item.get().fillFactor = fillFactor;
		}
		
		else {			
			inventory.add(new Item(itemType, itemUUID, name, fillFactor));
		}	
	}


	/**
     * Returns a list of items (other than item types to be ignored) whose average fill factor is lower than the one provided
     * The result is an a array of 'ItemQuantity' objects, which basically holds an item type and a fill factor 
     * 
     * @param fillFactor
     * 
     * @return ItemQuantity[] - an array of ItemQuantity objects, i.e. [ itemType, fillFactor ]
     */
	public ItemQuantity[] getItems(Double fillFactor) {
		
		return inventory
					.stream()
					.filter(i -> !itemsToIgnore.contains(i.itemType))
					.collect(Collectors.groupingBy(i -> i.itemType, Collectors.averagingDouble(f -> Double.valueOf(f.fillFactor))))
					.entrySet()
					.stream()					
					.filter(f -> f.getValue() <= fillFactor)
					.map(s -> new ItemQuantity(s.getKey(), s.getValue()))
					.toArray(ItemQuantity[]::new);
	}
	

	/**
     * Returns the average fill factor for a given item type to be displayed to the owner. 
     * 
     * @param itemType
     *
     * @return a double representing the average fill factor for the item type
     */
	public Double getFillFactor(long itemType) {
		
		Optional<Double> item = inventory
								.stream()
								.collect(Collectors.groupingBy(i -> i.itemType, Collectors.averagingDouble(j -> Double.valueOf(j.fillFactor))))
								.entrySet()
								.stream()
								.filter(i -> i.getKey() == itemType)
								.map(f -> f.getValue())
								.findFirst();
		
		if(!item.isPresent())
			return Double.valueOf(0);
		else
			return item.get();		
	}

	
	/**
     * Stop tracking a given item 
     *
     * @param itemType
     */
	public void forgetItem(long itemType) {
		
		if(!itemsToIgnore.contains(itemType))
			itemsToIgnore.add(itemType);			
		
	}
}
