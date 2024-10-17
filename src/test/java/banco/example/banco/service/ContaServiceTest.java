package banco.example.banco.service;

import banco.example.banco.exceptions.AccountNotFoundException;
import banco.example.banco.model.Corrente;
import banco.example.banco.model.Poupanca;
import banco.example.banco.model.Salario;
import banco.example.banco.model.request.RequestTransacao;
import banco.example.banco.repository.CorrenteRepository;
import banco.example.banco.repository.PoupancaRepository;
import banco.example.banco.repository.SalarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private CorrenteRepository correnteRepository;

    @Mock
    private SalarioRepository salarioRepository;

    @Mock
    private PoupancaRepository poupancaRepository;

    @Test
    @DisplayName("Deve retornar uma conta salario do data-base pelo número")
    void deveRetornaUmaContaCorrenteDoDataBase() throws RuntimeException {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        when(correnteRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.of(this.correnteData()));

        Corrente corrente = contaService.getCorrente(requestTransacao);

        Assertions.assertNotNull(corrente);
        Assertions.assertInstanceOf(Corrente.class, corrente);
        Assertions.assertEquals(requestTransacao.getContaDestino(), corrente.getNumeroDeConta());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao não encontrar uma conta corrente do data-base")
    void deveLancarUmaExcecaoAoRetornaVazioDoDataBase() {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        when(correnteRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> contaService.getCorrente(requestTransacao));
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao retornar uma conta corrente desativada")
    void deveRetornarUmaExcecaoAoRetornarUmaContaCorrenteDesativada() {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        Corrente correnteData = this.correnteData();
        correnteData.setStatus(false);
        when(correnteRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.of(correnteData));

        Assertions.assertThrows(RuntimeException.class, () -> contaService.getCorrente(requestTransacao));
    }


    @Test
    @DisplayName("Deve retornar uma conta salario do data-base pelo número")
    void deveRetornaUmaContaSalarioDoDataBase() throws RuntimeException {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        when(salarioRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.of(this.salarioData()));

        Salario salario = contaService.getSalario(requestTransacao);

        Assertions.assertNotNull(salario);
        Assertions.assertInstanceOf(Salario.class, salario);
        Assertions.assertEquals(requestTransacao.getContaDestino(), salario.getNumeroDeConta());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao não encontar uma conta salario do data-base")
    void deveLancarUmaExcecaoAoRetornaContaSalarioVazioDoDataBase() {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        when(salarioRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> contaService.getSalario(requestTransacao));
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao retornar uma conta salario desativada")
    void deveRetornarUmaExcecaoAoRetornarUmaContaSalarioDesativada() {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        Salario salarioData = this.salarioData();
        salarioData.setStatus(false);
        when(salarioRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.of(salarioData));

        Assertions.assertThrows(RuntimeException.class, () -> contaService.getSalario(requestTransacao));
    }


    @Test
    @DisplayName("Deve retornar uma conta poupanca do data-base pelo número")
    void deveRetornaUmaContaPoupancaDoDataBase() throws RuntimeException {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        when(poupancaRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.of(this.poupancaData()));

        Poupanca poupanca = contaService.getPoupanca(requestTransacao);

        Assertions.assertNotNull(poupanca);
        Assertions.assertInstanceOf(Poupanca.class, poupanca);
        Assertions.assertEquals(requestTransacao.getContaDestino(), poupanca.getNumeroDeConta());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao não encontar uma conta poupanca do data-base")
    void deveLancarUmaExcecaoAoRetornaPoupancaVazioDoDataBase() {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        when(poupancaRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> contaService.getPoupanca(requestTransacao));
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao retornar uma conta poupanca desativada")
    void deveRetornarUmaExcecaoAoRetornarUmaContaPoupancaDesativada() {
        var requestTransacao = new RequestTransacao();
        requestTransacao.setContaDestino("0000000001");

        Poupanca poupancaData = this.poupancaData();
        poupancaData.setStatus(false);
        when(poupancaRepository.findByNumeroDeConta(requestTransacao.getContaDestino()))
                .thenReturn(Optional.of(poupancaData));

        Assertions.assertThrows(RuntimeException.class, () -> contaService.getPoupanca(requestTransacao));
    }


    private Corrente correnteData(){
        return new Corrente(1L, 0.0, "0000000001", true, LocalDateTime.now());
    }

    private Salario salarioData(){
        return new Salario(1L, 0.0, "0000000001", true, LocalDateTime.now());
    }

    private Poupanca poupancaData(){
        return new Poupanca(1L, 0.0, "0000000001", true, LocalDateTime.now());
    }
}