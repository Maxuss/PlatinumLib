package space.maxus.plib.lang;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.maxus.plib.json.JsonHelper;
import space.maxus.plib.modules.JavaModule;
import space.maxus.plib.utils.ComponentUtils;
import space.maxus.plib.utils.FallbackProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Class used for operations with localizing strings and components server side
 * <br/> <br/>
 * NOTE: if you want to localize string client-size, please use {@link net.kyori.adventure.text.TranslatableComponent} instead!
 * You can create one with {@link Component#translatable(String)}, where string is key to translated string
 * @deprecated see note above
 */
@SuppressWarnings("DeprecatedIsStillUsed") // sadge
@Deprecated
public class Localization {
    private final List<Locale> locales;

    private final HashMap<Locale, HashMap<String, String>> languages = new HashMap<>();

    public Localization(List<Locale> languages) {
        this.locales = languages;
    }

    /**
     * Loads languages for current localization
     *
     * @param owner owner module, to load languages for
     */
    public void loadLanguages(@NotNull JavaModule owner) {
        for (var locale : locales.parallelStream().toList()) {
            languages.put(locale, JsonHelper.deserializeJson(owner.getAssetText("lang/" + locale.toString() + ".json")));
        }
    }

    /**
     * Localizes string to provided locale if it was loaded,<br/>
     * else executes the fallback provider
     *
     * @param locale           locale to which component should be localized
     * @param str              string to be localized
     * @param fallbackProvider fallback provider that takes non-localized string and localizes it
     * @return localized string, or string processed through fallback provider
     */
    public String localizeStringOr(Locale locale, String str, @NotNull FallbackProvider<String, String> fallbackProvider) {
        if (!this.languages.containsKey(locale))
            return fallbackProvider.provide(str);
        var locs = this.languages.get(locale);
        if (!locs.containsKey(str))
            return fallbackProvider.provide(str);
        return locs.get(str);
    }


    /**
     * Localizes string to provided locale if it was loaded,<br/>
     * else returns default string
     *
     * @param locale        locale to which string should be localized
     * @param str           string to be localized
     * @param defaultString default string or null
     * @return localized string or default
     */
    public String localizeStringOrGet(Locale locale, String str, @Nullable String defaultString) {
        return localizeStringOr(locale, str, (in) -> defaultString == null ? str : defaultString);
    }

    /**
     * Localizes string to provided locale if it was loaded, <br/>
     * else returns version localized to fallback locale
     *
     * @param locale         locale to which string should be localized
     * @param str            string to be localized
     * @param fallbackLocale fallback locale
     * @return string localized to provided locale or fallback locale
     */
    public String localizeStringOrGet(Locale locale, String str, @NotNull Locale fallbackLocale) {
        return localizeStringOr(locale, str, (in) -> localizeStringOrGet(fallbackLocale, str, str));
    }

    /**
     * Localizes component to provided locale if it was loaded,<br/>
     * else executes the fallback provider
     *
     * @param locale           locale to which component should be localized
     * @param comp             component to be localized
     * @param fallbackProvider fallback provider that takes non-localized component and localizes it
     * @return localized component, or component processed through fallback provider
     */
    public @NotNull Component localizeComponentOr(Locale locale, Component comp, @NotNull FallbackProvider<Component, Component> fallbackProvider) {
        var raw = ComponentUtils.serializeComponent(comp);
        var plain = ChatColor.stripColor(raw);
        var colors = raw.replace(plain, "");
        var localizedStr = localizeStringOr(locale, plain, (in) -> ComponentUtils.serializeComponent(fallbackProvider.provide(ComponentUtils.deserializeComponent(in))));
        return ComponentUtils.deserializeComponent(colors + localizedStr);
    }

    /**
     * Localizes component to provided locale if it was loaded,<br/>
     * else returns default component
     *
     * @param locale           locale to which component should be localized
     * @param comp             component to be localized
     * @param defaultComponent default component or null
     * @return localized component or default
     */
    public @NotNull Component localizeComponentOrGet(Locale locale, Component comp, @Nullable Component defaultComponent) {
        return localizeComponentOr(locale, comp, (in) -> defaultComponent == null ? comp : defaultComponent);
    }

    /**
     * Localizes component to provided locale if it was loaded, <br/>
     * else returns version localized to fallback locale
     *
     * @param locale         locale to which component should be localized
     * @param comp           component to be localized
     * @param fallbackLocale fallback locale
     * @return component localized to provided locale or fallback locale
     */
    public @NotNull Component localizeComponentOrGet(Locale locale, Component comp, @NotNull Locale fallbackLocale) {
        return localizeComponentOr(locale, comp, (in) -> localizeComponentOrGet(fallbackLocale, comp, comp));
    }

    /**
     * Localizes component for player or returns non-localized component
     *
     * @param player player to which the component will be localized
     * @param comp   component to be localized
     * @return localized component
     */
    public @NotNull Component localizeComponent(@NotNull Player player, Component comp) {
        return localizeComponentOrGet(player.locale(), comp, comp);
    }

    /**
     * Localizes string for player or returns non-localized string
     *
     * @param player player to which the string will be localized
     * @param str    string to be localized
     * @return localized string
     */
    public String localizeString(@NotNull Player player, String str) {
        return localizeStringOrGet(player.locale(), str, str);
    }
}