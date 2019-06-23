package DataStore;

public interface IObserver
{
	public String getID();
	public void onAdd(DataStoreItem item);
	public void onUpdate(DataStoreItem item);
	public void onDelete(DataStoreItem item);
}