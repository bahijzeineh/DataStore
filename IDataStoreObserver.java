package DataStore;

public interface IDataStoreObserver
{
	public String getID();
	public void onAdd(DataStoreItem item);
	public void onUpdate(DataStoreItem item);
	public void onDelete(DataStoreItem item);
}