import org.junit.Test;
import static org.junit.Assert.*;
@SuppressWarnings("unchecked")
public class MyQueueTest{

   


    @Test
    public void testMyQueueConstructor(){
        DelayedQueue s = new MyQueue(3);
        assertEquals(0, s.size());

    }

    @Test
    public void testMyQueueConstructor_1(){
        DelayedQueue s = new MyQueue(0);
        assertEquals(0, s.size());

    }

    @Test
    public void testMyQueueenqueueanddequeue(){
        DelayedQueue s = new MyQueue(3);
        assertEquals(3, s.getDelay());
        s.enqueue("a");
        assertEquals(2, s.getDelay());
        assertNull(s.dequeue());
        s.enqueue("b");
        s.enqueue("c");
        assertEquals(0, s.getDelay());
        assertEquals("a", s.dequeue());
        assertEquals("b", s.dequeue());
        s.enqueue("d");
        assertEquals(2, s.getDelay());
        s.enqueue("e");
        assertEquals(3, s.size());

    }

    @Test
    public void testMyQueueenqueueanddequeue_1(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue("a");
        s.enqueue("b");
        s.enqueue("c");
        s.enqueue("d");

    }


    @Test(expected = IllegalStateException.class)
    public void testMyQueuedequeue(){
        DelayedQueue s = new MyQueue(3);
        s.dequeue();

    }

    @Test(expected = IllegalStateException.class)
    public void testMyQueuedequeue_1(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue(null);
        s.dequeue();

    }

    @Test(expected = IllegalStateException.class)
    public void testMyQueuepeek_1(){
        DelayedQueue s = new MyQueue(3);
        s.peek();

    }




    @Test
    public void testMyQueuepeek(){
        DelayedQueue s = new MyQueue(3);
        assertEquals(3, s.getDelay());
        s.enqueue("a");
        assertEquals(2, s.getDelay());
        assertNull(s.dequeue());
        s.enqueue("b");
        s.enqueue("c");
        assertEquals("a", s.peek());
        assertEquals("a", s.dequeue());
        assertEquals("b", s.peek());
        s.enqueue("d");
        assertEquals("b", s.peek());

    }


    @Test
    public void testMyQueueclear(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue("a");
        s.enqueue("b");
        s.enqueue("c");
        s.enqueue("d");
        assertEquals(4, s.size());
        assertTrue(s.clear());
        assertEquals(0, s.size());


    }

    @Test
    public void testMyQueueclear_1(){
        DelayedQueue s = new MyQueue(3);
        assertFalse(s.clear());
        s.enqueue("a");
        assertFalse(s.clear());
        s.enqueue("b");
        s.enqueue("c");
        assertTrue(s.clear());
            

    }

    @Test
    public void testMyQueuesetmax(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue("a");
        s.enqueue("b");
        assertEquals(1, s.getDelay());
        s.setMaximumDelay(5);
        assertEquals(1, s.getDelay());
        assertEquals(5, s.getMaximumDelay());
        assertNull(s.dequeue());
        s.enqueue("c");
        s.enqueue("d");
        assertEquals(4, s.getDelay());

    }


    @Test
    public void testMyQueuecontain(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue("a");
        s.enqueue("b");
        assertTrue(s.contains("a"));
        

    }

    @Test
    public void testMyQueuecontain_1(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue("a");
        s.enqueue("b");
        assertFalse(s.contains("c"));
        

    }


    @Test
    public void testMyQueuesetmax_1(){
        DelayedQueue s = new MyQueue(3);
        s.enqueue("a");
        s.enqueue("b");
        assertEquals(1, s.getDelay());
        s.setMaximumDelay(0);
        assertEquals(1, s.getDelay());
        assertEquals(1, s.getMaximumDelay());
        assertNull(s.dequeue());
        s.enqueue("c");
        s.enqueue("d");
        assertEquals(0, s.getDelay());
        

    }


























}
