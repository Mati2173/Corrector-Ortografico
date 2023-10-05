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

## Características

- **Interfaz gráfica intuitiva**: El programa cuenta con una interfaz gráfica fácil de usar que te permite cargar y analizar archivos de texto de manera sencilla.
- **Diccionario personalizado**: Puedes agregar manualmente palabras al diccionario para adaptarlo a tus necesidades específicas.
- **Análisis de palabras incorrectas**: El Corrector Ortográfico identifica y resalta las palabras mal escritas en el texto cargado, facilitando su corrección.
- **Sugerencias de corrección**: Para cada palabra incorrecta encontrada, el programa ofrece sugerencias de corrección que puedes utilizar para reemplazar la palabra mal escrita.
- **Ignorar y agregar palabras**: Tienes la opción de ignorar palabras incorrectas que consideres correctas en tu contexto o agregarlas al diccionario para que se reconozcan como válidas en futuros análisis.
- **Guardar los resultados**: Puedes guardar el archivo de texto analizado, incluyendo las correcciones realizadas, para futuras referencias.

## Interfaz Gráfica (GUI)
El programa cuenta con una interfaz gráfica que consta de dos pestañas: "Diccionario" y "Texto".

### Pestaña "Diccionario"
En la pestaña "Diccionario" se muestra un área dividida en dos secciones:

1. **Palabras Aceptadas**: En la sección izquierda se encuentra un componente de texto donde se muestran todas las palabras aceptadas por el diccionario. Aquí se pueden agregar manualmente palabras al diccionario utilizando un apartado en la sección superior.

2. **Palabras Ignoradas**: A la derecha se muestran las palabras ignoradas por el corrector ortográfico. Estas son palabras que se consideran correctas pero que no están en el diccionario.

Además, en la sección inferior se encuentra un botón que permite abrir un archivo de formato .txt que contiene un listado de palabras para ser insertadas en el diccionario.

### Pestaña "Texto"
En la pestaña "Texto" se muestra un área que contiene las siguientes secciones:

1. **Contenido del Texto**: En un JTextPane se muestra el contenido del texto que se desea analizar. Cada palabra será evaluada por el corrector ortográfico para determinar si está bien escrita o no.

2. **Palabras Incorrectas**: En esta sección se van mostrando las palabras incorrectas encontradas en el texto, y se resaltan en color rojo dentro del JTextPane. Junto a cada palabra incorrecta se encuentran tres botones:

   - "Siguiente": Permite avanzar a la siguiente palabra incorrecta en el texto.
   - "Ignorar": Permite ignorar la palabra incorrecta, evitando que se marque como incorrecta en futuros análisis.
   - "Agregar al diccionario": Permite agregar la palabra incorrecta al diccionario, considerándola como una palabra aceptada.

   Además, se proporciona un JComboBox con sugerencias de corrección para las palabras incorrectas. Un botón permite sobreescribir la palabra mal escrita por la sugerencia seleccionada en el JComboBox.

3. **Otras Funcionalidades**: En esta sección se encuentran tres botones adicionales:

   - "Abrir Archivo": Permite abrir un archivo de formato .txt que contiene el texto a analizar y cargarlo en el JTextPane.
   - "Guardar Archivo": Permite guardar el archivo de texto analizado en la computadora.
   - "Analizar Texto": Ejecuta el análisis del texto en busca de palabras incorrectas y muestra los resultados en la sección correspondiente.

## Tecnologías utilizadas

El Corrector Ortográfico en Java se desarrolló utilizando las siguientes tecnologías y bibliotecas:

- Lenguaje de programación: Java
- Biblioteca gráfica: Swing
- Estructura de datos personalizada: Trie (Árbol)

## Requisitos del sistema

Para ejecutar el Corrector Ortográfico en Java, asegúrate de tener instalado lo siguiente:

- Java Development Kit (JDK)
- Biblioteca gráfica compatible con Java (Swing)

## Instalación y ejecución

1. Clona o descarga el repositorio del proyecto en tu máquina local.
2. Abre el proyecto en tu IDE de Java preferido.
3. Compila y ejecuta el archivo COO.java
4. La interfaz gráfica del Corrector Ortográfico se abrirá y podrás comenzar a utilizarlo.
