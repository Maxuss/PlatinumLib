package space.maxus.plib.modules;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.exceptions.Provoker;
import space.maxus.plib.utils.Utils;

import java.io.IOException;
import java.util.logging.Level;

public abstract class JavaModule extends JavaPlugin {
    @Nullable
    public Module moduleAnnotation = this.getClass().getAnnotation(Module.class);

    @Nullable
    public String getAssetText(String assetPath) {
        try {
            return Utils.readToEnd(this.getResource("assets/"+moduleId+"/"+assetPath));
        } catch (IOException e) {
            Utils.logError(e);
        }
        return null;
    }

    public String moduleId;

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
        if(moduleAnnotation == null) {
            PlatinumLib.logger().log(Level.SEVERE, "The module " + this.getClass().getCanonicalName() + " does not have Module annotation! Prepare for crash!");
            Provoker.provoke();
        }
        moduleId = moduleAnnotation.id();
    }

    private void loadLocalization() {

    }
}
