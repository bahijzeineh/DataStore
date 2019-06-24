package DataStore;

import java.util.Scanner;
import java.io.*;

public abstract class FileDataStore<T extends DataStoreItem> extends DataStore<T,String>
{
	protected String dataFileName;
		
	//name is used for message logging
	public FileDataStore(String name, String dataFileName)
	{
		super(name);
		this.dataFileName=dataFileName;
		load();
	}
	
	public void load()//shouldn't need to call this directly as its done in constructor
	{
		Scanner sc=null;
		try
		{
			sc=new Scanner(new File(dataFileName));
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
	public void save()//called in close() so shouldn't need to call directly
	{
		PrintWriter pw=null;
		try
		{
			pw=new PrintWriter(new File(dataFileName));
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
}
