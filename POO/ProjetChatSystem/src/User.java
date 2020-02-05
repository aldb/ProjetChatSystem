import java.net.InetAddress;

class User
{
	private String username;
	private String macAddress;
	private InetAddress ipAddress;
    private boolean isActiveSession;


	User(String username, String macAddress, InetAddress ipAddress)
	{
		this.username = username;
		this.macAddress = macAddress;
		this.ipAddress = ipAddress;
		this.isActiveSession = false;
	}


	@Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        User user = (User)obj;
        if ((this.getUsername() != null && this.getUsername().equals(user.getUsername()))
                || (this.getMacAddress() != null && this.getMacAddress().equals(user.getMacAddress()))
                || (this.getIpAddress() != null && this.getIpAddress().equals(user.getIpAddress()))
            )
        {
            isEqual = true;
        }
        return isEqual;
    }


	String getUsername() { return this.username; }

	void setUsername(String username)
	{
		this.username = username;
	}

	String getMacAddress() { return this.macAddress; }

	void setMacAddress(String macAddress)
	{
		this.macAddress = macAddress;
	}

	InetAddress getIpAddress() { return this.ipAddress; }

	void setIpAddress(InetAddress ipAddress)
	{
		this.ipAddress = ipAddress;
	}

    boolean getIsActiveSession() { return this.isActiveSession; }

    void setIsActiveSession(boolean isActiveSession)
    {
        this.isActiveSession = isActiveSession;
    }
}
