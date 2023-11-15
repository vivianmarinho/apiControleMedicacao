package apiControleMedicacao.repository;

import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MedicacaoNotificacaoRepository extends JpaRepository<MedicacaoNotificacao, Long> {
}
