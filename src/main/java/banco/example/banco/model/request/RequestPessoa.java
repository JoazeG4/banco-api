package banco.example.banco.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor @NoArgsConstructor
public class RequestPessoa {

    @NotBlank(message = "Nome inválido ou nulo!")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres.")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "O nome deve conter apenas letras e espaços.")
    private String nome;

    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotNull(message = "Idade inválida!")
    @Min(value = 18, message = "Idade miníma apartir de 18 anos.")
    private Integer idade;

    @NotBlank(message = "Cep inválido!")
    @Size(min = 8, max = 8, message = "Cep deve conter exatamente 8 dígitos.")
    @Pattern(regexp = "^[0-9]+$", message = "Cep deve conter apenas números.")
    private String cep;

    @NotNull(message = "Tipo de conta inválido!")
    @Min(value = 1, message = "O tipo de conta deve ser no mínimo 1.")
    @Max(value = 3, message = "O tipo de conta deve ser no máximo 3.")
    private Integer tipoDeConta;
}