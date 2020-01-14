import javax.swing.JFileChooser;

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
    
    void sendFile()
    {
    	
    	JFileChooser choice = new JFileChooser();
    	choice.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	int retour=choice.showOpenDialog(null);
    	if(retour==JFileChooser.APPROVE_OPTION){
    		session.sendMessage("/file/"+choice.getSelectedFile().getName());
    	   session.sendFile( choice.getSelectedFile().getAbsolutePath());
    	}
    	
       
    }


    void closeSession()
    {
        session.sendMessage("/close");
        session.closeSession();
    }
}
