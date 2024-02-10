package apiControleMedicacao.repository;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MedicacaoNotificacaoRepository extends JpaRepository<MedicacaoNotificacao, Long> {
    @Transactional
    void deleteByMedicacao(Medicacao medicacao);
}
