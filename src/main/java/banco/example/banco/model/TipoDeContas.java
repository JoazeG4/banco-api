package banco.example.banco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "tipo_de_conta_db")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class TipoDeContas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer conta;

    @Column(nullable = false)
    private LocalDateTime dataDeCriacao;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public TipoDeContas(Integer conta, LocalDateTime data) {
        this.conta = conta;
        this.dataDeCriacao = data;
    }
}
