# Corrector Ortográfico en Java

El Corrector Ortográfico en Java es una aplicación diseñada para ayudarte a mejorar la corrección ortográfica en tus textos. Con esta herramienta, podrás analizar un archivo de texto y detectar las palabras mal escritas en base a un diccionario.

El programa utiliza un Trie personalizado para realizar operaciones eficientes de almacenamiento, búsqueda y recuperación de palabras. A continuación, se describe la estructura de datos Trie y las clases utilizadas en su implementación.

## Trie
Es una estructura de árbol conocida como árbol de recuperación. El Trie utilizado en este proyecto consta de los siguientes componentes:
1. **TrieNode (Nodo del Trie)**: Clase abstracta que representa un nodo genérico en el árbol Trie. Contiene un atributo que indica si es una hoja y un método getter correspondiente (`isLeaf`). Esta clase sirve como base para los nodos hoja y no hoja del Trie.

2. **TrieLeaf (Nodo Hoja del Trie)**: Clase que hereda de TrieNode y representa un nodo hoja en el Trie. Contiene el sufijo de la palabra correspondiente.

3. **TrieNonLeaf (Nodo No Hoja del Trie)**: Clase que hereda de TrieNode y representa un nodo no hoja en el Trie. Contiene una lista ordenada de LetterKey (`SortedList<LetterKey>`) que representa las letras posibles para los siguientes caracteres de una palabra. Además, tiene un atributo que indica si es el fin de una palabra y un método getter correspondiente (`isEndOfWord`).

4. **SortedList\<T> (Lista Ordenada)**: Clase que implementa una lista doblemente enlazada y almacena elementos de tipo genérico \<T> de manera ordenada. Cada elemento se encuentra contenido en un **DoubleNode\<T>**, que contiene referencias al nodo previo y siguiente, y también almacena el dato de tipo \<T>.

5. **LetterKey (Clave de Letra)**: Clase que contiene una letra y un puntero a un posible nodo hijo (`TrieNode`) en el Trie. Esta clase se utiliza en la lista ordenada dentro de los nodos no hoja para representar las letras posibles para los siguientes caracteres de la palabra.
