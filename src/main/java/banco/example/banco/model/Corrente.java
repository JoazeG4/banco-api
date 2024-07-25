package banco.example.banco.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "corrente-db")
public class Corrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double saldo;
    @Column(nullable = false)
    private String numeroDeConta;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = true)
    private LocalDateTime criacaoDeConta;
}
