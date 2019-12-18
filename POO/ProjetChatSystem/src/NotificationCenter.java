import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Predicate;

public class NotificationCenter
{
	String history;
	UdpCommunication udpcom; 
	UserList userList; 
	InetAddress broadcast; 
	
	NotificationCenter(UserList l)
	{
		udpcom= new  UdpCommunication();
		this.userList=l; 
		//creation adresse de broadcast pour l'instant nous meme
		try {broadcast= InetAddress.getByName("127.0.0.1");}
		catch(UnknownHostException e){}
	}
	
	// Code des notifications i,c,d,a, r reponse 
	public void check_disponibility(String username)
	{
		udpcom.sendDatagram(("i "+username),50000,this.broadcast );
	}
	
	public void notify_connexion(String username,String macAddress, String ipAddress)
	{
		udpcom.sendDatagram("c "+username+" "+macAddress+" "+ipAddress, 50000,this.broadcast);
	}
	
	public void notify_presence(String username,String macAddress, String ipAddress)
	{
		udpcom.sendDatagram("p "+username+" "+macAddress+" "+ipAddress,50000,this.broadcast);
	}
	
	public void notify_deconnexion(String username,String macAddress, String ipAddress)
	{
		udpcom.sendDatagram("d "+username+" "+macAddress+" "+ipAddress,50000 ,this.broadcast);
	}
	
	public void notify_change_username(String username,String newusername,String macAddress, String ipAddress)
	{
		udpcom.sendDatagram("a "+username+" "+macAddress+" "+ipAddress+" "+newusername, 50000 ,this.broadcast);
	}
	
	
	//lA FONCTION SUIVANTE DOIT ETRE APPELER DANS UNE BOUCLE WHILE APRES LA CONNEXION 
	public void handle_notification()  throws UsernameException
	{
		Notification notification= udpcom.receiveDatagram();
		
		if(! notification.data.equals("")) 
		{
			//reponse check disponibility
			if (notification.data.charAt(0)=='r') {
				if (notification.data.charAt(4)=='f') {
					throw new UsernameException();
				}
			}
			
			//check disponibility
			if (notification.data.charAt(0)=='i')
			{
				String[] data = notification.data.split(" ");
				User user= new User(data[1],"default","default");
				String message;
				//regarde si un utilisateur utilisant le username apparait dans la liste
				System.out.print(userList.toString()); 
				if (userList.usernameExist(user))
				{	System.out.print("Le nom d'utilisateur est pris: j'envoie le message"); 
					message="r i f";
				}
				else { message="r i t"; }
				//on envoie notre reponse
				udpcom.sendDatagram(message,notification.port ,notification.add);
			}
				
			
			//notify connexion
			if (notification.data.charAt(0)=='c')
			{
				String[] data = notification.data.split(" ");
				User user= new User(data[1],data[2],data[3]);
				//ajouter user a la list  
				userList.add(user); 
				IHM.model.addElement(user);
				//on notify notre connexion à cette personne
				notify_presence(IHM.currentUsername,IHM.currentMac, IHM.currentIp); 
				
	
			}
			
			
			//notify presence
			if (notification.data.charAt(0)=='p')
			{
				String[] data = notification.data.split(" ");
				User user= new User(data[1],data[2],data[3]);
				
				//ajouter user a la list (on le retire s'il existe déja ) si il n'est pas null
				if (! (data[1].equals("null")))
				{
					System.out.println(data[1]);
					IHM.model.removeElement(user);
					userList.remove(user);
					userList.add(user); 
					IHM.model.addElement(user);
				}
				
			}
			
			//notify deconnexion
			if (notification.data.charAt(0)=='d')
			{
				String[] data = notification.data.split(" ");
				User user= new User(data[1],data[2],data[3]);
				//enlever user à la list 

				Predicate<User> p =i-> i.getUsername()==data[1];  
				userList.remove(user);
				IHM.model.removeElement(user);
			}
			
			
			//notify change username
			if (notification.data.charAt(0)=='a')
			{
				String[] data = notification.data.split(" ");
				User user= new User(data[1],data[2],data[3]);
				String newusername= data[4];
				 
				//on retire l'ancien user de la liste 
				IHM.model.removeElement(user);
				// on ajoute le même avec un nom different 
				IHM.model.addElement(new User(data[4],data[2],data[3]));
				
				//modifier user de la liste avec newusername
				userList.changeUsername(user,newusername); 
			}
		}
	}
}