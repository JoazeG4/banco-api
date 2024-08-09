package banco.example.banco.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "endereco_db")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String localidade;
    @Column(nullable = false)
    private String uf;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String complemento;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
