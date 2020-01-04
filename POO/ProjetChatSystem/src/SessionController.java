class SessionController extends AbstractController
{
    Session session;


    SessionController(Session session)
    {
        super();
        this.session = session;
    }


    void sendMessage(String message)
    {
        // TODO: filtrer taille de message et caractere '[ ' ']' /close ...
        session.sendMessage(message);
    }


    void closeSession()
    {
        session.sendMessage("/close");
        session.closeSession();
    }
}
