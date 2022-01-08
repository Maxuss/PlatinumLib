package space.maxus.plib.textures;

import com.google.common.reflect.TypeToken;
import space.maxus.plib.PlatinumLib;
import space.maxus.plib.json.JsonHelper;
import space.maxus.plib.modules.JavaModule;
import space.maxus.plib.registry.Registry;
import space.maxus.plib.utils.Utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class ResourcePackGenerator implements Runnable {
    private final JavaModule module;
    private final String meta;
    public ResourcePackGenerator(JavaModule jm, String meta) {
        this.module = jm;
        this.meta = meta;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void run() {
        var itemsList = Registry.entries(Registry.ITEM);
        var id = module.getModuleId();
        // resolve cache path to store resourcepack data
        var rawDirectory = module.getDataFolder().toPath().resolve("rescache").resolve("raw");
        var outputPack = module.getDataFolder().toPath().resolve("rescache").resolve("PlatinumResources.zip");
        var assetFolder = rawDirectory.resolve("assets").resolve(module.getModuleId());
        var modelsDir = assetFolder.resolve("models");
        var textureDir = assetFolder.resolve("textures");
        try {
            Files.createDirectories(assetFolder);
            Files.createDirectories(modelsDir.resolve("item"));
            Files.createDirectories(textureDir.resolve("item"));
            Files.writeString(rawDirectory.resolve("pack.mcmeta"), meta);
        } catch (IOException e) {
            Utils.logError(e);
            return;
        }

        // iterate through all items and grab their models + textures
        for(var item: itemsList) {
            var modelPath = String.format("models/item/%s.json", item.getPath());
            var rawModel = module.getAssetText(modelPath);
            RawModelItem model = JsonHelper.GSON.fromJson(rawModel, new TypeToken<RawModelItem>() { }.getType());
            if(model == null) {
                PlatinumLib.logger().warning("Could not load model "+modelPath+"!");
                continue;
            }
            try {
                var path = modelsDir.resolve("item").resolve(item.getPath()+".json");
                var ignored = path.toFile().createNewFile();
                Files.writeString(path, rawModel);
            } catch (IOException e) {
                PlatinumLib.logger().warning("Could not load model "+modelPath+"!");
                Utils.logError(e);
                continue;
            }
            // get all textures from layers and copy them
            for(var layer: model.getTextures().entrySet()) {
                var value = layer.getValue();
                var path = value.split(":")[1];
                var texturePath = String.format("assets/%s/textures/%s.png", id, path);
                var baseStream = module.getResource(texturePath);
                if(baseStream == null) {
                    PlatinumLib.logger().warning("Could not load image layer for model "+modelPath+"!");
                    continue;
                }
                try {
                    var input = new BufferedInputStream(baseStream);
                    Path imgPath = textureDir;
                    var split = path.split("/");
                    var last = split[split.length - 1];
                    for(var sp: split) {
                        if(Objects.equals(sp, last)) {
                            imgPath = imgPath.resolve(sp + ".png");
                        } else imgPath = imgPath.resolve(sp);
                    }
                    var ignored = imgPath.toFile().createNewFile();
                    var output = Files.newOutputStream(imgPath);

                    int b;
                    while((b = input.read()) != -1)
                        output.write(b);

                    input.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Utils.zipDir(rawDirectory, outputPack);
        } catch (IOException e) {
            PlatinumLib.logger().severe("Could not zip platinum plugin resources into directory!");
            Utils.logError(e);
        }
    }
}
