package banco.example.banco.model.request;

import banco.example.banco.model.TipoDeContas;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class RequestPessoa {

    private String nome;
    private String cpf;
    private Integer idade;
    private String cep;
    private List<TipoDeContas> tiposDeContas;
}
