package banco.example.banco.integracoes;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;


import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private EnderecoFeign enderecoFeign;

    @Mock
    private Validator validator;

    @Test
    @DisplayName("Deve retornar uma endereco da api externa VIA CEP")
    public void deveRetornaUmEnderecoDaApiExternaViaCep(){
        String requestCep = "00000000";
        when(enderecoFeign.buscaEnderecoCep(requestCep)).thenReturn(this.enderecoExterno());
        var enderecoExterno = enderecoService.executa(requestCep);

        Assertions.assertNotNull(enderecoExterno);
        Assertions.assertEquals("00000000", enderecoExterno.getCep());
        Assertions.assertEquals("Rua Exemplo", enderecoExterno.getLogradouro());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao retornar um endereco com valores nulos")
    public void deveLancarUmaExcecaoAoRetornarUmEnderecoNulo() {
        String requestCep = "00000000";
        var enderecoExterno = new EnderecoResponse();

        when(enderecoFeign.buscaEnderecoCep(requestCep)).thenReturn(enderecoExterno);
        Set<ConstraintViolation<EnderecoResponse>> invalidacoes = Set.of(Mockito.mock(ConstraintViolation.class));
        when(validator.validate(enderecoExterno)).thenReturn(invalidacoes);

        Assertions.assertThrows(ConstraintViolationException.class, () -> enderecoService.executa(requestCep));
    }

    private @NotNull EnderecoResponse enderecoExterno(){
        return new EnderecoResponse("00000000","Rua Exemplo", "Bairro Exemplo", "Loca. Exemplo", "EE","Comple. Exemplo");
    }
}