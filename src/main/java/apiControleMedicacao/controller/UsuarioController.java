package apiControleMedicacao.controller;

import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.UsuarioRepository;
import apiControleMedicacao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/usuario",
        method = {RequestMethod.GET})


public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;


    public void AuthenticationController() {
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<Usuario> adicionarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.adicionarUsuario(usuario));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historico/{cpf}")
    public List<UserDetails> buscarHistoricoUsuario(@PathVariable String cpf) {
        return usuarioService.buscarHistoricoUsuario(cpf);
    }











}
