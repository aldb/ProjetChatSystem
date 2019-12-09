
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.accessibility.*;

public class IHM implements ActionListener {
//List<Session> sessions  = new ArrayList<Session>();
UserList userlist= new UserList();
static String currentUsername; //instancié lors de la connexion 
static String currentIp; //instancié lors de la connexion 
static String currentMac; //instancié lors de la connexion 

NotificationCenter notificationCenter= new NotificationCenter(userlist);

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
login =new JTextField(15);
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

public void connection(String username) 
{	
	StringBuffer s = new StringBuffer();
	
	//check_disponilily
	notificationCenter.check_disponibility(username);
	boolean ok= true;
	//attendre une reponse X fois 
	for (int i ; i<100; i++)
		{	try {notificationCenter.wait_response();}
			catch ( usernameException e) {
				ok=false; 
				break; 
			}
			i++; 
		}
	
	//aucune exception n'a été levé on peut se connecter
	if (ok) 
	{	
		currentUsername=username; 
		//notify + update the list
		try
        {
			currentIp = InetAddress.getLocalHost().toString();
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni =  NetworkInterface.getByInetAddress(address);
            if (ni != null) 
            {
                byte[] macbyte = ni.getHardwareAddress();
             
                for (int i = 0; i < macbyte.length; i++) {
                	s.append(String.format("%02X%s", macbyte[i]));
                }
            } 
                
        }
        catch (UnknownHostException e) {  } 
		currentMac= s.toString();
		//on s'ajoute a la list des utilisateurs
		User us= new User(currentUsername,currentMac,"127.0.0.1");
		userlist.add(us);
		//on notifie la connexion les reponses nous servirons à construire notre liste des utilisateur actif 
		notificationCenter.notify_connexion(currentUsername,currentMac,currentIp);
		
		//changer l'interface graphique afficher la liste des utilisteur actif la rafraichir automatiquement 
		
		
	}
}
	

public void deconnection() {
	//notify disconnection
	notificationCenter.notify_deconnexion(currentUsername,currentMac,currentIp);
	//close open session
	
	//return to connection page
	
}
public void changeUsername() {
	//notify the name change
	
	//change it in our list 
}
public void openSession() {
	
	
}

public void actionPerformed(ActionEvent event) {
	if (event.getActionCommand().equals("Connexion"))
	{	
		String log= login.getText();
		connection(log);
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
