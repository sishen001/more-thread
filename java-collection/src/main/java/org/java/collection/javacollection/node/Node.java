package org.java.collection.javacollection.node;

/**
 * 自定义节点
 */
public class Node {

    private Object data;

    private Node next;

    private Node pre;

    public Node(Object data) {
        this.data = data;

    }

    public Node() {

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }
}
