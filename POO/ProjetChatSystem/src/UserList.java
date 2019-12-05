import java.util.ArrayList;

@SuppressWarnings("serial")
public class UserList extends ArrayList<User>
{
	UserList(){};
	
	public boolean usernameExist(User user) {
		int i;
		boolean exist= false;
		while (i < this.size() && !exist  ) {
			exist= User.compareUsername(this.get(i), user);
			i++;
		}
		
		return exist; 	
	}
	
	public void changeUsername(User user, String newusername) 
	{
		int i;
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