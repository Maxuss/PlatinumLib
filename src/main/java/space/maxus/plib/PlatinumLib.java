package space.maxus.plib;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public final class PlatinumLib extends JavaPlugin {
    public static PlatinumLib INSTANCE;

    public static Logger logger() {
        return INSTANCE.getLogger();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        INSTANCE = null;
    }
}
