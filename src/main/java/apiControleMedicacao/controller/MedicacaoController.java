package apiControleMedicacao.controller;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.service.MedicacaoService;
import apiControleMedicacao.service.MedicamentoService;
import apiControleMedicacao.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/medicacao",
        method = RequestMethod.GET)
public class MedicacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private MedicacaoService medicacaoService;

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

    @GetMapping("/buscarMedicacaoPorId/{id}")
    public ResponseEntity<Medicacao> buscarMedicacaoPorId(@PathVariable Long id) {
        try {
            Medicacao medicacao = medicacaoService.buscarMedicacaoPorId(id);

            Usuario usuario = medicacao.getUsuario();
            Medicamento medicamento = medicacao.getMedicamento();

            // Aqui você pode usar pessoa e medicamento conforme necessário...

            return ResponseEntity.ok(medicacao);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

   /* public ResponseEntity<Medicacao>bucarMedicacaoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(medicacaoService.buscarMedicacaoPorId(id));

    }*/



}
