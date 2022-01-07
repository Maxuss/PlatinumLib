package space.maxus.plib.registry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import space.maxus.plib.modules.JavaModule;

@SuppressWarnings("ClassCanBeRecord")
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
     * @deprecated can cause unexpected behaviour with resources. Use
     */
    @SuppressWarnings("DeprecatedIsStillUsed") // sadge
    @Deprecated
    public Identifier(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public static Identifier of(JavaModule owner, String path) {
        return new Identifier(owner.getModuleId(), path);
    }

    @Override
    public String toString() {
        return namespace+':'+path;
    }
}
