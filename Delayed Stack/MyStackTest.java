import org.junit.Test;
import static org.junit.Assert.*;
@SuppressWarnings("unchecked")
public class MyStackTest{

   


    @Test
    public void testMyStackConstructor(){
        DelayedStack s = new MyStack(3);
        assertEquals(0, s.size());

    }

    @Test
    public void testMyStackConstructor_1(){
        DelayedStack s = new MyStack(0);
        assertEquals(0, s.size());

    }

    @Test
    public void testMyStackpushandpop(){
        DelayedStack s = new MyStack(3);
        assertEquals(3, s.getDelay());
        s.push("a");
        assertEquals(2, s.getDelay());
        assertNull(s.pop());
        s.push("b");
        s.push("c");
        assertEquals(0, s.getDelay());
        assertEquals("c", s.pop());
        assertEquals("b", s.pop());
        s.push("d");
        assertEquals(2, s.getDelay());
        s.push("e");
        assertEquals(3, s.size());

    }

    @Test
    public void testMyStackpushandpop_1(){
        DelayedStack s = new MyStack(3);
        s.push("a");
        s.push("b");
        s.push("c");
        s.push("d");

    }


    @Test(expected = IllegalStateException.class)
    public void testMyStackpop(){
        DelayedStack s = new MyStack(3);
        s.pop();

    }

    @Test(expected = IllegalStateException.class)
    public void testMyStackpeek_1(){
        DelayedStack s = new MyStack(3);
        s.peek();

    }




    @Test
    public void testMyStackpeek(){
        DelayedStack s = new MyStack(3);
        assertEquals(3, s.getDelay());
        s.push("a");
        assertEquals(2, s.getDelay());
        assertNull(s.pop());
        s.push("b");
        s.push("c");
        assertEquals("c", s.peek());
        assertEquals("c", s.pop());
        assertEquals("b", s.peek());
        s.push("d");
        assertEquals("d", s.peek());

    }


    @Test
    public void testMyStackclear(){
        DelayedStack s = new MyStack(3);
        s.push("a");
        s.push("b");
        s.push("c");
        s.push("d");
        assertEquals(4, s.size());
        assertTrue(s.clear());
        assertEquals(0, s.size());


    }

    @Test
    public void testMyStackclear_1(){
        DelayedStack s = new MyStack(3);
        assertFalse(s.clear());
        s.push("a");
        assertFalse(s.clear());
        s.push("b");
        s.push("c");
        assertTrue(s.clear());
            

    }

    @Test
    public void testMyStacksetmax(){
        DelayedStack s = new MyStack(3);
        s.push("a");
        s.push("b");
        assertEquals(1, s.getDelay());
        s.setMaximumDelay(5);
        assertEquals(1, s.getDelay());
        assertEquals(5, s.getMaximumDelay());
        assertNull(s.pop());
        s.push("c");
        s.push("d");
        assertEquals(3, s.getDelay());

    }


    @Test
    public void testMyStackcontain(){
        DelayedStack s = new MyStack(3);
        s.push("a");
        s.push("b");
        assertTrue(s.contains("a"));
        

    }

    @Test
    public void testMyStackcontain_1(){
        DelayedStack s = new MyStack(3);
        s.push("a");
        s.push("b");
        assertFalse(s.contains("c"));
        

    }


    @Test
    public void testMyStacksetmax_1(){
        DelayedStack s = new MyStack(3);
        s.push("a");
        s.push("b");
        assertEquals(1, s.getDelay());
        s.setMaximumDelay(0);
        assertEquals(1, s.getDelay());
        assertEquals(1, s.getMaximumDelay());
        assertNull(s.pop());
        s.push("c");
        s.push("d");
        assertEquals(0, s.getDelay());
        

    }


























}
