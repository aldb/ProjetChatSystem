import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

class UserManager extends AbstractModel
{
    private String log; // TODO: implement log
    private User currentUser;
    private UserListModel userListModel;
    private DecentralizedCenter decentralizedCenter;


    UserManager()
    {
        this.currentUser = new User(null, null, null, false);
        this.userListModel = new UserListModel();
        this.decentralizedCenter = new DecentralizedCenter(currentUser, userListModel);
        this.decentralizedCenter.setView(this.view);
    }


    void establishConnection()
    {
        if (this.retrieveLocalMacAndIpAddress()) // si le reseau est valide
        {
            // on notifie la connexion en checkant la disponibilite du username et les reponses nous servirons a construire notre liste des utilisateurs actifs
            this.decentralizedCenter.notify_connexion();
            // attente d'une reponse
            for (int time = 0; this.decentralizedCenter.getUsernameStatus() == DecentralizedCenter.UsernameStatus.PENDING && time < 1000; time += 50)
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(50);
                }
                catch (InterruptedException e)
                {
                    this.view.showErrorDialog(e.getMessage());
                }
            }

            if (this.decentralizedCenter.getUsernameStatus() == DecentralizedCenter.UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED) // username non disponible
            {
                this.view.showErrorDialog("Ce nom d'utilisateur n'est pas disponible.");
            }
            else // NONE ou AVAILABLE --> username non utilise donc connexion ok
            {
                // Affiche la fenetre principale et fait disparaitre la fenetre de connexion
                SessionManager sessionManager = new SessionManager(this);
                MainController mainController = new MainController(this, sessionManager);
                MainView mainView = new MainView(mainController, this.currentUser.getUsername());
                this.view.setVisible(false);
                this.view.dispose(); // Destroy the JFrame object
                sessionManager.setView(mainView);
                this.setView(mainView);
                decentralizedCenter.setView(mainView);
                mainView.setUserListModel(this.userListModel);
            }
        }
        else
        {
            this.view.showErrorDialog("Aucune connexion réseau valide.");
        }
    }


    private boolean retrieveLocalMacAndIpAddress()
    {
        boolean found = false;
        try
        {
            Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
            while (ni.hasMoreElements() && !found)
            {
                NetworkInterface e = ni.nextElement();
                if (!e.isLoopback() && e.isUp()) // TODO: conditionner parfaitement
                {
                    byte[] macbyte = e.getHardwareAddress();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < macbyte.length; i++)
                    {
                        sb.append(String.format("%02X%s", macbyte[i], (i < macbyte.length - 1) ? "-" : ""));
                    }
                    this.currentUser.setMacAddress(sb.toString()); // MAC
                    InetAddress addr = e.getInetAddresses().nextElement();
                    this.currentUser.setIpAddress(addr); // IP
                    for (InterfaceAddress interfaceAddress : e.getInterfaceAddresses())
                    {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast != null)
                        {
                            this.decentralizedCenter.setBroadcastAddress(broadcast); // Broadcast IP
                            found = true;
                        }
                    }
                }
            }
        } catch (SocketException e)
        {
            this.view.showErrorDialog(e.getMessage());
        }
        return found;
    }


    void abortConnection()
    {
        decentralizedCenter.notify_deconnexion();
        userListModel.clear();
        this.view.setVisible(false);
        this.view.dispose(); // Destroy the JFrame object
        LogInController logInController = new LogInController(this);
        LogInView logInView = new LogInView(logInController);
        this.setView(logInView);
        decentralizedCenter.setView(logInView);
    }


    void changeUsername(String newName)
    {
        if (!currentUser.getUsername().equals(newName)) // Si on se nomme deja comme ca
        {
            //check avaibility of new Username --> check in the list
            if (userListModel.find(new User(newName, null, null, false)) != null)
            {
                this.view.showErrorDialog("Ce nom d'utilisateur est deja pris");
            } else
            {
                currentUser.setUsername(newName);
                this.view.setTitle(newName);
                // notify the name change
                decentralizedCenter.notify_change_username();
            }
        }
    }


    User findUser(User user)
    {
        return userListModel.find(user);
    }


    User getCurrentUser()
    {
        return currentUser;
    }


    void setCurrentUsername(String username)
    {
        currentUser.setUsername(username);
    }
}
