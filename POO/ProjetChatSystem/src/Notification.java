import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Notification
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


    public String getData() { return data; }

    public InetAddress getIpAddress() { return ipAddress; }

    public Date getDate() { return date; }
}
