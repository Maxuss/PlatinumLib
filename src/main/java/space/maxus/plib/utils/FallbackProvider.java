package space.maxus.plib.utils;

import org.jetbrains.annotations.NotNull;

public interface FallbackProvider<I, O> {
    /**
     * Provides the {@link O} value out of consumed {@link I} value
     *
     * @param in consumed in value
     * @return provided out value
     */
    @NotNull
    O provide(I in);
}
