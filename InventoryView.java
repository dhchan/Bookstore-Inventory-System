import java.util.Properties;

//View, handles all the outputs to the console.
public class InventoryView {
	
	//prints the contents of a properties.
	public String printInventory(Properties prop)
	{
		//prop.list(System.out);
		//System.out.println(" ");
		return(prop.toString() + "\n");
	}
	
	//depending on what int is passed, will output a different line of text.
	public void printLine(int input)
	{
		if (input == 1)
			System.out.println("Books in Inventory:");
		else if (input == 2)
			System.out.println("CDs in Inventory:");
		else if (input == 3)
			System.out.println("DVDs in Inventory:");
		else if (input == 4)
		{
			System.out.println("Welcome to Books 'n Nobles Inventory System.");
			System.out.println("If you want to load an inventory, Enter '1'. Otherwise, Enter '2'.");
		}
		else if (input == 5)
			System.out.println("1 = Add, 2 = Edit, 3 = Delete, 4 =  Done:");
		else if (input == 6)
			System.out.println("1 = Book, 2 = CD, 3 = DVD:");
		else if (input == 7)
			System.out.println("Enter item Name:");
		else if (input == 8)
			System.out.println("Enter number of items:");
		else if (input == 9)
			System.out.println("Invalid Choice.");
	}
}
