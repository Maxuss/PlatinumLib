package space.maxus.plib.exceptions;

public class AnnotationMissingException extends Exception {
    public AnnotationMissingException(Class<?> annotationClass) {
        super(String.format("Annotation %s is missing on this class!", annotationClass.getCanonicalName()));
    }
}
