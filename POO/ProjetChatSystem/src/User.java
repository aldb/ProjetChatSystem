import java.net.InetAddress;

public class User
{
	private String username;
	private String macAddress;
	private InetAddress ipAddress;
	private boolean isCentralized;
    private boolean isActiveSession;


	User(String username, String macAddress, InetAddress ipAddress, boolean isCentralized)
	{
		this.username = username;
		this.macAddress = macAddress;
		this.ipAddress = ipAddress;
		this.isCentralized = isCentralized;
		this.isActiveSession = false;
	}


	@Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        User user = (User)obj;
        if ((this.getUsername() != null && this.getUsername().equals(user.getUsername()))
                || (this.getMacAddress() != null && this.getMacAddress().equals(user.getMacAddress()))
                || (this.getIpAddress() != null && this.getIpAddress().equals(user.getIpAddress()) && !isCentralized)
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

	public InetAddress getIpAddress() { return this.ipAddress; }

	public void setIpAddress(InetAddress ipAddress)
	{
		this.ipAddress = ipAddress;
	}

    public boolean getIsCentralized() { return this.isCentralized; }

    public void setIsCentralized(boolean isCentralized)
    {
        this.isCentralized = isCentralized;
    }

    public boolean getIsActiveSession() { return this.isActiveSession; }

    public void setIsActiveSession(boolean isActiveSession)
    {
        this.isActiveSession = isActiveSession;
    }
}
