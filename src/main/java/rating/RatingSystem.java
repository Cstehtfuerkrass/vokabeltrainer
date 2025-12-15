package rating;

import ADT.Queue;
import ADT.DynArray;
import setclasses.Set;
import setclasses.Vocabulary;

import java.util.Random;

/**
 * Steuert den gesamten Prüfungsablauf für ein Vokabel-Set und nutzt eine Queue
 * zur Wiederholung.
 */
public class RatingSystem {

    private final Set vocabularySet;
    private final ExamMode mode; // UMBENANNT
    private final Queue examQueue; // NICHT generisch
    private int correctAnswers;
    private int totalAttempts;
    private final Random random = new Random();

    public RatingSystem(Set vocabularySet, ExamMode mode) { // UMBENANNT
        this.vocabularySet = vocabularySet;
        this.mode = mode;
        this.examQueue = new Queue();
        this.correctAnswers = 0;
        this.totalAttempts = 0;

        initializeExam();
    }

    /**
     * Erstellt eine ExamUnit basierend auf dem gewählten Modus für eine gegebene
     * Vokabel.
     */
    private ExamUnit createExamUnit(Vocabulary vocab) {

        boolean lang1ToLang2 = (mode == ExamMode.LANG1_TO_LANG2);

        if (mode == ExamMode.RANDOM) {
            lang1ToLang2 = random.nextBoolean();
        }

        if (lang1ToLang2) {
            return new ExamUnit(vocab, vocabularySet.getLang1(), vocabularySet.getLang2());
        } else {
            return new ExamUnit(vocab, vocabularySet.getLang2(), vocabularySet.getLang1());
        }
    }

    /**
     * Füllt die interne Warteschlange in der Sequenz, wie sie im Set gespeichert
     * ist.
     */
    private void initializeExam() {
        DynArray vocabularies = vocabularySet.getVocabularies();

        for (int i = 0; i < vocabularies.getLength(); i++) {

            // Casting ist notwendig, da DynArray nicht generisch ist
            Vocabulary vocab = (Vocabulary) vocabularies.getItem(i);

            ExamUnit unit = createExamUnit(vocab);
            examQueue.enqueue(unit);
        }
    }

    /**
     * Liefert die nächste Vokabel, ohne sie zu entfernen (nutzt head() der
     * Lehrer-Queue).
     */
    public ExamUnit getNextUnit() {
        // Casting ist notwendig, da die Queue nicht generisch ist
        if (examQueue.isEmpty())
            return null;
        return (ExamUnit) examQueue.head();
    }

    /**
     * Verarbeitet die Antwort des Benutzers und steuert die Warteschlange.
     */
    public boolean rateAndProceed(String answer) {
        // Casting ist notwendig
        ExamUnit currentUnit = (ExamUnit) examQueue.dequeue();

        if (currentUnit == null)
            return false;

        totalAttempts++;
        boolean isCorrect = currentUnit.rateAnswer(answer);

        if (isCorrect) {
            correctAnswers++;
        } else {
            examQueue.enqueue(currentUnit);
        }

        return isCorrect;
    }

    // --- Getters und Status-Methoden ---

    public boolean isExamFinished() {
        return examQueue.isEmpty();
    }

    public double getSuccessRate() {
        if (totalAttempts == 0)
            return 0.0;
        return (double) correctAnswers / totalAttempts;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    /**
     * Hinweis: Da die gegebene Queue keine size()-Methode hat,
     * geben wir hier 0 zurück. Diese Methode kann später implementiert werden.
     */
    public int getRemainingVocabularies() {
        return 0;
    }
}