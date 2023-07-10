package GUI.text_pane;

import sources.Messages;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import java.awt.Color;


public class StyledTextPane extends AbstractTextPane {
    private StyledDocument textStyledDoc;
    private Style defaultStyle;

    public StyledTextPane(Style defaultStyle) {
        super(new JTextPane());
        this.textStyledDoc = ((JTextPane) this.textPane).getStyledDocument();
        setStyle(defaultStyle);
    }

    public StyledTextPane() {
        super(new JTextPane());
        this.textStyledDoc = ((JTextPane) this.textPane).getStyledDocument();
        setDefaultStyle();
    }

    @Override
    public int getLength() {
        return this.textStyledDoc.getLength();
    }

    @Override
    public String getText() {
        try {
            return this.textStyledDoc.getText(0, getLength());
        }
        catch (BadLocationException e) {
            Messages.show(e, "Ocurrió un error inesperado, lo siento");
            return null;
        }
    }

    @Override
    public void removeText() {
        try {
            this.textStyledDoc.remove(0, getLength());
        }
        catch (BadLocationException e) {
            Messages.show(e);
        }
    }

    @Override
    public void writeLine(String line) {
        writeLine(line, this.defaultStyle);
    }

    public void cleanText() {
        removeStyle(0, getLength());
    }

    public Style getDefaultStyle() {
        return this.defaultStyle;
    }

    public void setStyle(Style style) {
        this.textStyledDoc.removeStyle("default");
        this.defaultStyle = this.textStyledDoc.addStyle("default", style);
        ((JTextPane) this.textPane).setLogicalStyle(this.defaultStyle);
    }

    public void setDefaultStyle() {
        this.defaultStyle = this.textStyledDoc.addStyle("default", null);
        StyleConstants.setFontFamily(this.defaultStyle, "Calibri");
        StyleConstants.setFontSize(this.defaultStyle, 15);
        StyleConstants.setForeground(this.defaultStyle, Color.BLACK);
    }

    public void writeLine(String line, AttributeSet style) {
        try {
            this.textStyledDoc.insertString(getLength(), line + "\n", style);
        }
        catch (BadLocationException e) {
            Messages.show(e);
        }
    }

    public void replace(int beginIndex, int length, String newWord) throws BadLocationException {
        this.textStyledDoc.remove(beginIndex, length);
        this.textStyledDoc.insertString(beginIndex, newWord, defaultStyle);
    }

    public Style addSyle(String nm, Style parent) {
        return this.textStyledDoc.addStyle(nm, parent);
    }

    public void applyStyle(int beginIndex, int length, AttributeSet style) {
        this.textStyledDoc.setCharacterAttributes(beginIndex, length, style, true);
    }

    public void removeStyle(int beginIndex, int length) {
        applyStyle(beginIndex, length, this.defaultStyle);
    }
}
