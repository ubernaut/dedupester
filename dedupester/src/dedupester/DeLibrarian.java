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
	//private helper method maintains library root path String in each DeRecord
	public void createLibrary(File path)
	{
		createLibrary(path, path.getPath());
	}

	private void createLibrary(File path, String libRoot)
	{
		if(path.exists())
		{
			File[] filelist = path.listFiles();
			for(File f : filelist)
			{
				if(f.isDirectory())
					createLibrary(f, libRoot);
				else
					if(f.getName().toLowerCase().endsWith(".mp3"))
					{
						DeRecord record = new DeRecord(f);
						record.setLibRoot(libRoot);
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

	public void generateDupeReportByPath(File path) throws IOException
	{
		path.createNewFile();
		PrintWriter pw = new PrintWriter(path);
		pw.print(library.dupesByPathToString());
		pw.close();
	}

	public void printLibraryContents()
	{
		System.out.println("\n" + library);
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

	//extracts unique records to quarFolder
	public void quarantine(File quarFolder)
	{
		//if quarFolder does not exist, create it
		if(!quarFolder.exists())
			quarFolder.mkdir();

		//make sure it was created
		if(quarFolder.exists())
		{
			TreeSet<DeRecord> uniqueFiles = library.getRecordsByName();
			for(DeRecord uRecord: uniqueFiles)
			{
				String filename = uRecord.getFileName();
				String sourceFolder = uRecord.getFilePath();

				//cut the libRoot off of the front of sourceFolder, and attach quarFolder
				String destFolder = quarFolder.getPath() + sourceFolder.replace(uRecord.getLibRoot(), "");

				File sourceFile = new File(sourceFolder, filename);
				File destFile = new File(destFolder, filename);

				//create the destFolder, multiple dirs if needed
				new File(destFolder).mkdirs();

				//incremental rename if destFile exists
				//could happen due to filename collision but no filesize match
				int count = 0;
				while(destFile.exists())
				{
					count++;
					destFile = new File(destFolder, "(" + count + ")" + filename);
				}
				sourceFile.renameTo(destFile);
			}
		}
	}

	public int getLibrarySize()
	{
		return library.size();
	}
}