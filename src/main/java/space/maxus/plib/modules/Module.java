package space.maxus.plib.modules;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation, that marks the module as existing
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    /**
     * ID of module
     */
    @NotNull String id();
}
