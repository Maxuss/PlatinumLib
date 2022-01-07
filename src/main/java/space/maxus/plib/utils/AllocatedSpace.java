package space.maxus.plib.utils;

import lombok.Getter;
import lombok.Setter;

public class AllocatedSpace<T> {
    @Getter
    @Setter
    private T reserved;

    private AllocatedSpace() {
        reserved = null;
    }

    public static <V> AllocatedSpace<V> empty() {
        return new AllocatedSpace<>();
    }

    public static <V> AllocatedSpace<V> filled(V with) {
        var space = new AllocatedSpace<V>();
        space.setReserved(with);
        return space;
    }
}
