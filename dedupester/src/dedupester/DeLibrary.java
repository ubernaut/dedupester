package dedupester;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Comparator;
import java.io.File;

public class DeLibrary
{
	//stores each different sort of the records.
	private TreeSet<DeRecord> recordsByPath;
	private TreeSet<DeRecord> recordsByName;

	public DeLibrary()
	{
		recordsByPath = new TreeSet<DeRecord>();
		recordsByName = new TreeSet<DeRecord>(NAME_ORDER);
	}

	//adds a DeRecord to ALL different sorts, or none
	public boolean add(DeRecord r)
	{
		boolean flag = recordsByPath.add(r);
		if(flag)
			recordsByName.add(r);
		return flag;
	}

	public boolean isEmpty()
	{
		return recordsByPath.isEmpty();
	}

	public int size()
	{
		return recordsByPath.size();
	}

	public String toStringByName()
	{
		Iterator<DeRecord> i = recordsByName.iterator();
		String s = "";
		while(i.hasNext())
			s += i.next();

		return s;
	}

	public String toString()
	{
		Iterator<DeRecord> i = recordsByPath.iterator();
		String s = "";
		while(i.hasNext())
			s += i.next();

		return s;
	}
	public TreeSet getRecordsByName()
	{
		return recordsByName;
	}
	//sorts by file name, then size
	final Comparator<DeRecord> NAME_ORDER = new Comparator<DeRecord>()
	{
		public int compare(DeRecord r1, DeRecord r2)
		{
			//TreeSet requires .compareTo to match .equals
			if(r1.equals(r2))
				return 0;

			int nameCmp = r1.getFileName().compareTo(r2.getFileName());
			if(nameCmp != 0)
				return nameCmp;
			else
				return (int)(r1.getFileSize() - r2.getFileSize());
		}
	};


}