import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
class History extends ArrayList<Message>
{
   // private final String saveDirectory = "home/aldebert/Histories/";
	private final String saveDirectory = "./Histories/"; // TODO: regler probleme history relatif linux
    private User currentUser;
	private User receiver;


	History(User currentUser, User receiver)
	{
	    super();
	    this.currentUser = currentUser;
		this.receiver = receiver;
	}
	
	
	boolean saveHistory()
	{
        receiver.setIsActiveSession(false);
	    boolean succeed = false;
		String contents = "History File - Current User @MAC " + currentUser.getMacAddress() + " - Receiver @MAC " + this.receiver.getMacAddress() + "\r\n";
		for (Message message : this)
			contents += "[" + (new SimpleDateFormat("dd/MM HH:mm")).format(message.getDate()) + "][" + message.getSender().getMacAddress() + "]" + message.getData() + "\n";
        
		String absoluteFilePath = this.saveDirectory + "history_" + this.receiver.getMacAddress() + "_data.txt";
		File file = new File(absoluteFilePath);
        if (file.exists()) file.delete();
        try
		{
			file.createNewFile();
			Files.write(Paths.get(absoluteFilePath), contents.getBytes("UTF-8"));
			succeed = true;
		} catch (IOException e)
		{
			// Handle by return value
		}
		return succeed;
	}

	
	boolean retrieveHistory()
	{
	    receiver.setIsActiveSession(true);
        boolean succeed = false;
		String absoluteFilePath = this.saveDirectory + "history_" + this.receiver.getMacAddress() + "_data.txt";
		try
		{
			List<String> contents = Files.readAllLines(Paths.get(absoluteFilePath));
			contents.remove(0); // first information line
			for (String line : contents)
			{
				Date date = new SimpleDateFormat("dd/MM HH:mm").parse(line.split("]")[0].replace("[", ""));
				String senderMAC = line.split("]")[1].replace("[", "");
				String data = line.split("]")[2];
				if (receiver.getMacAddress().equals(senderMAC))
					this.addReceivedMessage(data, date);
				else this.addSentMessage(data, date);
			}
            succeed = true;
		} catch (IOException | ParseException e)
		{
		    // Handle by return value
		}
        return succeed;
	}


	void addSentMessage(String data, Date date)
    {
        this.add(new Message(data, currentUser, date));
    }


    void addReceivedMessage(String data, Date date)
    {
        this.add(new Message(data, receiver, date));
    }
	

	String getMessagesData()
	{
		String messagesData = "";
		for (Message message : this)
			messagesData += "[" + (new SimpleDateFormat("dd/MM HH:mm")).format(message.getDate()) + "] " + message.getSender().getUsername() + " : " + message.getData() + "\n";
		return messagesData;
	}
}
