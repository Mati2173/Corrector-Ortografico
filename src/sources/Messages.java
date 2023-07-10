package sources;

import javax.swing.JOptionPane;

// Proporciona métodos estáticos para mostrar mensajes utilizando la clase JOptionPane.
public class Messages {
    public static final int ERROR = JOptionPane.ERROR_MESSAGE;
    public static final int PLAIN = JOptionPane.PLAIN_MESSAGE;
    public static final int INFORMATION = JOptionPane.INFORMATION_MESSAGE;
    public static final int WARNING = JOptionPane.WARNING_MESSAGE;

    public static void show(Exception e) {
        JOptionPane.showMessageDialog(null, "Exception: " + e, "Ha ocurrido una excepción", ERROR);
    }

    public static void show(Exception e, String message) {
        JOptionPane.showMessageDialog(null, message + "\nException: " + e, "Ha ocurrido una excepción", ERROR);
    }

    public static void show(String message, int messageType) {
        String title = null;
        switch (messageType) {
            case ERROR -> title = "Error";
            case INFORMATION -> title = "Aviso";
            case WARNING -> title = "Advertencia";
            case PLAIN -> title = "Mensaje";
        }
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    public static String showInput(String message) {
        return JOptionPane.showInputDialog(null, message, "Mensaje", PLAIN);
    }
}
