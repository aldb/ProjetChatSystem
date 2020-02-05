import javax.swing.*;
import java.awt.*;

class UserListCellRenderer extends DefaultListCellRenderer
{
    UserListCellRenderer()
    {

    }


    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        User user = (User)value;
        setText(user.getUsername());
        return this;
    }
}
