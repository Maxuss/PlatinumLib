package space.maxus.plib.exceptions;

import org.jetbrains.annotations.NotNull;

public class AnnotationMissingException extends Exception {
    public AnnotationMissingException(@NotNull Class<?> annotationClass) {
        super(String.format("Annotation %s is missing on this class!", annotationClass.getCanonicalName()));
    }
}
