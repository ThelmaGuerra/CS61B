import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    ArrayList<T> items;
    int nextFirst;
    int nextLast;
    int size;

    public ArrayDeque() {
        items = new ArrayList<>(8);

        for(int i = 0; i < 8; i++) {
            items.add(null);
        }

        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    public void handleResizingUp() {
        ArrayList<T> new_items = new ArrayList<>(items.size() * 2);

        int first_item_index = nextLast;
        int zero_index = 0;

        for(int i = 0; i < items.size() * 2; i++) {
            if(i >= items.size()) {
                new_items.add(null);
            } else {
                if(first_item_index + i < items.size()) {
                    new_items.add(i, items.get(first_item_index + i));
                } else {
                    new_items.add(i, items.get(zero_index));
                    zero_index++;
                }
            }
        }

        nextFirst = new_items.size() - 1;
        nextLast = items.size();
        items = new_items;
    }

    public void handleResizingDown() {
        ArrayList<T> new_items = new ArrayList<>(items.size() / 2);

        int first_item_index;

        if(nextFirst == items.size() - 1) {
            first_item_index = 0;
        } else {
            first_item_index = nextFirst + 1;
        }

        int zero_index = 0;

        for(int i = 0; i < items.size() / 2; i++) {
            if(i >= items.size() / 4) {
                new_items.add(null);
            } else {
                if(first_item_index + i < items.size()) {
                    new_items.add(i, items.get(first_item_index + i));
                } else {
                    new_items.add(i, items.get(zero_index));
                    zero_index++;
                }
            }
        }

        nextFirst = new_items.size() - 1;
        nextLast = new_items.size() / 2;
        items = new_items;

    }

    @Override
    public void addFirst(T x) {
        if(items.get(nextFirst) != null) {
            handleResizingUp();
        }

        items.set(nextFirst, x);
        --nextFirst;
        ++size;

        if(nextFirst == -1) {
            nextFirst = items.size() - 1;
        }
        System.out.println(items);
    }

    @Override
    public void addLast(T x) {
        if(items.get(nextLast) != null) {
            handleResizingUp();
        }

        items.set(nextLast,x);
        ++nextLast;
        ++size;

        if(nextLast == items.size()) {
            nextLast = 0;
        }
        System.out.println(items);
    }

    @Override
    public List<T> toList() {
        return items;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public T removeFirst() {
        if(items.size() >= 16 && size <= items.size()) {
            handleResizingDown();
        }

        int firstIndex;

        if(nextFirst == items.size() - 1) {
            firstIndex = 0;
        } else {
            firstIndex = nextFirst + 1;
        }

        T removedItem = items.get(firstIndex);
        items.set(firstIndex, null);

        nextFirst = firstIndex;

        --size;

        return removedItem;
    }

    @Override
    public T removeLast() {
        if(items.size() >= 16 && size <= items.size() / 4) {
            handleResizingDown();
        }

        int lastIndex;

        if(nextLast == 0) {
            lastIndex = items.size() - 1;
        } else {
            lastIndex = nextLast - 1;
        }

        T removedItem = items.get(lastIndex);
        items.set(lastIndex, null);

        nextLast = lastIndex;

        --size;

        return removedItem;
    }

    @Override
    public T get(int index) {
        return null;
    }

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(20);
        ad.addFirst(10);
        ad.addLast(30);
        ad.addLast(40);
        ad.addLast(50);
        ad.addLast(60);
        ad.addLast(70);
        ad.addLast(80);
        ad.addLast(90);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(3);
        ad.addLast(100);
        ad.addLast(120);

        System.out.println("this is the list " + ad.toList());

        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        System.out.println("-------------");

        System.out.println(ad.toList());

        ad.addLast(6);
        ad.addFirst(2);



    }
}
