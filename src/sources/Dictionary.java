package sources;

import trie.Trie;

public class Dictionary {
    private Trie words;

    public Dictionary() {
        this.words = new Trie();
    }

    public boolean isEmpty() {
        return this.words.isEmpty();
    }

    public void clean() {
        this.words.clean();
    }

    public void addWord(String word) {
        this.words.add(word);
    }

    public boolean contains(String word) {
        return this.words.contains(word);
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

    public String[] getAllWords() {
        return this.words.getAllWords();
    }

    public String[] suggestWords(String word) {
        return this.words.suggestWords(word);
    }
}
