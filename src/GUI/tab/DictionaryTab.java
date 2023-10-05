package GUI.tab;

import GUI.text_pane.SimpleTextPane;
import sources.Dictionary;
import sources.FileManager;
import sources.Messages;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/*
 * Pestaña "Diccionario", que tiene 2 funcionalidades:
 * 1) Se pueden agregar manualmente palabras al diccionario utilizando un apartado en la sección superior.
 * 2) Se puede abrir un archivo .txt que contiene un listado de palabras para ser insertadas en el diccionario.
 *
 * Esta pestaña se divide en dos secciones:
 * 1) Palabras Aceptadas: En la sección izquierda se encuentra un componente de texto donde se muestran todas las
 * palabras aceptadas por el diccionario.
 * 2) Palabras Ignoradas: En la sección derecha se muestran las palabras ignoradas por el corrector ortográfico,
 * es decir, palabras que se consideran correctas, pero que no están en el diccionario.
 */
public class DictionaryTab extends JPanel {
    private Dictionary dictionary;
    private Dictionary ignoredWords;
    private JTextField wordTxtField;
    private JButton addWordBtn, openDictBtn;
    private SimpleTextPane correctTxtPane, ignoredTxtPane;
    private JPanel topPanel, centerPanel, bottomPanel;
    private String correctWordsFilePath, ignoredWordsFilePath;
    private FileManager fileManager;

    public DictionaryTab(Dictionary dictionary, Dictionary ignoredWords) {
        super();
        this.dictionary = dictionary;
        this.ignoredWords = ignoredWords;

        createTopPanel();
        createCenterPanel();
        createBottomPanel();

        setLayout(new BorderLayout());
        add(this.topPanel, BorderLayout.NORTH);
        add(this.centerPanel, BorderLayout.CENTER);
        add(this.bottomPanel, BorderLayout.SOUTH);

        this.fileManager = new FileManager();
        this.correctWordsFilePath = "sources\\Dictionary.txt";
        this.ignoredWordsFilePath = "sources\\Ignored_Words.txt";
        transcribeFile(new File(this.correctWordsFilePath), this.correctTxtPane, this.dictionary);
        transcribeFile(new File(this.ignoredWordsFilePath), this.ignoredTxtPane, this.ignoredWords);
    }

    private void createTopPanel() {
        JLabel wordTxtLabel = new JLabel("Agregar palabra a diccionario");
        this.wordTxtField = new JTextField(20);
        this.wordTxtField.addKeyListener(new KeyListener());
        this.addWordBtn = new JButton("Agregar");
        this.addWordBtn.addActionListener(new AddWordListener());

        this.topPanel = new JPanel();
        this.topPanel.add(wordTxtLabel);
        this.topPanel.add(this.wordTxtField);
        this.topPanel.add(this.addWordBtn);
    }

    private void createCenterPanel() {
        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(1, 2));
        labels.add(new JLabel("Diccionario"));
        labels.add(new JLabel("Palabras ignoradas"));

        this.correctTxtPane = new SimpleTextPane();
        this.correctTxtPane.setEditable(false);
        this.ignoredTxtPane = new SimpleTextPane();
        this.ignoredTxtPane.setEditable(false);

        JPanel txtPanel = new JPanel();
        txtPanel.setLayout(new GridLayout(1, 2));
        txtPanel.add(this.correctTxtPane);
        txtPanel.add(this.ignoredTxtPane);

        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BorderLayout());
        this.centerPanel.add(labels, BorderLayout.NORTH);
        this.centerPanel.add(txtPanel, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        this.openDictBtn = new JButton("Cargar Diccionario");
        this.openDictBtn.addActionListener(new OpenTxtListener());

        this.bottomPanel = new JPanel();
        this.bottomPanel.setLayout(new BorderLayout());
        this.bottomPanel.add(this.openDictBtn, BorderLayout.CENTER);
    }

    public void cleanCorrect() {
        this.correctTxtPane.removeText();
        this.dictionary.clean();
    }

    public void cleanIgnored() {
        this.ignoredTxtPane.removeText();
        this.ignoredWords.clean();
    }

    public boolean invalidWord(String word) {
        if (word == null || word.isBlank()) {
            Messages.show("La palabra no puede estar vacía", Messages.WARNING);
            return true;
        }

        if (word.contains(" ")) {
            Messages.show("La palabra no puede contener espacios en blanco", Messages.WARNING);
            return true;
        }

        if (this.dictionary.isNumeric(word)) {
            Messages.show("La palabra no puede ser un número", Messages.INFORMATION);
            return true;
        }

        return false;
    }

    public void addWord(String word, boolean ignore) {
        // Recibe la palabra a añadir en el diccionario, sí 'ignore' es true significa que quiere ignorar dicha palabra,
        // de lo contrario se la considera como correcta
        if (invalidWord(word)) return;

        word = word.toLowerCase();
        if (!this.dictionary.contains(word)) {
            this.wordTxtField.setText(null);
            if (!ignore) {
                writeWord(this.correctTxtPane, this.dictionary, word);
                Messages.show("Palabra agregada con éxito", Messages.INFORMATION);
            } else {
                writeWord(this.ignoredTxtPane, this.ignoredWords, word);
                Messages.show("Palabra ignorada con éxito", Messages.INFORMATION);
            }
        } else {
            Messages.show("La palabra ya está en el diccionario o ha sido ignorada", Messages.INFORMATION);
        }
    }

    private void writeWord(SimpleTextPane dest, Dictionary dictionary, String word) {
        dest.writeLine(word);
        dictionary.addWord(word);
    }

    public void transcribeFile(File file, SimpleTextPane dest, Dictionary d) {
        try {
            this.fileManager.openReader(file);

            if(dest == this.correctTxtPane)
                cleanCorrect();
            else
                cleanIgnored();

            String line;
            while ((line = this.fileManager.readFileLine()) != null) {
                String[] words = line.split("[\\p{Punct}¿¡\\s]+");
                for(String word: words)
                    if(!word.isBlank() && !d.isNumeric(word)) {
                        d.addWord(word);
                    }
            }


            for(String word: d.getAllWords())
                dest.writeLine(word);

            this.fileManager.closeReader();
        }
        catch (Exception e) {
            Messages.show(e);
        }
    }

    public void saveDictionary() {
        try {
            this.fileManager.saveFile(new File(this.correctWordsFilePath), this.correctTxtPane.getText());
            this.fileManager.saveFile(new File(this.ignoredWordsFilePath), this.ignoredTxtPane.getText());
        }
        catch (Exception e) {
            Messages.show(e, "Ocurrió un error inesperado guardando el archivo");
        }
    }

    private class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                addWord(wordTxtField.getText(), false);
            }
        }
    }

    private class AddWordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addWord(wordTxtField.getText(), false);
        }
    }

    private class OpenTxtListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileManager.chooseFile("Archivos de texto (*.txt)", "txt");

            if(!fileManager.containsFile())
              return;

            if (!fileManager.fileExtensionContains(".txt")) {
                Messages.show("Formato de texto inválido", Messages.ERROR);
                return;
            }

            try {
                transcribeFile(fileManager.getFile(), correctTxtPane, dictionary);
                correctWordsFilePath = fileManager.getFileAbsolutePath();
            }
            catch (Exception exc) {
                Messages.show(exc);
            }
        }
    }
}
