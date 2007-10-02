//package dupester;
import java.io.File;

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
				if(f.getName().endsWith(".mp3"))
				{
					DeRecord record = new DeRecord(f);
					library.add(record);
				}
		}
	}

	public void printLibraryContents()
	{
		System.out.println("\n" + library);
	}
}