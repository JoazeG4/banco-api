package banco.example.banco.exceptions;

public class AccountInvalidException extends RuntimeException {
    public AccountInvalidException(){
        super("Account invalid!");
    }

    public AccountInvalidException(String message){
        super(message);
    }
}
