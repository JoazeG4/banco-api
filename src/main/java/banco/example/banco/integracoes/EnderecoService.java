package banco.example.banco.integracoes;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class EnderecoService {

    private EnderecoFeign enderecoFeign;

    private Validator validator;

    public EnderecoResponse executa(String cep) {
        var enderecoExterno = enderecoFeign.buscaEnderecoCep(cep);
        Set<ConstraintViolation<EnderecoResponse>> invalidacoes = validator.validate(enderecoExterno);
        if (!invalidacoes.isEmpty())
            throw new ConstraintViolationException(invalidacoes);
        return enderecoExterno;
    }
}
