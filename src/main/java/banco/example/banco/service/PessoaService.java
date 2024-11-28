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

        return Optional.of(pessoaRepository.save(new Pessoa.Builder()
                .nome(requestPessoa.getNome())
                .cpf(requestPessoa.getCpf())
                .idade(requestPessoa.getIdade())
                .endereco(List.of(endereco))
                .dataDeCriacao(LocalDateTime.now())
                .tiposDeContas(List.of(new TipoDeContas(requestPessoa.getTipoDeConta(), LocalDateTime.now())))
                .build()));
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

    @Transactional
    public void deleteByCpf(@NotNull RequestCpf requestCpf) throws PersonNotFoundException {
        pessoaRepository.findByCpf(requestCpf.getCpf())
                .orElseThrow(PersonNotFoundException::new);
        pessoaRepository.deleteByCpf(requestCpf.getCpf());
    }
}
