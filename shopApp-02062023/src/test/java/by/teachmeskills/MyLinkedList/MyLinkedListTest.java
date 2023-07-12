package by.teachmeskills.MyLinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyLinkedListTest {
    private MyLinkedList<Integer> myLinkedList;

    @BeforeEach
    public void setUp() {
        myLinkedList = new MyLinkedList<>();
    }

    @Test
    public void checkIsEmptyReturnTrue() {
        assertTrue(myLinkedList.isEmpty());
    }

    @Test
    public void checkIsEmptySizeReturnZero() {
        assertEquals(0, myLinkedList.size());
    }

    @Test
    public void checkRemoveNotPresentThrowsException() {
        assertThrows(NoSuchElementException.class, () -> myLinkedList.remove(2));
    }

    @Test
    public void checkAddBeforeNotFoundThrowsException() {
        assertThrows(NoSuchElementException.class, () -> myLinkedList.addBefore(0, 1));
    }

    @Test
    public void testInsertAtFront() {
        for (int i = 5; i != -1; i--) {
            myLinkedList.addFirst(i);
        }
        assertEquals("[0,1,2,3,4,5]", myLinkedList.toString());
    }

    @Test
    public void testInsertAtEnd() {
        for (int i = 0; i < 5; i++) {
            myLinkedList.addLast(i);
        }
        assertEquals("[0,1,2,3,4]", myLinkedList.toString());
    }

    @Test
    public void testAddAfter() {
        for (int i = 5; i != -1; i--) {
            myLinkedList.addFirst(i);
        }

        //[0,1,2,3,4,5]

        myLinkedList.addAfter(5, 55);
        myLinkedList.addAfter(4, 44);
        myLinkedList.addAfter(3, 33);
        myLinkedList.addAfter(2, 22);
        myLinkedList.addAfter(1, 11);

        assertEquals("[0,1,11,2,22,3,33,4,44,5,55]", myLinkedList.toString());
    }

    @Test
    public void testAddBefore() {
        for (int i = 0; i < 5; i++) {
            myLinkedList.addFirst(i);
        }

        //[0,1,2,3,4]

        myLinkedList.addBefore(4, 44);
        myLinkedList.addBefore(3, 33);
        myLinkedList.addBefore(2, 22);
        myLinkedList.addBefore(1, 11);

        assertEquals("[44,4,33,3,22,2,11,1,0]", myLinkedList.toString());
    }

    @Test
    public void testRemove() {
        for (int i = 5; i != -1; i--) {
            myLinkedList.addFirst(i);
        }

        //[0,1,2,3,4,5]

        myLinkedList.remove(2);

        assertEquals("[0,1,3,4,5]", myLinkedList.toString());
    }
}
