package banco.example.banco.repository;

import banco.example.banco.model.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Long> {

    Optional<Salario> findByNumeroDeConta(String numeroConta);
    void deleteByNumeroDeConta(String numeroConta);
}
