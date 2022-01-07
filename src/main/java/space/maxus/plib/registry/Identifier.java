package space.maxus.plib.registry;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Identifier {
    @Getter
    private final String namespace;
    @Getter
    private final String path;

    /**
     * Constructs a new identifier
     * @param namespace namespace eof identifier
     * @param path path of identifier
     */
    @Deprecated
    public Identifier(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }
}
