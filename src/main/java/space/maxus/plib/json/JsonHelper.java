package space.maxus.plib.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

public class JsonHelper {
    public static @NotNull Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T deserializeJson(String json) {
        return GSON.fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    public static String serializeJson(Object object) {
        return GSON.toJson(object);
    }
}
