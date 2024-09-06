package banco.example.banco.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Table(name = "tipo_de_conta_db")
@Entity
@Data
public class TipoDeContas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer valor;

    @Column(nullable = false)
    private LocalDateTime dataDeCriacao;

    public TipoDeContas(Integer valor, LocalDateTime data) {
        this.valor = valor;
        this.dataDeCriacao = data;
    }
}
