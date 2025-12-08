package ADT;

public class Queue
{
    private Element head;
    private Element tail;

    /**
     * Constructor for objects of class Stack
     */
    public Queue()
    {
        head = null;
        tail = null;
    }

    public boolean isEmpty()
    {
        return head == null;
    }
    
    public Object head()
    {
        return head.inhalt;
    }
    
    public void enqueue(Object element)
    {
        Element hilf = new Element();
        hilf.inhalt = element;
        hilf.nachfolger = null;
        if (isEmpty())
        {
            head = hilf;
            tail = hilf;
        }
        else
        {
            tail.nachfolger = hilf;
            tail = hilf;
        }
        
    }
    
    public Object dequeue()
    {
        Object hilf = head.inhalt;
        head = head.nachfolger;
        if (isEmpty())
        {
            tail = null;
        }
        return hilf;
    }
}
