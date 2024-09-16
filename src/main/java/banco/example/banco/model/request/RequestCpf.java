package banco.example.banco.model.request;

import lombok.Getter;

@Getter
public class RequestCpf {

    private final String cpf;

    public RequestCpf(String cpf){
        this.cpf = cpf;
    }
}
