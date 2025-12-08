import ADT.DynArray;
public class Set {
    private String name;
    private String lang1;
    private String lang2;
    private DynArray pairs = new DynArray();

    public void addPair(String[] pair) {
        pairs.append(pair);
    }

    public void deletePair(int i) {
        pairs.delete(i);
    }

    public void printSet() {
        for (int i = 0; i < pairs.getLength(); i++) {
            Object pair = pairs.getItem(i);
            for (String string : pair) {
                
            }
        }
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang1() {
        return lang1;
    }

    public void setLang1(String lang1) {
        this.lang1 = lang1;
    }

    public String getLang2() {
        return lang2;
    }

    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    public Set(String name, String lang1, String lang2) {
        this.name = name;
        this.lang1 = lang1;
        this.lang2 = lang2;
    }
}