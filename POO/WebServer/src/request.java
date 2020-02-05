import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class request
 */
@WebServlet("/request")
public class request extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	private ArrayList<User> userList;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public request()
    {
        super();
        this.userList = new ArrayList<User>();
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String type = request.getParameter("Type");
		String username = request.getParameter("Username");
		String macAddress = request.getParameter("MacAddress");
		String ipAddress = request.getParameter("IpAddress");
		if (type != null && username != null && macAddress != null && ipAddress != null)
		{
			User user = new User(username, macAddress, ipAddress);
			switch (type)
			{
				case "Refresh":
					refresh(user, response);
					break;
				case "Connection":
					connection(user, response);
					break;
				case "Disconnection":
					disconnection(user, response);
					break;
				default:
					break;
			}
		}
	}
	
	
	private void refresh(User user, HttpServletResponse response) throws IOException
	{
		User UserInL = find(user);
		if(UserInL != null && UserInL.getUsername() != user.getUsername()) // Si changement username
		{
			UserInL.setUsername(user.getUsername());
		}
		for (User s : userList) // Retourne la liste
			response.getWriter().write(s.getUsername() + "|" + s.getMacAddress() + "|" + s.getIpAddress() + "\n");
	}
	
	
	private void connection(User user, HttpServletResponse response) throws IOException
	{
		// Existe deja ou username pas dispo
		if(findUserByUsername(user.getUsername()) != null)// || find(user) != null) // TODO: commenter pour test local
		{
			response.getWriter().write("NOT OK");
		}
		else
		{
			userList.add(user);
			response.getWriter().write("OK");
		}
	}
	
	
	private void disconnection(User user, HttpServletResponse response) throws IOException
	{
		User userInL = find(user);
		if(userInL != null) // Existe donc unsubscribe
		{
			userList.remove(userInL);
			response.getWriter().write("OK");
		}
		else // N'existait pas
		{
			response.getWriter().write("NOT OK");
		}
	}
	
	
	private User find(User userToFind)
	{
		User user = null;
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).equals(userToFind))
            {
                user = userList.get(i);
                i = userList.size();
            }
        }
		return user;
	}
	
	
	private User findUserByUsername(String username)
	{
		User user = null;
        for (int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getUsername().equals(username))
            {
                user = userList.get(i);
                i = userList.size();
            }
        }
		return user;
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
