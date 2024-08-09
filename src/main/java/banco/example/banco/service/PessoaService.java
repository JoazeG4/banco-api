package banco.example.banco.service;

import banco.example.banco.model.Pessoa;
import banco.example.banco.model.request.RequestPessoa;
import banco.example.banco.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PessoaService {

    private PessoaRepository pessoaRepository;

    private EnderencoService enderencoService;

    public Pessoa salvarPessoa(Pessoa requestPessoa) throws Exception{
        var pessoaData = pessoaRepository.findByCpf(requestPessoa.getCpf());
        if (pessoaData.isPresent()){
            throw new Exception("Pessoa já existente");
        }
        requestPessoa.setDataDeCriacao(LocalDateTime.now());
        return pessoaRepository.save(requestPessoa);
    }
}
