import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class SetStorage {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void saveSet(Set set, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(set, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set loadSet(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Set.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
