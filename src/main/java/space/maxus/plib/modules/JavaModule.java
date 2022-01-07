package space.maxus.plib.modules;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.exceptions.Provoker;
import space.maxus.plib.json.JsonHelper;
import space.maxus.plib.lang.Localization;
import space.maxus.plib.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public abstract class JavaModule extends JavaPlugin {
    @Nullable
    public Module moduleAnnotation = this.getClass().getAnnotation(Module.class);
    @UnknownNullability
    private String moduleId;
    @Nullable
    private Localization localization;

    @UnknownNullability
    public String getModuleId() {
        return moduleId;
    }

    @Nullable
    public Localization getLocalization() {
        return localization;
    }

    @Nullable
    public String getAssetText(String assetPath) {
        try {
            var resource = this.getResource("assets/" + moduleId + "/" + assetPath);
            if(resource == null)
                throw new IOException("Could not find resource by asset path "+assetPath+"!");
            return Utils.readToEnd(resource);
        } catch (IOException e) {
            Utils.logError(e);
        }
        return null;
    }

    @Override
    public final void onDisable() {
        PlatinumLib.logger().log(Level.INFO, String.format("Disabling Module %s", this.getClass().getCanonicalName()));
    }

    @Override
    public final void onEnable() {
        PlatinumLib.logger().log(Level.INFO, String.format("Enabling Module %s", this.getClass().getCanonicalName()));
    }

    @Override
    public final void onLoad() {
        PlatinumLib.logger().log(Level.INFO, String.format("Loading Module %s", this.getClass().getCanonicalName()));
        if (moduleAnnotation == null) {
            PlatinumLib.logger().log(Level.SEVERE, "The module " + this.getClass().getCanonicalName() + " does not have Module annotation! Prepare for crash!");
            Provoker.provoke();
        }
        moduleId = moduleAnnotation.id();
    }

    private void loadLocalization() {
        var languages = getAssetText("lang/languages.json");
        if (languages == null) return;
        List<String> langs = JsonHelper.deserializeJson(languages);
        var locales = langs.stream().map(Utils::getLocaleFromString).toList();
        localization = new Localization(locales);
        localization.loadLanguages(this);
    }
}
