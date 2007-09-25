package dupester;

public class DeRecord {
	
	private String fileName;
	private int fileSize;
	private String filePath;
	private String separator;
	private String artist;
	private String album;
	private String trackTitle;
	private int trackNum;
	
	
	public DeRecord() {
		fileName = "no name set";
		fileSize = 0;
		filePath = "no path Set";
		separator = "-";
		artist = "Artist Undefined";
		album = "Album Undefined";
		trackTitle = "Track Undefined";
		trackNum = 0;
	}
	
	public DeRecord(Object aFile){}
	public void printRecord(){}
	public void writeRecord(){}
	public boolean equals(DeRecord someRecord)
	{
		if(fileName.equals(someRecord.fileName)&&fileSize.equals(someRecord.fileSize))return true;	
		else return false;
	}

}
