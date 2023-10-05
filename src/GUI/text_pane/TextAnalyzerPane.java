package GUI.text_pane;

import containers.Queue;
import sources.Dictionary;
import sources.Messages;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/*
 * Clase que extiende de StyledTextPane y proporciona métodos y funcionalidades relacionadas con
 * el análisis de texto. Permite guardar las palabras incorrectas en una estructura de cola,
 * para mostrarlas a medida que fueron encontradas (FIFO), aplicando estilos en ellas.
 */
public class TextAnalyzerPane extends StyledTextPane {
    private Style wrongWordsStyle;
    private Dictionary dictionary, ignoredWords;
    private Queue<String> wrongWords;
    private int beginIndex;

    public TextAnalyzerPane(Dictionary dictionary, Dictionary ignoredWords) {
        super();
        this.dictionary = dictionary;
        this.ignoredWords = ignoredWords;
        this.wrongWords = new Queue<String>();
        this.beginIndex = 0;
        this.wrongWordsStyle = addSyle("Wrong word", getDefaultStyle());
        StyleConstants.setForeground(this.wrongWordsStyle, Color.RED);
    }

    public int getWrongWordsCount() {
        return this.wrongWords.size();
    }

    public void analizeText() {
        if(this.dictionary.isEmpty()) {
            // Si el diccionario no contiene ninguna palabra, se muestra un mensaje de error y la función se termina
            Messages.show("No hay un diccionario cargado", Messages.ERROR);
            return;
        }

        if(isEmpty()) {
            // Si el panel de texto está vacío, se muestra un mensaje de error y la función se termina
            Messages.show("No hay un texto para analizar", Messages.ERROR);
            return;
        }

        this.wrongWords.clean();
        this.beginIndex = 0;
        String[] words = getText().split("[\\p{Punct}¿¡\\s]+");

        // Se analizan cada una de las palabras obtenidas del texto.
        // Se verifica que la palabra no esté bien escrita o ignorada y se la agrega a la cola de palabras incorrectas.
        for(String word: words) {
            if(!word.isBlank() && !this.dictionary.isNumeric(word) && !this.dictionary.contains(word) && !this.ignoredWords.contains(word))
                    this.wrongWords.push(word);
        }
    }

    public boolean analysisCompleted() {
        return currentWord() == null;
    }

    public void moveToNextWord() {
        this.wrongWords.pull();
    }

    public String currentWord() {
        return this.wrongWords.viewTop();
    }

    public String[] currentWordSuggestions() {
        return this.dictionary.suggestWords(currentWord());
    }

    public void underlineCurrentWord() {
        underlineWord(currentWord());
    }

    public void replaceCurrentWord(String newWord) {
        replaceWord(currentWord(), newWord);
    }

    public void removeLastUnderline() {
        removeUnderline(this.wrongWords.getLastPulled());
    }

    private void underlineWord(String word) {
        if(word != null) {
            int beginIndex = indexOf(word, this.beginIndex);

            if(beginIndex != -1) {
                applyStyle(beginIndex, word.length(), this.wrongWordsStyle);
                this.beginIndex = beginIndex + word.length();
            }
        }
    }

    private void replaceWord(String oldWord, String newWord) {
        if(oldWord != null && newWord != null) {
            try {
                int beginIndex = this.beginIndex - oldWord.length();
                replace(beginIndex, oldWord.length(), newWord);
            }
            catch (BadLocationException e) {
                Messages.show(e);
            }
        }
    }

    private void removeUnderline(String word) {
        if(word != null) {
            int beginIndex = this.beginIndex - word.length();
            removeStyle(beginIndex, word.length());
        }
    }
}
