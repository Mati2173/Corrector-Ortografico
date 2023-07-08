package sources.nodes;

/*
 * Representa un nodo genérico en el árbol (Trie).
 * Contiene un atributo (leaf) que indica si es una hoja o no.
 * Esta clase sirve como base para los nodos hoja y no hoja del Trie.
 */
public abstract class TrieNode {
    protected boolean leaf;

    public TrieNode(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLeaf() {
        return this.leaf;
    }
    
}
