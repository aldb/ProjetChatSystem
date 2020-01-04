import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


class MainView extends AbstractView
{
    private JPanel openSessionPanel, disconnectionPanel, changeUsernamePanel, mainPanel;
    private JTextField newUsernameTextField;
    private JLabel openSessionLabel;
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
        // create and set up the different panel
        openSessionPanel = new JPanel(new GridLayout(3, 1));
        disconnectionPanel = new JPanel(new GridLayout(1, 2));
        changeUsernamePanel = new JPanel(new GridLayout(1, 2));
        mainPanel = new JPanel(new GridLayout(4, 1));

        // for change username
        newUsernameTextField = new JTextField(15);
        changeUsernameButton = new JButton("Changer nom d'utilisateur");
        changeUsernameButton.addActionListener(new ChangeUsernameButtonListener());

        // to disconnect
        disconnectionButton = new JButton("Deconnexion");
        disconnectionButton.addActionListener(new DisconnectionButtonListener());

        // to open a new session
        openSessionButton = new JButton("Ouvrir la session");
        openSessionButton.addActionListener(new OpenSessionButtonListener());
        openSessionLabel = new JLabel("Click the \"Open session\" button" + " once you have selected user.", JLabel.CENTER);

        // show user list
        userList.setCellRenderer(new UserListCellRenderer());
        userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        userList.setLayoutOrientation(JList.VERTICAL_WRAP);
        userList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(userList);
        listScroller.setPreferredSize(new Dimension(400, 200));

        //add widget to the different panel
        changeUsernamePanel.add(newUsernameTextField, BorderLayout.CENTER);
        changeUsernamePanel.add(changeUsernameButton, BorderLayout.SOUTH);
        disconnectionPanel.add(disconnectionButton, BorderLayout.SOUTH);
        openSessionPanel.add(openSessionLabel, BorderLayout.NORTH);
        openSessionPanel.add(openSessionButton, BorderLayout.SOUTH);
        openSessionPanel.add(userList, BorderLayout.CENTER);

        mainPanel.add(changeUsernamePanel, BorderLayout.NORTH);
        mainPanel.add(disconnectionPanel, BorderLayout.SOUTH);
        mainPanel.add(openSessionPanel, BorderLayout.CENTER);

        // Affiche la fenetre  principale
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        this.addWindowListener(new MainViewListener());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(new Dimension(400, 400));
        this.pack();
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
