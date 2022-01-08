package space.maxus.plib.textures;

import lombok.Getter;

import java.util.HashMap;

public class RawModelItem {
    @Getter
    private String parent;
    @Getter
    private HashMap<String, String> textures;
}
