package sources.nodes;

public class Node<T> {
    private T nodeInfo;
    private Node<T> nextNode;

    public Node(T nodeInfo){
        this(nodeInfo,null);
    }

    public Node(T nodeInfo, Node<T> nextNode){
        this.nodeInfo = nodeInfo;
        this.nextNode = nextNode;
    }

    public void setNodeInfo(T nodeInfo){
        this.nodeInfo = nodeInfo;
    }

    public void setNextNodo(Node<T> nextNode){
        this.nextNode = nextNode;
    }

    public T getNodeInfo(){
        return this.nodeInfo;
    }

    public Node<T> getNextNodo(){
        return this.nextNode;
    }

    @Override
    public String toString() {
        if(this.nodeInfo == null) return null;
        else return this.nodeInfo.toString();
    }
}
