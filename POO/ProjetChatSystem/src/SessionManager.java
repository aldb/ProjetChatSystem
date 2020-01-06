import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SessionManager extends AbstractModel implements Runnable
{
	private ServerSocket serverSock;
    private ArrayList<Session> sessions;
    private UserManager userManager;

	SessionManager(UserManager userManager)
	{
	    this.userManager = userManager;
        this.sessions  = new ArrayList<>();
        new Thread(this).start();
    }


	public void run()
	{
		try
		{
			int port = Launch.portB + 1000;
			this.serverSock = new ServerSocket(port, 1000);
			while (!serverSock.isClosed())
			{
				Socket sock = serverSock.accept();
				User remoteUser = userManager.findUser(new User(null, null, sock.getInetAddress(), false));
				openSession(remoteUser, sock);
			}
		}
		catch (IOException e)
		{
            if (!e.getMessage().equals("socket closed") && !e.getMessage().equals("Socket closed"))
                this.view.showErrorDialog("Erreur lors de l'execution du socket serveur session TCP : " + e.getMessage());
		}
	}
	
	
	private void disableIncomingSessionRequests()
	{
		try
		{
			serverSock.close();
		} catch (IOException e)
		{
            this.view.showErrorDialog("Erreur lors de la fermeture du socket serveur session TCP : " + e.getMessage());
		}
	}


    void openSessionFromUserList(User selectedUser)
    {
        try
        {
            Socket sock = new Socket(selectedUser.getIpAddress(), Launch.portA + 1000);
            openSession(selectedUser, sock);
        } catch (IOException e)
        {
            this.view.showErrorDialog("Erreur lors de la creation du socket session TCP : " + e.getMessage());
        }
    }


    private void openSession(User remoteUser, Socket sock)
    {
        if (sock != null)
        {
            Session session = new Session(userManager.getCurrentUser(), remoteUser, sock);
            SessionController sessionController = new SessionController(session);
            SessionView sessionView = new SessionView(sessionController, remoteUser.getUsername());
            session.setView(sessionView);
            session.start();
            sessions.add(session);
        }
        else
            this.view.showErrorDialog("Erreur nouveau socket session TCP null");
    }


    void closeAllSessions()
    {
        disableIncomingSessionRequests();
        for (Session session : sessions)
        {
            session.view.dispose();
        }
    }
}
