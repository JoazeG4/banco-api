package banco.example.banco.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RequestTransacao {

    @NotNull(message = "Valor inválido ou nulo!")
    @Min(value = 0, message = "Valor mínimo 0,0.")
    private Double valor;
    @NotNull(message = "Tipo de conta inválido!")
    @Min(value = 1, message = "O tipo de conta deve ser no mínimo 1.")
    @Max(value = 3, message = "O tipo de conta deve ser no máximo 3.")
    private Integer tipoDeConta;
    @NotBlank(message = "Número de conta inválida!")
    @Pattern(regexp = "^[0-9]{10}$", message = "O número de conta deve conter 10 digitos numéricos.")
    private String numeroDeConta;
    @NotBlank(message = "Conta de destino inválida!")
    @Pattern(regexp = "^[0-9]{10}$", message = "A conta destino deve conter 10 digitos numéricos.")
    private String contaDestino;
}
