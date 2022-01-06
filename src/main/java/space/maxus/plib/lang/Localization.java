package space.maxus.plib.lang;

import space.maxus.plib.json.JsonHelper;
import space.maxus.plib.modules.JavaModule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Localization {
    private final List<Locale> locales;

    private final HashMap<Locale, HashMap<String, String>> languages = new HashMap<>();

    public Localization(List<Locale> languages) {
        this.locales = languages;
    }

    public void loadLanguages(JavaModule owner) {
        for(var locale: locales.parallelStream().toList()) {
            languages.put(locale, JsonHelper.serializeJson(owner.getAssetText("lang/"+locale.toString()+".json")));
        }
    }
}