package nl.haris.backendspringboottechiteasymodel.exceptions;

public class NameTooLongException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NameTooLongException() {
        super();
    }
    public NameTooLongException(String message) {
        super(message);
    }
}
