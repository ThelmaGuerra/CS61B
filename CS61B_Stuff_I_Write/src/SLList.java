public class SLList {

    /* The first item (if it exists) is at sentinel.next */
    public IntNode sentinel;
    public int size;

    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    /** Creates an empty SLList */
    public SLList() {
        sentinel = new IntNode(56,null);
        size = 0;
    }

    /** Creates a SLList with 1 item */
    public SLList(int x) {
        sentinel = new IntNode(56,null);
        sentinel.next = new IntNode(x, null);
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size++;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        size++;

        IntNode p = sentinel;

        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    /** Returns the size of the list that starts at IntNode p */
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        SLList L = new SLList();

        L.addLast(50);

        System.out.println(L.size());
    }
}
