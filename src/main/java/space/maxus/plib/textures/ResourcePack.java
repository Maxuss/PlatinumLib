package space.maxus.plib.textures;

import net.minecraft.SharedConstants;
import net.minecraft.server.packs.PackType;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.modules.JavaModule;

import java.util.Objects;

public class ResourcePack {
    private final JavaModule module;

    public ResourcePack(JavaModule owner) {
        this.module = owner;
    }

    public String getPackMeta() {
        var format = PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion());
        return String.format("{\"pack\": {\"pack_format\": " + format + ",\"description\":\"%s\"}}", Objects.requireNonNull(module.getModuleAnnotation()).description());
    }

    public boolean cacheExists() {
        return module.getDataFolder().toPath().resolve("rescache").resolve("PlatinumResources.zip").toFile().exists();
    }

    public void generateResourcePack() {
        var generator = new ResourcePackGenerator(module, getPackMeta());
        if(cacheExists())
            PlatinumLib.THREAD_POOL.execute(generator);
        else generator.run();
    }

    public void sendResourcePack() {
        var sender = new ResourcePackSender(module.getDataFolder().toPath().resolve("rescache").resolve("PlatinumResources.zip").toFile());
        if(cacheExists()) PlatinumLib.THREAD_POOL.execute(sender);
        else sender.run();
    }
}
