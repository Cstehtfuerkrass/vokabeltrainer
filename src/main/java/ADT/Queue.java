package ADT;

/**
 * Warteschlange, implementiert als einfach verkettete Liste (GENERISCH).
 */
public class Queue<T> {
    private Element<T> head;
    private Element<T> tail;

    public Queue() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Liefert den Inhalt des ersten Elements als Typ T, ohne es zu entfernen
     * (peek).
     */
    public T head() {
        if (head == null) {
            // In einer realen Anwendung müsste hier eine Exception geworfen werden
            return null;
        }
        return head.inhalt;
    }

    /**
     * Fügt ein Element vom Typ T am Ende der Queue hinzu (enqueue).
     */
    public void enqueue(T element) {
        Element<T> hilf = new Element<>();
        hilf.inhalt = element;
        hilf.nachfolger = null;
        if (isEmpty()) {
            head = hilf;
            tail = hilf;
        } else {
            tail.nachfolger = hilf;
            tail = hilf;
        }

    }

    /**
     * Entfernt und liefert das Element vom Anfang der Queue als Typ T (dequeue).
     */
    public T dequeue() {
        if (head == null) {
            // In einer realen Anwendung müsste hier eine Exception geworfen werden
            return null;
        }
        T hilf = head.inhalt;
        head = head.nachfolger;
        if (isEmpty()) {
            tail = null;
        }
        return hilf;
    }
}
