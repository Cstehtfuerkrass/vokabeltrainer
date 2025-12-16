package setclasses;
import com.google.gson.*;
import java.lang.reflect.Type;

public class SetAdapter implements JsonSerializer<Set>, JsonDeserializer<Set> {

    @Override
    public JsonElement serialize(Set src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject json = new JsonObject();
        json.addProperty("name", src.getName());
        json.addProperty("lang1", src.getLang1());
        json.addProperty("lang2", src.getLang2());

        // pairs als einfache Liste speichern
        JsonArray arr = new JsonArray();
        for (int i = 0; i < src.pairs.getLength(); i++) {
            String[] pair = (String[]) src.pairs.getItem(i);

            JsonArray p = new JsonArray();
            p.add(pair[0]);
            p.add(pair[1]);

            arr.add(p);
        }

        json.add("pairs", arr);
        return json;
    }

    @Override
    public Set deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();

        Set set = new Set(
                obj.get("name").getAsString(),
                obj.get("lang1").getAsString(),
                obj.get("lang2").getAsString()
        );

        JsonArray arr = obj.get("pairs").getAsJsonArray();
        for (JsonElement el : arr) {
            JsonArray p = el.getAsJsonArray();
            set.addPair(new String[]{ p.get(0).getAsString(), p.get(1).getAsString() });
        }

        return set;
    }
}
