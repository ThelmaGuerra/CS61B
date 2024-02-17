package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> myComparator;
    public MaxArrayDeque(Comparator<T> c) {
        c = myComparator;
    }

    
}
