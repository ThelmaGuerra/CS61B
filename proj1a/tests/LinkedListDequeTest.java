import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.

    @Test
    /** This tests the size and isEmpty methods with an empty LinkedListDeque and a filled one.
     *  It also tests when the size changes. */
    public void sizeAndIsEmptyTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        // Checks for an empty LinkedListDeque.
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        // Checks for a filled LinkedListDeque;
        lld1.addLast(1);
        lld1.addFirst(0);
        lld1.addLast(2);
        lld1.addLast(3);

        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld1.isEmpty()).isFalse();

        // Checks the size after it changes.
        lld1.addLast(4);
        lld1.addLast(5);

        assertThat(lld1.size()).isEqualTo(6);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void getTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        // Empty LinkedListDeque.
        assertThat(lld1.get(0)).isEqualTo(null);

        // Filled LinkedListDeque.
        lld1.addFirst(10);
        lld1.addLast(20);
        lld1.addLast(30);
        lld1.addLast(40);
        lld1.addLast(50);

        assertThat(lld1.get(3)).isEqualTo(40);

        // Tests for invalid Arguments (negative index and above the last index).
        assertThat(lld1.get(-10)).isEqualTo(null);
        assertThat(lld1.get(5)).isEqualTo(null);
    }

    @Test
    public void getRecursiveTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        // Empty LinkedListDeque.
        assertThat(lld1.getRecursive(0)).isEqualTo(null);

        // Filled LinkedListDeque.
        lld1.addFirst(10);
        lld1.addLast(20);
        lld1.addLast(30);
        lld1.addLast(40);
        lld1.addLast(50);

        assertThat(lld1.getRecursive(3)).isEqualTo(40);

        // Tests for invalid Arguments (negative index and above the last index).
        assertThat(lld1.getRecursive(-10)).isEqualTo(null);
        assertThat(lld1.getRecursive(5)).isEqualTo(null);
    }

    @Test
    public <T> void removeFirstAndLastTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

        // Check if the item removed from an empty list is "null".
        Integer removedItem = lld1.removeFirst();
        assertThat(removedItem).isNull();

        // Fill LinkedListDeque.
        lld1.addFirst(10);
        lld1.addLast(20);
        lld1.addLast(30);
        lld1.addLast(40);
        lld1.addLast(50);

        // Check if "removeFirst()" returns the first item and removes it from the list.

        removedItem = lld1.removeFirst();
        assertThat(removedItem).isEqualTo(10);
        assertThat(lld1.toList()).containsExactly(20, 30, 40, 50).inOrder();

        // Check if "removeLast()" returns the last item and removes it from the list.

        removedItem = lld1.removeLast();
        assertThat(removedItem).isEqualTo(50);
        assertThat(lld1.toList()).containsExactly(20, 30, 40).inOrder();
    }
}