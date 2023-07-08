package trie;

/*
* Es una estructura de árbol conocida como árbol de recuperación.
* Es utilizada para el almacenamiento, búsqueda y recuperación eficiente de palabras.
* En un árbol Trie, cada nodo representa un carácter o parte de una palabra.
* Cada nodo puede tener varios hijos, correspondientes a los caracteres posibles que pueden seguir
* al prefijo representado por ese nodo. Los nodos hoja representan el sufijo de una palabra completa.
*/
public class Trie {
    private TrieNonLeaf root;

    public Trie() {
        clean();
    }

    public void clean() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    private void verifyString(String word) {
        if(word == null) throw new NullPointerException("El String no puede ser nulo");
        if(word.isBlank()) throw new IllegalArgumentException("El String no puede estar vacío o solo contener espacios en blanco");
    }

    public void add(String word) {
        verifyString(word);
        //...
    }


    public boolean contains(String word) {
        if(isEmpty()) return false;
        verifyString(word);
        //...
        return false;
    }
}
