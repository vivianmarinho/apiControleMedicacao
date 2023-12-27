package apiControleMedicacao.controller;

import apiControleMedicacao.infrasecurity.TokenService;
import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.service.MedicacaoService;
import apiControleMedicacao.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping(value = "/medicacao",
        method = RequestMethod.GET)
public class MedicacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicacaoService medicacaoService;

    @Autowired
    TokenService tokenService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("")
    public ResponseEntity<Medicacao> realizarRegistroMedicacao(@RequestBody Medicacao medicacao) {
        // MedicacaoService medicacaoService = new MedicacaoService();

        return ResponseEntity.status(HttpStatus.OK).body(medicacaoService.realizarRegistroMedicacao(medicacao));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Medicacao> deletarMedicacaoPorId(@PathVariable Long id) {
        medicacaoService.deletarMedicacaoPorId(id);
        return ResponseEntity.noContent().build();
    }

   @GetMapping("/historico/medicacao")
    public ResponseEntity<List<Medicacao>> buscarMedicacoesDoUsuarioAutenticado(@RequestHeader("Authorization") String token) {
       System.out.println(token);
       if (token == null || token.isEmpty()) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }

       String cpfUsuario = tokenService.validateToken(token.replace("Bearer ", ""));


       if (!cpfUsuario.isEmpty()) {
           Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpfUsuario);


           if (usuario != null) {
               List<Medicacao> medicacoes = medicacaoService.buscarMedicacoesPorUsuario(usuario);
               return ResponseEntity.ok(medicacoes);
           } else {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
           }
       } else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }
   }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMedicacaoDoUsuarioAutenticado(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        try {
            medicacaoService.deletarMedicacaoDoUsuarioAutenticado(token, id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }










}
