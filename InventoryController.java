//Controller. Contains an Inventory
public class InventoryController {

	private InventoryModel item;
	private InventoryView view;

	//Inventory Controller Constructor
	public InventoryController(InventoryModel item, InventoryView view){
	   this.item = item;
	   this.view = view;
	}
	
	//calls the setProperty function on the InventoryModel
	public void addItem(String key, String value){
		item.setProp(key, value);
	}
	//calls the remove function on the InventoryModel
	public void removeItem(String key){
		item.removeProp(key);
	}
	
	//prints contents of Property via the InventoryView.
	public String updateView(){				
	    return (view.printInventory(item.getPropertyName()));
	}
	
	//prints lines depending on input, calling printline function on a view.
	public void updateView(int input){
		view.printLine(input);
	}
}
