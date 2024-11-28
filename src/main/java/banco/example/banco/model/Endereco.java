package banco.example.banco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(nullable = true)
    private String complemento;

    @Column(nullable = false)
    private LocalDateTime dataDeCriacao;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
