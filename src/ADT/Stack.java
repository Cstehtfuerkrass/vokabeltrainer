package ADT;

public class Stack
{
    // instance variables - replace the example below with your own
    
    private Element tos;

    /**
     * Constructor for objects of class Stack
     */
    public Stack()
    {
        tos = null;
    }

    public boolean isEmpty()
    {
        return tos == null;
    }
    
    public Object top()
    {
        return tos.inhalt;
    }
    
    public void push(Object element)
    {
        Element hilf = new Element();
        hilf.inhalt = element;
        hilf.nachfolger = tos;
        tos = hilf;
        
    }
    
    public Object pop()
    {
        Object hilf = tos.inhalt;
        tos = tos.nachfolger;
        return hilf;
    }
    
}
