import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class SetStorage {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Set.class, new SetAdapter())
            .setPrettyPrinting()
            .create();

    public static void saveSet(Set set, String filepath) {
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(set, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set loadSet(String filepath) {
        try (FileReader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, Set.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
