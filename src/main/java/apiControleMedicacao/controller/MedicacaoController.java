package apiControleMedicacao.controller;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.service.MedicacaoService;
import apiControleMedicacao.service.MedicamentoService;
import apiControleMedicacao.service.UsuarioService;
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

    @PostMapping
    public  ResponseEntity<Medicacao>realizarRegistroMedicacao(@RequestBody Medicacao medicacao){
       // MedicacaoService medicacaoService = new MedicacaoService();
        return ResponseEntity.status(HttpStatus.OK).body(medicacaoService.realizarRegistroMedicacao(medicacao));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Medicacao> deletarMedicacao(@PathVariable Long id){
        medicacaoService.deletarMedicacaoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Medicacao>buscarUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(medicacaoService.bucarMedicacaoPorId(id));
    }

}
