package apiControleMedicacao.service;

import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario adicionarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        // Verifique se o ID não é nulo antes de realizar a consulta no banco de dados
        if (id == null) {
            throw new IllegalArgumentException("O ID da pessoa não pode ser nulo.");
        }

        Optional<Usuario> pessoaOptional = usuarioRepository.findById(id);

        if (pessoaOptional.isPresent()) {
            return pessoaOptional.get();
        } else {
            throw new EntityNotFoundException("Pessoa não encontrada com o ID: " + id);
        }
    }
}