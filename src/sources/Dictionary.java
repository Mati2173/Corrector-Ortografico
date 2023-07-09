package sources;

import trie.Trie;

public class Dictionary {
    private Trie correctWords;
    private Trie ignoredWords;

    public Dictionary() {
        this.correctWords = new Trie();
        this.ignoredWords = new Trie();
    }

    public boolean isEmpty() {
        return this.correctWords.isEmpty();
    }

    public void cleanCorrect() {
        this.correctWords.clean();
    }

    public void cleanIgnored() {
        this.ignoredWords.clean();
    }

    public void addWord(String word) {
        this.correctWords.add(word);
    }

    public void ignoreWord(String word) {
        this.ignoredWords.add(word);
    }

    public boolean contains(String word) {
        return wellWriten(word) || isIgnored(word) || isNumeric(word);
    }

    public boolean wellWriten(String word) {
        return this.correctWords.contains(word);
    }

    public boolean isIgnored(String word) {
        return this.ignoredWords.contains(word);
    }

    public boolean isNumeric(String word) {
        int i = 0;
        boolean flag = true;

        while(i < word.length() && flag) {
            if(!Character.isDigit(word.charAt(i)))
                flag = false;
            i++;
        }

        return flag;
    }

    public String[] suggestWords(String word) {
        // Completar un método para el Trie que me permita devolver un listado de palabras incorrectas
        return null;
    }
}
