package dupester;

public class DeRecord implements Comparable{
	
	private String fileName;
	private int fileSize;
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
		if(((DeRecord) aRecord).fileName ==fileName && ((DeRecord) aRecord).fileSize==fileSize )return 0;
		if(((DeRecord) aRecord).fileName.compareToIgnoreCase(fileName)==-1)return -1;
		else return 0;
	}

}
