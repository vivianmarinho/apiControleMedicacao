package apiControleMedicacao.repository;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicacaoRepository extends JpaRepository<Medicacao, Long> {
      List<Medicacao> findByUsuario(Usuario usuario);
}




