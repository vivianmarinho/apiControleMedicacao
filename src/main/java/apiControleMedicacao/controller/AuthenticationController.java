package apiControleMedicacao.controller;

import apiControleMedicacao.infrasecurity.TokenService;
import apiControleMedicacao.model.AuthenticationDTO;
import apiControleMedicacao.model.LoginResponseDTO;
import apiControleMedicacao.model.RegisterDTO;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;


    @PostMapping("/usuario")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data){
        if (data.cpf() == null || data.senha() == null || data.senha().isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, forneça CPF e senha válidos.");
        }

        var userNamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.senha());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        // Resto do código para lidar com a autenticação
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data){
        if(this.usuarioRepository.findByCpf(data.cpf())!= null){
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.cpf(),encryptedPassword, data.nome(), data.email(), data.telefone());
       // this.usuarioRepository.save(novoUsuario);
        this.usuarioRepository.save(novoUsuario);


        return ResponseEntity.ok().build();

    }
}
