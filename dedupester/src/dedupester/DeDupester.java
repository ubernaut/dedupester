package dedupester;

//import java.awt.FileDialog;
import java.io.*;
import java.util.Scanner;

public class DeDupester {

	public DeDupester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		//check for -gui paramater
		if(args.length != 0 && args[0].equals("-gui"))
		{
			//Start the DeDupester GUI here
		}
		//no paramaters implies text menu mode
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
			System.out.println();
			System.out.println("     DeDupester is a program Designed to help you merge mp3 libraries. ");
			System.out.println("     It can Sort mp3s, Generate Reports on your library,  Eliminate ");
			System.out.println("     Duplicate songs, and Merge Libraries without copying in redundant mp3s.");

			//start main loop
			DeLibrarian librarian = new DeLibrarian();
			String input;
			Scanner sc = new Scanner(System.in);
			do
			{

				//display menu
				System.out.println();
				System.out.println("     l    Load library");
				System.out.println("     r    Generate a report");
				System.out.println("     d    Quarentine Duplicates");
				System.out.println("     m    Merge two Libraries");
				System.out.println("     s    Sort Library");
				System.out.println("     q    Quit");
				System.out.println();

				//get menu choice
				System.out.print("     :");
				input = sc.nextLine();
				if(input.length() > 0)
					input = input.substring(0,1);
				else
					input = "";

				//check for defined flags


				//load library
				if(input.equals("l"))
				{
					//prompt for library path
					System.out.println();
					System.out.print("Library path: ");
					String temp = sc.nextLine();

					//load library from path
					File path = new File(temp);
					if(path.exists())
						librarian.createLibrary(path);
					else
						System.out.println("Path does not exist.");
				}

				//generate report
				if(input.equals("r"))
				{
					//prompt for report path
					System.out.println();
					System.out.print("Report path: ");
					String temp = sc.nextLine();
					File path = new File(temp);
					try
					{
						librarian.generateReport(path);
					}
					catch(IOException e)
					{
						System.out.println(e);
					}
				}
			}
			while(!input.equals("q"));
			sc.close();
		}
	}
}