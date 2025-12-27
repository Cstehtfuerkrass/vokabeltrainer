package gui;

import java.net.URL;
import java.util.ResourceBundle;
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
    private ComboBox<rating.ExamMode> modeComboBox;
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
    public void initialize() { // <--- Keine Parameter (URL/ResourceBundle)
        if (modeComboBox != null) {
            modeComboBox.getItems().setAll(rating.ExamMode.values());
            modeComboBox.setValue(rating.ExamMode.LANG1_TO_LANG2);
            System.out.println("Modi geladen!"); // Test-Ausgabe für die Konsole
        }
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
        // 1. Den Namen des gewählten Sets aus der ComboBox holen
        String selectedSetName = setSelectionCombo.getValue();

        // 2. Den gewählten Modus aus der NEUEN ComboBox holen
        rating.ExamMode selectedMode = modeComboBox.getValue();

        // 3. Sicherheitscheck: Wurde beides ausgewählt?
        if (selectedSetName == null || selectedMode == null) {
            progressLabel.setText("Fehler: Bitte Set und Modus wählen!");
            return;
        }

        try {
            // 4. Das Vokabelset über den Manager laden
            setclasses.Set selectedSet = setManager.loadSet(selectedSetName);

            if (selectedSet != null) {
                // 5. Das RatingSystem mit dem Set UND dem gewählten Modus initialisieren
                this.ratingSystem = new rating.RatingSystem(selectedSet, selectedMode);

                // 6. UI vorbereiten
                progressLabel.setText("Set '" + selectedSetName + "' geladen. Viel Erfolg!");

                // Das Panel mit der Abfrage sichtbar machen
                examArea.setVisible(true);

                // Das erste Wort anzeigen
                updateUI();

                // Optional: Eingabefeld fokussieren
                inputField.requestFocus();
            }
        } catch (Exception e) {
            progressLabel.setText("Fehler beim Laden des Sets: " + e.getMessage());
            e.printStackTrace();
        }

        setSelectionCombo.setDisable(true);
        modeComboBox.setDisable(true);
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