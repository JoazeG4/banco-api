package banco.example.banco.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class RequestCpf {

    @CPF(message = "CPF Inválido!")
    private String cpf;
}
