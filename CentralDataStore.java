package DataStore;

import java.util.HashMap;
import java.util.Map;

//this centralised data store assumes there will be only one instance of each DataStore
//in the system. if this is not desired then do not use this class as only the first
//instantiation of each DataStore will be present here unless one manually registers
//a later instance with the override flag set to true.
public class CentralDataStore 
{
	@SuppressWarnings("rawtypes")
	private static Map<Class<? extends DataStore>,DataStore> dataStores=null;
	
	public static boolean registerDataStore(@SuppressWarnings("rawtypes") DataStore ds)
	{
		return registerDataStore(ds,false);
	}
	public static boolean registerDataStore(@SuppressWarnings("rawtypes") DataStore ds, boolean override)
	{
		if(dataStores==null)
			dataStores=new HashMap<>();

		if(override || !dataStores.containsKey(ds.getClass()))
		{
			dataStores.put(ds.getClass(), ds);
			System.out.println("registered dataStore: "+ds.getClass().getName());
			return true;
		}
		return false;
	}
	@SuppressWarnings({ "rawtypes" })
	public static DataStore get(Class<? extends DataStore> cls)
	{
		if(dataStores==null)
			return null;
		else
			return (DataStore)dataStores.get(cls);
	}
	@SuppressWarnings("rawtypes")
	public static void closeDataStores()
	{
		if(dataStores!=null)
		{
			for(Class<? extends DataStore> key : dataStores.keySet())
			{
				dataStores.get(key).close();
				dataStores.remove(key);
			}
		}
	}
}
