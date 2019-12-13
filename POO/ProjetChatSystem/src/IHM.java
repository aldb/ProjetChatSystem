import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class IHM implements ActionListener
{
	ArrayList<Session> sessions  = new ArrayList<Session>();
	static UserList userList = new UserList();
	static NotificationCenter notificationCenter= new NotificationCenter(userList);
	static boolean nameconflict=false; 
	static String currentUsername; //instanci� lors de la connexion 
	static String currentIp; //instanci� lors de la connexion 
	static String currentMac; //instanci� lors de la connexion 
	
	JFrame mainFrame, connectedFrame;
	JPanel connexionPanel,opensessionPanel, deconnectionPanel,changeusernamePanel, mainPanel;
	JTextField login, New_Username;
	JLabel connexionLabel, mainFrameLabel,opensessionLabel,changeusernameLabel, disconnectionLabel;
	JButton connexion, opensession, send, disconnection, changeusername;
	JList<User> list;
	Vector<User> vector = new Vector<>(userList);
	static DefaultListModel<User> model=new DefaultListModel<>();
	
	public IHM()
	{
		// Create and set up the main window
		mainFrame = new JFrame("Chat Application");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(new Dimension(4000, 4000));
		
		//Create and set up the connexion pannel
		connexionPanel= new JPanel(new GridLayout(2, 2));
		
		//create widget
		login =new JTextField(15);
		connexionLabel= new JLabel("Enter login");
		connexion= new JButton("Connexion");
		connexion.addActionListener(this);
		login.addActionListener(this);// utilit� a� revoir
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
		
		//attendre une reponse X fois 
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//aucune exception n'a �t� lev�e on peut se connecter
		if (! nameconflict) 
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
	             
	                for (int i = 0; i < macbyte.length; i++)
	                {
	                	s.append(String.format("%02X%s", macbyte[i]));
	                }
	            } 
	                
	        }
	        catch (UnknownHostException | SocketException e) {  } 
			currentMac= s.toString();
			//on s'ajoute a la list des utilisateurs
			User us= new User(currentUsername,currentMac,"127.0.0.1");
			userList.add(us);
			//on notifie la connexion les reponses nous servirons à construire notre liste des utilisateur actif 
			notificationCenter.notify_connexion(currentUsername,currentMac,currentIp);
			
			//changer l'interface graphique afficher la liste des utilisteur actif la rafraichir automatiquement 
			
			
			//create and set up the connected window
			connectedFrame = new JFrame("Connected");
			connectedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			connectedFrame.setSize(new Dimension(4000, 4000));
			
			//create and set up the different panel 
			opensessionPanel= new JPanel(new GridLayout(3, 1));
			deconnectionPanel= new JPanel(new GridLayout(1, 2));
			changeusernamePanel= new JPanel(new GridLayout(1, 2));
			mainPanel=new JPanel(new GridLayout(4, 1));
			//create widget
			
			//for change username
			New_Username =new JTextField(15);
			changeusername= new JButton("Change username");
			changeusername.addActionListener(this);
			New_Username.addActionListener(this);// utilit� a� revoir
			
			//to disconnect
			disconnection= new JButton("Disconnect");
			disconnection.addActionListener(this);
			
			//to open a new session 
			opensession=new JButton("Open session");
			opensession.addActionListener(this);
			opensessionLabel=new JLabel("Click the \"Open session\" button"+ " once you have selected user.",JLabel.CENTER);
			
			
			list = new JList<>( vector ); //data has type Object[]
			list.setModel(model);
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			list.setVisibleRowCount(-1);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(250, 80));
			
			
			
			//add widget to the different panel
			changeusernamePanel.add(New_Username,BorderLayout.CENTER);
			changeusernamePanel.add(changeusername,BorderLayout.SOUTH);
			
			deconnectionPanel.add(disconnection,BorderLayout.SOUTH); 
			
			opensessionPanel.add(opensessionLabel,BorderLayout.NORTH);
			opensessionPanel.add(opensession,BorderLayout.SOUTH);
			opensessionPanel.add(list,BorderLayout.CENTER);
			
			//add panel to the 
			mainPanel.add(changeusernamePanel,BorderLayout.NORTH);
			mainPanel.add(deconnectionPanel,BorderLayout.SOUTH);
			mainPanel.add(opensessionPanel,BorderLayout.CENTER);
			connectedFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			
			//fait disparaitre la fenêtre de connexion
			mainFrame.setVisible(false);
			//Affiche la fenêtre  principale
			connectedFrame.pack();
			connectedFrame.setVisible(true);
			
		}
		else 
		{
			nameconflict=false; 
		}
	}
		
	
	public void deconnection()
	{
		//notify disconnection
		notificationCenter.notify_deconnexion(currentUsername,currentMac,currentIp);
		//close open session
		
		//return to connection page
		connectedFrame.setVisible(false);
		mainFrame.setVisible(true);
		
	}
	
	
	public void changeUsername(String newname)
	{	//check_disponilily
		notificationCenter.check_disponibility(newname);
		//attendre une reponse pendant X seconde  
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//no exception rise change of name possible
		if (!nameconflict) 
		{	
		
		//notify the name change
		notificationCenter.notify_change_username(currentUsername,newname,currentMac, currentIp);
		//change it in our list 
		User us= new User(currentUsername,currentMac,"127.0.0.1");
		userList.changeUsername(us, newname);
		currentUsername=newname; 
		connectedFrame.setTitle(currentUsername);
		}
		else 
		{
			nameconflict=false; 
		}
		
	}
	
	
	public void openSession()
	{
		
		
	}
	
	
	public void actionPerformed(ActionEvent event)
	{
		if (event.getActionCommand().equals("Connexion"))
		{	
			String log= login.getText();
			connection(log);
		}
		else if (event.getActionCommand().equals("Disconnect"))
		{
			
			deconnection(); 
		}
		else if (event.getActionCommand().equals("Change username"))
		{
			String name= New_Username.getText();
			changeUsername(name);
		}
		else if (event.getActionCommand().equals("Open session"))
		{
			openSession();
		}
		
	}
	
	
	private static void createAndShowGUI()
	{
	    //Make sure we have nice window decorations.
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    IHM chatApp = new IHM();
	}
	
	
	public static void main(String[] args)
	{
	    //Schedule a job for the event-dispatching thread:
	    //creating and showing this application's GUI.
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            createAndShowGUI();
	        }
	    });
	    
	    while(true) 
	    {
	    	//handle notification
	    	try { notificationCenter.handle_notification(); }
			catch ( UsernameException e)
			{
				nameconflict=true; 
			}
	    	
	    	
	    	//update display list 
	    	
	    	//test ajouter un element 
	    }
	}
}
