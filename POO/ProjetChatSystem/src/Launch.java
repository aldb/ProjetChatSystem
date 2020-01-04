import javax.swing.*;

public class Launch
{
    public static int portA, portB; // for local test
    public static void main(String[] args)
    {
        portA = Integer.parseInt(args[0]);
        portB = Integer.parseInt(args[1]);

        initLookAndFeel();

        UserManager userManager = new UserManager();
        LogInController logInController = new LogInController(userManager);
        LogInView logInView = new LogInView(logInController);
        userManager.setView(logInView);
    }


    private static void initLookAndFeel()
    {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try
        {
            UIManager.setLookAndFeel(lookAndFeel);
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            System.err.println("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
        }
    }
}
