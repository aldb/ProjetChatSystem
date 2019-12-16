import java.util.ArrayList;

@SuppressWarnings("serial")
public class UserList extends ArrayList<User>
{
	public UserList() {};
	
	public boolean usernameExist(User user)
	{
		int i = 0;
		boolean exist= false;
		while (i < this.size() && !exist) {
			exist= User.compareUsername(this.get(i), user);
			System.out.print("valeur exist: "+exist); 
			i++;
		}
		System.out.print("Le nom apparrait dans ma liste "+exist); 
		return exist; 	
	}
	
	
	
	public void changeUsername(User user, String newusername)  // ??????????
	{
		int i = 0;
		if (this.contains(user)) 
		{
			while (i < this.size()) {
				if (this.get(i).equals(user)) {
					this.get(i).setUsername(newusername);
				}
				i++;
			}
		}
		else
			user.setUsername(newusername);
			this.add(user);
	}
}
