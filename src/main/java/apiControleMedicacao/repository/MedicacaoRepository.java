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
   // @Query(value = "SELECT * FROM medicacao WHERE cpf = :cpf", nativeQuery = true)
   // List<Medicacao> buscarPorUsuarioPorCpf(@Param("cpf") String cpf);

    //List<Medicacao> findByUsuarioId(Long usuarioId);

  //  @Query(value = "SELECT * FROM Medicacao WHERE usuario_id = :idUsuario", nativeQuery = true)
  // List<Medicacao> findAllUsuarioId(@Param("idUsuario") Long idUsuario);

    List<Medicacao> findByUsuario(Usuario usuario);






}

