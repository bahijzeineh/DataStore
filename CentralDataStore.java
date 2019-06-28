package DataStore;

import java.util.HashMap;
import java.util.Map;

public class CentralDataStore 
{
	@SuppressWarnings("rawtypes")
	private static Map<Class<? extends DataStore>,DataStore> dataStores=null;
	
	public static void addDataStore(@SuppressWarnings("rawtypes") DataStore ds)
	{
		if(dataStores==null)
			dataStores=new HashMap<>();
		dataStores.put(ds.getClass(), ds);
		System.out.println("added dataStore: "+ds.getClass().getName());
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends DataStore> T get(Class<? extends DataStore> cls)
	{
		if(dataStores==null)
			return null;
		else
		{
			Object ds=dataStores.get(cls);
			if(ds!=null)
				return (T) ds.getClass().cast(ds);
		}
		return null;
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
