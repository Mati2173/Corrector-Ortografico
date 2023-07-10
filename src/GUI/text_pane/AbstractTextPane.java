package GUI.text_pane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class AbstractTextPane extends JPanel {
    protected JTextComponent textPane;

    public AbstractTextPane(JTextComponent textPane) {
        super();
        this.textPane = textPane;
        setLayout(new BorderLayout());
        add(new JScrollPane(this.textPane));
    }

    public abstract String getText();

    public int getLength() {
        return getText().length();
    }

    public abstract void removeText();

    public abstract void writeLine(String line);

    public void setEditable(boolean b) {
        this.textPane.setEditable(b);
    }

    public boolean isEmpty() {
        return getLength() == 0;
    }

    public int indexOf(String word, int beginIndex) {
        String text = getText();
        Pattern pattern = Pattern.compile("\\b" + word + "\\b");
        Matcher matcher = pattern.matcher(text);
        matcher.region(beginIndex, text.length());

        if(matcher.find())
            return matcher.start();

        return -1;
    }

    public void setTextFont(Font font) {
        this.textPane.setFont(font);
    }
}
