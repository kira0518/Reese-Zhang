@SuppressWarnings("unchecked")
public class MyStack<E> implements DelayedStack<E>{
    public int value;
    public Object[] stack;
	public Object[] new_stack;
    public int count;
    public int idx = 0;
    public boolean can_pop;
	public int new_value;
	public int init_value;
	public int current_value;
	public int push_num;
	public boolean reset;
	public int reset_count = 2;
	public boolean set_max;
	public int max_value;
	public boolean pushed;
	public int push_times = 0;
	public int delay = 0;
	public int push_count = 0;
	public int pop_count = 0;
	public int set_pop;
	public int set_push;

	

    public MyStack(int value){
		this.pushed = false;
		this.set_pop = 0;
		this.set_push = 0;
		if (value <= 0)
		{
			this.init_value = 1;
			this.current_value = 1;
		}else
		{
			this.init_value = value;
			this.current_value = value;
		}
		
        this.delay = value;
        this.stack = new Object[this.init_value];
		// System.out.println(this.stack);
		
        this.can_pop = false;
		this.count = 0;
		
		this.reset = false;
		this.max_value = this.init_value;
		this.set_max = false;
		
		
		
        
    }
    
    /**
	 * Returns the number of elements currently in the stack.
	 * @return The size of the stack.
	 */
	public int size(){
       int con = 0;
	   for (int i=0;i<this.stack.length;i++)
        {
            if (this.stack[i] != null)
			{
				con = con + 1;
			}
        }
        return con;
    }
	
	/**
	 * Add an element to this stack.
	 * @param element The element to be added.
	 */
	public void push(E element){
		//System.out.println(this.current_value);
		//System.out.println(this.count);
		//System.out.println(this.current_value);
		this.can_pop = false;
		push_num = push_num + 1;
		this.set_push = this.set_push + 1;
		if (this.set_max == true && this.delay == 0)
		{
			this.init_value = this.max_value;
			this.set_max = false;
			this.delay = this.init_value - 1;
			push_count = 0;
			pop_count = 0;
		}
		
		if(this.stack[this.stack.length - 1]!= null)
		{
			Object[] new_stack = new Object[this.stack.length + 10];
			// System.out.println(this.stack.length);
			System.arraycopy(this.stack, 0, new_stack, 0, this.stack.length);
			
			this.stack = new_stack;
			// System.out.println(this.stack.length);
		}
		
		

		push_count = push_count + 1;
		if (this.delay != 0)
		{
			this.delay = this.delay - 1;
		}else
		{
			this.delay = this.init_value-1;
		}
		
		// System.out.println(this.stack.length);
		this.count = this.count + 1;	
		this.pushed = true;
		// System.out.println(this.stack);
		this.stack[push_num - 1] = element;
		push_times = push_times + 1;
		// System.out.println(this.init_value);
		// System.out.println(this.init_value);
		if (push_times >= this.init_value)
		{
			
			this.can_pop = true;
			
		}

    }
	
	/**
	 * Attempt to remove an element from this stack. 
	 * The first element to remove is the most recent one that was added.
	 * If the stack is empty, throw an IllegalStateException.
	 * If an element cannot be removed due to the delay condition, return null.
	 * 
	 * @return The element that was removed.
	 * @throws IllegalStateException if the stack is empty.
	 */
	public E pop() throws IllegalStateException{
        boolean empty = true;
		// System.out.println(this.can_pop);
		if (this.stack[0]==null)
		{
			throw new IllegalStateException();
			
		}else if (this.can_pop == false)
		{
			return null;
		}else
		{	
			push_num = push_num -1;
			E ele = (E)this.stack[push_num];
			this.stack[push_num] = null;

			push_times = 0;
			pop_count = pop_count + 1;
			this.set_pop = this.set_pop + 1;
			this.set_push = this.set_push - 1;
			return ele;
			
		
		}
		
		
    }
	
	/**
	 * Return the element at the top of this stack, without removing it.
	 * 
	 * @return The element at the top of the stack.
	 * @throws IllegalStateException if the stack is empty.
	 */
	public E peek() throws IllegalStateException{
		boolean empty = true;
		

		if (this.stack[0]==null)
		{
			throw new IllegalStateException();
			
		}else
		{
			E ele = (E)this.stack[push_num-1];
			return ele;
		}

		
		

		
		
	}
	
	/**
	 * Returns how many more elements must be added before the stack will allow removals to commence.
	 * @return
	 */
	public int getDelay(){
		int ele = this.delay;
		
		return this.delay;
	}
	
	/**
	 * Sets the maximum delay, which is the number of elements that must be added to the stack before any can be removed.
	 * Once a removal is allowed to occur, there is no limit on the number of removals.
	 * Modifying this value while the stack is in use does not change the current delay value. It only changes the next time the delay is reset.
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
		this.set_push = 0;
		this.set_pop = 0;

		
	}
	
	/**
	 * Get the maximum possible delay, which is the number of push operations that must occur before pop operations can occur, given that the first push operation has not yet occurred (ie. a pop operation just occurred).
	 * @return The maximum number of push operations before pop operations can occur.
	 */
	public int getMaximumDelay(){
		return this.max_value;
	}
	
	/**
	 * Remove all elements in this stack. Similarly to the pop operation, it is constrained by the delay condition.
	 * @return Whether the clear succeeded or failed due to the delay condition.
	 */
	public boolean clear(){
		if (this.delay <= 0)
		{
			this.can_pop = true;
		}
		if (this.stack[0]==null)
		{
			return false;
		}
		if (this.can_pop == false)
		{
			return false;
		}else
		{
			int len = this.stack.length;
			this.stack = new Object[len];
			push_count = 0;
			pop_count = 0;
			this.delay = this.init_value;
			push_times = 0;
			this.count = 0;
			return true;
		}
		
	}
	
	/**
	 * Check whether the stack contains this element, according to the .equals() method of the element.
	 * @param elem The element to search for.
	 * @return Whether the element was found or not.
	 */
	public boolean contains(E elem){
		for (int i=0;i<this.stack.length;i++)
        {
            if (this.stack[i] != null)
			{
				if (this.stack[i].equals(elem) == true)
				{
					return true;
				}
			}
			
        }
		return false;
	}



	
	

}
