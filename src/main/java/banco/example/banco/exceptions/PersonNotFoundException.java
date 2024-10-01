package banco.example.banco.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(){
        super("Person not found!");
    }

    public PersonNotFoundException(String mensagem){
        super(mensagem);
    }
}
