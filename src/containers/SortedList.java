package containers;

import sources.nodes.DoubleNode;
import sources.Operations;

import java.util.Iterator;

public class SortedList<T extends Comparable<T>> implements Operations, Iterable<T> {
    private DoubleNode<T> first, last;
    public static final int NOT_FOUND = -1;
    private int size;

    public SortedList() {
        clean();
    }

    public SortedList(T[] elements) {
        this();

        if(elements == null)
            throw new NullPointerException("No es posible crear la lista porque \"elements\" es nulo");
        else
            for(T element: elements)
                add(element);
    }

    @Override
    public void clean() {
        this.first = this.last = null;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    public void add(T element) {
        // Si está vacía, lo inserto al principio y al final
        if(isEmpty())
            this.first = this.last = new DoubleNode<T>(element);

        // Si es menor que el primero, lo inserto en primer lugar
        else if(element.compareTo(this.first.getNodeInfo()) < 0) {
            this.first = new DoubleNode<T>(element, null, this.first);
            this.first.getNextNode().setPrevNode(this.first);
        }

        // Si es mayor o igual que el último, lo inserto al final
        else if(element.compareTo(this.last.getNodeInfo()) >= 0) {
            this.last = new DoubleNode<T>(element, this.last, null);
            this.last.getPrevNode().setNextNode(this.last);
        }

        // Lo inserto en su lugar correspondiente al medio
        else {
            DoubleNode<T> temp = this.first;
            while(element.compareTo(temp.getNextNode().getNodeInfo()) >= 0)
                temp = temp.getNextNode();

            DoubleNode<T> node = new DoubleNode<T>(element, temp, temp.getNextNode());
            temp.getNextNode().setPrevNode(node);
            temp.setNextNode(node);
        }

        this.size++;
    }

    private void checkIndex(int index) {
        if(isEmpty() || index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + this.size);
    }

    public T remove(int index) {
        checkIndex(index);

        T element;
        // Hay que eliminar el primero
        if(index == 0) {
            element = this.first.getNodeInfo();
            this.first = this.first.getNextNode();
            this.first.setPrevNode(null);
        }
        // Hay que eliminar el último
        else if(index == this.size - 1) {
            element = this.last.getNodeInfo();
            this.last = this.last.getPrevNode();
            this.last.setNextNode(null);
        }
        // Hay que eliminar del medio
        else {
            DoubleNode<T> prev = this.first, curr = this.first.getNextNode();
            for (int i = 1; i < index; i++) {
                prev = curr;
                curr = curr.getNextNode();
            }

            element = curr.getNodeInfo();
            curr = curr.getNextNode();
            prev.setNextNode(curr);
            curr.setPrevNode(prev);
        }

        this.size--;

        return element;
    }

    public boolean remove(T element) {
        DoubleNode<T> prev = null, curr = this.first;

        while(curr != null && element.compareTo(curr.getNodeInfo()) != 0) {
            prev = curr;
            curr = curr.getNextNode();
        }

        if(curr != null) {
            if(prev != null) {
                curr = curr.getNextNode();
                prev.setNextNode(curr);
                curr.setPrevNode(prev);
            }
            else {
                this.first = this.first.getNextNode();
                this.first.setPrevNode(null);
            }

            this.size--;
            return true;
        }
        else
            return false;
    }

    public T getElement(int index) {
        checkIndex(index);

        DoubleNode<T> temp = this.first;
        for (int i = 0; i < index; i++)
            temp = temp.getNextNode();

        return temp.getNodeInfo();
    }

    public int indexOf(T element) {
        if(isEmpty())
            return NOT_FOUND;

        DoubleNode<T> temp = this.first;
        int index = 0;
        while(temp != null && element.compareTo(temp.getNodeInfo()) != 0) {
            temp = temp.getNextNode();
            index++;
        }

        return temp != null ? index : NOT_FOUND;
    }

    public boolean contains(T element) {
        return indexOf(element) != NOT_FOUND;
    }

    @Override
    public String toString() {
        if(isEmpty()) return "[]";

        DoubleNode<T> temp = this.first;
        StringBuilder str = new StringBuilder("[");

        for (int i = 0; i < this.size() - 1; i++) {
            str.append(temp.toString());
            str.append(", ");
            temp = temp.getNextNode();
        }

        str.append(this.last);
        str.append("]");

        return str.toString();
    }


    // UTILIZADO PARA EL FOR-EACH
    @Override
    public Iterator<T> iterator() {
        return new ListIterator(this.first);
    }

    private class ListIterator implements Iterator<T> {
        private DoubleNode<T> node;

        public ListIterator(DoubleNode<T> first) {
            this.node = first;
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public T next() {
            T element = this.node.getNodeInfo();
            this.node = this.node.getNextNode();
            return element;
        }
    }
}
