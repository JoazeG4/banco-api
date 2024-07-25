package banco.example.banco.repository;

import banco.example.banco.model.Poupanca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PoupancaRepository extends JpaRepository<Poupanca, Long> {

    Optional<Poupanca> findByNumeroDeConta(String numeroConta);
    void deleteByNumeroDeConta(String numeroConta);
}
