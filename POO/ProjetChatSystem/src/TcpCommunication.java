import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpCommunication
{
	private Socket sock;
	private static ServerSocket serverSock; // 
	
	public TcpCommunication() throws UnknownHostException, IOException
	{
		// Generer num port
		int port = 5000;
		serverSock = new ServerSocket(port, 1000);
	}
	
	public Socket newConnection()
	{
		Socket sock =
		null;
		try
		{	
			// TCP
			sock = serverSock.accept();
			//SockThread sockThread = new SockThread(sock);
			//sockThread.start();		
			}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sock;
	}
	
	public void send(String data)
	{
		
	}
	
	public String receive()
	{
		return "";
	}
}
