package banco.example.banco.service;

import banco.example.banco.model.Corrente;
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

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CorrenteServiceTest {

    @InjectMocks
    CorrenteService correnteService;

    @Mock
    CorrenteRepository correnteRepository;
    @Mock
    SalarioRepository salarioRepository;
    @Mock
    PoupancaRepository poupancaRepository;

    @Test
    @DisplayName("Deve retornar um optional vazio do data-base")
    void deveRetornarVazioDoDataBase() {
        var corrente = new Corrente();
        corrente.setNumeroDeConta("00000");
        when(correnteRepository.findByNumeroDeConta(corrente.getNumeroDeConta())).thenReturn(Optional.empty());

        var correnteData = correnteRepository.findByNumeroDeConta(corrente.getNumeroDeConta());
        Assertions.assertTrue(correnteData.isEmpty());
    }

    @Test
    @DisplayName("Deve lançar uma exceção se houver uma conta já existente no data-base")
    void deveLancarUmaExcecaoSeHouverUmaContaNoDataBase() {
        var corrente = new Corrente();
        corrente.setNumeroDeConta("00000");
        when(correnteRepository.findByNumeroDeConta(corrente.getNumeroDeConta())).thenReturn(Optional.of(corrente));
        Assertions.assertThrows(Exception.class, () -> correnteService.salvar(corrente));
    }

    @Test
    @DisplayName("Deve salvar uma conta corrente no data-base")
    void deveSalvarUmaContaCorrenteNoDataBase() throws Exception {
        var corrente = new Corrente();
        corrente.setNumeroDeConta("00000");

        when(correnteRepository.findByNumeroDeConta(corrente.getNumeroDeConta())).thenReturn(Optional.empty());
        when(correnteRepository.save(corrente)).thenReturn(new Corrente());
        var correnteSave = correnteService.salvar(corrente);
        verify(correnteRepository ,times(1)).save(corrente);

        Assertions.assertNotNull(correnteSave);
        Assertions.assertEquals(String.class, correnteSave.getClass());
    }

    @Test
    void deletarPorNumeroDeConta() {
    }

    @Test
    void deletarTodos() {
    }

    @Test
    void depositar() {
    }

    @Test
    void sacar() {
    }

    @Test
    void transferencia() {
    }

    @Test
    void saldo() {
    }
}