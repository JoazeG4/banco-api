package banco.example.banco.model.request;

import lombok.Data;

@Data
public class RequestTransacao {

    private Double valor;

    private int tipoDeConta;

    private String numeroDeConta;

    private String  conDestino;
}
