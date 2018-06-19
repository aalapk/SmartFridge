package sandbox.ak;

/**
 * This class is used to construct results for getItems() method.
 * The class provides an item type and a fill factor - what the getItems() expects. A list of ItemQuantity objects is returned from the getItems() implementation
 *
 */
public class ItemQuantity {
	
	private long itemType;
	private double fillFactor;
	
	public ItemQuantity(long itemType, double fillFactor) {
		this.itemType = itemType;
		this.fillFactor = fillFactor;
	}

	public long getItemType() {
		return itemType;
	}

	public void setItemType(long itemType) {
		this.itemType = itemType;
	}

	public double getFillFactor() {
		return fillFactor;
	}

	public void setFillFactor(double fillFactor) {
		this.fillFactor = fillFactor;
	}
}
