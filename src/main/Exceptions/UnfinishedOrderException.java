package main.Exceptions;

/**
 *
 * @author Rafael Inigo
 */
public class UnfinishedOrderException extends UnexpectedOrderException {

    /**
     * Creates a new instance of <code>UnfinishedOrderException</code> without
     * detail message.
     */
    public UnfinishedOrderException() {
        
    }

    /**
     * Constructs an instance of <code>UnfinishedOrderException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnfinishedOrderException(String msg) {
        super(msg);
    }
}
