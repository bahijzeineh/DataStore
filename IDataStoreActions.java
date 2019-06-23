package DataStore;

public interface IDataStoreActions<T extends DataStoreItem>
{
    public void load();
    public void save();
    public T find(String id);
	public void add(T item) throws DuplicateIDException;
	public void update(T item) throws ItemNotFoundException;
	public void delete(String id) throws ItemNotFoundException;
} 