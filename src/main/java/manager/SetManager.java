package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import setclasses.Set;

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
    private final String BASE_PATH = "src\\main\\resources";

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
        Path filePath = getFilePath(set.getName());

        // HIER: VOR dem Speichern die Daten in das GSON-Array kopieren
        set.copyToPlainArray();

        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(set, writer);
            System.out.println("Set erfolgreich gespeichert unter: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern des Sets '" + set.getName() + "': " + e.getMessage());
            return false;
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