package space.maxus.plib.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {
    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T serializeJson(String json) {
        return GSON.fromJson(json, new TypeToken<T>() { }.getType());
    }

    public static String deserializeJson(Object object) {
        return GSON.toJson(object);
    }
}
