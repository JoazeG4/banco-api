package banco.example.banco.integracoes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnderecoService {

    private EnderecoFeign enderecoFeign;

    public EnderecoResponse executa(String cep){
        return enderecoFeign.buscaEnderecoCep(cep);
    }
}
