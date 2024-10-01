package banco.example.banco.exceptions;

public class ExistingAccountException extends RuntimeException {

    public ExistingAccountException(){
        super("Existing Account");
    }
    public ExistingAccountException(String mensagem){
        super(mensagem);
    }
}
