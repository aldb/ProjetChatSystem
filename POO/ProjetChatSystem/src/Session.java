import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Session
{
	private History history;
	private User receiver;
	private Socket sock;
	
	
	public Session(User receiver, Socket sock)
	{
		this.receiver = receiver;
		this.sock = sock;
		this.history = new History();
		
		this.history.retrieveHistory();
		this.enableReceivingMessages();
	}
	
	
	public void sendMessage(String data)
	{
		try
		{
			PrintWriter writer = new PrintWriter(sock.getOutputStream());
			writer.write(data);
			writer.flush();
			writer.close();
			//history.add(new Message(data, IHM.currentUser));
		} catch (IOException e)
		{
			//history.add(new Message("Impossible d'envoyer le message : Connexion interrompu."), );
			e.printStackTrace();
		}
	}
	
	private void enableReceivingMessages()
	{
		SocketReceiveThread sockReceiveThread = new SocketReceiveThread(this.sock, this.history);
		sockReceiveThread.start();
	}
	
	
	public void closeSession() throws IOException
	{
		this.sock.close();
		this.history.saveHistory();
	}
	

	// Display on IHM
	public History getHistory()
	{
		return this.history;
	}

	public User getReceiver()
	{
		return this.receiver;
	}
}
