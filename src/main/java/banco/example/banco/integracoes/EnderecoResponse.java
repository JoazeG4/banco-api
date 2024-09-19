package banco.example.banco.integracoes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponse {

    @NotBlank(message = "Cep inválido ou nulo!")
    private String cep;
    @NotBlank(message = "Logradouro inválido ou nulo!")
    private String logradouro;
    @NotBlank(message = "Bairro inválido ou nulo!")
    private String bairro;
    @NotBlank(message = "Localidade inválido ou nulo!")
    private String localidade;
    @NotBlank(message = "UF inválido ou nulo!")
    private String uf;
    private String complemento;
}
