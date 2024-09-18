package banco.example.banco.integracoes;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponse {

    @NotBlank(message = "Cep inválido!")
    private String cep;
    @NotBlank(message = "Logradouro inválido!")
    private String logradouro;
    @NotBlank(message = "Bairro inválido!")
    private String bairro;
    @NotBlank(message = "Localidade inválida!")
    private String localidade;
    @NotBlank(message = "UF inválido!")
    private String uf;
    @NotBlank(message = "Número inválido!")
    private String numero;
    private String complemento;
}
