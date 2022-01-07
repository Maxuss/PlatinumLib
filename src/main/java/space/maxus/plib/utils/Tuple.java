package space.maxus.plib.utils;

import org.jetbrains.annotations.Nullable;

/**
 * An immutable structure, used for containing two values of two types
 *
 * @param <F>    first type
 * @param <S>    second type
 * @param first  first element in tuple
 * @param second second element in tuple
 */
public record Tuple<F, S>(@Nullable F first, @Nullable S second) {
}
