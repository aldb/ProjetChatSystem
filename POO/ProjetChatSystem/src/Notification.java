import java.net.InetAddress;
import java.util.Date;

class Notification
{
	private String data;
	private InetAddress ipAddress;
	private Date date;
	
	
	Notification(String data, InetAddress ipAddress, Date date)
	{
		this.ipAddress = ipAddress;
		this.data = data;
		this.date = date;
	}


    String getData() { return data; }

    InetAddress getIpAddress() { return ipAddress; }

    public Date getDate() { return date; }
}
