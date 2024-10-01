package banco.example.banco.exceptions;

public class NoPersonsFoundException extends RuntimeException {

    public NoPersonsFoundException(){
        super("No persons found!");
    }
    public NoPersonsFoundException(String message) {
        super(message);
    }
}
