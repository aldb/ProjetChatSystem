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
    	   session.sendMessage("/file748159263/"+choice.getSelectedFile().getName()+"/"+choice.getSelectedFile().length());
    	   System.out.println(choice.getSelectedFile().length());
    	   session.sendFile( choice.getSelectedFile().getAbsolutePath());
    	}
    	
       
    }


    void closeSession()
    {
        session.sendMessage("/close748159263");
        session.closeSession();
    }
}
