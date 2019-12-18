import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SessionPage
{
	Session session;
	JFrame mainFrame;
	
	public SessionPage(Session session)
	{
		this.session = session;
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            createAndShowGUI();
	        }
	    });
	}
	
	
	private void createAndShowGUI()
	{
	    //Make sure we have nice window decorations.
		initLookAndFeel();
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    
    	// Create and set up the main window
 		mainFrame = new JFrame("Session avec " + session.getReceiver().getUsername());
 		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		mainFrame.setSize(new Dimension(4000, 4000));

 		//Display the window.
 		mainFrame.pack();
 		mainFrame.setVisible(true);
	}
	
	
	private void initLookAndFeel()
	{
	    String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
	    
	    try {
	        UIManager.setLookAndFeel(lookAndFeel);
	    } catch (ClassNotFoundException e) {
	        System.err.println("Couldn't find class for specified look and feel:" + lookAndFeel);
	        System.err.println("Did you include the L&F library in the class path?");
	        System.err.println("Using the default look and feel.");
	    } catch (UnsupportedLookAndFeelException e) {
	        System.err.println("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.");
	        System.err.println("Using the default look and feel.");
	    } catch (Exception e) {
	        System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
	        System.err.println("Using the default look and feel.");
	        e.printStackTrace();
	    }
    }
}
