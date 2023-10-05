package GUI.tab;

import GUI.text_pane.TextAnalyzerPane;
import sources.Dictionary;
import sources.FileManager;
import sources.Messages;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * Pestaña "Texto", que tiene varias funcionalidades:
 * 1) Se puede cargar un archivo de texto (.txt), guardarlo e analizarlo basado en un diccionario utilizando un
 * apartado en la sección superior.
 * 2) Se puede editar dicho archivo de texto dentro del recuadro de texto.
 *
 * El análisis del texto se realiza palabra por palabra, verificando que la misma esté correctamente escrita
 * o esté ignorada por el usuario.
 *
 * Esta pestaña se divide en dos secciones:
 * 1) Contenido Del Texto: En la sección izquierda se encuentra un componente de texto donde se muestra el texto
 * extraído del archivo cargado, o el texto escrito por el usuario manualmente.
 * 2) Palabras Incorrectas: En la sección derecha se van mostrando las palabras incorrectas encontradas dentro
 * del texto, resaltándolas en color rojo para una mejor visualización. Cuenta con los siguientes botones y su
 * respectiva funcionalidad:
 * a) Siguiente: Permite avanzar a la siguiente palabra incorrecta en el texto.
 * b) Ignorar: Permite ignorar la palabra incorrecta, evitando que se marque como incorrecta en futuros análisis.
 * c) Agregar al diccionario: Permite agregar la palabra incorrecta al diccionario,
 * considerándola como una palabra aceptada.
 * d) Cambiar: Permite sobreescribir la palabra incorrecta por la sugerencia seleccionada.
 */
public class TextTab extends JPanel {
    private JButton openTextBtn, saveTextBtn, analizeTextBtn;

    private JPanel topPanel, rightPanel;
    private TextAnalyzerPane textAnalysisPanel;
    private DictionaryTab dictPane;

    private JLabel wrongLabel;
    private JTextField wrongText;
    private JButton btnNext, btnIgnore, btnReplace, btnAddToDic;
    private JComboBox<String> suggestions;

    String filePath;
    private FileManager fileManager;
    private boolean errorsFound;
    private int wrongWordsCount;
    private int wordsCounter;


    public TextTab(Dictionary dictionary, Dictionary ignoredWords) {
        super();
        this.errorsFound = false;
        this.fileManager = new FileManager();
        createTopPanel();
        createTextPanel(dictionary, ignoredWords);
        createRightPanel();

        setLayout(new BorderLayout());
        add(this.topPanel, BorderLayout.NORTH);
        add(this.textAnalysisPanel, BorderLayout.CENTER);
        add(this.rightPanel, BorderLayout.EAST);
    }

    private void createTopPanel() {
        this.topPanel = new JPanel();
        this.openTextBtn = new JButton("Cargar");
        this.openTextBtn.addActionListener(new TopButtonListener());
        this.saveTextBtn = new JButton("Guardar");
        this.saveTextBtn.addActionListener(new TopButtonListener());
        this.analizeTextBtn = new JButton("Analizar");
        this.analizeTextBtn.addActionListener(new TopButtonListener());

        this.topPanel.add(this.openTextBtn);
        this.topPanel.add(this.saveTextBtn);
        this.topPanel.add(this.analizeTextBtn);

    }

    private void createTextPanel(Dictionary dictionary, Dictionary ignoredWords) {
        this.textAnalysisPanel = new TextAnalyzerPane(dictionary, ignoredWords);
    }

    private void createRightPanel() {
        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new GridLayout(8, 1));

        this.wrongLabel = new JLabel("Palabras Incorrectas");
        this.wrongText = new JTextField("", 20);
        this.wrongText.setEditable(false);
        this.btnNext = new JButton("Siguiente");
        this.btnNext.addActionListener(new RightButtonListener());
        this.btnIgnore = new JButton("Ignorar");
        this.btnIgnore.addActionListener(new RightButtonListener());
        this.btnReplace = new JButton("Cambiar");
        this.btnReplace.addActionListener(new RightButtonListener());
        this.btnAddToDic = new JButton("Agregar al diccionario");
        this.btnAddToDic.addActionListener(new RightButtonListener());
        JLabel suggestLabels = new JLabel("Sugerencias");
        this.suggestions = new JComboBox<String>();

        this.rightPanel.add(this.wrongLabel);
        this.rightPanel.add(this.wrongText);
        this.rightPanel.add(this.btnNext);
        this.rightPanel.add(this.btnIgnore);
        this.rightPanel.add(this.btnReplace);
        this.rightPanel.add(this.btnAddToDic);
        this.rightPanel.add(suggestLabels);
        this.rightPanel.add(this.suggestions);
    }

    public void setDictPane(DictionaryTab panel) {
        this.dictPane = panel;
    }

    private boolean continueAnalysis() {
        if(!this.textAnalysisPanel.analysisCompleted()){
            return true;
        }
        else if(!this.errorsFound) {
            restart();
            this.errorsFound = true;
        }
        Messages.show("No se encontraron mas errores", Messages.INFORMATION);
        return false;
    }

    private void analizeText() {
        restart();
        this.textAnalysisPanel.cleanText();
        this.textAnalysisPanel.analizeText();

        if(continueAnalysis()) {
            this.textAnalysisPanel.underlineCurrentWord();
            this.wrongText.setText(this.textAnalysisPanel.currentWord());
            this.wrongWordsCount = this.textAnalysisPanel.getWrongWordsCount();
            setSuggestions();
            updateWrongLabel();
        }
    }

    private void restart() {
        this.errorsFound = false;
        this.wordsCounter = 0;
        this.wrongLabel.setText("Palabras incorrectas");
        this.wrongText.setText(null);
        this.suggestions.removeAllItems();
        this.textAnalysisPanel.removeLastUnderline();
    }

    private void updateWrongLabel() {
        this.wordsCounter++;
        this.wrongLabel.setText("Palabras incorrectas (" + this.wordsCounter + "/" + this.wrongWordsCount + ")");
    }

    private void nextWord() {
        this.textAnalysisPanel.moveToNextWord();
        if(continueAnalysis()) {
            updateWrongLabel();
            this.textAnalysisPanel.removeLastUnderline();
            this.textAnalysisPanel.underlineCurrentWord();
            this.wrongText.setText(this.textAnalysisPanel.currentWord());
            setSuggestions();
        }
    }

    private void setSuggestions() {
        String[] suggestions = this.textAnalysisPanel.currentWordSuggestions();
        this.suggestions.removeAllItems();
        this.suggestions.setModel(new DefaultComboBoxModel<String>(suggestions));
    }

    private void ignoreCurrentWord() {
        if(continueAnalysis()) {
            this.dictPane.addWord(this.textAnalysisPanel.currentWord(), true);
            nextWord();
        }

    }

    private void replaceCurrentWord() {
        if(continueAnalysis()) {
            String newWord = (String) this.suggestions.getSelectedItem();
            if (newWord != null) {
                this.textAnalysisPanel.replaceCurrentWord(newWord);
                nextWord();
            }
            else
                Messages.show("Lo siento. No hay sugerencias", Messages.INFORMATION);
        }
    }

    private void addCurrentWordToDict() {
        if(continueAnalysis()) {
            this.dictPane.addWord(this.textAnalysisPanel.currentWord(), false);
            nextWord();
        }
    }

    private class RightButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == btnNext)
                nextWord();
            else if(e.getSource() == btnIgnore)
                ignoreCurrentWord();
            else if(e.getSource() == btnReplace)
                replaceCurrentWord();
            else if(e.getSource() == btnAddToDic)
                addCurrentWordToDict();
        }
    }

    private class TopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == openTextBtn) {
                fileManager.chooseFile("Archivos de texto (*.txt)" ,"txt");

                if(!fileManager.containsFile())
                    return;

                if (!fileManager.fileExtensionContains(".txt")) {
                    Messages.show("Formato de texto inválido", Messages.ERROR);
                    return;
                }

                try {
                    transcribeFile(fileManager.getFile());
                    filePath = fileManager.getFileAbsolutePath();
                }
                catch (Exception exc) {
                    Messages.show(exc);
                }

            }

            else if(e.getSource() == saveTextBtn) {
                try {
                    fileManager.saveFile(new File(filePath), textAnalysisPanel.getText());
                    Messages.show("Se guardó exitosamente", Messages.INFORMATION);
                }
                catch (Exception exc) {
                    Messages.show(exc, "Ocurrió un error inesperado guardando el archivo");
                }

            }

            else if(e.getSource() == analizeTextBtn)
                analizeText();
        }

        public void transcribeFile(File file) {
            try {
                fileManager.openReader(file);
                textAnalysisPanel.removeText();

                String line;
                while ((line = fileManager.readFileLine()) != null) {
                    textAnalysisPanel.writeLine(line);
                }

                fileManager.closeReader();
            }
            catch (Exception e) {
                Messages.show(e);
            }
        }

    }
}
