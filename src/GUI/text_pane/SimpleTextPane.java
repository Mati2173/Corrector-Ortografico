package GUI.text_pane;

import javax.swing.JTextArea;


public class SimpleTextPane extends AbstractTextPane {

    public SimpleTextPane() {
        super(new JTextArea());
    }

    @Override
    public String getText() {
        return this.textPane.getText();
    }

    @Override
    public void removeText() {
        this.textPane.setText(null);
    }

    @Override
    public void writeLine(String line) {
        ((JTextArea) this.textPane).append(line + "\n");
    }
}
