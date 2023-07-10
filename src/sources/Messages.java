package sources;

import javax.swing.JOptionPane;

public class Messages {
    public static int ERROR = JOptionPane.ERROR_MESSAGE;
    public static int PLAIN = JOptionPane.PLAIN_MESSAGE;
    public static int INFORMATION = JOptionPane.INFORMATION_MESSAGE;
    public static int WARNING = JOptionPane.WARNING_MESSAGE;

    public static void show(Exception e) {
        JOptionPane.showMessageDialog(null, "Exception: " + e, "Ha ocurrido una excepción", ERROR);
    }

    public static void show(Exception e, String message) {
        JOptionPane.showMessageDialog(null, message + "\nException: " + e, "Ha ocurrido una excepción", ERROR);
    }

    public static void show(String message, int messageType) {
        if(messageType == ERROR)
            JOptionPane.showMessageDialog(null, message, "Error", messageType);
        else if(messageType == INFORMATION)
            JOptionPane.showMessageDialog(null, message, "Aviso", messageType);
        else if(messageType == WARNING)
            JOptionPane.showMessageDialog(null, message, "Advertencia", messageType);
        else if(messageType == PLAIN)
            JOptionPane.showMessageDialog(null, message);
    }

    public static String showInput(String message) {
        return JOptionPane.showInputDialog(null, message, "Aviso", PLAIN);
    }
}
