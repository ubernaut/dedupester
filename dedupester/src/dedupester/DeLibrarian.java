package dedupester;

import java.io.*;
import java.util.TreeSet;
public class DeLibrarian {

	private DeLibrary library;

	public DeLibrarian()
	{
		library = new DeLibrary();
	}

	//recursively scans dir tree at path, creating records and adding them to library
	//invalid library path simple adds no files.
	public void createLibrary(File path)
	{
		if(path.exists())
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
	}

	public void generateReportByPath(File path) throws IOException
	{
		path.createNewFile();
		PrintWriter pw = new PrintWriter(path);
		pw.print(library);
		pw.close();
	}

	public void generateReportByName(File path) throws IOException
	{
		path.createNewFile();
		PrintWriter pw = new PrintWriter(path);
		pw.print(library.toStringByName());
		pw.close();
	}

	public void printLibraryContents()
	{
		System.out.println("\n" + library);
	}
	public void findDupes()
	{
		library.findDupes();
	}
	public long dupeSize()
	{
		return library.getDupeSize();
	}
	public int dupeCount()
	{
		return library.getDupeCount();
	}
	public void Dedupe(File dupeFolder)
	{
		boolean folderMade = dupeFolder.mkdir();
		if(!folderMade)
			System.out.println("Folder could not be created!");
		else
		{
			TreeSet<DeRecord> allDupes = library.getDupeTree();
			for(DeRecord aDupe: allDupes)
			{
				File sourceFile = new File(aDupe.getFilePath(), aDupe.getFileName());
				File destFile = new File(dupeFolder, aDupe.getFileName());
				boolean success = sourceFile.renameTo(destFile);
				if(!success)
				{
					for(int i=1;!success;i++)
					{
						String dupeCount = Integer.toString(i);
						destFile= new File(dupeFolder,aDupe.getFileName()+'('+dupeCount+')');
						success = sourceFile.renameTo(destFile);
						if(i>=100)break;
					}

				}
			}
		}


	}
	public void quarantine(File quarFolder)
	{
		boolean folderMade=quarFolder.mkdir();
		if(!folderMade)
			System.out.println("Folder could not be created!");
		else
		{
			System.out.println("unique files found: "+library.nameSize());
			TreeSet uniqueFiles = library.getRecordsByName();
			for(Object uniqueRecord: uniqueFiles)
			{
				DeRecord uRecord = ((DeRecord)uniqueRecord);
				File sourceFile = new File(uRecord.getFilePath(), uRecord.getFileName());
				File destFile = new File(quarFolder, uRecord.getFileName());
				boolean success = sourceFile.renameTo(destFile);
				if(!success)break;
			}
		}

	}

	public int getLibrarySize()
	{
		return library.size();
	}
}