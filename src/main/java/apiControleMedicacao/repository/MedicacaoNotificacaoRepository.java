package apiControleMedicacao.repository;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface MedicacaoNotificacaoRepository extends JpaRepository<MedicacaoNotificacao, Long> {

}
