package apiControleMedicacao.controller;


import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/usuario",
        method = {RequestMethod.GET})


public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> adicionarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.adicionarUsuario(usuario));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Usuario> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Usuario>buscarUsuarioPorNome(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarUsuarioPorId(id));
    }


}
