package dedupester;

//import java.awt.FileDialog;
import java.io.File;

public class DeDupester {

	public DeDupester() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//no parameters implies GUI mode
		if(args.length == 0)
		{
			//Start the DeDupester GUI here
		}
		//any parameters imply CLI mode
		else
		{
			System.out.println("");

			System.out.println(" ________         ________                                __                ");
			System.out.println(" \\______ \\   ____ \\______ \\  __ ________   ____   _______/  |_  ___________ ");
			System.out.println("  |    |  \\_/ __ \\ |    |  \\|  |  \\____ \\_/ __ \\ /  ___/\\   __\\/ __ \\_  __ \\");
			System.out.println("  |    `   \\  ___/ |    `   \\  |  /  |_> >  ___/ \\___ \\  |  | \\  ___/|  | \\/");
			System.out.println(" /_______  /\\___  >_______  /____/|   __/ \\___  >____  > |__|  \\___  >__|   ");
			System.out.println("         \\/     \\/        \\/      |__|        \\/     \\/            \\/       	       ");
			System.out.println("																");
			System.out.println("                           Welcome to DeDupester");

			//check for a defined flag
			//need to think about multiple flags in one command (later)
			if(args[0].matches("-[lrdms]"))
			{
				//load library
				if(args[0].equals("-l") && args.length >= 2)
				{
					DeLibrarian librarian = new DeLibrarian();
					File path = new File(args[1]);
					if(path.exists())
					{
						librarian.createLibrary(new File(args[1]));
						librarian.printLibraryContents();
					}
				}
			}
			//undefined flags (or -h) get the help screen
			else
			{
				System.out.println("     Description:");
				System.out.println("                 ");
				System.out.println("     DeDupester is a program Designed to help you merge mp3 libraries. ");
				System.out.println("     It can Sort mp3s, Generate Reports on your library,  Eliminate ");
				System.out.println("     Duplicate songs, and Merge Libraries without copying in redundant mp3s.");
				System.out.println("   ");
				System.out.println("     DeDupester Help:");
				System.out.println("");
				System.out.println("####################################################");
				System.out.println("##     Command             ##  Result             ##");
				System.out.println("####################################################");
				System.out.println("                           ## ");
				System.out.println(" C:\\> java DeDupester      ## Start the DeDupester GUI");
				System.out.println("                           ## ");
				System.out.println("####################################################");
				System.out.println("                               ");
	//			System.out.println("     If you prefer the command line you can launch DeDupster with:");
				System.out.println("                               ");

				System.out.println("####################################################");
				System.out.println("##               Command Line Syntax:             ##");
				System.out.println("####################################################");
				System.out.println("##                                                ##");
				System.out.println("## DeDupester <option> -l <path to music library> ##");
				System.out.println("##                                                ##");
				System.out.println("####################################################");
				System.out.println("##   Option                ##  Description        ##");
				System.out.println("####################################################");
				System.out.println("                           ## ");
				System.out.println("     -h                    ## Display this screen");
				System.out.println("     -r                    ## Generate a report");
				System.out.println("     -d                    ## Quarentine Duplicates");
				System.out.println("     -m <path to library>  ## Merge two Libraries");
				System.out.println("     -s                    ## Sort Library");
			}
		}
	}
}
