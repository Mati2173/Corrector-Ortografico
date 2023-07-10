package GUI.text_pane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Clase abstracta que extiende de JPanel y proporciona una base para otros paneles de texto.
 * El componente está agregado en un JScrollPane para permitir el desplazamiento del texto.
 * Tiene métodos y funcionalidades básicas relacionadas con la manipulación de texto.
 * Tiene métodos abstractos para obtener el texto, eliminar el texto y escribir líneas, que serán implementados
 * dependiendo de la implementación en una clase concreta según el JTextComponent utilizado.
 */
public abstract class AbstractTextPane extends JPanel {
    protected JTextComponent textPane;

    public AbstractTextPane(JTextComponent textPane) {
        super();
        this.textPane = textPane;
        setLayout(new BorderLayout());
        add(new JScrollPane(this.textPane));
    }

    public abstract String getText();

    public abstract void removeText();

    public abstract void writeLine(String line);

    public int getLength() {
        return getText().length();
    }

    public void setEditable(boolean b) {
        this.textPane.setEditable(b);
    }

    public boolean isEmpty() {
        return getLength() == 0;
    }

    public int indexOf(String word) {
        return indexOf(word, 0, getLength());
    }

    public int indexOf(String word, int beginIndex) {
        return indexOf(word, beginIndex, getLength());
    }

    public int indexOf(String word, int beginIndex, int endIndex) {
        /*
        Busca la primera aparición de una palabra en un rango específico dentro del texto del panel de texto.
        Devuelve el índice de inicio de la coincidencia si se encuentra, de lo contrario, devuelve -1.

        Para lograr esto, se crea un objeto Pattern que define el patrón de búsqueda utilizando la palabra y los
        límites de palabras (\\b), asegurando que en la búsqueda solo coincida con la palabra completa y no
        con partes de otras palabras.
        Luego, crea un objeto Matcher a partir de ese patrón y el texto del panel de texto.
        */
        Pattern pattern = Pattern.compile("\\b" + word + "\\b");
        Matcher matcher = pattern.matcher(getText());
        matcher.region(beginIndex, endIndex);

        return matcher.find() ? matcher.start() : -1;
    }

    public void setTextFont(Font font) {
        this.textPane.setFont(font);
    }
}
