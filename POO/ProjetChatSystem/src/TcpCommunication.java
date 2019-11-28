import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpCommunication
{
	private Socket sock;
	private static ServerSocket serverSock; // partagé //serverSock = new ServerSocket(5000, 1000, );
	
	public TcpCommunication(boolean type, String ipAddress) throws UnknownHostException, IOException
	{
		// Generer num port
		int port = 5000;
		
	}
	
	public void send(String data)
	{
		
	}
	
	public String receive()
	{
		return "";
	}
}
