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
    private final ExamMode mode;
    private final Queue<ExamUnit> examQueue; // Generische Queue
    private int correctAnswers;
    private int totalAttempts;
    private final Random random = new Random();

    public RatingSystem(Set vocabularySet, ExamMode mode) {
        this.vocabularySet = vocabularySet;
        this.mode = mode;
        this.examQueue = new Queue<>(); // Generische Instanziierung
        this.correctAnswers = 0;
        this.totalAttempts = 0;

        initializeExam();
    }

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
     * Füllt die interne Warteschlange. Kein Casting nötig, da DynArray<Vocabulary>
     * typsicher ist.
     */
    private void initializeExam() {
        DynArray<Vocabulary> vocabularies = vocabularySet.getVocabularies();

        for (int i = 0; i < vocabularies.getLength(); i++) {

            Vocabulary vocab = vocabularies.getItem(i); // KEIN CASTING

            ExamUnit unit = createExamUnit(vocab);
            examQueue.enqueue(unit);
        }
    }

    public ExamUnit getNextUnit() {
        if (examQueue.isEmpty())
            return null;
        return examQueue.head(); // KEIN CASTING
    }

    public boolean rateAndProceed(String answer) {
        ExamUnit currentUnit = examQueue.dequeue(); // KEIN CASTING

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

    public int getRemainingVocabularies() {
        // Implementierung würde eine size()-Methode in ADT/Queue erfordern.
        return 0;
    }
}