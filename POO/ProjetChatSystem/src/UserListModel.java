import javax.swing.*;

@SuppressWarnings("serial")
public class UserListModel extends DefaultListModel<User>
{
	UserListModel()
    {
        super();
    }

	public User find(User userToFind)
	{
		User user = null;
        for (int i = 0; i < this.size(); i++)
        {
            if (this.get(i).equals(userToFind))
            {
                user = this.get(i);
                i = this.size();
            }
        }
		return user;
	}


    public boolean remove(User userToRemove)
    {
        boolean succeed = false;
        User user = this.find(userToRemove);
        if (user != null)
        {
            succeed = removeElement(user);
        }
        return succeed;
    }
}
