package apiControleMedicacao.repository;

import apiControleMedicacao.model.Medicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicacaoRepository extends JpaRepository<Medicacao, Long> {

}

