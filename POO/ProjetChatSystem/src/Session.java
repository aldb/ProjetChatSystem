import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;


class Session extends AbstractModel implements Runnable
{
	private History history;
	private Socket sock;
	private PrintWriter writer;
	
	
	Session(User currentUser, User receiver, Socket sock)
    {
        this.sock = sock;
        this.history = new History(currentUser, receiver);
    }


    void start()
    {
        if (!this.history.retrieveHistory())
            ((SessionView) this.view).refreshChatTextArea("Aucun historique trouve ou erreur a la recuperation");
        else
            ((SessionView)this.view).refreshChatTextArea(history.getMessagesData());

        try
        {
            writer = new PrintWriter(sock.getOutputStream());
        } catch (IOException e)
        {
            this.view.showErrorDialog("Erreur lors de la creation du writer du socket TCP de la session : " + e.getMessage());
        }

        new Thread(this).start();
    }


    void sendMessage(String data)
    {
        writer.write("\0" + data);
        writer.flush();
        if (!data.equals("/close748159263") && !data.contains("/file") )
        {
            history.addSentMessage(data, new Date());
            ((SessionView)this.view).refreshSendMessageTextField();
            ((SessionView)this.view).refreshChatTextArea(history.getMessagesData());
        }
    }


    void sendFile(String path)
    {
		try
        {
            byte[] data = Files.readAllBytes(new File(path).toPath());
            writer.write("\0");
            writer.flush();
            sock.getOutputStream().write(data);
            sock.getOutputStream().flush();
            history.addSentMessage("Envoi du fichier " + path, new Date());
            ((SessionView)this.view).refreshChatTextArea(history.getMessagesData());
		} catch (IOException e)
        {
            this.view.showErrorDialog("Erreur lors de l'envoi du fichier : " + e.getMessage());
        }
    }
	
	
	void closeSession()
	{
        try
        {
            this.sock.close();
        } catch (IOException e)
        {
            this.view.showErrorDialog("Erreur lors de la fermeture du socket TCP de la session : " + e.getMessage());
        }
        if(!this.history.saveHistory())
            this.view.showErrorDialog("Erreur lors de la sauvegarde de l'historique");
        this.view.setVisible(false);
        this.view.dispose(); // Destroy the JFrame object
	}


    @Override
    public void run()
    {
        try
        {
            String fichier = null;
            int size = 0;
            BufferedInputStream reader = new BufferedInputStream(sock.getInputStream());
            while(reader.read() != -1)
            {
            	byte[] b;
                if (fichier != null)
                {
                	b = new byte[size];
                }
                else
                {
                	b = new byte[1024];
                }
                int stream = reader.read(b);
                String data = new String(b, 0, stream);
                
                if (data.equals("/close"))
                {
                    this.view.showInformationDialog("Session ferme par l'utilisateur distant");
                    this.closeSession();
                }
                else if (data.contains("/file"))
                {
                	fichier = data.split("/")[2];
                	size = Integer.valueOf(data.split("/")[3]);
                }
                else if (fichier != null)
                {
                    try
                    {
                        String path = new File(".").getCanonicalPath() + "\\Files\\" + fichier;
                        File file = new File(path);
                        if (file.exists()) file.delete(); // TODO: message "ecraser le fichier ? oui/non"
                        file.createNewFile();
                        Files.write(Paths.get(path),b);
                        history.addReceivedMessage("Reception du fichier " + path, new Date());
                        ((SessionView)this.view).refreshChatTextArea(history.getMessagesData());
                    } catch (IOException e)
                    {
                        this.view.showErrorDialog("Erreur lors de la reception d'un fichier : " + e.getMessage());
                    }
                    fichier = null;
                }
                else // Message texte
                {
                    history.addReceivedMessage(data, new Date());
                    ((SessionView)this.view).refreshChatTextArea(history.getMessagesData());
                }
            }
        }
        catch (IOException e)
        {
            if (!e.getMessage().equals("Socket closed"))
                this.view.showErrorDialog("Erreur lors de la lecture du socket TCP de la session : " + e.getMessage());
        }
    }
}
