import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpCommunication extends Thread
{
	ServerSocket serverSock;
	IHM mainIHM;
	
	public TcpCommunication(IHM mainIHM)
	{
		// Generer num port
		this.mainIHM = mainIHM;
	}
	
	public void run()
	{
		try
		{
			int port = 5000;
			this.serverSock = new ServerSocket(port, 1000);
			while (serverSock.isClosed())
			{
				Socket sock = serverSock.accept();
				if (sock != null) mainIHM.openEnteringSession(sock);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace(); // TODO: handle
		}
	}
	
	
	public void disableEnteringConnection()
	{
		try
		{
			serverSock.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void enableEnteringConnection()
	{
		run();
	}
	
	
	public Socket getNewConnectionSocket(String addr, int port)
	{
		Socket sock = null;
		try
		{
			sock = new Socket(addr, port);
		} catch (IOException e)
		{
			// TODO: handle
			e.printStackTrace();
		}
		return sock;
	}
}
