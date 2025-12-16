import setclasses.Set;
import setclasses.SetStorage;

public class App {
    public static void main(String[] args) {
        String name = "EN-DE";
        String lang1 = "en";
        String lang2 = "de";
        String path = "res/vocab.json";
        
        Set set = new Set(name, lang1, lang2);
        String[] arr = { "Bank", "bench / bank" };  // Hier wären String Operationen notwendig, dafür feste Array Size
        set.addPair(arr);
        set.addPair(new String[] { "Cat", "Katze" });
        set.addPair(new String[] { "Dog", "Hund" });
        set.printSet();
        SetStorage.saveSet(set, path);
    }
}