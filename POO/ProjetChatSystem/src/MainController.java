

class MainController extends AbstractController
{
    private SessionManager sessionManager;
    private UserManager userManager;


    MainController(UserManager userManager, SessionManager sessionManager)
    {
        super();
        this.userManager = userManager;
        this.sessionManager = sessionManager;
    }


    void openSession(User selectedUser)
    {
        if (selectedUser != null && !selectedUser.getIsActiveSession())
        {
            sessionManager.openSessionFromUserList(selectedUser);
        }
    }


    void changeUsername(String newName)
    {
        // TODO: filtrer les caracteres de username > 0
        userManager.changeUsername(newName);
    }


    void disconnection()
    {
        // notify disconnection
        userManager.abortConnection();
        // close opened sessions and server socket
        sessionManager.closeAllSessions();
    }
}
