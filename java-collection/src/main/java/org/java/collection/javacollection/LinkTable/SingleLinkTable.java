package org.java.collection.javacollection.LinkTable;

import org.java.collection.javacollection.node.Node;

import java.io.Serializable;

public class SingleLinkTable<E> implements Serializable {

    private Node root = new Node();

    private Node last; // 最后一个节点

    private int size = 0;

    public int size() {
        return size;
    }

    /**
     * 添加节点
     * 1：如果是第一个节点，就在跟节点上追加一个节点
     * 2：如果不是第一个节点，就在最后一个节点追加一个节点
     * @param e
     */
   public void addNode(E e){
     Node next = new Node(e);
     if(size == 0){
         root.setNext(next);
         last = next;
     }else{
         last.setNext(next);
         last = next;
     }
       size++;
   }

    /**
     * 根据下标获取指定元素
     * @param index
     * @return
     */
   public Object get(int index){
       checkIndex(index);
       Node node = root.getNext();
       for(int i = 0; i < index; i++){
           node = node.getNext();
       }
       return node.getData();
   }

    /**
     * 检查下标Index是否合法
     */
    private void checkIndex(int index) {
        if (index < 0 || index > size) {// 不合法报错
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    public static void main(String[] arg0){
        SingleLinkTable table = new SingleLinkTable();
        for(int i = 0; i < 1; i++){
            table.addNode(i);
        }

        System.out.println(table.get(0));
    }


}
