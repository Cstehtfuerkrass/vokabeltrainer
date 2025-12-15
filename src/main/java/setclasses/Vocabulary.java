package setclasses;

/**
 * Repräsentiert ein einzelnes Vokabelpaar, einschließlich seiner
 * zwei Sprachkomponenten und der spezifischen Sprachen, die es repräsentiert.
 */
public class Vocabulary {

    // Jede Sprachkomponente ist ein Array, das mehrere Übersetzungen/Synonyme
    // erlaubt (max 3)
    private String[] vocab1 = new String[3];
    private String[] vocab2 = new String[3];

    private Languages lang1;
    private Languages lang2;

    // Languages im Set gespeichert

    public Vocabulary(String[] vocab1, String[] vocab2) {
        this.vocab1 = vocab1;
        this.vocab2 = vocab2;
    }


    //----------Getters und Setters-------------------
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

    public Languages getLang1() {
        return lang1;
    }

    public void setLang1(Languages lang1) {
        this.lang1 = lang1;
    }

    public Languages getLang2() {
        return lang2;
    }

    public void setLang2(Languages lang2) {
        this.lang2 = lang2;
    }
}