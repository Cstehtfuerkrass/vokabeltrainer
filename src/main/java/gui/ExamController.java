package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color; // Für Textfarbe
import manager.SetManager;
import rating.ExamMode;
import rating.ExamUnit;
import rating.RatingSystem;
import setclasses.Set;
import setclasses.Languages;

public class ExamController {

    // FXML-Elemente, die mit fx:id im FXML verbunden sind
    @FXML
    private Label promptLabel;
    @FXML
    private Label contextLabel;
    @FXML
    private TextField inputField;
    @FXML
    private Button checkButton;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private Button restartButton;

    // Anwendungslogik
    private Set currentVocabularySet;
    private RatingSystem ratingSystem;

    // Diese Methode wird automatisch aufgerufen, wenn die FXML-Datei geladen wurde.
    @FXML
    public void initialize() {
        // 1. Initialisiere den SetManager und lade ein Standard-Set
        SetManager setManager = new SetManager();
        String defaultSetName = "Grundlagen Deutsch-Englisch"; // Name deines Test-Sets

        currentVocabularySet = setManager.loadSet(defaultSetName);

        if (currentVocabularySet != null) {
            // 2. Initialisiere das RatingSystem mit dem geladenen Set
            // Wähle hier deinen gewünschten Prüfungsmodus
            ratingSystem = new RatingSystem(currentVocabularySet, ExamMode.LANG2_TO_LANG1); // Beispiel: Englisch ->
                                                                                            // Deutsch
            contextLabel.setText(String.format("(von %s nach %s)",
                    currentVocabularySet.getLang2().name(),
                    currentVocabularySet.getLang1().name()));
            updateGUI(); // Aktualisiere die GUI mit der ersten Vokabel
        } else {
            promptLabel.setText("Fehler: Vokabel-Set '" + defaultSetName + "' konnte nicht geladen werden.");
            inputField.setDisable(true);
            checkButton.setDisable(true);
            feedbackLabel.setTextFill(Color.RED);
            feedbackLabel.setText("Bitte Set-Datei prüfen.");
            contextLabel.setText("");
        }
    }

    /**
     * Wird aufgerufen, wenn der "Prüfen"-Button geklickt oder Enter im Textfeld
     * gedrückt wird.
     */
    @FXML
    private void handleCheckAnswer() {
        if (ratingSystem == null || ratingSystem.isExamFinished()) {
            return; // Nichts tun, wenn System nicht bereit oder Prüfung beendet ist
        }

        String userAnswer = inputField.getText().trim();
        if (userAnswer.isEmpty()) {
            feedbackLabel.setTextFill(Color.ORANGE);
            feedbackLabel.setText("Bitte gib eine Antwort ein.");
            return;
        }

        boolean isCorrect = ratingSystem.rateAndProceed(userAnswer);

        if (isCorrect) {
            feedbackLabel.setTextFill(Color.GREEN);
            feedbackLabel.setText("KORREKT! ✅");
        } else {
            feedbackLabel.setTextFill(Color.RED);
            // Optional: Die korrekte Antwort anzeigen
            ExamUnit currentUnit = ratingSystem.getNextUnit(); // Holen, um die richtige Antwort zu zeigen, aber nicht
                                                               // de-queuen
            String[] correctOptions;
            if (currentUnit.getAskedLanguage().equals(currentVocabularySet.getLang1())) {
                correctOptions = currentUnit.getVocabulary().getVocab2();
            } else {
                correctOptions = currentUnit.getVocabulary().getVocab1();
            }
            feedbackLabel.setText("FALSCH! ❌ Richtige Antwort: " + String.join(", ", cleanArray(correctOptions)));
        }

        inputField.setText(""); // Textfeld leeren
        updateGUI();
    }

    /**
     * Startet die Prüfung neu.
     */
    @FXML
    private void handleRestartExam() {
        if (currentVocabularySet != null) {
            ratingSystem = new RatingSystem(currentVocabularySet, ExamMode.LANG2_TO_LANG1); // Modus beibehalten
            feedbackLabel.setTextFill(Color.BLACK);
            feedbackLabel.setText("Prüfung neu gestartet.");
            inputField.setDisable(false);
            checkButton.setDisable(false);
            restartButton.setVisible(false);
            updateGUI();
        }
    }

    /**
     * Aktualisiert die Anzeige der aktuellen Vokabel und des Fortschritts.
     */
    private void updateGUI() {
        if (ratingSystem.isExamFinished()) {
            promptLabel.setText("Prüfung beendet!");
            feedbackLabel.setTextFill(Color.BLUE);
            feedbackLabel.setText(
                    "Ergebnis: " + ratingSystem.getCorrectAnswers() + " von " + ratingSystem.getTotalAttempts());
            inputField.setDisable(true);
            checkButton.setDisable(true);
            restartButton.setVisible(true); // Neustart-Button anzeigen
        } else {
            ExamUnit nextUnit = ratingSystem.getNextUnit();
            String askedWord = "";
            if (nextUnit != null) {
                // Welches Wort soll abgefragt werden, basierend auf dem Modus
                if (nextUnit.getAskedLanguage().equals(currentVocabularySet.getLang1())) {
                    askedWord = nextUnit.getVocabulary().getVocab1()[0];
                } else {
                    askedWord = nextUnit.getVocabulary().getVocab2()[0];
                }
            }
            promptLabel.setText("Übersetze: '" + askedWord + "'");
        }
        progressLabel.setText(String.format("%d/%d (%d richtig)",
                ratingSystem.getTotalAttempts(),
                currentVocabularySet.getVocabularies().getLength(),
                ratingSystem.getCorrectAnswers()));
    }

    // Hilfsmethode, um leere Strings aus dem Array zu filtern
    private String[] cleanArray(String[] array) {
        return java.util.Arrays.stream(array)
                .filter(s -> s != null && !s.trim().isEmpty())
                .toArray(String[]::new);
    }
}
