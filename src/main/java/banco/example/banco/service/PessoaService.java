package banco.example.banco.service;


import banco.example.banco.integracoes.EnderecoService;
import banco.example.banco.model.Endereco;
import banco.example.banco.model.Pessoa;
import banco.example.banco.model.TipoDeContas;
import banco.example.banco.model.request.RequestPessoa;
import banco.example.banco.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PessoaService {

    private PessoaRepository pessoaRepository;

    private EnderecoService enderecoService;

    public Pessoa salvarPessoa(RequestPessoa requestPessoa) throws Exception{

        var pessoaData = pessoaRepository.findByCpf(requestPessoa.getCpf());
        var enderecoExterno = enderecoService.executa(requestPessoa.getCep());

        if (pessoaData.isPresent()){
            throw new Exception("Pessoa já existente");
        }
        if (enderecoExterno.getCep() == null){
            throw new Exception("Cep incorreto!");
        }

        Endereco endereco = new Endereco();
        endereco.setDataDeCriacao(LocalDateTime.now());
        BeanUtils.copyProperties(enderecoExterno, endereco);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(requestPessoa.getNome());
        pessoa.setCpf(requestPessoa.getCpf());
        pessoa.setIdade(requestPessoa.getIdade());
        pessoa.setEnderecos(List.of(endereco));
        pessoa.setDataDeCriacao(LocalDateTime.now());
        pessoa.setTiposDeContas(List.of(new TipoDeContas(requestPessoa.getTipoDeConta(), LocalDateTime.now())));
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarTodasPessoas() throws Exception {
        var pessoasData = pessoaRepository.findAll();
        if (pessoasData.isEmpty())
            throw new Exception("Não há pessoas cadastrada");
        return pessoasData;
    }

    public Pessoa findByCpf(String cpf) throws Exception {
        var pessoasData = pessoaRepository.findByCpf(cpf);
        if (pessoasData.isEmpty())
            throw new Exception("Não encontrada!");
        return pessoasData.get();
    }

    public void deletarPorCpf(String cpf) throws Exception {
        var pessoasData = pessoaRepository.findByCpf(cpf);
        if (pessoasData.isEmpty())
            throw new Exception("Não encontrada!");
        pessoaRepository.deleteByCpf(cpf);
    }
}
