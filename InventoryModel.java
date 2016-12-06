import java.util.Properties;

/*Model, contains a Properties.*/
public class InventoryModel {
	private Properties prop;
	
	//returns property (accessor method)
	public Properties getPropertyName() 
	{
	   return prop;
	}
	//mutator method for a Properties
	public void setPropertyName(Properties prop) 
	{
	   this.prop = prop;
	}
	
	//ADDS or EDITS an item in prop
	public void setProp(String key, String value)
	{
		prop.setProperty(key, value);
	}
	
	//DELETES an item in prop
	public void removeProp(String key)
	{
		prop.remove(key);
	}
}
