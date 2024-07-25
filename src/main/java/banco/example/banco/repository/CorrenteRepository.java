package banco.example.banco.repository;

import banco.example.banco.model.Corrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorrenteRepository extends JpaRepository<Corrente, Long> {

    Optional<Corrente> findByNumeroDeConta(String numeroConta);
    void deleteByNumeroDeConta(String numeroConta);
}
