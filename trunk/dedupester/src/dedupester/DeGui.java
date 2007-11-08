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
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import java.awt.Rectangle;
import java.io.*;
import java.awt.event.*;
import javax.swing.JLabel;


public class DeGui 
{
    public DeGui() {
    	DeDupeFrame myFrame = new DeDupeFrame();
    	myFrame.setTitle("DeDupester");
    	myFrame.setVisible(true);
    }
        
    class DeDupeFrame extends JFrame 
    {
        DeLibrarian librarian = new DeLibrarian();
        
        private static final long serialVersionUID = 1;
        private DeDupePanel myPanel;
        private final int FRAMEW = 600;
        private final int FRAMEH = 545;
        
    	private JTextArea actionRecordArea = new JTextArea("Welcome to DeDupester..."
    			
    		+ "\n\nDeDupester is a program Designed to help you merge mp3 libraries. "
    		+ "It can Sort mp3s, Generate Reports on your library,  Eliminate "
    		+ "Duplicate songs, and Merge Libraries without copying in redundant mp3s.", 
    		  20, 20);
    	
    	private JScrollPane actionScrollPane;

        public DeDupeFrame()
        {
        	setSize(FRAMEW, FRAMEH);
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	myPanel = new DeDupePanel();
        	Container contentPane = getContentPane();
        	contentPane.setLayout(null);
        	
        	
        	// add command buttons & listeners
        	
            JButton load = new JButton("Load library"); 
            load.setBounds(20,100,200,50);
            contentPane.add(load, contentPane);
            LoadListener myLoadListener = new LoadListener();
            load.addActionListener(myLoadListener);
      
            JButton generate = new JButton("Generate a report"); 
            generate.setBounds(20,155,200,50);
            contentPane.add(generate, contentPane);
            GenerateListener myGenerateListener = new GenerateListener();
            generate.addActionListener(myGenerateListener);
          
            JButton status = new JButton("Library status"); 
            status.setBounds(20,210,200,50);
            contentPane.add(status, contentPane);      
            StatusListener myStatusListener = new StatusListener();
            status.addActionListener(myStatusListener);
                
            JButton quarantine = new JButton("Quarantine duplicates"); 
            quarantine.setBounds(new Rectangle(20,265,200,50));
            contentPane.add(quarantine);
            QuarantineListener myQuarantineListener = new QuarantineListener();
            quarantine.addActionListener(myQuarantineListener);

            JButton merge = new JButton("Merge two libraries"); 
            merge.setBounds(new Rectangle(20,320,200,50));
            contentPane.add(merge);
            MergeListener myMergeListener = new MergeListener();
            merge.addActionListener(myMergeListener);
                
            JButton sort = new JButton("Sort library"); 
            sort.setBounds(new Rectangle(20,375,200,50));
            contentPane.add(sort);
            SortListener mySortListener = new SortListener();
            sort.addActionListener(mySortListener);
                
            JButton quit = new JButton("Quit"); 
            quit.setBounds(new Rectangle(20,430,200,50));
            contentPane.add(quit);
            QuitListener myQuitListener = new QuitListener();
            quit.addActionListener(myQuitListener);
               
    		actionRecordArea.setEditable(false);
    		actionRecordArea.setLineWrap(true);
    		actionScrollPane = new JScrollPane(actionRecordArea,
    		                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    		                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    		actionScrollPane.setBounds(new Rectangle(250,100,325,381));
    		add(actionScrollPane);
    		
    		contentPane.add(myPanel);	
    		setVisible(true);
         
        }
  
        private class LoadListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                String temp = JOptionPane.showInputDialog("Enter library path: ");
                File path = new File(temp);
                librarian.createLibrary(path);
            }
        }
 
        private class GenerateListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
            	//prompt for report path
                String pathString = JOptionPane.showInputDialog("Report path: ");
				File path = new File(pathString);

				//prompt for report type
				String typeString = JOptionPane.showInputDialog("Report sort type"
						                                      + " (path, name): ");
				try
				{
					if(typeString.equals("path"))
						librarian.generateReportByPath(path);
					else if(typeString.equals("name"))
						librarian.generateReportByName(path);
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
        
        private class StatusListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                
                JOptionPane.showMessageDialog(null, "Records loaded in library: " 
                		+ librarian.getLibrarySize(), "Library status",
                		JOptionPane.PLAIN_MESSAGE);  
            }
        }
        
        private class QuarantineListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                // to be filled in
            }
        }
        
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
}

class DeDupePanel extends JPanel
{
	private static final long serialVersionUID = 1;

	public DeDupePanel()
	{
		super();
//		addMouseListener(new MouseHandler());
		setFocusable(true);

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;           
		g2.drawString("DeDupester", 250, 20);		
        setVisible(true);
	}

/*	
  
 	// Handles what happens when the mouse is click. May not be necessary
 	// with the button listeners in DeDupeFrame
 
    private class MouseHandler extends MouseAdapter
	{
    	public void mouseClicked(MouseEvent event)
        {

        }
			
    }

*/
	
}