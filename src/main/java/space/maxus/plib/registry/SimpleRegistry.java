package space.maxus.plib.registry;

import org.jetbrains.annotations.NotNull;
import space.maxus.plib.PlatinumLib;

import java.util.HashMap;
import java.util.List;

public abstract sealed class SimpleRegistry<T> implements Registry<T> permits ItemRegistry {
    @NotNull
    protected final HashMap<Identifier, T> registrants = new HashMap<>();

    @Override
    public void performRegistration(Identifier id, T value) {
        if (registrants.containsKey(id)) {
            PlatinumLib.logger().warning("Re-registering an element with id " + id + "!");
        }

        registrants.put(id, value);

        externalRegistration(id, value);
    }

    @Override
    public T find(Identifier id) {
        if (!registrants.containsKey(id))
            return null;

        return externalFind(id);
    }

    @Override
    public List<Identifier> identifiers() {
        return this.registrants.keySet().stream().toList();
    }

    protected abstract void externalRegistration(Identifier id, T value);

    protected abstract T externalFind(Identifier id);
}
