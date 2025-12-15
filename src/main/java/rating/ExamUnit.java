package rating;

import setclasses.Vocabulary;
import setclasses.Languages;

/**
 * Repräsentiert eine einzelne Vokabelabfrage und deren Ergebnis.
 */
public class ExamUnit {

    private final Vocabulary vocabulary;
    private final Languages askedLanguage;
    private final Languages expectedLanguage;
    private boolean isCorrect;
    private String givenAnswer;

    public ExamUnit(Vocabulary vocabulary, Languages asked, Languages expected) {
        this.vocabulary = vocabulary;
        this.askedLanguage = asked;
        this.expectedLanguage = expected;
        this.isCorrect = false;
        this.givenAnswer = "";
    }

    /**
     * Bewertet die Antwort des Benutzers und aktualisiert den Status.
     */
    public boolean rateAnswer(String answer) {
        this.givenAnswer = answer.trim();

        String[] targetVocabularies;

        if (askedLanguage.equals(vocabulary.getLang1())) {
            targetVocabularies = vocabulary.getVocab2();
        } else {
            targetVocabularies = vocabulary.getVocab1();
        }

        // Prüft, ob die gegebene Antwort mit einer der möglichen Zielvokabeln
        // übereinstimmt (case-insensitive)
        for (String target : targetVocabularies) {
            if (target != null && !target.trim().isEmpty() && target.trim().equalsIgnoreCase(givenAnswer)) {
                this.isCorrect = true;
                return true;
            }
        }
        this.isCorrect = false;
        return false;
    }

    // --- Getters ---
    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public Languages getAskedLanguage() {
        return askedLanguage;
    }

    public Languages getExpectedLanguage() {
        return expectedLanguage;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }
}