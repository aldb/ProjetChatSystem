import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class SocketReceiveThread extends Thread
{
	Socket sock;
	BufferedInputStream reader;
	ArrayList<Message> history;
	
	
	SocketReceiveThread(Socket sock, ArrayList<Message> history, )
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
				history.add(new Message(data, ));
			}
		}
		catch (IOException e)
		{
			// Socket not connected
			//history.add(new Message("La connexion a été interrompu."));
			e.printStackTrace();
		}
	}
}
