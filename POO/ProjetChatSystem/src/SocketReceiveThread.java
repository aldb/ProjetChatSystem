import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class SocketReceiveThread extends Thread
{
	Socket sock;
	BufferedInputStream reader;
	History history;
	
	
	SocketReceiveThread(Socket sock, History history)
	{
		this.sock = sock;
		this.history = history;
	}
	
	
	public void run()
	{
		try
		{
			reader = new BufferedInputStream(sock.getInputStream()); 
			while(reader.read() != -1)
			{
				String data = "";
				byte[] b = new byte[4096]; // change length ?
				int stream = reader.read(b);
				data = new String(b, 0, stream);
				history.add(new Message(data, IHM.currentUser, new Date()));
			}
		}
		catch (IOException e)
		{
			// Socket not connected
			history.add(new Message("La connexion a été interrompu.", new User("System", "System", "System"), new Date()));
			e.printStackTrace();
		}
	}
}
