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
		// retrieve history
	}
	
	public void closeSession() throws IOException
	{
		sock.close();
		// save history?
	}
	
	public void saveHistory()
	{
		// in a txt file
	}
	
	public void retrieveHistory()
	{
		// from a txt file
	}
	

	public ArrayList<Message> getHistory()
	{
		return history;
	}

	public User getReceiver()
	{
		return receiver;
	}
}
