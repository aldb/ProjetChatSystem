import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.*;


class SessionView extends AbstractView
{
    private JButton sendMessageButton;
    private JTextField sendMessageTextField;
    private JTextArea chatTextArea;
    private JButton FileChoiceButton; 

    SessionView(SessionController sessionController, String remoteUsername)
	{
	    super(sessionController, "Discussion avec " + remoteUsername);
        chatTextArea = new JTextArea();
		javax.swing.SwingUtilities.invokeLater(() -> initializeComponent());
	}

	
	private void initializeComponent()
	{
	    // Create and set up the main window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        sendMessageTextField = new JTextField(30);
        sendMessageTextField.requestFocusInWindow();

        sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(new SendMessageButtonListener());
        
        FileChoiceButton = new JButton("File");
        FileChoiceButton.addActionListener(new FileChoiceButtonListener());

        chatTextArea.setEditable(false);
        chatTextArea.setFont(new Font("Serif", Font.PLAIN, 15));
        chatTextArea.setLineWrap(true);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(sendMessageTextField, left);
        southPanel.add(sendMessageButton, right);
        southPanel.add(FileChoiceButton, right);
        mainPanel.add(new JScrollPane(chatTextArea), BorderLayout.CENTER);
        mainPanel.add(BorderLayout.SOUTH, southPanel);

        this.addWindowListener(new SessionViewListener());
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(470, 300);
        this.setVisible(true);
	}


	void refreshChatTextArea(String text)
    {
        this.chatTextArea.setText(text);
    }


    void refreshSendMessageTextField()
    {
        sendMessageTextField.setText("");
        sendMessageTextField.requestFocusInWindow();
    }


    private class SessionViewListener implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e) { }

        @Override
        public void windowClosing(WindowEvent e) { }

        @Override
        public void windowClosed(WindowEvent e)
        {
            ((SessionController)controller).closeSession();
        }

        @Override
        public void windowIconified(WindowEvent e) { }

        @Override
        public void windowDeiconified(WindowEvent e) { }

        @Override
        public void windowActivated(WindowEvent e) { }

        @Override
        public void windowDeactivated(WindowEvent e) { }
    }


    private class SendMessageButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        
            ((SessionController)controller).sendMessage(sendMessageTextField.getText());
        }
    }
    
    private class FileChoiceButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        
            ((SessionController)controller).sendFile();
        }
    }
}
