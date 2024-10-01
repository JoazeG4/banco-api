package banco.example.banco.exceptions;

public class ExistingPersonException extends RuntimeException {

    public ExistingPersonException(){
        super("Existing person!");
    }

    public ExistingPersonException(String mensagem){
        super(mensagem);
    }
}
