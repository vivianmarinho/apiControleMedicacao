package apiControleMedicacao.repository;

import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}
