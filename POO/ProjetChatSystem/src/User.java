
public class User
{
	private String username;
	private boolean isActive;
	private String macAddress;
	private String ipAddress;
	
	public User(String username, String macAdd, String ipAdd)
	{
		this.username= username;
		this.macAddress= macAdd;
		this.ipAddress= ipAdd; 
	}
	
	public static boolean compareUsername (User u1, User u2) 
	{
		return u1.getUsername() == u2.getUsername();	
	}
	

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public String getMacAddress()
	{
		return macAddress;
	}

	public void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
}
