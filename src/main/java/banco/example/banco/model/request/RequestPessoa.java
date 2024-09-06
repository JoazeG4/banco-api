package banco.example.banco.model.request;

import lombok.Getter;


@Getter
public class RequestPessoa {

    private String nome;
    private String cpf;
    private Integer idade;
    private String cep;
    private Integer tipoDeConta;
}