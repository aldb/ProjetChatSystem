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
public class History extends ArrayList<Message>
{
	private final String saveDirectory = "Histories/";
	private User receiver;
	
	public History(User receiver)
	{
		this.receiver = receiver;
	}
	
	
	public void saveHistory()
	{
		String contents = "History File - Current User @MAC " + IHM.currentUser.getMacAddress() + " - Receiver @MAC " + this.receiver.getMacAddress() + "\r\n";
		for (Message message : this)
			contents += "[" + String.format("dd-MMM-yyyy HH:mm:ss", message.getDate()) + "][" + message.getSender().getMacAddress() + "]" + message.getData() + "\n";
        
		String absoluteFilePath = this.saveDirectory + "history_" + IHM.currentUser.getMacAddress() + "_" + this.receiver.getMacAddress() + "_data.txt";
		File file = new File(absoluteFilePath);
        if (file.exists()) file.delete();
        try
		{
			if (file.createNewFile()) System.out.println("File Created: " + absoluteFilePath);  // TODO: display to IHM
			Files.write(Paths.get(absoluteFilePath), contents.getBytes("UTF-8"));
		} catch (IOException e)
		{
			e.printStackTrace(); // TODO Handle exception : fail
		}
	}
	
	
	public void retrieveHistory()
	{
		String absoluteFilePath = this.saveDirectory+ "history_" + IHM.currentUser.getMacAddress() + "_" + this.receiver.getMacAddress() + "_data.txt";;
		try
		{
			List<String> contents = Files.readAllLines(Paths.get(absoluteFilePath));
			contents.remove(0); // first information line
			for (String line : contents)
			{
				Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(line.split("]")[0].replace("[", ""));
				String senderMAC = line.split("]")[1].replace("[", "");
				User sender;
				if (IHM.currentUser.getMacAddress() == senderMAC)
					sender = IHM.currentUser;
				else if (receiver.getMacAddress() == senderMAC)
					sender = receiver;
				else sender = new User("System", "System", "System");
				String data = line.split("]")[2];
				this.add(new Message(data, sender, date));
			}
		} catch (IOException | ParseException e)
		{
			e.printStackTrace(); // TODO Handle exception : no history found for this session...
		}
	}
	
	
	// To display it on IHM
	public String getMessagesData()
	{
		String messagesData = "";
		for (Message message : this)
		{
			messagesData += "[" + String.format("dd-MMM-yyyy HH:mm:ss", message.getDate()) + "] " + message.getSender().getUsername() + " : " + message.getData() + "\n";
		}
		return messagesData;
	}
}
