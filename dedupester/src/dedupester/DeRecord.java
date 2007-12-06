package dedupester;

import java.io.File;
import java.io.IOException;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

@SuppressWarnings("unchecked")
public class DeRecord implements Comparable{

	private String libRoot;

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

	public DeRecord(File aFile)throws IOException, TagException {
		this();
		fileName = aFile.getName();
        fileSize = aFile.length();
        
        // use jid3lib to set DeRecord attributes based on id3 tags
        // TODO: error handling; use these attributes in dupe processing... 
        MP3File taggedFile = new MP3File(aFile);
        artist = taggedFile.getID3v2Tag().getLeadArtist();
        album = taggedFile.getID3v2Tag().getAlbumTitle();
        trackTitle = taggedFile.getID3v2Tag().getSongTitle();
        trackNum = Integer.getInteger(taggedFile.getID3v2Tag().getTrackNumberOnAlbum(), 0);
        
        try{filePath = aFile.getParentFile().getCanonicalPath();}
        catch(Exception e){System.out.println(e);}
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

	//equals is implemented to satisfy TreeSet
	//tests equality of the DeRecord object, not of a song.
	public boolean equals(DeRecord someRecord)
	{
		if(fileName.equals(someRecord.fileName) && fileSize == someRecord.fileSize && filePath.equals(someRecord.fileSize))
			return true;
		else
			return false;
	}

	//default sort
	//sorts by path, file name
	public int compareTo(Object aRecord) throws ClassCastException
	{
		if(!(aRecord instanceof DeRecord))
			throw new ClassCastException("DeRecord object expected.");

		//TreeSet requires .compareTo to match .equals
		if(this.equals(aRecord))
			return 0;

		DeRecord r = (DeRecord) aRecord;

		if(r.filePath.compareTo(filePath) != 0)
			return filePath.compareTo(r.getFilePath());
		else
			return fileName.compareTo(r.getFileName());
	}

	public String toString()
	{
		String s = "\"";
		s += fileName;
		s += "\" \"";
		s += fileSize;
		s += "\" \"";
		s += filePath;
	/*	s += "\" \"";
		s += separator;
		s += "\" \"";
		s += artist;
		s += "\" \"";
		s += album;
		s += "\" \"";
		s += trackTitle;
		s += "\" \"";
		s += trackNum;
		s += "\" \"";
		s += bitRate;
	*/
		s += "\"\r\n";

		return s;
	}

	public void setLibRoot(String _libRoot) {libRoot = _libRoot;}
	public String getLibRoot() {return libRoot;}

	public String getFileName() {return fileName;}
	public long getFileSize() {return fileSize;}
	public String getFilePath() {return filePath;}
	public String getSeparator() {return separator;};
	public String getArtist() {return artist;}
	public String getAlbum() {return album;}
	public String getTrackTitle() {return trackTitle;}
	public int getTrackNum() {return trackNum;}
	public int getBitRate() {return bitRate;}
}
