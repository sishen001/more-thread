package org.java.collection.javacollection.LinkTable;

import com.alibaba.fastjson.JSON;
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
     * 设置某一个节点的值
     * @param index
     * @param e
     */
   public void set(int index,E e){
       checkIndex(index);
       Node node = root.getNext();
       for(int i = 0; i < index; i++){
           node = node.getNext();
       }
       node.setData(e);
   }

    /**
     * 移除节点
     * @param index
     */
   public void remove(int index){
       checkIndex(index);
       Node node = root.getNext();
       if(index == 0){
           Node node2 = node.getNext();
           root.setNext(node2);
           node = null;
       }else if(index == (size - 1)){
           for(int i = 0; i < index - 1; i++){
               node = node.getNext();
           }
           Node lastNode = node.getNext();
           node.setNext(null);
           lastNode = null;
       }else{
           for(int i = 0; i < index - 1; i++){
               node = node.getNext();
           }
           Node indexNode = node.getNext();
           Node indexNodeNext = indexNode.getNext();
           node.setNext(indexNodeNext);
           indexNode = null;
       }
       size -- ;
   }

    /**
     * 插入节点
     * @param index
     */
   public void insert(int index, E e){
       checkIndex(index);
       Node node = root.getNext();
       if(index == 0){
           Node newNode = new Node(e);
           if(null != node){
               newNode.setNext(node);
           }
           root.setNext(newNode);
       }else if(index == size){
           for(int i = 0; i < index - 1; i++){
               node = node.getNext();
           }
           Node newNode = new Node(e);
           node.setNext(newNode);
       }else{
           for(int i = 0; i < index - 1; i++){
               node = node.getNext();
           }
           Node newNodexNext = node.getNext();
           Node newNode = new Node(e);
           newNode.setNext(newNodexNext);
           node.setNext(newNode);
       }
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

    @Override
    public String toString() {
        return "SingleLinkTable{" +
                "root=" + root +
                '}';
    }

    public static void main(String[] arg0){
        SingleLinkTable table = new SingleLinkTable();
        for(int i = 0; i < 10; i++){
            table.addNode(i+1);
        }

        /*System.out.println("移除第一个="+table.get(0));
        table.remove(0);
        System.out.println("移除第一个="+table.get(0));*/
       /* System.out.println("移除最后一个="+table.get(9));
        System.out.println("移除最后一个="+table.get(8));
        table.remove(9);
        System.out.println("移除最后一个="+table.get(8));*/
        /*System.out.println("第5个="+table.get(4));
        table.remove(4);
        System.out.println("移除第5个="+table.get(4));
        System.out.println("移除第5个="+table.get(3));*/

        System.out.println("插入5之前="+table.get(8));
        System.out.println("插入5之前="+table.get(9));
        table.insert(10,20);
        System.out.println("插入5之后="+table.get(8));
        System.out.println("插入5之后="+table.get(9));
        System.out.println("插入5之后="+table.get(10));
    }


}
