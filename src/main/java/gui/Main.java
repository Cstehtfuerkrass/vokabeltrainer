package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Lade das Layout aus der FXML-Datei
        // Stelle sicher, dass "ExamView.fxml" im gleichen Ressourcen-Pfad wie diese
        // Klasse liegt.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExamView.fxml"));
        Parent root = loader.load();

        // Optional: Hole den Controller, falls du direkt Methoden aufrufen möchtest.
        // ExamController controller = loader.getController();
        // controller.initData(...); // Beispiel: Daten an den Controller übergeben

        // Erstelle die Hauptszene mit den gewünschten Dimensionen
        Scene scene = new Scene(root, 600, 400); // Angepasste Größe für einen Vokabeltrainer

        // Konfiguriere das Hauptfenster (Stage)
        primaryStage.setTitle("Vokabeltrainer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Startet die JavaFX-Anwendung
        launch(args);
    }
}
