package dedupester;

import java.io.File;

@SuppressWarnings("unchecked")
public class DeRecord implements Comparable{

	private String fileName;
	private long fileSize;
	private String filePath;
	private String separator;
	private String artist;
	private String album;
	private String trackTitle;
	private int trackNum;
	private int bitRate;


	public DeRecord() {
		fileName = "no name set";
		fileSize = 0;
		filePath = "no path Set";
		separator = "-";
		artist = "Artist Undefined";
		album = "Album Undefined";
		trackTitle = "Track Undefined";
		trackNum = 0;
		bitRate =0;
	}
	public DeRecord(File aFile){
		this();
		fileName = aFile.getName();
        fileSize = aFile.length();
        try{filePath = aFile.getParentFile().getCanonicalPath();}
        catch(Exception e){System.out.println(e);}
	}

	public DeRecord(Object aFile){

	}

	public DeRecord(DeRecord someRecord){
		fileName = someRecord.fileName;
		fileSize = someRecord.fileSize;
		filePath = someRecord.filePath;
		separator = someRecord.separator;
		artist = someRecord.artist;
		album = someRecord.album;
		trackTitle = someRecord.trackTitle;
		trackNum = someRecord.trackNum;
		bitRate = someRecord.bitRate;
	}

	public void printRecord(){}

	public void writeRecord(){}

	public boolean equals(DeRecord someRecord)	{
		if(fileName.equals(someRecord.fileName)&&fileSize==someRecord.fileSize)return true;
		else return false;
	}

	public int compareTo(Object aRecord)
	{
		DeRecord r = (DeRecord) aRecord;
		if(r.fileName.compareToIgnoreCase(fileName) == -1) return -1;
		else if(r.fileName == fileName && r.fileSize > fileSize) return -1;
		else if(r.fileName == fileName && r.fileSize == fileSize) return 0;
		else return 1;
	}

	public String toString()
	{
		String s = "";
		s += fileName;
		s += " ";
		s += fileSize;
		s += " ";
		s += filePath;
		s += " ";
	/*	s += separator;
		s += " ";
		s += artist;
		s += " ";
		s += album;
		s += " ";
		s += trackTitle;
		s += " ";
		s += trackNum;
		s += " ";
		s += bitRate;
	*/
		s += "\r\n";

		return s;
	}
}