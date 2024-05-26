package main.Exceptions;

/**
 *
 * @author user
 */
public class UnexpectedOrderException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>UnfinishedOrderException</code> without
     * detail message.
     */
    public UnexpectedOrderException() {
        super("this is not what i ordered");
    }

    /**
     * Constructs an instance of <code>UnfinishedOrderException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnexpectedOrderException(String msg) {
        super(msg);
    }
    
}
