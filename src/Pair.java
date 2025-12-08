public class Pair {
    String[] vocab1 = new String[3]; //max 3 Synonyme für Übersicht
    String[] vocab2 = new String[3]; //DynArray vlt besser geeignet

    public String[] getVocab1() {
        return vocab1;
    }
    public void setVocab1(String[] vocab1) {
        this.vocab1 = vocab1;
    }
    public String[] getVocab2() {
        return vocab2;
    }

    public void setVocab2(String[] vocab2) {
        this.vocab2 = vocab2;
    }
    public Pair(String[] vocab1, String[] vocab2) {
        this.vocab1 = vocab1;
        this.vocab2 = vocab2;
    }
}
