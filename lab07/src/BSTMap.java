import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K, V>{

    private K key;
    private V value;
    private BSTMap<K, V> left;
    private BSTMap<K, V> right;
    private int size = 0;


    @Override
    public void put(K key, V value) {
        BSTMap<K, V> target = recursiveSearch(key);

        if(target.key == null) {
            size++;
        }

        target.key = key;
        target.value = value;
    }

    public BSTMap<K, V> recursiveSearch (K key) {
        if(this.key == null) {
            return this;
        }

        int comparison = key.compareTo(this.key);

        if(comparison < 0) {
            if(left == null) {
                left = new BSTMap<>();
            }
            return left.recursiveSearch(key);
        } else if(comparison > 0) {
            if(right == null) {
                right = new BSTMap<>();
            }
            return right.recursiveSearch(key);
        }
        return this;
    }


    @Override
    public V get(K key) {
        BSTMap<K, V> target = recursiveSearch(key);
        return target.value;
    }

    @Override
    public boolean containsKey(K key) {
        BSTMap<K, V> target = recursiveSearch(key);
        return target.key != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> b = new BSTMap<>();
        b.put("B", 2);
        b.put("A", 1);
        b.put("D", 4);
        b.put("C", 3);
        b.put("C", 10);
        b.put("E", 5);
        b.put("F", 6);

        System.out.println(b.get("E"));
        System.out.println(b.containsKey("P"));
        System.out.println(b.size());
    }
}
