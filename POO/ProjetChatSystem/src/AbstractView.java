import javax.swing.*;

abstract class AbstractView extends JFrame
{
    AbstractController controller;


    AbstractView(AbstractController controller, String title)
    {
        super(title);
        this.controller = controller;
    }


    void showErrorDialog(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }


    void showInformationDialog(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
