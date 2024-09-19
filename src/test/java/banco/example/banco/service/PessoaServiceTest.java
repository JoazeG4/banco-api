package banco.example.banco.service;

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


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoService enderecoService;


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
    @DisplayName("Deve lançar exceção quando houver uma pessoa já existente no data-base")
    public void lancarExcecaoQuandoHouverUmaPessoaJaExistenteNoDataBase() {
        var requestPessoa = new RequestPessoa();
        requestPessoa.setCpf("000000000000");
        var pessoaData = this.pessoaData(this.enderecoExterno());
        when(pessoaRepository.findByCpf(requestPessoa.getCpf())).thenReturn(Optional.of(pessoaData));
        Assertions.assertThrows(Exception.class, ()-> pessoaService.salvarPessoa(requestPessoa));
    }

    @Test
    @DisplayName("Deve retorna uma endereco de uma api externa (via cep)")
    public void deveRetornarUmEnderecoDeUmaApiExterna() {
        var requestPessoa = new RequestPessoa();
        requestPessoa.setCep("00000000");
        when(enderecoService.executa(requestPessoa.getCep())).thenReturn(this.enderecoExterno());
        var enderecoExterno = enderecoService.executa(requestPessoa.getCep());

        Assertions.assertNotNull(enderecoExterno);
        Assertions.assertEquals(EnderecoResponse.class, enderecoExterno.getClass());
        Assertions.assertEquals(requestPessoa.getCep(), enderecoExterno.getCep());
    }

    @Test
    @DisplayName("Retornar uma lista apenas com uma pessoa")
    public void deveRetornaUmaListaDePessoas() throws Exception {
        when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(this.pessoaData(this.enderecoExterno())));
        Assertions.assertFalse(pessoaService.listarTodasPessoas().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando retorna uma lista de pessoas vazia")
    public void deveLancaExeccaoAoRetornaUmaListaDePessoaVazia() {
        when(pessoaRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(Exception.class, () -> pessoaService.listarTodasPessoas());
    }

    @Test
    @DisplayName("Retornar uma pessoa pelo cpf")
    public void retornarUmaPessoaPeloCpf() throws Exception {
        var requestCpf = new RequestCpf();
        requestCpf.setCpf("000000000000");
        when(pessoaRepository.findByCpf(requestCpf.getCpf())).thenReturn(Optional.of(this.pessoaData(this.enderecoExterno())));
        Assertions.assertEquals(Pessoa.class, pessoaService.findByCpf(requestCpf).getClass());
    }

    private @NotNull Pessoa pessoaData(EnderecoResponse enderecoResponse){
        var tipoDeConta = new TipoDeContas(1L, 1, LocalDateTime.now());
        var endereco = new Endereco();
        endereco.setDataDeCriacao(LocalDateTime.now());
        BeanUtils.copyProperties(enderecoResponse, endereco);
        return new Pessoa(1L, "Nome Exemplo", "000000000000", 18, List.of(endereco), List.of(tipoDeConta), LocalDateTime.now());
    }

    private @NotNull EnderecoResponse enderecoExterno(){
        return new EnderecoResponse("00000000","Rua Exemplo", "Bairro Exemplo", "Loca. Exemplo", "EE", "Comple. Exemplo");
    }
}