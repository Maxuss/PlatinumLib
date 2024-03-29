package space.maxus.plib.modules;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.exceptions.AnnotationMissingException;
import space.maxus.plib.exceptions.Provoker;
import space.maxus.plib.json.JsonHelper;
import space.maxus.plib.lang.Localization;
import space.maxus.plib.textures.ResourcePack;
import space.maxus.plib.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * Main entrypoint for Platinum modules
 */
@SuppressWarnings({"deprecation", "DeprecatedIsStillUsed"})
public abstract class JavaModule extends JavaPlugin {
    /**
     * The {@link Module} annotation of this module
     */
    @Nullable
    @Getter
    private final Module moduleAnnotation = this.getClass().getAnnotation(Module.class);
    @Getter
    private ResourcePack resourcePack;
    @UnknownNullability
    @Getter
    private String moduleId;
    @Nullable
    @Getter
    private Localization localization;

    /**
     * Override this method to provide module enable logic
     */
    public void moduleEnable() {

    }

    /**
     * Override this method to provide module disable logic
     */
    public void moduleDisable() {

    }

    /**
     * Override this method to provide module loading logic
     */
    public void moduleLoad() {

    }

    /**
     * Reads the asset from text
     *
     * @param assetPath relative path from `assets` folder to the required resource
     * @return text of asset or null
     */
    @Nullable
    public String getAssetText(String assetPath) {
        try {
            var resource = this.getResource("assets/" + moduleId + "/" + assetPath);
            if (resource == null)
                throw new IOException("Could not find resource by asset path " + assetPath + "!");
            return Utils.readToEnd(resource);
        } catch (IOException e) {
            Utils.logError(e);
        }
        return null;
    }

    @Deprecated
    @Override
    public void onDisable() {
        PlatinumLib.logger().log(Level.INFO, String.format("Disabling Module %s", this.getClass().getCanonicalName()));

        moduleDisable();
    }

    @Deprecated
    @Override
    public void onEnable() {
        PlatinumLib.logger().log(Level.INFO, String.format("Enabling Module %s", this.getClass().getCanonicalName()));
        if (moduleAnnotation == null) {
            PlatinumLib.logger().log(Level.SEVERE, "The module " + this.getClass().getCanonicalName() + " does not have Module annotation! Prepare for crash!");
            Provoker.provoke(new AnnotationMissingException(Module.class));
        }
        moduleId = moduleAnnotation.id();
        loadLocalization();
        resourcePack = new ResourcePack(this);
        moduleEnable();
        PlatinumLib.THREAD_POOL.execute(() -> {
            resourcePack.generateResourcePack();
            resourcePack.sendResourcePack();
        });
    }

    @Deprecated
    @Override
    public void onLoad() {
        moduleLoad();
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
