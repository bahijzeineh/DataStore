package DataStore;

import java.util.HashMap;
import java.util.Map;

public class CentralDataStore 
{
	@SuppressWarnings("rawtypes")
	private static Map<String,DataStore> dataStores=null;
	
	public static void addDataStore(@SuppressWarnings("rawtypes") DataStore ds)
	{
		if(dataStores==null)
			dataStores=new HashMap<>();
		dataStores.put(ds.name, ds);
	}
	@SuppressWarnings("rawtypes")
	public static DataStore get(String name)
	{
		if(dataStores==null)
			return null;
		else
			return dataStores.get(name);
	}
	@SuppressWarnings("rawtypes")
	public static void closeDataStores()
	{
		if(dataStores!=null)
		{
			for(String key : dataStores.keySet())
			{
				((DataStore)dataStores.get(key)).close();
				dataStores.remove(key);
			}
		}
	}
}
