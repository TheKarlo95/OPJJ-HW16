package hr.fer.zemris.java.hw16.vector;

/**
 * {@code VectorArithmeticException} is the an exception which occurs when there
 * is an error while performing mathematical calculations using {@link Vector}
 * objects.
 *
 * @author Karlo Vrbić
 * @version 1.0
 * @see RuntimeException
 * @see Vector
 */
public class VectorArithmeticException extends RuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = -503461348314283083L;

    /**
     * Constructs a new {@code VectorArithmeticException} with {@code null} as
     * its detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public VectorArithmeticException() {
        super();
    }

    /**
     * Constructs a new {@code VectorArithmeticException} with the specified
     * detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param message
     *            the detail message. The detail message is saved for later
     *            retrieval by the {@link #getMessage()} method.
     */
    public VectorArithmeticException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code VectorArithmeticException} with the specified
     * detail message and cause.
     * <p>
     * Note that the detail message associated with {@code cause} is <i>not</i>
     * automatically incorporated in this {@code VectorArithmeticException}'s
     * detail message.
     *
     * @param message
     *            the detail message (which is saved for later retrieval by the
     *            {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A {@code null}value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public VectorArithmeticException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code VectorArithmeticException} with the specified
     * cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains
     * the class and detail message of {@code cause}). This constructor is
     * useful for {@code VectorArithmeticException}s that are little more than
     * wrappers for other throwables.
     *
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            {@link #getCause()} method). (A {@code null} value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     */
    public VectorArithmeticException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code VectorArithmeticException} with the specified
     * detail message, cause, suppression enabled or disabled, and writable
     * stack trace enabled or disabled.
     *
     * @param message
     *            the detail message.
     * @param cause
     *            the cause. (A {@code null} value is permitted, and indicates
     *            that the cause is nonexistent or unknown.)
     * @param enableSuppression
     *            whether or not suppression is enabled or disabled
     * @param writableStackTrace
     *            whether or not the stack trace should be writable
     */
    public VectorArithmeticException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
