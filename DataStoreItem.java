package DataStore;


import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public abstract class DataStoreItem
{
	private String id;
	public DataStoreItem(String id) {this.id=id;}
	public String getID() {return id;}
	
	public static String generateID(String prefix)
	{
		DateTimeFormatter f = DateTimeFormatter.ofPattern("n");//nanosecond
		LocalDateTime now = LocalDateTime.now();
		return prefix+"_"+f.format(now);
	}
	
	public static Date parseDate(String txt) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return sdf.parse(txt);
	}
	public static String getDateString(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		return sdf.format(date);
	}
	
	public abstract String toString();

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataStoreItem other = (DataStoreItem) obj;
		if (id == null) 
		{
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}
