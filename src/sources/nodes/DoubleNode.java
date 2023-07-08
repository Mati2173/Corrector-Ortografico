package sources.nodes;

public class DoubleNode<T> {
    private T nodeInfo;
    private DoubleNode<T> prevNode, nextNode;

    public DoubleNode(T nodeInfo) {
        this(nodeInfo, null, null);
    }

    public DoubleNode(T nodeInfo, DoubleNode<T> nextNode){
        this(nodeInfo, null, nextNode);
    }

    public DoubleNode(T nodeInfo, DoubleNode<T> prevNode, DoubleNode<T> nextNode){
        this.nodeInfo = nodeInfo;
        this.prevNode = prevNode;
        this.nextNode = nextNode;
    }

    public void setPrevNode(DoubleNode<T> prevNode) {
        this.prevNode = prevNode;
    }

    public DoubleNode<T> getPrevNode() {
        return this.prevNode;
    }

    public void setNextNode(DoubleNode<T> nextNode) {
        this.nextNode = nextNode;
    }

    public DoubleNode<T> getNextNode() {
        return this.nextNode;
    }

    public void setNodeInfo(T nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public T getNodeInfo(){
        return this.nodeInfo;
    }

    @Override
    public String toString() {
        if(this.nodeInfo == null) return null;
        else return this.nodeInfo.toString();
    }

}
