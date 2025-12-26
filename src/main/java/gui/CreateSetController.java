package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import setclasses.Set;
import setclasses.Languages;
import manager.SetManager;

public class CreateSetController {

    @FXML
    private TextField setNameField;
    @FXML
    private ComboBox<Languages> lang1Combo;
    @FXML
    private ComboBox<Languages> lang2Combo;
    @FXML
    private TextField vocab1Field;
    @FXML
    private TextField vocab2Field;
    @FXML
    private ListView<String> vocabListView;
    @FXML
    private Label statusLabel;

    private Set newSet;
    private SetManager setManager = new SetManager();

    @FXML
    public void initialize() {
        lang1Combo.setItems(FXCollections.observableArrayList(Languages.values()));
        lang2Combo.setItems(FXCollections.observableArrayList(Languages.values()));
        lang1Combo.getSelectionModel().select(Languages.DE);
        lang2Combo.getSelectionModel().select(Languages.EN);
    }

    @FXML
    private void handleAddVocabulary() {
        // 1. Die Eingaben aus den Vokabel-Feldern holen und bei Komma trennen
        // Das \\s*,\\s* sorgt dafür, dass Leerzeichen vor/nach dem Komma ignoriert
        // werden
        String[] v1Array = vocab1Field.getText().split("\\s*,\\s*");
        String[] v2Array = vocab2Field.getText().split("\\s*,\\s*");

        // Validierung: Sind die Vokabel-Felder leer?
        if (v1Array[0].isEmpty() || v2Array[0].isEmpty()) {
            statusLabel.setText("Bitte Vokabeln eingeben!");
            return;
        }

        // 2. Initialisierung des Sets beim ersten Eintrag
        if (newSet == null) {
            String name = setNameField.getText().trim(); // Hier holen wir den Namen!

            if (name.isEmpty()) {
                statusLabel.setText("Zuerst einen Namen für das Set eingeben!");
                return;
            }

            // Jetzt ist 'name' verfügbar und wir können das Set erstellen
            newSet = new Set(name, lang1Combo.getValue(), lang2Combo.getValue());

            // UI sperren, damit Name und Sprachen während der Erstellung nicht geändert
            // werden
            setNameField.setDisable(true);
            lang1Combo.setDisable(true);
            lang2Combo.setDisable(true);
        }

        // 3. Die Vokabel-Arrays zum Set hinzufügen
        newSet.addVocab(v1Array, v2Array);

        // 4. In der Liste anzeigen (schön formatiert mit Kommas)
        vocabListView.getItems().add(String.join(", ", v1Array) + "  ↔  " + String.join(", ", v2Array));

        // 5. Felder leeren und Fokus zurück auf das erste Feld
        vocab1Field.clear();
        vocab2Field.clear();
        vocab1Field.requestFocus();
        statusLabel.setText("Vokabel hinzugefügt.");
    }

    @FXML
    private void handleDeleteSelected() {
        int idx = vocabListView.getSelectionModel().getSelectedIndex();
        if (idx != -1 && newSet != null) {
            newSet.getVocabularies().delete(idx);
            vocabListView.getItems().remove(idx);
        }
    }

    @FXML
    private void handleSaveSet() {
        if (newSet != null) {
            if (setManager.saveSet(newSet)) {
                statusLabel.setText("Gespeichert!");
                reset();
            }
        }
    }

    private void reset() {
        newSet = null;
        setNameField.clear();
        setNameField.setDisable(false);
        vocabListView.getItems().clear();
    }
}