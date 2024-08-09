package banco.example.banco.model.response;

import lombok.Data;


@Data
public class ResponseEndereco {

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

}
