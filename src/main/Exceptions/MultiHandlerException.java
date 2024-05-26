package main.Exceptions;

/**
 *
 * @author user
 */
public class MultiHandlerException extends IllegalStateException {
    
    public MultiHandlerException() {
        super("Handler already in use");
    }
}
