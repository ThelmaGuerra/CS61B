import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(20);
        ad.addFirst(10);

        assertThat(ad.toList()).containsExactly(null, null, null, 10, 20, null, null, null).inOrder();

        ad.addFirst(9);
        ad.addFirst(8);
        ad.addFirst(7);
        ad.addFirst(6);
        ad.addFirst(5);

        assertThat(ad.toList()).containsExactly(7, 8, 9, 10, 20, null, 5, 6).inOrder();
    }

    @Test
    public void addLastTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        ad.addLast(10);
        ad.addLast(20);
        ad.addLast(30);


        assertThat(ad.toList()).containsExactly(null, null, null, null, null, 10, 20, 30).inOrder();

        ad.addLast(40);
        ad.addLast(50);
        ad.addLast(60);

        assertThat(ad.toList()).containsExactly(40, 50, 60, null, null, 10, 20, 30).inOrder();

    }

    @Test
    public void resizingTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(10);
        ad.addLast(20);
        ad.addLast(30);
        ad.addLast(40);
        ad.addLast(50);
        ad.addLast(60);
        ad.addLast(70);
        ad.addLast(80);

        assertThat(ad.toList()).containsExactly(50, 60, 70, 80, 10, 20, 30, 40).inOrder();

        ad.addLast(90);

        assertThat(ad.toList()).containsExactly(10, 20, 30, 40, 50, 60, 70, 80, 90, null, null, null, null, null, null, null).inOrder();

    }

    @Test
    public void isEmptyTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        assertThat(ad.isEmpty()).isTrue();

        ad.addFirst(10);

        assertThat(ad.isEmpty()).isFalse();
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        ad.addFirst(20);
        ad.addFirst(10);

        assertThat(ad.toList()).containsExactly(null, null, null, 10, 20, null, null, null).inOrder();

        int removedItem = ad.removeFirst();

        assertThat(ad.toList()).containsExactly(null, null, null, null, 20, null, null, null).inOrder();
        assertThat(removedItem).isEqualTo(10);

        // Test when the array "loops around itself".

        ad.addFirst(10);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(3);
        ad.addFirst(2);

        assertThat(ad.toList()).containsExactly(3, 4, 5, 10, 20, null, null, 2).inOrder();

        removedItem = ad.removeFirst();

        assertThat(ad.toList()).containsExactly(3, 4, 5, 10, 20, null, null, null).inOrder();
        assertThat(removedItem).isEqualTo(2);

        removedItem = ad.removeFirst();

        assertThat(ad.toList()).containsExactly(null, 4, 5, 10, 20, null, null, null).inOrder();
        assertThat(removedItem).isEqualTo(3);

    }

    @Test
    public void removeLastTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        ad.addLast(10);
        ad.addLast(20);

        assertThat(ad.toList()).containsExactly(null, null, null, null, null, 10, 20, null).inOrder();

        int removedItem = ad.removeLast();

        assertThat(ad.toList()).containsExactly(null, null, null, null, null, 10, null, null).inOrder();

        assertThat(removedItem).isEqualTo(20);

        // Test when the array "loops around itself".

        ad.addLast(20);
        ad.addLast(30);
        ad.addLast(40);

        assertThat(ad.toList()).containsExactly(40, null, null, null, null, 10, 20, 30).inOrder();


        // Remove 40
        removedItem = ad.removeLast(); // expected to be 40

        assertThat(ad.toList()).containsExactly(null, null, null, null, null, 10, 20, 30).inOrder();
        assertThat(removedItem).isEqualTo(40);

        // Remove 30
        removedItem = ad.removeLast(); // expected to be 30

        assertThat(ad.toList()).containsExactly(null, null, null, null, null, 10, 20, null).inOrder();
        assertThat(removedItem).isEqualTo(30);

        // Test resizing up the array, then you remove items so it occupies 25% or less of the capability of the array.

        ad.addLast(30);
        ad.addLast(40);
        ad.addLast(50);
        ad.addLast(60);
        ad.addLast(70);
        ad.addLast(80);

        assertThat(ad.toList()).containsExactly(40, 50, 60, 70, 80, 10, 20, 30).inOrder();

        ad.addLast(90);

        assertThat(ad.toList()).containsExactly(10, 20, 30, 40, 50, 60, 70, 80, 90, null, null, null, null, null, null, null).inOrder();

        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();

        assertThat(ad.toList()).containsExactly(10, 20, 30, 40, null, null, null, null, null, null, null, null, null, null, null, null).inOrder();

        ad.removeLast();

        assertThat(ad.toList()).containsExactly(10, 20, 30, null, null, null, null, null);
    }
}
