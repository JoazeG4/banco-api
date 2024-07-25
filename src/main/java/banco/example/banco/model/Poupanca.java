package banco.example.banco.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "poupanca-db")
public class Poupanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double saldo = 0.00;
    @Column(nullable = false)
    private String numeroDeConta;
    @Column(nullable = false)
    private Boolean status;
    @Column(nullable = true)
    private LocalDateTime criacaoDeConta;
}

