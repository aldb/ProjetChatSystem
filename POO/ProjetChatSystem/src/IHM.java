
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.accessibility.*;

public class IHM implements ActionListener {
//List<Session> sessions  = new ArrayList<Session>();
//List<User> users  = new ArrayList<User>();
String currentUsername; //instancié lors de la connexion 
//NotificationCenter notificationCenter= new NotificationCenter();

JFrame mainFrame;
JPanel connexionPanel;
JTextField login;
JLabel connexionLabel, mainFrameLabel;
JButton connexion, opensession, send;

IHM(){
// Create and set up the main window
mainFrame = new JFrame("Chat Application");
mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
mainFrame.setSize(new Dimension(400, 400));
//Create and set up the connexion pannel
connexionPanel= new JPanel(new GridLayout(2, 2));
//create widget
login =new JTextField(10);
connexionLabel= new JLabel("Enter login");
connexion= new JButton("Connexion");
connexion.addActionListener(this);
login.addActionListener(this);// utilité à revoir
//add widget
connexionPanel.add(connexionLabel);
connexionPanel.add(login);
connexionPanel.add(connexion);
//Add the panel to the window.
mainFrame.getContentPane().add(connexionPanel, BorderLayout.CENTER);

//Display the window.
mainFrame.pack();
mainFrame.setVisible(true);
}	

public void connection() 
{
}
public void deconnection() {}
public void changeUsername() {}
public void openSession() {}

public void actionPerformed(ActionEvent event) {
	if (event.getActionCommand().equals("Connexion"))
	{
		connection();
	}
	else if (event.getActionCommand().equals("Deconnexion"))
	{
		deconnection(); 
	}
	else if (event.getActionCommand().equals("Change username"))
	{
		changeUsername();
	}
	else if (event.getActionCommand().equals("Open session"))
	{
		openSession();
	}
	
}
private static void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);
    IHM chatApp = new IHM();
}
public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
  /*  javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            createAndShowGUI();
        }
    }); */
}


}
