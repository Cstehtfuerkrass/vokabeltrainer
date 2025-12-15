package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import setclasses.Set;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Verwaltet das Speichern (Serialisieren) und Laden (Deserialisieren)
 * von Set-Objekten mithilfe der GSON-Bibliothek.
 */
public class SetManager {

    // GSON-Instanz für die Serialisierung/Deserialisierung.
    // GsonBuilder wird verwendet, um die JSON-Ausgabe besser lesbar zu machen
    // (pretty printing).
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Pfad zum Basisverzeichnis, in dem die Set-Dateien gespeichert werden sollen.
    private final String BASE_PATH = "src\\main\\resources";

    /**
     * Konstruktor, der sicherstellt, dass das Basisverzeichnis existiert.
     */
    public SetManager() {
        // Erstellt das Verzeichnis, falls es noch nicht existiert.
        // Das ist wichtig, bevor wir versuchen, Dateien zu speichern.
        try {
            java.nio.file.Files.createDirectories(Paths.get(BASE_PATH));
            System.out.println("Basisverzeichnis '" + BASE_PATH + "' existiert oder wurde erstellt.");
        } catch (IOException e) {
            System.err.println("Fehler beim Erstellen des Basisverzeichnisses: " + e.getMessage());
        }
    }

    /**
     * Gibt den vollständigen Pfad zur JSON-Datei für das angegebene Set zurück.
     * 
     * @param setName Der Name des Sets.
     * @return Das Path-Objekt zur Set-Datei.
     */
    private Path getFilePath(String setName) {
        // Wir verwenden den Set-Namen als Dateinamen und fügen die Endung .json hinzu.
        return Paths.get(BASE_PATH, setName.replaceAll("\\s+", "_") + ".json");
    }

    // -----------------------------------------------------------------
    // 1. Speichern (Serialisierung)
    // -----------------------------------------------------------------

    /**
     * Speichert (serialisiert) ein Set-Objekt in einer JSON-Datei.
     * Der Dateiname basiert auf dem Namen des Sets.
     * 
     * @param set Das zu speichernde Set-Objekt.
     * @return true bei Erfolg, false bei einem Fehler.
     */
    public boolean saveSet(Set set) {
        Path filePath = getFilePath(set.getName());

        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            // Konvertiert das Java-Objekt (Set) in seine JSON-String-Repräsentation
            gson.toJson(set, writer);
            System.out.println("Set erfolgreich gespeichert unter: " + filePath.toAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern des Sets '" + set.getName() + "': " + e.getMessage());
            return false;
        }
    }

    // -----------------------------------------------------------------
    // 2. Laden (Deserialisierung)
    // -----------------------------------------------------------------

    /**
     * Lädt (deserialisiert) ein Set-Objekt aus einer JSON-Datei.
     * 
     * @param setName Der Name des zu ladenden Sets.
     * @return Das geladene Set-Objekt oder null, wenn die Datei nicht existiert
     *         oder ein Fehler auftritt.
     */
    public Set loadSet(String setName) {
        Path filePath = getFilePath(setName);

        if (!java.nio.file.Files.exists(filePath)) {
            System.err.println("Fehler: Set-Datei nicht gefunden unter: " + filePath.toAbsolutePath());
            return null;
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            // Konvertiert den JSON-String aus der Datei zurück in ein Java-Objekt (Set)
            Set set = gson.fromJson(reader, Set.class);
            System.out.println("Set erfolgreich geladen: " + set.getName());
            return set;
        } catch (IOException e) {
            System.err.println("Fehler beim Laden des Sets '" + setName + "': " + e.getMessage());
            return null;
        }
    }
}