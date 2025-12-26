package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import setclasses.Set;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Verwaltet das Speichern und Laden von Set-Objekten mithilfe der
 * GSON-Bibliothek.
 */
public class SetManager {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String BASE_PATH = "src/main/resources/vocab_sets/";

    public SetManager() {
        try {
            // Hier sollte der Pfad zur Laufzeitumgebung passen
            Files.createDirectories(Paths.get(BASE_PATH));
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen des Basisverzeichnisses: " + e.getMessage());
        }
    }

    private Path getFilePath(String setName) {
        return Paths.get(BASE_PATH, setName.replaceAll("\\s+", "_") + ".json");
    }

    public boolean saveSet(Set set) {
        // 1. Ordner prüfen/erstellen
        File directory = new File(BASE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // 2. Den Pfad für die spezifische Datei zusammenbauen
        // Beispiel: "src/main/resources/vocab_sets/" + "Vokabeln1" + ".json"
        File file = new File(BASE_PATH + set.getName() + ".json");
        
        // 3. Mit GSON in die Datei schreiben
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
                    .setPrettyPrinting() // Macht das JSON lesbar
                    .create();
            
            gson.toJson(set, writer); // Schreibt das Set-Objekt als JSON
            return true; // Speichern erfolgreich
            
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false; // Fehler beim Speichern
        }
    }

    public Set loadSet(String setName) {
        Path filePath = getFilePath(setName);

        if (!Files.exists(filePath)) {
            System.err.println("Fehler: Set-Datei nicht gefunden unter: " + filePath.toAbsolutePath());
            return null;
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            Set set = gson.fromJson(reader, Set.class);

            if (set != null) {
                // HIER: NACH dem Laden die Daten zurück in das DynArray kopieren
                set.copyToDynArray();
            }

            System.out.println("Set erfolgreich geladen: " + set.getName());
            return set;
        } catch (IOException e) {
            System.err.println("Fehler beim Laden des Sets '" + setName + "': " + e.getMessage());
            return null;
        }
    }
}