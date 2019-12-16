import java.util.Date;

public class Message
{
	private String data;
	private Date date;
	private User sender;
	
	
	public Message(String data, User sender, Date date)
	{
		this.data = data;
		this.sender = sender;
		this.date = date;
	}

	public String getData()
	{
		return this.data;
	}

	public Date getDate()
	{
		return this.date;
	}
	
	public User getSender()
	{
		return this.sender;
	}
}
