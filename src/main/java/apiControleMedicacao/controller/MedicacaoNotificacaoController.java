package apiControleMedicacao.controller;

import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.service.MedicacaoNotificacaoService;
import apiControleMedicacao.service.MedicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/medicacao_notificacao",
                method = RequestMethod.GET)


public class MedicacaoNotificacaoController {

    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    private MedicacaoNotificacaoService medicacaoNotificacaoService;

    @Autowired
    private MedicacaoService medicacaoService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/teste")
    public ResponseEntity<String> receberEscolha(@RequestBody MedicacaoNotificacao medicacaoNotificacao) {

               medicacaoNotificacao =  medicacaoNotificacaoService.buscarMedicacaoNotificacaoPorId(medicacaoNotificacao.getId());
               medicacaoNotificacao.setMedicacaoTomada(true);
               medicacaoNotificacaoService.adicionarMedicacaoNotificacao(medicacaoNotificacao);

            return ResponseEntity.ok("Escolha Sim recebida com sucesso"); // id da Pessoa
        }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MedicacaoNotificacao> deletarMedicacaoNotificacaoPorId(@PathVariable Long id) {
        medicacaoNotificacaoService.deletarMedicacaoNotificacaoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<MedicacaoNotificacao>buscarUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(medicacaoNotificacaoService.buscarMedicacaoNotificacaoPorId(id));
    }
}


