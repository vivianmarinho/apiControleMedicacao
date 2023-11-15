package apiControleMedicacao.controller;

import apiControleMedicacao.model.Medicacao;
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


}
