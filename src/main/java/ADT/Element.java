package ADT;

/**
 * Repräsentiert einen einzelnen Knoten in der verketteten Liste (GENERISCH).
 */
public class Element<T> {
    public T inhalt; // Inhalt ist jetzt vom Typ T
    public Element<T> nachfolger; // Der Nachfolger ist vom Typ Element<T>

    public Element() {
        this.inhalt = null;
        this.nachfolger = null;
    }
}