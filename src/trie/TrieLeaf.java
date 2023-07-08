package trie;

import sources.nodes.TrieNode;

/*
 * Hereda de TrieNode y representa un nodo hoja en el Trie.
 * Almacena el sufijo de la palabra correspondiente.
 */
public class TrieLeaf extends TrieNode {
    private String suffix;

    public TrieLeaf(String suffix) {
        super(true);
        this.suffix = suffix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
