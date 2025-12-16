package ADT;

/**
 * Eine dynamische Datenstruktur, implementiert als einfach verkettete Liste
 * (GENERISCH).
 */
public class DynArray<T> {
    private Element<T> first;
    private Element<T> last;
    private int length;

    public DynArray() {
        this.first = null;
        this.last = null;
        this.length = 0;
    }

    // --- Basis-Methoden ---

    public boolean isEmpty() {
        return this.first == null;
    }

    public void append(T obj) {
        Element<T> newElement = new Element<>();
        newElement.inhalt = obj;
        newElement.nachfolger = null;
        if (last == null) {
            this.first = newElement;
        } else {
            this.last.nachfolger = newElement;
        }
        this.last = newElement;
        this.length++;
    }

    public T getItem(int index) {
        Element<T> search = this.first;
        for (int i = 0; i < index; i++) {
            search = search.nachfolger;
        }
        return search.inhalt;
    }

    private Element<T> getElement(int index) {
        if (index < 0 || index >= this.length) {
            throw new IndexOutOfBoundsException("Index " + index + " ist außerhalb der Grenzen.");
        }
        Element<T> search = this.first;
        for (int i = 0; i < index; i++) {
            search = search.nachfolger;
        }
        return search;
    }

    public void setItem(int index, T obj) {
        Element<T> target = this.getElement(index);
        target.inhalt = obj;
    }

    public void delete(int index) {
        if (this.length == 0)
            return;

        if (index == 0) {
            this.first = this.first.nachfolger;
            if (this.first == null)
                this.last = null;
        } else {
            Element<T> search = this.first;

            int prevIndex = index - 1;
            if (prevIndex < 0)
                prevIndex = 0;

            for (int i = 0; i < prevIndex; i++) {
                search = search.nachfolger;
            }

            search.nachfolger = search.nachfolger.nachfolger;

            if (search.nachfolger == null) {
                this.last = search;
            }
        }

        this.length--;
    }

    public int getLength() {
        return this.length;
    }
}