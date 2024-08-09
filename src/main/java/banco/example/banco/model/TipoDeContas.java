package banco.example.banco.model;

import jakarta.persistence.*;
import lombok.Data;
@Table(name = "tipo_de_conta_db")
@Entity
@Data
public class TipoDeContas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int valor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
