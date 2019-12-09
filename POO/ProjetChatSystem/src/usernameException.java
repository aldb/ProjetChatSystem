

@SuppressWarnings("serial")
public class UsernameException extends Exception
{
	public UsernameException() 
	{
		System.out.println("Ce nom d'utilisateur est déja pris");
	}
}
