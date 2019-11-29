package src;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NotificationCenter {
String history;
UdpCommunication udpcom; 
UserList userList; 
InetAddress broadcast; 

NotificationCenter(){
	udpcom= new  UdpCommunication();
	userList= new UserList(); 
	try {broadcast= InetAddress.getByName("127.0.0.1");}
	catch(UnknownHostException e){}
}

// Code des notifications i,c,d,a, r reponse 
public void check_disponibility(String username) 
{
	udpcom.sendDatagram(("i "+username), 1234 ,this.broadcast );
}

public void notify_connexion(String username) 
{
	udpcom.sendDatagram("c "+username, 1234 ,this.broadcast);
}
public void notify_deconnexion(String username) 
{
	udpcom.sendDatagram("d "+username, 1234 ,this.broadcast);
}
public void notify_change_username(String username,String newusername) 
{
	udpcom.sendDatagram("c "+username+" "+newusername, 1234 ,this.broadcast);
}

//lA FONCTION SUIVANTE DOIT ETRE APPELER DANS UNE BOUCLE WHILE APRES LA CONNEXION 
public void handle_notification()
{
	Notification notification= udpcom.receiveDatagram();
	if (notification.data.charAt(0)=='i') {
		String username= notification.data.substring(2);
		String message;
		//a faire: regarde si un utilisateur utilisant le username apparait dans la liste
		if (userList.contains(username)) {
			message="r i f";
		} 
		else {message="r i t"; }
		udpcom.sendDatagram(message,notification.port ,notification.add);
	
	}
	if (notification.data.charAt(0)=='c') {
	
	
}
}