package ADT;

/**
 * Stapel (Stack), implementiert als einfach verkettete Liste (GENERISCH).
 */
public class Stack<T> // <--- NEU: Klasse ist generisch
{
    // Die Spitze des Stapels (Top of Stack)
    private Element<T> tos; // <--- NEU: Typ Element<T>

    /**
     * Constructor for objects of class Stack
     */
    public Stack() {
        tos = null;
    }

    /**
     * Prüft, ob der Stapel leer ist.
     */
    public boolean isEmpty() {
        return tos == null;
    }

    /**
     * Liefert das oberste Element, ohne es zu entfernen (peek).
     * 
     * @return Das oberste Element vom Typ T, oder null, wenn der Stack leer ist.
     */
    public T top() // <--- NEU: Rückgabetyp T
    {
        if (tos == null) {
            return null;
        }
        return tos.inhalt;
    }

    /**
     * Fügt ein Element vom Typ T oben auf den Stapel hinzu (push).
     * 
     */
    public void push(T element) // <--- NEU: Parameter ist T
    {
        Element<T> hilf = new Element<>(); // <--- NEU: Element<T> wird instanziiert
        hilf.inhalt = element;
        hilf.nachfolger = tos;
        tos = hilf;
    }

    /**
     * Entfernt und liefert das oberste Element vom Stapel (pop).
     * 
     * @return Das entfernte Element vom Typ T, oder null, wenn der Stack leer ist.
     * 
     */
    public T pop() // <--- NEU: Rückgabetyp T
    {
        if (tos == null) {
            return null;
        }
        T hilf = tos.inhalt; // <--- Nutzt T
        tos = tos.nachfolger;
        return hilf;
    }

}
