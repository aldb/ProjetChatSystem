import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Session
{
	private ArrayList<Message> history;
	private User receiver;
	private Socket sock;
	
	
	public Session(User receiver, Socket sock)
	{
		this.receiver = receiver;
		this.sock = sock;
		this.history = new ArrayList<Message>();
		retrieveHistory();
	}
	
	public void closeSession() throws IOException
	{
		sock.close();
		saveHistory();
	}
	
	
	
	public void saveHistory()
	{
		// in a txt file
	}
	
	public void retrieveHistory()
	{
		// from a txt file
	}
	

	// Display on IHM
	public ArrayList<Message> getHistory()
	{
		return history;
	}

	public User getReceiver()
	{
		return receiver;
	}
}
