package apiControleMedicacao.controller;

import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.service.MedicacaoNotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController


public class MedicacaoNotificacaoController {

    @Autowired
    private MedicacaoNotificacaoService medicacaoNotificacaoService;

    public ResponseEntity<MedicacaoNotificacao> salvarMedicacaoNotificacao(@RequestBody MedicacaoNotificacao medicacaoNotificacao){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicacaoNotificacaoService.salvarMedicacaoNotificacao(medicacaoNotificacao));
    }

    public ResponseEntity<String> receberEscolha(@RequestBody MedicacaoNotificacao medicacaoNotificacao) {
        if ("Sim".equals(medicacaoNotificacao.getMedicacaoTomada())) {
            // Processamento para escolha "Sim"
            return ResponseEntity.ok("Escolha Sim recebida com sucesso"); // id da Pessoa
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escolha inválida");
        }
    }
}
