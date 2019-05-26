package DataStore;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public abstract class DataStore<T extends DataStoreItem>
{
	protected String name, dataFile;
	protected ArrayList<T> data;
	protected ArrayList<Observer> observers;
		
	//name is used for message logging
	public DataStore(String name, String dataFile)
	{
		this.name=name;
		this.dataFile=dataFile;
		data=new ArrayList<T>();
		observers=new ArrayList<Observer>();
		loadFromFile();
	}
	
	protected void loadFromFile()//shouldn't need to call this directly as its done in constructor
	{
		Scanner sc=null;
		try
		{
			sc=new Scanner(new File(dataFile));
		}
		catch(Exception e)
		{
			System.err.print(e.getMessage());
		}
		if(sc!=null)
		{
			while(sc.hasNext())
			{
				String line=sc.nextLine();
				try 
				{
					add(parse(line));
				}
				catch(DuplicateIDException e)
				{
					System.err.println("error in file read: "+e.getMessage());
				}
				catch(Exception e)
				{
					System.err.println("error in file read: "+e.getMessage());
				}
			}
			sc.close();
		}
	}
	protected void writeToFile()//called in close() so shouldn't need to call directly
	{
		PrintWriter pw=null;
		try
		{
			pw=new PrintWriter(new File(dataFile));
		}
		catch(Exception e)
		{
			System.err.print(e.getMessage());
		}
		if(pw!=null)
		{
			for(int i=0;i<data.size();++i)
				pw.println(data.get(i));
			pw.close();
		}
	}
	//called by centralDataStore.closeDataStores so shouldnt need to call directly
	protected void close()
	{
		writeToFile();
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
	public void registerObserver(Observer o)
	{
		observers.add(o);
	}
	public void removeObserver(Observer o)
	{
		for(int i=0;i<observers.size();++i)
			if(observers.get(i).getID().equals(o.getID()))
			{
				observers.remove(i);
				return;
			}
	}
	protected void notifyObserversOfAdd(DataStoreItem item)
	{
		for(int i=0;i<observers.size();++i)
			observers.get(i).onAdd(item);
	}
	protected void notifyObserversOfUpdate(DataStoreItem item)
	{
		for(int i=0;i<observers.size();++i)
			observers.get(i).onUpdate(item);
	}
	protected void notifyObserversOfDelete(DataStoreItem item)
	{
		for(int i=0;i<observers.size();++i)
			observers.get(i).onDelete(item);
	}
	
	//to be implemented to convert a one line data string into the appropriate derived DataStoreItem
	protected abstract T parse(String line);
	
	//for debugging
	public void printData()
	{
		System.out.println("Printing "+name);
		for(int i=0;i<data.size();++i)
			System.out.println(data.get(i));
	}
}
