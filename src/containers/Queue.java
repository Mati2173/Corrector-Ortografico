package containers;

import sources.nodes.Node;
import sources.Operations;

public class Queue<T> implements Operations {
    private Node<T> first, last;
    private int size;
    private T lastPulled;

    public Queue() {
        clean();
    }

    @Override
    public void clean() {
        this.first = this.last = null;
        this.size = 0;
        this.lastPulled = null;
    }

    @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    public T getLastPulled() {
        return this.lastPulled;
    }

    public void push(T element) {
        if(isEmpty())
            this.first = this.last = new Node<T>(element);

        else {
            this.last.setNextNodo(new Node<T>(element));
            this.last = this.last.getNextNodo();
        }

        this.size++;
    }

    public T viewTop() {
        if(this.first == null) return null;
        else return this.first.getNodeInfo();
    }

    public T pull() {
        T element = null;
        if(!isEmpty()) {
            element = this.lastPulled = this.first.getNodeInfo();
            this.first = this.first.getNextNodo();

            if(isEmpty())
                this.last = null;

            this.size--;
        }

        return element;
    }

    @Override
    public String toString() {
        if(viewTop() == null) return null;
        else return viewTop().toString();
    }
}
