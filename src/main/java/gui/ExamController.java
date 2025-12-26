package gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import manager.SetManager;
import rating.ExamMode;
import rating.RatingSystem;
import setclasses.Set;
import java.io.File;

public class ExamController {

    @FXML
    private ComboBox<String> setSelectionCombo;
    @FXML
    private VBox examArea;
    @FXML
    private Label promptLabel;
    @FXML
    private Label contextLabel;
    @FXML
    private TextField inputField;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label progressLabel;

    private Set currentSet;
    private RatingSystem ratingSystem;
    private SetManager setManager = new SetManager();

    @FXML
    public void initialize() {
        refreshSetList();
    }

    @FXML
    public void refreshSetList() {
        setSelectionCombo.getItems().clear();

        // Wir nutzen den Pfad zu deinen Resources
        // Im Entwicklungsmodus (IDE) zeigt dieser Pfad auf src/main/resources/...
        File folder = new File("src/main/resources/vocab_sets");

        if (!folder.exists()) {
            folder.mkdirs(); // Erstellt den Ordner, falls er fehlt
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File f : files) {
                // Nur den Namen ohne .json hinzufügen
                setSelectionCombo.getItems().add(f.getName().replace(".json", ""));
            }
        }
    }

    @FXML
    private void handleLoadSelectedSet() {
        String selected = setSelectionCombo.getValue();
        if (selected == null)
            return;

        currentSet = setManager.loadSet(selected);
        if (currentSet != null) {
            ratingSystem = new RatingSystem(currentSet, ExamMode.LANG1_TO_LANG2);
            examArea.setVisible(true);
            feedbackLabel.setText("");
            updateUI();
        }
    }

    @FXML
    private void handleCheckAnswer() {
        if (ratingSystem == null || ratingSystem.isExamFinished())
            return;

        String answer = inputField.getText().trim();
        String expected = ratingSystem.getNextUnit().getCorrectAnswersAsString();
        System.out.println("DEBUG:");
        System.out.println("asked: " + promptLabel.getText());
        System.out.println("input: " + answer );
        System.out.println("expected: " + expected);
        boolean correct = ratingSystem.rateAndProceed(answer);

        feedbackLabel.setText(correct ? "Richtig! ✅" : "Falsch! ❌");
        inputField.clear();
        updateUI();
    }

    private void updateUI() {
        if (ratingSystem.isExamFinished()) {
            promptLabel.setText("Fertig!");
            progressLabel.setText("Ergebnis: " + ratingSystem.getCorrectAnswers() + " richtig.");
        } else {
            rating.ExamUnit unit = ratingSystem.getNextUnit();
            String askedWord = unit.getVocabulary().getVocab1()[0];

            promptLabel.setText(askedWord);
            progressLabel.setText("Vokabel: " + ratingSystem.getTotalAttempts());
        }
    }
}