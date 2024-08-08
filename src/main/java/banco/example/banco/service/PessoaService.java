package banco.example.banco.service;

import banco.example.banco.model.Pessoa;
import banco.example.banco.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PessoaService {

    private PessoaRepository pessoaRepository;

    public void salvarPessoa(Pessoa pessoa) throws Exception{
        var pessoaData = pessoaRepository.findByCpf(pessoa.getCpf());
        if (pessoaData.isPresent()){
            throw new Exception("Pessoa já existente");
        }
        pessoaRepository.save(pessoa);
    }
}
