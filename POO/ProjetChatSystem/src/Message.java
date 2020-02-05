import java.util.Date;

class Message
{
	private String data;
	private Date date;
	private User sender;
	
	
	Message(String data, User sender, Date date)
	{
		this.data = data;
		this.sender = sender;
		this.date = date;
	}

	String getData()
	{
		return this.data;
	}

	Date getDate()
	{
		return this.date;
	}
	
	User getSender()
	{
		return this.sender;
	}
}
