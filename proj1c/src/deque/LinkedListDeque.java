package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        public Node prev;
        public T item;
        public Node next;

        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public Node sentinel;
    public int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        size = 0;
    }

    private class lldIterator implements Iterator<T> {
        private int position;
        private Node movingNode;

        public lldIterator() {
            position = 0;
            movingNode = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T currentItem = movingNode.item;
            movingNode = movingNode.next;

            position++;

            return currentItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new lldIterator();
    }

    @Override
    public void addFirst(T x) {
        // Creates new Node with the Item x, with prev = sentinel and next = the current first item of the list.
        Node new_node = new Node( sentinel, x, sentinel.next);

        // Points the prev of the current first item of the list to the new Node.
        sentinel.next.prev = new_node;

        // Points the next of the sentinel to the new Node, making it the new first item of the list.
        sentinel.next = new_node;

        // Increments size with +1
        size++;
    }

    @Override
    public void addLast(T x) {
        // Creates new Node with the Item x, with prev = current last item of the list, and next = sentinel.
        Node new_node = new Node(sentinel.prev, x, sentinel);

        // Points the next of the last item of the list to the new Node.
        sentinel.prev.next = new_node;

        // Points the prev of the sentinel to the new Node, making it the new last item of the list.
        sentinel.prev = new_node;

        // Increments size with +1
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;

        while(current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        Node removedItem = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;

        size--;

        return removedItem.item;
    }

    @Override
    public T removeLast() {
        Node removedItem = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;

        size--;

        return removedItem.item;
    }

    @Override
    public T get(int index) {
        if(size == 0 || index < 0 || index >= size) {
            return null;
        }

        if(index == 0) {
            return sentinel.next.item;
        }

        Node node = sentinel.next;

        for(int i = 1; i < size; i++) {
            node = node.next;
            if(i == index) {
                return node.item;
            }
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        int iterator = 0;
        return doRecursive(index, sentinel.next, iterator);
    }

    @Override
    public boolean equals (Object other) {
        if(this == other) { return true; }

        if(other instanceof LinkedListDeque x ) {
            LinkedListDeque<T> otherList = (LinkedListDeque<T>) x;

            if(this.size != otherList.size) {
                return false;
            }

            ArrayList<T> list = new ArrayList<>();

            for(T otherItem : otherList) {
                list.add(otherItem);
            }

            for(T item : this) {
                if(!list.contains(item)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        ArrayList<T> list = new ArrayList<>();

        for(T item : this) {
            list.add(item);
        }
        return list.toString();
    }


    private T doRecursive(int index, Node node, int iterator) {
        if(size == 0 || index < 0 || index >= size) {
            return null;
        }

        if(index == iterator) {
            return node.item;
        }

        return doRecursive(index, node.next, iterator + 1);
    }

    public static void main(String[] args) {

        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(10);
        lld.addLast(20);
        lld.addLast(30);
        lld.addFirst(5);
        System.out.println(lld.toList());

        Deque<Integer> lld2 = new LinkedListDeque<>();
        lld2.addLast(10);
        lld2.addLast(20);
        lld2.addLast(30);
        lld2.addFirst(5);

        System.out.println(lld.equals(lld2));

        System.out.println(lld);

    }
}
