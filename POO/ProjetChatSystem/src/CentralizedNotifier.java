import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CentralizedNotifier extends AbstractModel implements Runnable
{
    private User currentUser;
    private UserListModel userListModel;

    HttpURLConnection httpURLConnection;

    enum UsernameStatus { PENDING, AVAILABLE, ALREADY_TAKEN_OR_NOT_ASSIGNED }
    private UsernameStatus usernameStatus;


    CentralizedNotifier(User currentUser, UserListModel userListModel, String Url)
    {
        this.currentUser = currentUser;
        this.userListModel = userListModel;
        this.usernameStatus = UsernameStatus.ALREADY_TAKEN_OR_NOT_ASSIGNED;

        new Thread(this).start();
    }


    void notifyConnection()
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Type", "Subscribe");
        parameters.put("Username", currentUser.getUsername());
        parameters.put("MacAddress", currentUser.getMacAddress());
        parameters.put("IpAddress", currentUser.getIpAddress().getHostAddress());
        String response = executeHttpRequest("localhost/Connection", getParamsString(parameters)); // TODO: change URL

    }


    private String executeHttpRequest(String Url, String params)
    {
        String response = "";
        try
        {
            URL url = new URL(Url);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(params);
            dataOutputStream.flush();
            dataOutputStream.close();

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);

            int status = httpURLConnection.getResponseCode(); // Executing request and wait for response

            InputStreamReader inputStreamReader;
            if (status > 299)
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
            e.printStackTrace(); // TODO: handle
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
                e.printStackTrace(); // TODO: handle
            }
        }
        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }


    @Override
    public void run()
    {

    }
}
