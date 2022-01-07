package space.maxus.plib;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class PlatinumLib extends JavaPlugin {
    public static PlatinumLib INSTANCE;

    public static @NotNull Logger logger() {
        return INSTANCE.getLogger();
    }


    public void TEST() {

    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        TEST();
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
    }
}
