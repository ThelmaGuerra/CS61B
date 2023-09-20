import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeckQueue {
    private class Node {
        public Node prev;
        public int item;
        public Node next;

        public Node(Node p, int i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public Node sentinel;
    public int size;
    public int n;
    public int[] plateia;

    public DeckQueue(int n) {
        sentinel = new Node(null, 55, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        plateia = new int[n - 1];

        size = 0;
        this.n = n;

        // Implements the deck with a loop from 1 to n.
        for(int i = 1; i <= n; i++) {
            addLast(i);

        }
    }

    public void addLast(int card) {
        // Creates new Node with the Item x, with prev = current last item of the list, and next = sentinel.
        Node new_node = new Node(sentinel.prev, card, sentinel);

        // Points the next of the last item of the list to the new Node.
        sentinel.prev.next = new_node;

        // Points the prev of the sentinel to the new Node, making it the new last item of the list.
        sentinel.prev = new_node;

        // Increments size with +1
        size++;
    }

    public int removeFirst() {
        Node first_node = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;

        size--;

        return first_node.item;
    }

    public void doTurn(int i) {
        int first = removeFirst();
        plateia[i] = first;

        first = removeFirst();
        addLast(first);
    }

    public List<Integer> toList() {
        List<Integer> returnList = new ArrayList<>();
        Node current = sentinel.next;

        while(current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }

        return returnList;
    }

    public static void main(String[] args) {
        DeckQueue deck1 = new DeckQueue(50);

        System.out.println(deck1.size);
        System.out.println(Arrays.toString(deck1.plateia));
        System.out.println(deck1.toList());

        for(int i = 0; i < 49; i++) {
            deck1.doTurn(i);
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println(deck1.size);
        System.out.println(Arrays.toString(deck1.plateia));
        System.out.println(deck1.toList());
    }
}
