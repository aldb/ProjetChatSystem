package src;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NotificationCenter {
String history;
UdpCommunication udpcom; 
UserList userList; 

InetAddress broadcast; 

NotificationCenter(UserList l){
	udpcom= new  UdpCommunication();
	this.userList=l; 
	//creation addresse de broadcast pour l'instant nous même
	try {broadcast= InetAddress.getByName("127.0.0.1");}
	catch(UnknownHostException e){}
}

// Code des notifications i,c,d,a, r reponse 
public void check_disponibility(String username) 
{
	udpcom.sendDatagram(("i "+username), 1234 ,this.broadcast );
}

public void notify_connexion(String username,String macAddress, String ipAddress) 
{
	udpcom.sendDatagram("c "+username+" "+macAddress+" "+ipAddress, 1234 ,this.broadcast);
}

public void notify_deconnexion(String username,String macAddress, String ipAddress) 
{
	udpcom.sendDatagram("d "+username+" "+macAddress+" "+ipAddress, 1234 ,this.broadcast);
}

public void notify_change_username(String username,String newusername,String macAddress, String ipAddress) 
{
	udpcom.sendDatagram("a "+username+" "+macAddress+" "+ipAddress+" "+newusername, 1234 ,this.broadcast);
}

public void wait_response () throws usernameException 
{	
	Notification notification= udpcom.receiveDatagram();
	//recevoir une réponse à check disponibility 
		if (notification.data.charAt(0)=='r') {
			if (notification.data.charAt(4)=='f') {
				throw new usernameException();
			}
		}
		
}

//lA FONCTION SUIVANTE DOIT ETRE APPELER DANS UNE BOUCLE WHILE APRES LA CONNEXION 
public void handle_notification()
{
	Notification notification= udpcom.receiveDatagram();
	//check disponibility
	if (notification.data.charAt(0)=='i') {
		String[] data = notification.data.split(" ");
		User user= new User(data[1],data[2],data[3]);
		String message;
		//regarde si un utilisateur utilisant le username apparait dans la liste
		if (userList.usernameExist(user)) {
			message="r i f";
		} 
		else {message="r i t"; }
		//on envoie notre reponse
		udpcom.sendDatagram(message,notification.port ,notification.add);
	
	}
	
	
		
	//notify connexion
	if (notification.data.charAt(0)=='c') {
		String[] data = notification.data.split(" ");
		User user= new User(data[1],data[2],data[3]);
		//ajouter user a la list
		userList.add(user); 
		
		//on notify notre connexion à cette personne
		udpcom.sendDatagram("c "+IHM.currentUsername+" "+IHM.currentMac+" "+IHM.currentIp, 1234 ,notification.add);
		
		
		
	}
	//notify deconnexion
	if (notification.data.charAt(0)=='d') {
		String[] data = notification.data.split(" ");
		User user= new User(data[1],data[2],data[3]);
		//enlever user à la list 
		userList.remove(user); 
	}
	
	//notify change username
	if (notification.data.charAt(0)=='a') {
		String[] data = notification.data.split(" ");
		User user= new User(data[1],data[2],data[3]);
		String newusername= data[4];
		//modifier user de la liste avec newusername
		userList.changeUsername(user,newusername);  	
	}
	
	
	
	
}
}