import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


class LogInView extends AbstractView
{
	private JPanel panel;
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(300, 175));
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        panel = new JPanel();
        this.add(panel);
        panel.setLayout(null);

        connectionLabel = new JLabel("Pseudo");
        connectionLabel.setBounds(55, 30, 50, 25);
        panel.add(connectionLabel);

        loginTextField = new JTextField();
        loginTextField.setBounds(105, 30, 130, 25);
        panel.add(loginTextField);

        connectionButton = new JButton("Connexion");
        connectionButton.setBounds(55, 60, 180, 45);
        panel.add(connectionButton);
        connectionButton.addActionListener(new ConnexionButtonListener());

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
