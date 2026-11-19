package edu.practice.cloud.exception;

/**
 * WEEK 6 CONCEPT: Custom Unchecked Domain Exception.
 * Always extends RuntimeException so callers don't need 'throws' declarations.
 */
public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(long id) {
        super("Compute job not found: " + id);
    }
}
