package dedupester;

//import java.awt.FileDialog;
import java.io.*;
import java.util.Scanner;

public class DeDupester {
//private DeGui myGui;
	public DeDupester() {
		// TODO Auto-generated constructor stub

	}

	public static void main(String[] args) {

		//check for -gui paramater
		if(args.length != 0 && args[0].equals("--gui"))
		{
			//Start the DeDupester GUI here
			DeGui myGui = new DeGui();
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
				System.out.println("     a    Library status");
				System.out.println("     e    Extract Unique Files");
				System.out.println("     d    Extract Duplicate Files");
				System.out.println("     f    Find Duplicate Files");
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
					librarian.createLibrary(path);
				}

				//generate report
				if(input.equals("r"))
				{
					//prompt for report path
					System.out.println();
					System.out.print("Report path: ");
					String pathString = sc.nextLine();
					File path = new File(pathString);

					//prompt for report type
					System.out.print("Report sort type (path, name): ");
					String typeString = sc.nextLine();
					try
					{
						if(typeString.equals("path"))
							librarian.generateReportByPath(path);
						else if(typeString.equals("name"))
							librarian.generateReportByName(path);
						else
							System.out.println("Bad report type.");
					}
					catch(IOException e)
					{
						System.out.println(e);
					}
				}

				//libary status
				if(input.equals("a"))
				{
					//print library size
					System.out.println("\nRecords loaded in library: " + librarian.getLibrarySize());
				}

				if(input.equals("d"))
				{
					System.out.println();
					System.out.println("Specify a folder on the same drive");
					System.out.print("where you would like me to place the duplicates: ");
					String pathString = sc.nextLine();
					File dupeFolder = new File(pathString);
					librarian.Dedupe(dupeFolder);
				}

				if(input.equals("f"))
				{
					librarian.findDupes();
					System.out.println("\nDuplicates found: " + librarian.dupeCount());
					System.out.println("total size: "+librarian.dupeSize());

				}

				if(input.equals("e"))
				{
					//prompt for path to uniquesFolder
					System.out.println();
					System.out.print("Extract to: ");
					String pathString = sc.nextLine();
					File quarFolder = new File(pathString);

					librarian.quarantine(quarFolder);
				}
			}
			while(!input.equals("q"));
			sc.close();
		}
	}
}
