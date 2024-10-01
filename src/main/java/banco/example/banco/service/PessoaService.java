package banco.example.banco.service;


import banco.example.banco.exceptions.ExistingPersonException;
import banco.example.banco.exceptions.NoPersonsFoundException;
import banco.example.banco.exceptions.PersonNotFoundException;
import banco.example.banco.integracoes.EnderecoService;
import banco.example.banco.model.Endereco;
import banco.example.banco.model.Pessoa;
import banco.example.banco.model.TipoDeContas;
import banco.example.banco.model.request.RequestCpf;
import banco.example.banco.model.request.RequestPessoa;
import banco.example.banco.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaService {

    private PessoaRepository pessoaRepository;

    private EnderecoService enderecoService;

    @Transactional
    public Optional<Pessoa> savePerson(@NotNull RequestPessoa requestPessoa) throws ExistingPersonException {
        var pessoaData = pessoaRepository.findByCpf(requestPessoa.getCpf());
        var enderecoExterno = enderecoService.executa(requestPessoa.getCep());

        if (pessoaData.isPresent()) return Optional.empty();

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
        return Optional.of(pessoaRepository.save(pessoa));
    }

    public List<Pessoa> listAllPeople() throws NoPersonsFoundException {
        var ListPeopleData = pessoaRepository.findAll();
        if (ListPeopleData.isEmpty()) throw new NoPersonsFoundException();
        return ListPeopleData;
    }

    public Pessoa findByCpf(@NotNull RequestCpf requestCpf) throws PersonNotFoundException {
        return pessoaRepository.findByCpf(requestCpf.getCpf())
                .orElseThrow(PersonNotFoundException::new);
    }

    public void deleteByCpf(@NotNull RequestCpf requestCpf) throws PersonNotFoundException {
        pessoaRepository.findByCpf(requestCpf.getCpf())
                .orElseThrow(PersonNotFoundException::new);
        pessoaRepository.deleteByCpf(requestCpf.getCpf());
    }
}
