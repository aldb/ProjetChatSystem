import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


class MainView extends AbstractView
{
    private JPanel panel;
    private JTextField newUsernameTextField;
    private JButton openSessionButton, disconnectionButton, changeUsernameButton;
    private JList<User> userList;
    private JScrollPane listScroller;


    MainView(MainController controller, String title)
    {
        super(controller, title);
        userList = new JList<>();
        javax.swing.SwingUtilities.invokeLater(() -> initializeComponent());
    }


    private void initializeComponent()
    {
        this.addWindowListener(new MainViewListener());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(new Dimension(300, 175));
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        panel = new JPanel();
        this.add(panel);
        panel.setLayout(null);

        // for change username
        newUsernameTextField = new JTextField();
        newUsernameTextField.setBounds(5, 47, 130, 25);
        panel.add(newUsernameTextField);

        changeUsernameButton = new JButton("Changer le pseudo");
        changeUsernameButton.setBounds(5, 74, 130, 25);
        panel.add(changeUsernameButton);
        changeUsernameButton.addActionListener(new ChangeUsernameButtonListener());

        // to disconnect
        disconnectionButton = new JButton("Deconnexion");
        disconnectionButton.setBounds(5, 110, 130, 25);
        panel.add(disconnectionButton);
        disconnectionButton.addActionListener(new DisconnectionButtonListener());

        // to open a new session
        openSessionButton = new JButton("Ouvrir la session");
        openSessionButton.setBounds(5, 5, 130, 25);
        panel.add(openSessionButton);
        openSessionButton.addActionListener(new OpenSessionButtonListener());

        // show user list
        userList.setCellRenderer(new UserListCellRenderer());
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(userList);
        listScroller.setPreferredSize(new Dimension(138, 130));
        listScroller.setBounds(142, 5, 138, 130);
        panel.add(listScroller);

        this.setVisible(true);
    }


    void setUserListModel(UserListModel userListModel)
    {
        userList.setModel(userListModel);
    }


    void refreshUserList()
    {
        userList.updateUI();
    }


    private class MainViewListener implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }

        @Override
        public void windowClosing(WindowEvent e)
        {
            ((MainController)controller).disconnection();
        }

        @Override
        public void windowClosed(WindowEvent e) { }

        @Override
        public void windowIconified(WindowEvent e) { }

        @Override
        public void windowDeiconified(WindowEvent e) { }

        @Override
        public void windowActivated(WindowEvent e) { }

        @Override
        public void windowDeactivated(WindowEvent e) { }
    }


    private class ChangeUsernameButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ((MainController)controller).changeUsername(newUsernameTextField.getText());
        }
    }


    private class DisconnectionButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ((MainController)controller).disconnection();
        }
    }


    private class OpenSessionButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            ((MainController)controller).openSession(userList.getSelectedValue());
        }
    }
}
