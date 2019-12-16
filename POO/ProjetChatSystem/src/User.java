
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
		return u1.getUsername().equals(u2.getUsername());	
	}
	
	@Override
	public boolean equals(Object o) { 
		  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof User)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        User c = (User) o; 
          
        // Compare the data members and return accordingly  
        return c.getUsername().equals(this.getUsername());
    } 
	
	public String toString() { return this.username;}

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
