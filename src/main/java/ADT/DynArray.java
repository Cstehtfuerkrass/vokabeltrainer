package ADT;

public class DynArray {
    private Element first;
    private Element last;
    private int length;
    
    /**
     * Constructor for objects of class DynArray
     */
    public DynArray() {
        this.first = null;
        this.last = null;
        this.length = 0;
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public Object getItem(int index) {
        Element search = this.first;
        for (int i = 0; i < index; i++) {
            search = search.nachfolger;
        }

        return search.inhalt;
    }
    // Eigene Methode um gesamtes Element, anstatt Inhalt zu bekommen
    public Element getElement(int index) {
        Element search = this.first;
        for (int i = 0; i < index; i++) {
            search = search.nachfolger;
        }

        return search;
    }
    
    public void append(Object obj) {
        Element newElement = new Element();
        newElement.inhalt = obj;
        newElement.nachfolger = null;
        if(last == null) {
            this.first = newElement;
        } else {
            this.last.nachfolger = newElement;
        }
        this.last = newElement;
        
        this.length++;
    }
    
    public void insertAt(int index, Object obj) {
        Element tmp = new Element();
        tmp.inhalt = obj;
        if(index == this.getLength() - 1) {
            this.append(obj);
        } else if(index == 0) {
            tmp.nachfolger = this.first;
            this.first = tmp;
            this.length++;
        } else {
            Element search = first;
            for(int i = 0; i < index - 1; i++) {
                search = search.nachfolger;
            }
            
            tmp.nachfolger = search.nachfolger;
            search.nachfolger = tmp;
            this.length++;
        }
        
        
    }
    
    public void setItem(int index, Object obj) {
        if(index == 0) {
            this.first.inhalt = obj;
        } else if(index== this.getLength() - 1) {
            this.last.inhalt = obj;
        } else {
            Element search = this.first;
            for(int i = 0; i < index; i++) {
                search = search.nachfolger;
            }
            
            search.inhalt = obj;
        }
    }
    
    public void delete(int index) {
        if(index == 0) {
            this.first = this.first.nachfolger;
        } else if(index == this.getLength() - 1 ) {
            Element search = this.first;
            while(search.nachfolger != this.last) {
                search = search.nachfolger;
            }
            search.nachfolger = null;
            this.last = search;
        } else {
            Element search = this.first;
            for(int i = 0; i < index - 2; i++) {
                search = search.nachfolger;
            }
            search.nachfolger = search.nachfolger.nachfolger;
        }
        
        this.length--;
    }
    
    public int getLength() {
        return this.length;
    }

    // Eigene Shuffle Methode mit Fisher Yates Shuffle
    public void shuffle() {

    }
}
