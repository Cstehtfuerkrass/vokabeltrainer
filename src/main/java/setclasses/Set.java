package setclasses;

import ADT.DynArray;

/**
 * Repräsentiert ein komplettes Set von Vokabeln.
 */
public class Set {
    private String name;
    private Languages lang1;
    private Languages lang2;

    // Typsichere Speicherung in DynArray<Vocabulary>
    public DynArray<Vocabulary> vocabularies = new DynArray<>();

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