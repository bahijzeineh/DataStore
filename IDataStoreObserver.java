package DataStore;

public interface IDataStoreObserver<T extends DataStoreItem>
{
	public String getID();
	public void onAdd(T item);
	public void onUpdate(T item);
	public void onDelete(T item);
}