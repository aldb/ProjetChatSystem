package src;

import java.net.InetAddress;
import java.util.Date;

public class Notification {
	
	String data;
	int port ;
	InetAddress add;
	Date date;
	
	
	public Notification(String data, int port, InetAddress add )
	{	this.port= port;
		this.add=  add; 
		this.data = data;
		this.date = new Date();
	}
	public Notification() {
		this.data = "";
	} 
}
