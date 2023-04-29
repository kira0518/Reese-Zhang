@SuppressWarnings("unchecked")
public class MyQueue<E> implements DelayedQueue<E>{
    public int init_value;
    public Object[] queue;
    public int delay;
    public boolean can_dequeue;
    public int count;
    public boolean set_max;
    public int max_value;
    public int enqueue_num = 0;
    public boolean enqueued;
    public int enqueue_times = 0;
    public int dequeue_idx;
    public int dequeue_num = 0;

    public MyQueue(int value){
        this.enqueued = false;
        if (value <= 0)
		{
			this.init_value = 1;
		}else
		{
			this.init_value = value;
		}

        this.delay = this.init_value;
        this.queue = new Object[this.init_value];

        this.can_dequeue = false;
        this.count = 0;

        this.max_value = this.init_value;
        this.set_max = false;
        this.dequeue_idx = 0;


    }

    /**
	 * Returns the number of elements currently in the stack.
	 * @return The size of the stack.
	 */
	public int size(){
        int con = 0;
	    for (int i=0;i<this.queue.length;i++)
        {
            if (this.queue[i] != null)
			{
				con = con + 1;
			}
        }
        return con;


    }


    /**
	 * Add an element to this queue.
	 * @param element The element to be added.
	 */
	public void enqueue(E element){
        this.can_dequeue = false;
        enqueue_num = enqueue_num + 1;
        if (this.set_max == true && this.delay == 0)
        {
            this.init_value = this.max_value;
            this.set_max = false;
            this.delay = this.init_value;

        }

        if(this.queue[this.queue.length - 1]!=null)
		{
			Object[] new_queue = new Object[this.queue.length + 10];
			System.arraycopy(this.queue, 0, new_queue, 0, this.queue.length);
			this.queue = new_queue;
		}

        if (this.delay != 0)
		{
			this.delay = this.delay - 1;
		}else
		{
			this.delay = this.init_value-1;
		}

        this.count = this.count + 1;
        this.enqueued = true;
        this.queue[enqueue_num - 1] = element;
        enqueue_times = enqueue_times + 1;
        if (enqueue_times >= this.init_value)
        {
            this.can_dequeue = true;
        }

    }

    /**
	 * Attempt to remove an element from this queue. 
	 * The first element to remove is the most recent one that was added.
	 * If the enqueue is empty, throw an IllegalStateException.
	 * If an element cannot be removed due to the delay condition, return null.
	 * 
	 * @return The element that was removed.
	 * @throws IllegalStateException if the queue is empty.
	 */
	public E dequeue() throws IllegalStateException{
        if (enqueue_num == 0)
        {
            throw new IllegalStateException();
        }
        if (this.queue[enqueue_num-1]==null)
        {
            throw new IllegalStateException();
        }

        if (this.can_dequeue == false)
        {
            return null;
        }else
        {
            dequeue_num = dequeue_num - 1;
            E ele = (E)this.queue[this.dequeue_idx];
            this.queue[this.dequeue_idx] = null;
            this.dequeue_idx = this.dequeue_idx + 1;
            enqueue_times = 0;
            return ele;
            
            


        }





    }



    /**
	 * Return the element at the front of this queue, without removing it.
	 * 
	 * @return The element at the front of the queue.
	 * @throws IllegalStateException if the queue is empty.
	 */
	public E peek() throws IllegalStateException{
        if (enqueue_num == 0)
        {
            throw new IllegalStateException();
        }
        // if (this.queue[enqueue_num-1]==null)
        // {
        //     throw new IllegalStateException();
        // }

        E ele = (E)this.queue[this.dequeue_idx];
        return ele;



    }
    
    /**
	 * Returns how many more elements must be added before the queue will allow removals to commence.
	 * @return
	 */
	public int getDelay(){
		int ele = this.delay;
		
		
		return this.delay;
	}

    
	/**
	 * Sets the maximum delay, which is the number of elements that must be added to the queue before any can be removed.
	 * Once a removal is allowed to occur, there is no limit on the number of removals.
	 * Modifying this value while the queue is in use does not change the current delay value. It only changes the next time the delay is reset.
	 * 
	 * @param d The number of elements that must be added before any can be removed.
	 */
	public void setMaximumDelay(int d){
        this.set_max = true;
		if (d <= 0)
		{
			d = 1;
		}
		this.max_value = d;
    }


    /**
	 * Get the maximum possible delay, which is the number of push operations that must occur before pop operations can occur, given that the first push operation has not yet occurred (ie. a pop operation just occurred).
	 * @return The maximum number of push operations before pop operations can occur.
	 */
	public int getMaximumDelay(){
        return this.max_value;
    }

    /**
	 * Remove all elements in this queue. Similarly to the pop operation, it is constrained by the delay condition.
	 * @return Whether the clear succeeded or failed due to the delay condition.
	 */
	public boolean clear(){
        if (this.delay <= 0)
        {
            this.can_dequeue = true;
        }
        if (this.can_dequeue == false)
        {
            return false;
        }else
        {
            int len = this.queue.length;
            this.queue = new Object[len];
            // this.delay = this.init_value;
            enqueue_times = 0;
            this.count = 0;
            return true;
        }

    }

    /**
	 * Check whether the queue contains this element, according to the .equals() method of the element.
	 * @param elem The element to search for.
	 * @return Whether the element was found or not.
	 */
	public boolean contains(E elem){
        for (int i=0;i<this.queue.length;i++)
        {
            if (this.queue[i] != null)
			{
				if (this.queue[i].equals(elem) == true)
				{
					return true;
				}
			}
			
        }
		return false;
    }

    // public static void main(String[] args){
    //     MyQueue s = new MyQueue(0);

    //     // s.enqueue("a");
    //     // s.enqueue("b");
    //     // s.enqueue("c");
    //     // s.enqueue("d");
    //     // System.out.println(s.dequeue());
    //     // System.out.println(s.peek());


    //     // s.enqueue("first element");
	// 	// s.enqueue("something else");
	// 	// System.out.println(s.dequeue());
	// 	// s.enqueue("third");
	// 	// s.enqueue("fourth");
	// 	// System.out.println(s.dequeue());
	// 	// s.enqueue("another one");
	// 	// System.out.println(s.dequeue());
	// 	// s.enqueue(2);
	// 	// s.enqueue(3);
	// 	// s.enqueue(4);
	// 	// System.out.println(s.dequeue());
	// 	// System.out.println(s.dequeue());
	// 	// System.out.println(s.dequeue());
	// 	// System.out.println(s.dequeue());
	// 	// System.out.println(s.dequeue());
	// 	// System.out.println(s.dequeue());


    //     s.enqueue("hello");
	// 	System.out.println(s.dequeue());
	// 	s.setMaximumDelay(2);
	// 	System.out.println(s.getMaximumDelay());
	// 	// System.out.println(s.dequeue());
	// 	s.enqueue("x");
	// 	s.enqueue("a");
	// 	s.enqueue("b");
	// 	s.enqueue("c");
	// 	System.out.println(s.dequeue());
	// 	System.out.println(s.dequeue());
	// 	s.setMaximumDelay(4);
	// 	System.out.println(s.getDelay());
	// 	System.out.println(s.dequeue());
	// 	s.enqueue("Y");
	// 	s.enqueue("Z");
	// 	s.setMaximumDelay(-1);
	// 	System.out.println(s.getDelay());
	// 	s.enqueue("An");
	// 	System.out.println(s.getDelay());
	// 	System.out.println(s.dequeue());
	// 	s.enqueue("AX");
	// 	System.out.println(s.getDelay());
	// 	System.out.println(s.dequeue());


	// 	// System.out.println(s.contains("first element"));

    //     // for (int i=0;i<s.queue.length;i++)
    //     // {
    //     //     System.out.println(s.queue[i]);
    //     // }

    // }







}  
