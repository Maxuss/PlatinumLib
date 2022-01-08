package space.maxus.plib;

import org.jetbrains.annotations.NotNull;
import space.maxus.plib.model.ItemModel;
import space.maxus.plib.modules.JavaModule;
import space.maxus.plib.modules.Module;
import space.maxus.plib.registry.Identifier;
import space.maxus.plib.registry.Registry;
import space.maxus.plib.settings.FoodSettings;
import space.maxus.plib.settings.ItemSettings;
import space.maxus.plib.settings.Rarity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@SuppressWarnings("deprecation")
@Module(
        id = "platinum",
        description = "Core library for platinum plugins"
)
public final class PlatinumLib extends JavaModule {
    public static PlatinumLib INSTANCE;

    public static boolean PRODUCTION_MODE = false;

    public static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    public static @NotNull Logger logger() {
        return INSTANCE.getLogger();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        super.onEnable();
    }

    @Override
    public void moduleEnable() {
    }

    @Override
    public void onDisable() {
        super.onDisable();
        INSTANCE = null;
    }
}
