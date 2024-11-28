package banco.example.banco.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "pessoa_db")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private Integer idade;
    @JsonManagedReference
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
    @JsonManagedReference
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TipoDeContas> tiposDeContas;
    @Column(nullable = false)
    private LocalDateTime dataDeCriacao;

    public Pessoa(){}

    private Pessoa(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.cpf = builder.cpf;
        this.idade = builder.idade;
        this.enderecos = builder.enderecos;
        this.tiposDeContas = builder.tiposDeContas;
        this.dataDeCriacao = builder.dataDeCriacao;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String cpf;
        private Integer idade;
        private List<Endereco> enderecos;
        private List<TipoDeContas> tiposDeContas;
        private LocalDateTime dataDeCriacao;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder nome(String nome){
            this.nome = nome;
            return this;
        }

        public Builder cpf(String cpf){
            this.cpf = cpf;
            return this;
        }

        public Builder idade(Integer idade){
            this.idade = idade;
            return this;
        }

        public Builder endereco(List<Endereco> endereco){
            this.enderecos = endereco;
            return this;
        }

        public Builder tiposDeContas(List<TipoDeContas> tiposDeContas){
            this.tiposDeContas = tiposDeContas;
            return this;
        }

        public Builder dataDeCriacao(LocalDateTime dataDeCriacao){
            this.dataDeCriacao = dataDeCriacao;
            return this;
        }

        public Pessoa build(){
            return new Pessoa(this);
        }
    }
}
