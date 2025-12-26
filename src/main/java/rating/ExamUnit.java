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
    public boolean rateAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.trim().isEmpty())
            return false;

        // 1. Die korrekten Lösungen aus dem Vokabel-Objekt holen
        String[] correctWords = (this.expectedLanguage == vocabulary.getLang2())
                ? vocabulary.getVocab2()
                : vocabulary.getVocab1();

        // 2. Die Eingabe des Nutzers an Kommas splitten (falls er mehrere eingibt)
        // Beispiel: "bank, bench" wird zu ["bank", "bench"]
        String[] userInputs = userAnswer.split("\\s*,\\s*");

        // 3. Prüfung: Jedes vom Nutzer eingegebene Wort muss korrekt sein
        for (String input : userInputs) {
            boolean inputFound = false;

            for (String correct : correctWords) {
                if (correct != null && correct.trim().equalsIgnoreCase(input.trim())) {
                    inputFound = true;
                    break;
                }
            }

            // Wenn auch nur eines der eingegebenen Wörter nicht in der Liste ist: Falsch.
            if (!inputFound) {
                this.isCorrect = false;
                return false;
            }
        }

        // Wenn wir hier ankommen, waren alle eingegebenen Wörter (egal ob eins oder
        // viele) richtig.
        this.isCorrect = true;
        return true;
    }
    
    // In ExamUnit.java hinzufügen:
    public String getCorrectAnswersAsString() {
        String[] correctWords = (this.expectedLanguage == vocabulary.getLang2())
                ? vocabulary.getVocab2()
                : vocabulary.getVocab1();

        return String.join(", ", correctWords);
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