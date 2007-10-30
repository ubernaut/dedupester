package dedupester;

import java.io.*;

public class DeLibrarian {

	private DeLibrary library;

	public DeLibrarian()
	{
		library = new DeLibrary();
	}

	//recursively scans dir tree at path, creating records and adding them to library
	public void createLibrary(File path)
	{
		File[] filelist = path.listFiles();
		for(File f : filelist)
		{
			if(f.isDirectory())
				createLibrary(f);
			else
				if(f.getName().toLowerCase().endsWith(".mp3"))
				{
					DeRecord record = new DeRecord(f);
					library.add(record);
				}
		}
	}

	public void generateReport(File path) throws IOException
	{
		path.createNewFile();
		PrintWriter pw = new PrintWriter(path);
		pw.print(library);
		pw.close();
	}

	public void printLibraryContents()
	{
		System.out.println("\n" + library);
	}
}