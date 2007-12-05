/*
 * DeGui.java
 *
 * Created on November 4, 2007, 2:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dedupester;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.Rectangle;
import java.io.*;
import java.awt.event.*;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.table.DefaultTableModel;
import java.util.StringTokenizer;
import java.util.Vector;


public class DeGui
{
	private DeLibrarian librarian = new DeLibrarian();
	private JTable DupeTable;

    public DeGui() {
    	DeDupeFrame myFrame = new DeDupeFrame();
    	myFrame.setTitle("DeDupester");
    	myFrame.setVisible(true);
    }

    class DeDupeFrame extends JFrame
    {
        private static final long serialVersionUID = 1;
        private DeDupePanel myPanel;
        private JFileChooser chooser;
        private final int FRAMEW = 1200;
        private final int FRAMEH = 545;

        public DeDupeFrame()
        {
        	setTitle("DeDupe Frame");
        	setSize(FRAMEW, FRAMEH);
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	myPanel = new DeDupePanel();
        	Container contentPane = getContentPane();
        	contentPane.add(myPanel, "Center");
        	chooser = new JFileChooser(); // create file chooser
        	setLayout(null);

            // create table

            String[] columns = {"File name", "File size", "Path"};
            Object[][] data = {};
            DefaultTableModel DupeTableModel = new DefaultTableModel(data, columns);
            DupeTable = new JTable(DupeTableModel);
            JScrollPane scrollPane = new JScrollPane(DupeTable);
            scrollPane.setBounds(new Rectangle(250,100,925,381));
            add(scrollPane);

        	// add command buttons & listeners

            JButton load = new JButton("Load library");
            load.setBounds(20,100,200,50);
            LoadListener myLoadListener = new LoadListener();
            load.addActionListener(myLoadListener);
            add(load);

            JButton generate = new JButton("Generate a report");
            generate.setBounds(20,155,200,50);
            GenerateListener myGenerateListener = new GenerateListener();
            generate.addActionListener(myGenerateListener);
            add(generate);

            JButton status = new JButton("Library status");
            status.setBounds(20,210,200,50);
            StatusListener myStatusListener = new StatusListener();
            status.addActionListener(myStatusListener);
            add(status);

            JButton quarantine = new JButton("Extract unique files");
            quarantine.setBounds(20,265,200,50);
            QuarantineListener myQuarantineListener = new QuarantineListener();
            quarantine.addActionListener(myQuarantineListener);
            add(quarantine);

            JButton merge = new JButton("Merge two libraries");
            merge.setBounds(20,320,200,50);
            MergeListener myMergeListener = new MergeListener();
            merge.addActionListener(myMergeListener);
            add(merge);

            JButton sort = new JButton("Sort library");
            sort.setBounds(20,375,200,50);
            SortListener mySortListener = new SortListener();
            sort.addActionListener(mySortListener);
            add(sort);

            JButton quit = new JButton("Quit");
            quit.setBounds(20,430,200,50);
            QuitListener myQuitListener = new QuitListener();
            quit.addActionListener(myQuitListener);
            add(quit);


// Try adding a border around the buttons, the table, and the title label...

// Figure out fonts

/*
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();
            int i = 0;
            while(fontNames[i] != null)
            {
            	System.out.println("Font ["+i+"] = "+fontNames[i]);
            	i++;
            }
*/

            // Create title label at top of frame

    		Label logo = new Label("DEDUPESTER");
    		logo.setFont(new Font("Serif",Font.BOLD,40));
    		Panel logoPanel = new Panel();
    		logoPanel.add(logo);
    		add(logoPanel);
    		logoPanel.setBounds(410,15,380,50);
        }

        private class LoadListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
            	// prompt for library path
    			chooser.setCurrentDirectory(new File("."));
    			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    			int selectValue = chooser.showDialog(DeDupeFrame.this, "Select path");

    			if (selectValue == JFileChooser.APPROVE_OPTION)
    			{
    				String pathString = chooser.getSelectedFile().getPath();
    				File path = new File(pathString);
    				librarian.createLibrary(path);
    			}
            }
        }

        private class GenerateListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
            	chooser.setCurrentDirectory(new File("."));
    			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    			//prompt for report path
    			int selectValue = chooser.showDialog(DeDupeFrame.this, "Select path");
    			String pathString = chooser.getSelectedFile().getPath();

				//prompt for report filename
				String reportName = JOptionPane.showInputDialog("Enter report's " +
						"filename (eg. report.txt): ");

				// append file's chosen name to filePath for processing
				pathString = pathString + "\\" + reportName;
				File path = new File(pathString);

    			//prompt for report type
    			String typeString = JOptionPane.showInputDialog("Report sort type"
    					                                      + " (path or name): ");
    			try
    			{
    				if((selectValue == JFileChooser.APPROVE_OPTION) &&
    				   (typeString.equalsIgnoreCase("path")))
    				{
    					librarian.generateReportByPath(path);
    					setTableContents(path);
    				}
    				else if((selectValue == JFileChooser.APPROVE_OPTION) &&
    					    (typeString.equalsIgnoreCase("name")))
    				{
    					librarian.generateReportByName(path);
    					setTableContents(path);
    				}
    				else if (selectValue == JFileChooser.CANCEL_OPTION)
    				{

    				}
    				else{
    					JOptionPane.showMessageDialog(null, "Bad report type.",
    					    "Error", JOptionPane.ERROR_MESSAGE);
    				}
    			}
    			catch(IOException e)
    			{
    				System.out.println(e);
    			}
            }
        }

        private class QuarantineListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
            	chooser.setCurrentDirectory(new File("."));
    			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    			//prompt for folder path
    			int selectValue = chooser.showDialog(DeDupeFrame.this, "Extract to");
    			String pathString = chooser.getSelectedFile().getPath();

    			if(selectValue == JFileChooser.APPROVE_OPTION)
    			{
    				//prompt for folder name
    				String folderName = JOptionPane.showInputDialog("Enter folder " +
    						"name: ");

    				// append folder's chosen name to filePath for processing
    				pathString = pathString + "\\" + folderName;
    				File quarFolder = new File(pathString);
    				librarian.quarantine(quarFolder);
    			}
            }
        }

        private class StatusListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                JOptionPane.showMessageDialog(null, "Records loaded in library: "
                		+ librarian.getLibrarySize(), "Library status",
                		JOptionPane.PLAIN_MESSAGE);
            }
        }

        // reads in content written to file and duplicates the output in
        // a table for the gui

        private void setTableContents(File filePath) throws IOException
        {
        	StringTokenizer st1, st2;
        	Vector<String> rowData;
      	    DefaultTableModel DupeTableModel = (DefaultTableModel)DupeTable.getModel();

      	    // clear any present data in table

      	    clearTable(DupeTableModel);

        	// read file contents

        	BufferedReader fileIn =
				new BufferedReader(new FileReader(filePath));
			String fileLine = fileIn.readLine();

			// copy file contents into table

			while(fileLine != null)
			{
				st1 = new StringTokenizer(fileLine,"\"");

				// check for mixed caps in ".mp3" spelling then concatenate
				// out the filename

				if(fileLine.indexOf(".mp3") < 0)
				{
					String ext = fileLine.substring(fileLine.lastIndexOf("."),
											fileLine.lastIndexOf(".") + 4);

					if(ext.equals(".MP3"))
						fileLine = fileLine.substring(fileLine.lastIndexOf(".MP3") + 5);
					else if(ext.equals(".mP3"))
						fileLine = fileLine.substring(fileLine.lastIndexOf(".mP3") + 5);
					else if(ext.equals(".Mp3"))
						fileLine = fileLine.substring(fileLine.lastIndexOf(".Mp3") + 5);
				}else
					fileLine = fileLine.substring(fileLine.lastIndexOf(".mp3") + 5);
				st2 = new StringTokenizer(fileLine," ");
				String data1 = st1.nextToken();
				String data2 = st2.nextToken();
				fileLine = fileLine.substring(fileLine.lastIndexOf(data2) +
						                      data2.length() + 1);
				st1 = new StringTokenizer(fileLine,"\"");
				String data3 = st1.nextToken();

				// snip out quotation marks in strings

				data2 = data2.substring(1,data2.length() - 1);
				data3 = data3.substring(0,data3.length());

				// add data to table

				rowData = new Vector<String>(3);
				rowData.add(data1);
				rowData.add(data2);
				rowData.add(data3);
				DupeTableModel.addRow(rowData);

				fileLine = fileIn.readLine();
			}
			fileIn.close();
        }

        // removes any data present in table

        private void clearTable(DefaultTableModel a)
        {
        	for(int i = a.getRowCount() - 1; 0 <= i; i--)
        		a.removeRow(i);
        }
    }

// probably move below listeners into DeDupeFrame. Revise UML diagram

    private class MergeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            // to be filled in
        }
    }

    private class SortListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            // to be filled in
        }
    }

    private class QuitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
        	int n;
            Object[] options = {"Yes", "No"};
            n = JOptionPane.showOptionDialog(null,
                    "Do you really want to quit?", "DeDupester",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);

            if(n == 0)
                System.exit(0);
        }
    }
}

class DeDupePanel extends JPanel
{
	private static final long serialVersionUID = 1;

	public DeDupePanel()
	{
		super();
//		setFocusable(true);

	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
//		g2.drawString("Test", 20, 20);

 //       setVisible(true);
	}
}