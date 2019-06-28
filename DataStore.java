package DataStore;

import java.util.ArrayList;

public abstract class DataStore<T extends DataStoreItem, ParseParam> implements IDataStoreActions<T>
{
	protected String name;
	protected ArrayList<T> data;
	protected ArrayList<IDataStoreObserver<T>> observers;
		
	//name is used for message logging
	public DataStore(String name)
	{
		this.name=name;
		data=new ArrayList<T>();
        observers=new ArrayList<IDataStoreObserver<T>>();
        CentralDataStore.addDataStore(this);
	}
	
	public abstract void load();//shouldn't need to call this directly as its done in constructor
	public abstract void save();//called in close() so shouldn't need to call directly
	
	protected void close()
	{
		save();
		data.clear();
	}
	
	//find, add, update, delete functions
	public T find(String id)
	{
		for(int i=0;i<data.size();++i)
			if(data.get(i).getID().equals(id))
				return data.get(i);
		return null;
	}
	public void add(T item) throws DuplicateIDException
	{
		if(find(item.getID())!=null)
		{
			String err="error: attempt to add duplicate item in '"+name+"' with id "+item.getID();
			throw new DuplicateIDException(err);
		}
		data.add(item);
		notifyObserversOfAdd(item);
	}
	public void update(T item) throws ItemNotFoundException
	{
		DataStoreItem target=find(item.getID());
		if(target==null)
		{
			String err="error: could not find item with id "+item.getID()+" in "+name;
			throw new ItemNotFoundException(err);
		}
		target=item;
		notifyObserversOfUpdate(item);
	}
	public void delete(T item) throws ItemNotFoundException
	{
		delete(item.getID());
	}
	public void delete(String id) throws ItemNotFoundException
	{
		T target=find(id);
		if(target==null)
		{
			String err="error: could not find item with id "+id+" in "+name;
			throw new ItemNotFoundException(err);
		}
		data.remove(target);
		notifyObserversOfDelete(target);
	}	
	
	//observer stuff
	public void registerObserver(IDataStoreObserver<T> o)
	{
		observers.add(o);
	}
	public void removeObserver(IDataStoreObserver<T> o)
	{
		for(int i=0;i<observers.size();++i)
			if(observers.get(i).getID().equals(o.getID()))
			{
				observers.remove(i);
				return;
			}
	}
	protected void notifyObserversOfAdd(T item)
	{
		for(int i=0;i<observers.size();++i)
			observers.get(i).onAdd(item);
	}
	protected void notifyObserversOfUpdate(T item)
	{
		for(int i=0;i<observers.size();++i)
			observers.get(i).onUpdate(item);
	}
	protected void notifyObserversOfDelete(T item)
	{
		for(int i=0;i<observers.size();++i)
			observers.get(i).onDelete(item);
	}
	
	//to be implemented to convert a one line data string into the appropriate derived DataStoreItem
	protected abstract T parse(ParseParam data);
	
	//for debugging
	public void printData()
	{
		System.out.println("Printing "+name);
		for(int i=0;i<data.size();++i)
			System.out.println(data.get(i));
	}
}
