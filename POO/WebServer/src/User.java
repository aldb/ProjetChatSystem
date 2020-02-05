
public class User
{
	private String username;
	private String macAddress;
	private String ipAddress;


	User(String username, String macAddress, String ipAddress)
	{
		this.username = username;
		this.macAddress = macAddress;
		this.ipAddress = ipAddress;
	}


	@Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        User user = (User)obj;
        if ((this.getMacAddress() != null && this.getMacAddress().equals(user.getMacAddress()))
                || (this.getIpAddress() != null && this.getIpAddress().equals(user.getIpAddress()))
            )
        {
            isEqual = true;
        }
        return isEqual;
    }


	public String getUsername() { return this.username; }

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getMacAddress() { return this.macAddress; }

	public void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}

	public String getIpAddress() { return this.ipAddress; }

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
}
