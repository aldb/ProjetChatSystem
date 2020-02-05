import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class CentralizedNotifier extends AbstractModel implements Runnable
{
    private User currentUser;
    private UserListModel userListModel;

    enum UsernameStatus { PENDING, AVAILABLE, ALREADY_TAKEN_OR_NOT_ASSIGNED }
    private UsernameStatus usernameStatus;

    private final String RequestUrl = "http://localhost:8080/WebServer/request";


    CentralizedNotifier(User currentUser, UserListModel userListModel)
    {
        this.currentUser = currentUser;
        this.userListModel = userListModel;
        this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED;

        new Thread(this).start();
    }


    void notifyConnection()
    {
        this.usernameStatus = UsernameStatus.PENDING;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Type", "Connection");
        parameters.put("Username", currentUser.getUsername());
        parameters.put("MacAddress", currentUser.getMacAddress());
        parameters.put("IpAddress", currentUser.getIpAddress().getHostAddress());
        String response = executeHttpRequest(RequestUrl, getParamsString(parameters));
        // response connexion ok username pas prit ou not ok username prit
        if (response.equals("OK"))
        {
            this.usernameStatus = UsernameStatus.AVAILABLE;
        }
        else if (response.equals("NOT OK"))
        {
            this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED;
        }
    }


    void notifyDisconnection()
    {
        this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Type", "Disconnection");
        parameters.put("Username", currentUser.getUsername());
        parameters.put("MacAddress", currentUser.getMacAddress());
        parameters.put("IpAddress", currentUser.getIpAddress().getHostAddress());
        String response = executeHttpRequest(RequestUrl, getParamsString(parameters));
        if (response.equals("NOT OK"))
        {
            this.view.showErrorDialog("Erreur lors de la deconnection au webservice");
        }
    }


    void notifyRefresh()
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Type", "Refresh");
        parameters.put("Username", currentUser.getUsername());
        parameters.put("MacAddress", currentUser.getMacAddress());
        parameters.put("IpAddress", currentUser.getIpAddress().getHostAddress());
        String response = executeHttpRequest(RequestUrl, getParamsString(parameters));

        String[] usersString = response.split("\n");
        for (String userString : usersString)
        {
            String[] userInfo = userString.split("|");
            if (userInfo.length == 3)
            {
                try
                {
                    User user = new User(userInfo[0], userInfo[1], InetAddress.getByName(userInfo[2]));
                    User userInL = userListModel.find(user);
                    if (userInL != null)
                    {
                        // change username if changed
                        this.view.showInformationDialog(userInL.getUsername() + " est maintenant " + user.getUsername());
                        userInL.setUsername(user.getUsername());
                        ((MainView)view).refreshUserList();
                    }
                    else if (!user.equals(currentUser)) // on ne s'ajoute pas soi meme dans la liste -> pour test local pas besoin d'enlever car UDP ajoute dans la liste, sinon duplication
                    {
                        // insert new user
                        this.userListModel.addElement(user);
                    }
                }
                catch (UnknownHostException e)
                {
                    this.view.showErrorDialog("Erreur lors de la lecture de l'adresse IP d'un utilisateur du webservice");
                }
            }
        }
    }


    private String executeHttpRequest(String Url, String params)
    {
        String response = "";
        try
        {
            URL url = new URL(Url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(params);
            dataOutputStream.flush();
            dataOutputStream.close();

            int status = httpURLConnection.getResponseCode(); // Executing request and wait for response

            InputStreamReader inputStreamReader;
            if (status > 299) // error status
                inputStreamReader = new InputStreamReader(httpURLConnection.getErrorStream());
            else
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
                content.append(inputLine);
            bufferedReader.close();
            response = content.toString();

            httpURLConnection.disconnect();
        } catch (IOException e)
        {
            this.view.showErrorDialog("Erreur lors de l'execution d'une requete HTTP");
        }
        return response;
    }


    private String getParamsString(Map<String, String> params)
    {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            try
            {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            } catch (UnsupportedEncodingException e)
            {
                this.view.showErrorDialog("Erreur lors de l'encodage des parametres HTTP");
            }
        }
        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }


    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(1000);
            }
            catch (InterruptedException e)
            {
                this.view.showErrorDialog(e.getMessage());
            }

            if (this.usernameStatus == UsernameStatus.AVAILABLE)
            {
                this.notifyRefresh(); // on refresh la liste toutes les secondes
            }
        }
    }


    UsernameStatus getUsernameStatus()
    {
        return this.usernameStatus;
    }
}
