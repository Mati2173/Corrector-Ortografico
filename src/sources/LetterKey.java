package sources;

import sources.nodes.TrieNode;

/*
 * Contiene una letra y un puntero a un posible nodo hijo (TrieNode) en el Trie.
 * Se utiliza en la lista ordenada dentro de los nodos no hoja para representar
 * las letras posibles para los siguientes caracteres de la palabra.
 * Implementa la interfaz 'Comparable<T>' que permitirá ordenar dicha lista.
 */
public class LetterKey implements Comparable<LetterKey> {
    private char letter;
    private TrieNode child;

    public LetterKey(char letter) {
        this(letter, null);
    }

    public LetterKey(char letter, TrieNode child) {
        this.letter = letter;
        this.child = child;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public TrieNode getChild() {
        return child;
    }

    public void setChild(TrieNode child) {
        this.child = child;
    }

    @Override
    public int compareTo(LetterKey o) {
        char l1 = this.letter, l2 = o.getLetter();

        return Character.compare(l1, l2);
    }

    public String toString() {
        return this.letter + "," + this.child;
    }
}
