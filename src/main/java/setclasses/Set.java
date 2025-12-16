package setclasses;

import ADT.DynArray;

/**
 * Repräsentiert ein komplettes Set von Vokabeln.
 */
public class Set {
    private String name;
    private Languages lang1;
    private Languages lang2;

    // 1. Die interne Datenstruktur: MUSS transient sein, damit GSON die
    // ADT-Struktur ignoriert.
    public transient DynArray<Vocabulary> vocabularies = new DynArray<>();

    // 2. Das GSON-Speicherfeld: Hält die Vokabeln als einfaches Array FÜR die
    // JSON-Datei.
    private Vocabulary[] vocabulariesAsArray;

    public Set(String name, Languages lang1, Languages lang2) {
        this.name = name;
        this.lang1 = lang1;
        this.lang2 = lang2;
    }

    public void addVocab(String[] vocab1, String[] vocab2) {
        Vocabulary vocab = new Vocabulary(vocab1, vocab2);
        vocabularies.append(vocab);
    }

    public Vocabulary delVocab(int index) {
        Vocabulary removedVocab = vocabularies.getItem(index);
        vocabularies.delete(index);
        return removedVocab;
    }

    // --- GSON-Helfer-Methoden ---

    /**
     * Kopiert die Vokabeln aus dem DynArray in ein einfaches Array für GSON VOR dem
     * Speichern.
     */
    public void copyToPlainArray() {
        this.vocabulariesAsArray = new Vocabulary[this.vocabularies.getLength()];
        for (int i = 0; i < this.vocabularies.getLength(); i++) {
            this.vocabulariesAsArray[i] = this.vocabularies.getItem(i);
        }
    }

    /**
     * Kopiert die Vokabeln aus dem einfachen Array zurück in das DynArray NACH dem
     * Laden.
     */
    public void copyToDynArray() {
        // Zuerst das DynArray neu initialisieren (es war transient).
        this.vocabularies = new DynArray<>();
        if (this.vocabulariesAsArray != null) {
            for (Vocabulary vocab : this.vocabulariesAsArray) {
                if (vocab != null) {
                    this.vocabularies.append(vocab);
                }
            }
        }
        // Das GSON-Feld kann optional auf null gesetzt werden, um Speicher freizugeben.
        this.vocabulariesAsArray = null;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public Languages getLang1() {
        return lang1;
    }

    public Languages getLang2() {
        return lang2;
    }

    public DynArray<Vocabulary> getVocabularies() {
        return vocabularies;
    }
}