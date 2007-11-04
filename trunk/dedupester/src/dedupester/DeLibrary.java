package dedupester;

import java.util.Iterator;
import java.util.TreeSet;

public class DeLibrary
{
	private TreeSet<DeRecord> allRecords;

	public DeLibrary()
	{
		allRecords=new TreeSet<DeRecord>();
	}

	public boolean add(DeRecord r)
	{
		return allRecords.add(r);
	}

	public boolean isEmpty()
	{
		return allRecords.isEmpty();
	}

	public int size()
	{
		return allRecords.size();
	}

	public String toString()
	{
		Iterator<DeRecord> i = allRecords.iterator();
		String s = "";
		while(i.hasNext())
			s += i.next();

		return s;
	}
}
