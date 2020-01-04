import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


class LogInView extends AbstractView
{
	private JPanel connectionPanel;
	private JTextField loginTextField;
	private JLabel connectionLabel;
	private JButton connectionButton;


	LogInView(LogInController controller)
    {
        super(controller, "Connexion");
        javax.swing.SwingUtilities.invokeLater(() -> initializeComponent());
    }


    private void initializeComponent()
    {
        // create and set up the connexion panel
        connectionPanel = new JPanel(new GridLayout(2, 2));
        // create widget
        loginTextField = new JTextField(15);
        connectionLabel = new JLabel("Entrer nom d'utilisateur");
        connectionButton = new JButton("Connexion");
        connectionButton.addActionListener(new ConnexionButtonListener());
        // add widget
        connectionPanel.add(connectionButton);
        connectionPanel.add(loginTextField);
        connectionPanel.add(connectionLabel);
        // Add the panel to the window and display it
        this.getContentPane().add(connectionPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.pack();
        this.setVisible(true);
    }


    private class ConnexionButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String login = loginTextField.getText();
            ((LogInController)controller).connection(login);
        }
    }
}
