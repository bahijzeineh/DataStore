package DataStore;

import java.util.HashMap;
import java.util.Map;

public class CentralDataStore 
{
	@SuppressWarnings("rawtypes")
	static Map<String,DataStore> dataStores=null;
	
	static void addDataStore(@SuppressWarnings("rawtypes") DataStore ds)
	{
		if(dataStores==null)
			dataStores=new HashMap<>();
		dataStores.put(ds.name, ds);
	}
	@SuppressWarnings("rawtypes")
	static DataStore get(String name)
	{
		if(dataStores==null)
			return null;
		else
			return dataStores.get(name);
	}
	@SuppressWarnings("rawtypes")
	static void closeDataStores()
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
