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
        // TODO: check length of file, max 100Ko ?
    	JFileChooser choice = new JFileChooser();
    	choice.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	int retour=choice.showOpenDialog(null);
    	if(retour==JFileChooser.APPROVE_OPTION){
    	   session.sendMessage("/file/"+choice.getSelectedFile().getName()+"/"+choice.getSelectedFile().length());
    	   System.out.println(choice.getSelectedFile().length());
    	   session.sendFile( choice.getSelectedFile().getAbsolutePath());
    	}
    	
       
    }


    void closeSession()
    {
        session.sendMessage("/close");
        session.closeSession();
    }
}
