package trie;

import containers.SortedList;
import sources.LetterKey;
import sources.nodes.TrieNode;

/*
 * Hereda de TrieNode y representa un nodo no hoja en el Trie.
 * Contiene una lista ordenada (SortedList<LetterKey>) de letras clave (LetterKey)
 * que representan las letras posibles para los siguientes caracteres de la palabra.
 * Tiene un atributo que indica si es el fin de una palabra (endOfWord).
 */
public class TrieNonLeaf extends TrieNode {
    private boolean endOfWord;
    private SortedList<LetterKey> letters;

    public TrieNonLeaf(char letter) {
        super(false);
        this.endOfWord = false;
        this.letters = new SortedList<LetterKey>();
        this.letters.add(new LetterKey(letter));
    }

    public boolean isEndOfWord() {
        return this.endOfWord;
    }

    public SortedList<LetterKey> getLetters() {
        return this.letters;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

}
