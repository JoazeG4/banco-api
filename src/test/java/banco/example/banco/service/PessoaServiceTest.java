package banco.example.banco.service;

import banco.example.banco.model.Endereco;
import banco.example.banco.model.Pessoa;
import banco.example.banco.model.TipoDeContas;
import banco.example.banco.model.request.RequestCpf;
import banco.example.banco.repository.PessoaRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    @DisplayName("Retornar uma pessoa pelo cpf")
    public void retornarUmaPessoaPeloCpf() throws Exception {
        var requestCpf = new RequestCpf("13579584620");
        when(pessoaRepository.findByCpf("13579584620")).thenReturn(Optional.of(this.pessoaData()));
        Assertions.assertEquals(Pessoa.class, pessoaService.findByCpf(requestCpf).getClass());
    }

    @Test
    @DisplayName("Retornar uma lista apenas com uma pessoa")
    public void deveRetornaUmaListaDePessoas() throws Exception {
        when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(new Pessoa()));
        Assertions.assertFalse(pessoaService.listarTodasPessoas().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando retorna uma lista de pessoas vazia")
    public void deveLancaExeccaoAoRetornaUmaListaDePessoaVazia() {
        when(pessoaRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(Exception.class, () -> pessoaService.listarTodasPessoas());
    }

    private @NotNull Pessoa pessoaData(){
        var tipoDeConta = new TipoDeContas(1L, 1, LocalDateTime.now());
        var endereco = new Endereco(1L, "00000001","Rua de teste", "Pros. teste", "Novo bairro", "Teste City", "TC", LocalDateTime.now());
        return new Pessoa(1L, "nome", "13579584620", 18, List.of(endereco), List.of(tipoDeConta), LocalDateTime.now());
    }
}