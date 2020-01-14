import java.io.IOException;
import java.net.*;
import java.util.Date;

public class DecentralizedCenter extends AbstractModel implements Runnable
{
    private User currentUser;
    private UserListModel userListModel;

    private DatagramSocket datagramSocket;
    enum UsernameStatus { PENDING, AVAILABLE, ALREADY_TAKEN_OR_NOT_ASSIGNED }
    private UsernameStatus usernameStatus;
    private InetAddress broadcastAddress;


	DecentralizedCenter(User currentUser, UserListModel userListModel)
	{
	    this.currentUser = currentUser;
	    this.userListModel = userListModel;
        this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED;

        try
        {
            datagramSocket = new DatagramSocket(Launch.portB);
            datagramSocket.setSoTimeout(50);
        } catch (SocketException e)
        {
            this.view.showErrorDialog("Erreur lors de l'ouverture du socket UDP : " + e.getMessage());
        }
        new Thread(this).start();
    }


	// Code des notifications :
    // c = connexion (inclus la verification disponiblite pseudo)
    // r = reponse positive 't' / negative 'f' sur la verification du pseudo seulement lors de la connexion
    //     entraine la connexion de la personne et l'ajout a la liste de chaque machine si positive
    // d = deconnexion
    // a = changement username (valide par soi meme puisque userListModel valide a tout moment)
	void notify_connexion()
	{
	    this.usernameStatus = UsernameStatus.PENDING; // on laisse entrer les notifications
		this.sendNotification(new Notification("c "+currentUser.getUsername()+" "+currentUser.getMacAddress(), this.broadcastAddress, new Date()));
	}
	
	void notify_deconnexion()
	{
        this.sendNotification(new Notification("d "+currentUser.getUsername()+" "+currentUser.getMacAddress(), this.broadcastAddress, new Date()));
        this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED;  // on ne laisse plus entrer les notifications
	}
	
	void notify_change_username()
	{
        this.sendNotification(new Notification("a "+currentUser.getUsername()+" "+currentUser.getMacAddress(), this.broadcastAddress, new Date()));
	}

    private void notify_response_connexion (User destUser, char response) // on notifie le user de la reponse et on dit qui on est au cas ou c'est positif
    {
        this.sendNotification(new Notification("r"+response+" "+currentUser.getUsername()+" "+currentUser.getMacAddress(), destUser.getIpAddress(), new Date()));
    }
	

	public void run()
	{
        while (true)
        {
            Notification notification = this.receiveNotification();

            // TODO: ATTENTION A COMMENTER POUR TEST LOCAL (permet de ne pas recevoir nos propres notifications (a cause du broadcast)
            if(notification != null && this.usernameStatus != UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED )//&& !notification.getIpAddress().getHostAddress().equals(currentUser.getIpAddress().getHostAddress()))
            {
                String[] data = notification.getData().split(" ");
                User sender = new User(data[1], data[2], notification.getIpAddress(), false);

                // reponse : il nous suffit d'une seule reponse positive ou negative pour conclure car chacun est a jour
                if (notification.getData().charAt(0) == 'r')
                {
                    if (notification.getData().charAt(1) == 't')
                    {
                        this.usernameStatus = UsernameStatus.AVAILABLE;
                        this.userListModel.addElement(sender);
                    }
                    else
                    {
                        this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED; // on ne laisse plus entrer les notifications
                    }
                }

                // check disponibility of username then connect if ok
                else if (notification.getData().charAt(0)=='c')
                {
                    // regarde si un utilisateur utilisant le username apparait dans la liste
                    if (false) // TODO: for local test: false, or must be :(userListModel.find(sender) != null || sender.equals(currentUser)) instead
                    {
                        this.notify_response_connexion(sender, 'f');
                    }
                    else
                    {
                        // on sait qu'il sera connecté puisqu'on vérifie dans la liste
                        // on envoie une reponse qui inclus le fait qu'on est présent aussi
                        this.notify_response_connexion(sender, 't');
                        this.userListModel.addElement(sender);
                    }
                }

                // notify disconnection
                else if (notification.getData().charAt(0)=='d')
                {
                    boolean succeed = false;
                    User user = this.userListModel.find(sender);
                    if (user != null)
                        succeed = this.userListModel.remove(user);
                    if (!succeed)
                    {
                        this.view.showErrorDialog("Erreur lors de la reception d'une notification de deconnexion");
                    }
                }

                // notification of User.username change in userListModel
                else if (notification.getData().charAt(0)=='a')
                {
                    User user = this.userListModel.find(sender);
                    if (user != null)
                    {   
                        this.view.showInformationDialog(user.getUsername()+" est maintenat "+sender.getUsername());
                        user.setUsername(sender.getUsername());
                        ((MainView)view).refreshUserList();
                    } else
                    {
                        this.view.showErrorDialog("Erreur lors de la reception d'une notification de changement de nom d'utilisateur");
                    }
                }
            }
        }
	}


    private Notification receiveNotification()
    {
        Notification notification = null;
        byte[] buffer = new byte[256];
        DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
        try
        {
            this.datagramSocket.receive(inPacket);
            String message = new String(inPacket.getData(), 0, inPacket.getLength());
            notification = new Notification(message, inPacket.getAddress(), new Date());
        }
        catch (IOException e)
        {
            if (!e.getMessage().equals("Receive timed out"))
                this.view.showErrorDialog("Erreur lors de la reception d'une notification : " + e.getMessage());
        }
        return notification;
    }


    private void sendNotification(Notification notification)
    {
        DatagramPacket outPacket = new DatagramPacket(notification.getData().getBytes(), notification.getData().length(), notification.getIpAddress(), Launch.portA);
        try
        {
            this.datagramSocket.send(outPacket);
        } catch (IOException e)
        {
            this.view.showErrorDialog("Erreur lors de l'envoi d'une notification : " + e.getMessage());
        }
    }


    UsernameStatus getUsernameStatus()
    {
        return this.usernameStatus;
    }


    void setBroadcastAddress(InetAddress broadcastAddress)
    {
        this.broadcastAddress = broadcastAddress;
    }
}