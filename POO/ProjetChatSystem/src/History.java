import java.util.ArrayList;

@SuppressWarnings("serial")
public class History extends ArrayList<Message>
{
	public History()
	{
		
	}
	
	
	public void saveHistory()
	{
		// in a txt file
	}
	
	
	public void retrieveHistory()
	{
		// from a txt file
	}
	
	// To display it
	public String getMessagesData()
	{
		String messagesData = "";
		for (Message message : this)
		{
			messagesData += " " + message.getSender() + " : " + message.getData() + "\n\n";
		}
		return messagesData;
	}
}
