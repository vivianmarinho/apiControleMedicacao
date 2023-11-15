package apiControleMedicacao.controller;

import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/medicamento",
        method = RequestMethod.GET)
public class MedicamentoController {
    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<Medicamento> adicionarMedicamento(@RequestBody Medicamento medicamento){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.adicionarMedicamento(medicamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento>buscarMedicamentoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(medicamentoService.buscarMedicamentoPorId(id));

    }
}