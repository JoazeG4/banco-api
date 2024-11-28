package banco.example.banco.service;

import banco.example.banco.exceptions.NoPersonsFoundException;
import banco.example.banco.exceptions.PersonNotFoundException;
import banco.example.banco.integracoes.EnderecoResponse;
import banco.example.banco.integracoes.EnderecoService;
import banco.example.banco.model.Endereco;
import banco.example.banco.model.Pessoa;
import banco.example.banco.model.TipoDeContas;
import banco.example.banco.model.request.RequestCpf;
import banco.example.banco.model.request.RequestPessoa;
import banco.example.banco.repository.PessoaRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoService enderecoService;


    //Function savePerson
    @Test
    @DisplayName("Deve retornar vazio (false) do data-base para assim salvar uma pessoa")
    public void deveRetornaVazioDoDataBase() {
        var requestPessoa = new RequestPessoa();
        requestPessoa.setCpf("000000000000");
        when(pessoaRepository.findByCpf(requestPessoa.getCpf())).thenReturn(Optional.empty());
        var pessoa = pessoaRepository.findByCpf(requestPessoa.getCpf());

        Assertions.assertTrue(pessoa.isEmpty());
    }

    @Test
    @DisplayName("Deve retorna um optional vazio quando houver uma pessoa já existente no data-base")
    public void deverRetornaUmOptionalHouverUmaPessoaJaExistenteNoDataBase() {
        var requestPessoa = new RequestPessoa();
        requestPessoa.setCpf("000000000000");
        var pessoaData = this.pessoaData(this.enderecoExterno());
        when(pessoaRepository.findByCpf(requestPessoa.getCpf())).thenReturn(Optional.of(pessoaData));
        var pessoa = pessoaService.savePerson(requestPessoa);
        Assertions.assertTrue(pessoa.isEmpty());
    }

    @Test
    @DisplayName("Deve retorna uma endereco de uma api externa (via cep)")
    public void deveRetornarUmEnderecoDeUmaApiExterna() {
        var requestPessoa = new RequestPessoa();
        requestPessoa.setCep("00000000");
        when(enderecoService.executa(requestPessoa.getCep())).thenReturn(this.enderecoExterno());
        var enderecoExterno = enderecoService.executa(requestPessoa.getCep());

        Assertions.assertNotNull(enderecoExterno);
        Assertions.assertInstanceOf(EnderecoResponse.class, enderecoExterno);
        Assertions.assertEquals(requestPessoa.getCep(), enderecoExterno.getCep());
    }
    @Test
    @DisplayName("Deve salvar uma pessoa no data-base")
    public void deveSalvarUmaPessoaNoDataBase() {
        var requestPessoa = new RequestPessoa("Nome exemplo", "000000000000", "usuario@dominio.com", 18, "00000000", 1);

        when(pessoaRepository.findByCpf(requestPessoa.getCpf())).thenReturn(Optional.empty());
        when(enderecoService.executa(requestPessoa.getCep())).thenReturn(this.enderecoExterno());
        when(pessoaRepository.save(any())).thenReturn(this.pessoaData(this.enderecoExterno()));
        var pessoaSalva = pessoaService.savePerson(requestPessoa);

        Assertions.assertNotNull(pessoaSalva);
        Assertions.assertInstanceOf(Pessoa.class, pessoaSalva.get());
        Assertions.assertEquals(requestPessoa.getCpf(), pessoaSalva.get().getCpf());

        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }


    // Function listAllPeople
    @Test
    @DisplayName("Retornar uma lista apenas com uma pessoa")
    public void deveRetornaUmaListaDePessoas() {
        when(pessoaRepository.findAll())
                .thenReturn(Collections.singletonList(this.pessoaData(this.enderecoExterno())));
        Assertions.assertFalse(pessoaService.listAllPeople().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando retorna uma lista de pessoas vazia")
    public void deveLancaExeccaoAoRetornaUmaListaDePessoaVazia() {
        when(pessoaRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(NoPersonsFoundException.class, () -> pessoaService.listAllPeople());
    }


    // Function findByCpf
    @Test
    @DisplayName("Lancar uma exceção se não houver uma pessoa no data-base pelo cpf")
    public void lancarExceptionSeNaoHouverUmaPessoaNoDataBasePeloCpf() throws PersonNotFoundException {
        var requestCpf = new RequestCpf();
        requestCpf.setCpf("000000000000");

        when(pessoaRepository.findByCpf(requestCpf.getCpf())).thenReturn(Optional.empty());
        Assertions.assertThrows(PersonNotFoundException.class, () -> pessoaService.findByCpf(requestCpf));
    }

    @Test
    @DisplayName("Deve retorna uma pessoa do data-base pelo cpf")
    public void deveRetornarUmaPessoaPeloCpfDoDataBase(){
        var requestCpf = new RequestCpf();
        requestCpf.setCpf("000000000000");

        when(pessoaRepository.findByCpf(requestCpf.getCpf()))
                .thenReturn(Optional.of(this.pessoaData(this.enderecoExterno())));
        var pessoa = pessoaService.findByCpf(requestCpf);

        Assertions.assertNotNull(pessoa);
        Assertions.assertInstanceOf(Pessoa.class, pessoa);
        Assertions.assertEquals(requestCpf.getCpf(), pessoa.getCpf());
    }


    // Function deleteByCpf
    @Test
    @DisplayName("Dever lançar uma exceção quando não encontrar uma pessoa pelo cpf no data-base")
    public void deveLancarExceptionQuandoSeNaoEncontrarUmaPessoaPeloCpfNoDataBase() {
        var requestCpf = new RequestCpf();
        requestCpf.setCpf("000000000000");

        when(pessoaRepository.findByCpf(requestCpf.getCpf())).thenReturn(Optional.empty());

        Assertions.assertThrows(PersonNotFoundException.class, () -> pessoaService.deleteByCpf(requestCpf));
    }

    @Test
    @DisplayName("Dever excluir uma pessoa pelo seu cpf")
    public void deveExcluirUmaPessoaPeloCpf(){
        var requestCpf = new RequestCpf();
        requestCpf.setCpf("000000000000");

        when(pessoaRepository.findByCpf(requestCpf.getCpf()))
                .thenReturn(Optional.of(this.pessoaData(this.enderecoExterno())));
        pessoaService.deleteByCpf(requestCpf);

        verify(pessoaRepository, times(1)).deleteByCpf(requestCpf.getCpf());
    }


    // Database Entities
    private @NotNull Pessoa pessoaData(EnderecoResponse enderecoResponse){
        var tipoDeConta = new TipoDeContas(1, LocalDateTime.now());
        var endereco = new Endereco();
        endereco.setDataDeCriacao(LocalDateTime.now());
        BeanUtils.copyProperties(enderecoResponse, endereco);

        return new Pessoa.Builder()
                .id(1L)
                .nome("Nome Exemplo")
                .cpf("000000000000")
                .idade(18)
                .endereco(List.of(endereco))
                .tiposDeContas(List.of(tipoDeConta))
                .dataDeCriacao(LocalDateTime.now())
                .build();
    }

    private @NotNull EnderecoResponse enderecoExterno(){
        return new EnderecoResponse("00000000","Rua Exemplo", "Bairro Exemplo", "Loca. Exemplo", "EE", "Comple. Exemplo");
    }
}