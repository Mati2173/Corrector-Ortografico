package trie;

import containers.Queue;
import containers.SortedList;
import sources.LetterKey;
import sources.nodes.TrieNode;

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
        word = word.toLowerCase();

        // Si el árbol está vacío, agrega la palabra en la ruta del árbol
        if (isEmpty()) {
            this.root = new TrieNonLeaf(word.charAt(0));
            createLeaf(word.charAt(0), word.substring(1), this.root);
            return;
        }

        // El árbol no está vacío y agrega la palabra donde corresponde
        TrieNonLeaf node = this.root;
        String substring;
        int i = 0;

        // Se ejecuta hasta que la función haga 'return'
        while(true) {
            // Si se llega al final de la palabra ('word'), entonces setea 'endOfWord' en true
            if (i == word.length()) {
                // Entrada duplicada si ya fuera fin de palabra
                node.setEndOfWord(true);
                return;
            }

            SortedList<LetterKey> letters = node.getLetters();
            int pos = letters.indexOf(new LetterKey(word.charAt(i)));

            // Si la letra en la posición i-esima de la palabra ('word') no existe, crea una hoja y
            // almacena en ella el sufijo sin procesar de la palabra
            if (pos == SortedList.NOT_FOUND) {
                substring = word.substring(i + 1);
                createLeaf(word.charAt(i), substring, node);
                return;
            }

            LetterKey letter = letters.getElement(pos);
            // Si su nodo hijo está vacío
            if (letter.getChild() == null) {
                if (i + 1 == word.length()) // Entrada duplicada
                    return;

                letter.setChild(new TrieNonLeaf(word.charAt(i + 1)));
                ((TrieNonLeaf) letter.getChild()).setEndOfWord(true);


                // Revisa si queda algún sufijo
                substring = (word.length() > i + 2) ? word.substring(i + 2) : null;
                createLeaf(word.charAt(i + 1), substring, (TrieNonLeaf) letter.getChild());
                return;
            }

            // Si su nodo hijo es una hoja
            if (letter.getChild().isLeaf()) {
                TrieLeaf leaf = (TrieLeaf) letter.getChild(); // Guarda la hoja para no perderla
                substring = word.substring(i + 1);

                if (leaf.getSuffix().equals(substring)) // Entrada duplicada
                    return;

                // Se crean tantas hojas como el número de coincidencias en letras que
                // haya entre el prefijo de la palabra y el sufijo de la hoja
                int offset = 0;
                do {
                    letters = node.getLetters();
                    pos = letters.indexOf(new LetterKey(word.charAt(i + offset)));
                    letter = letters.getElement(pos);

                    // word = "ABC", leaf = "ABCDEF" => leaf = "DEF"
                    if (word.length() == i + offset + 1) {
                        letter.setChild(new TrieNonLeaf(leaf.getSuffix().charAt(offset)));
                        node = (TrieNonLeaf) letter.getChild();
                        node.setEndOfWord(true);
                        createLeaf(leaf.getSuffix().charAt(offset), leaf.getSuffix().substring(offset + 1), node);
                        return;
                    }
                    // word = "ABCDEF", leaf = "ABC" => leaf = "DEF"
                    else if (leaf.getSuffix().length() == offset) {
                        letter.setChild(new TrieNonLeaf(word.charAt(i + offset + 1)));
                        node = (TrieNonLeaf) letter.getChild();
                        node.setEndOfWord(true);
                        createLeaf(word.charAt(i + offset + 1), word.substring(i + offset + 2), node);
                        return;
                    }

                    letter.setChild(new TrieNonLeaf(word.charAt(i + offset + 1)));
                    node = (TrieNonLeaf) letter.getChild();
                    offset++;

                } while (word.charAt(i + offset) == leaf.getSuffix().charAt(offset - 1));

                offset--;
                // word = "ABCDEF", leaf = "ABCPQR" => leaf('D') = "EF", leaf('P') = "QR";

                // Revisa si queda algún sufijo
                // word = "ABCD", leaf = "ABCPQR" => leaf('D') = null, leaf('P') = "QR";
                substring = (word.length() > i + offset + 2) ? word.substring(i + offset + 2) : null;
                createLeaf(word.charAt(i + offset + 1), substring, node);
                // Revisa si queda algún sufijo
                // word = "ABCDEF", leaf = "ABCP" => leaf('D') = "EF", leaf('P') = null;
                substring = (leaf.getSuffix().length() > offset + 1) ? leaf.getSuffix().substring(offset + 1) : null;
                createLeaf(leaf.getSuffix().charAt(offset), substring, node);
                return;
            }

            node = (TrieNonLeaf) ((node.getLetters().getElement(pos))).getChild();
            i++;
        }
    }

    private void createLeaf(char letter, String suffix, TrieNonLeaf node) {
        if(node != null) {
            SortedList<LetterKey> letters = node.getLetters();
            LetterKey letterKey = new LetterKey(letter);
            TrieLeaf leaf = null;

            // Crea una nueva hoja si existe el sufijo
            if(suffix != null && suffix.length() > 0)
                leaf = new TrieLeaf(suffix);

            // Si no encuentra la letra en el listado, la agrega
            if(letters.indexOf(letterKey) == SortedList.NOT_FOUND)
                letters.add(letterKey);

            // Busca nuevamente su posición para insertar adecuadamente la hoja
            int pos = letters.indexOf(letterKey);
            letters.getElement(pos).setChild(leaf);
        }
    }

    public boolean contains(String word) {
        verifyString(word);
        if(isEmpty()) return false;

        word = word.toLowerCase();
        TrieNode currentNode = this.root;
        int pos, i = 0;

        while(true) {
            // Se verifica que el nodo actual sea una hoja
            if (currentNode.isLeaf())
                // Si el sufijo del nodo actual (hoja) coincide con el sufijo de la palabra ('word'), se encontró
                return word.substring(i).equals(((TrieLeaf) currentNode).getSuffix());


            // Se obtiene el listado de letras del nodo actual, y ahí se busca la letra de la posición i-esima de la palabra ('word')
            SortedList<LetterKey> letters = ((TrieNonLeaf) currentNode).getLetters();
            pos = letters.indexOf(new LetterKey(word.charAt(i)));

            // Se verifica que la letra fue encontrada
            if(pos != SortedList.NOT_FOUND) {
                LetterKey letra = letters.getElement(pos);

                // Se verifica que se llegó a la última letra de la palabra ('word')
                if (i + 1 == word.length()) {
                    // Si el final de la palabra corresponde a una hoja vacía, se encontró
                    if (letra.getChild() == null)
                        return true;

                    // Si no es una hoja y se llegó a fin de palabra, se encontró
                    return !letra.getChild().isLeaf() && ((TrieNonLeaf) (letra.getChild())).isEndOfWord();
                }

                // Se verifica que el nodo hijo no sea vacío para continuar con su camino
                if (letra.getChild() != null) {
                    currentNode = letra.getChild();
                    i++;
                    continue;
                }
            }
            // Si ninguna de las condiciones anteriores fue verdadera, entonces no encontró la palabra
            return false;
        }
    }

    public String[] getAllWords() {
        String[] words;
        if(!isEmpty()) {
            Queue<String> wordsQueue = new Queue<String>();
            DFS(this.root, wordsQueue, "");

            words = new String[wordsQueue.size()];
            int i = 0;
            while(!wordsQueue.isEmpty()) {
                words[i] = wordsQueue.pull();
                i++;
            }
        }
        else
            words = new String[] {};

        return words;
    }

    public String[] suggestWords(String word) {
        if(isEmpty()) return new String[] {};
        verifyString(word);

        word = word.toLowerCase();
        TrieNode currentNode = this.root;
        int pos;

        // Recorre el árbol buscando letra por letra de la palabra ('word'), hasta obtener el nodo (no hoja) que
        // contiene su última letra, para luego hacer un recorrido en profundidad a partir de ese nodo.
        for (int i = 0; i < word.length(); i++) {
            if(currentNode.isLeaf()) {
                // Si el nodo es una hoja, quiere decir que no se pudo encontrar el nodo no hoja y devuelve
                // una sugerencia compuesta el prefijo de la palabra ('word') y el sufijo del nodo hoja.
                return new String[] {word.substring(0, i) + ((TrieLeaf)currentNode).getSuffix()};
            }

            // Obtiene el listado de letras del nodo (no hoja) actual, y se busca en él la letra de
            // la posición i-esima de la palabra ('word').
            SortedList<LetterKey> letters = ((TrieNonLeaf) currentNode).getLetters();
            pos = letters.indexOf(new LetterKey(word.charAt(i)));

            // Se verifica que la letra fue encontrada
            if(pos != SortedList.NOT_FOUND) {
                LetterKey letter = letters.getElement(pos);

                // Se verifica que el nodo hijo no sea vacío para continuar con su camino
                if(letter.getChild() != null) {
                    currentNode = letter.getChild();
                    continue;
                }
            }
            // Si ninguna de las condiciones anteriores fue verdadera, entonces no encontró el nodo y retorna la lista vacía
            return new String[] {};
        }

        Queue<String> suggestions = new Queue<String>();
        // Hago un recorrido en profundidad a partir del nodo que contiene el prefijo completo
        DFS(currentNode, suggestions, word);
        // Guardo todos los elementos en un array
        String[] suggestedWords = new String[suggestions.size()];
        int i = 0;
        while(!suggestions.isEmpty()) {
            suggestedWords[i] = suggestions.pull();
            i++;
        }
        return suggestedWords;
    }

    private void DFS(TrieNode node, Queue<String> suggestions, String prefix) {
        /*
        DEPTH-FIRST SEARCH

        Recorre en profundidad a partir del nodo recibido por parámetro.
        Va agregando las palabras en las sugerencias, basándose en el prefijo recibido por parámetro.

        1) Si el nodo está vacío, agrega el prefijo (palabra completa) a las sugerencias.
        2) Si el nodo es una hoja, agrega el prefijo + sufijo del nodo.
        3) Si el nodo no es una hoja:
             Verifico si es fin de palabra y agrego el prefijo (palabra completa) a las sugerencias.
             Por cada una de las letras hago una llamada recursiva con su nodo hijo y el prefijo + letra
        */
        if(node == null)
            suggestions.push(prefix);

        else if (node.isLeaf())
            suggestions.push(prefix + ((TrieLeaf) node).getSuffix());

        else {
            TrieNonLeaf temp = (TrieNonLeaf) node;

            if (temp.isEndOfWord())
                suggestions.push(prefix);

            SortedList<LetterKey> letters = temp.getLetters();
            for (int i = 0; i < letters.size(); i++) {
                LetterKey letter = letters.getElement(i);
                String newPrefix = prefix + letter.getLetter();

                DFS(letter.getChild(), suggestions, newPrefix);
            }
        }
    }
}
