package apiControleMedicacao.service;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario adicionarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario deletarUsuarioPorId(Long id) {
        // Verifique se o ID não é nulo antes de realizar a exclusão
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        return null;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByCpf(username);
    }

    // @Override
   /* public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsuario(username);
    }*/
}
