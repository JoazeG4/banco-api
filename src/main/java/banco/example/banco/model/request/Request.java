package banco.example.banco.model.request;

import lombok.Data;

@Data
public class Request {

    private Double valor;
    private String tipoDeConta;
    private String numeroDeConta;
    private String  conDestino;
}
