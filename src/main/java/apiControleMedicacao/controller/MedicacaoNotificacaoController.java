package apiControleMedicacao.controller;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.service.MedicacaoNotificacaoService;
import apiControleMedicacao.service.MedicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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


   /* public void saveMedicacaoNotificacao(@RequestBody MedicacaoNotificacao medicacaoNotificacao){

        Medicacao medicacao = new Medicacao();

        if(medicacaoNotificacao.getDiahoraNotificacao()!=null && medicacaoNotificacao.getMedicacao()!=null){

            if(medicacaoNotificacao.getMedicacao().equals(medicacao.getIdMedicacao())){
                medicacao.getUsuario();
                medicacao.getMedicamento();

                if(medicacaoNotificacao.getMedicacao() == MedicacaoNotificacaoService.MedicacaoNotificacaoRequestDTO){
                    medicacaoNotificacao.setMedicacaoTomada(false);

                }else{
                    medicacaoNotificacao.setMedicacaoTomada(true);

                }
            }
        }

        // Salvando a instância no repositório
        medicacaoNotificacaoRepository.save(medicacaoNotificacao);
    }*/




    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/teste")
    public ResponseEntity<String> receberEscolha(@RequestBody MedicacaoNotificacao medicacaoNotificacao) {

               medicacaoNotificacao =  medicacaoNotificacaoService.buscarMedicacaoNotificacaoPorId(medicacaoNotificacao.getId());
               medicacaoNotificacao.setMedicacaoTomada(true);
               medicacaoNotificacaoService.adicionarMedicacaoNotificacao(medicacaoNotificacao);
             // medicacaoService.buscarMedicacaoPorId(medicacaoNotificacao.getMedicacao().getIdMedicacao());
             // System.out.println(medicacaoNotificacao.getMedicacao().getUsuario());

             // medicacao =  medicacaoService.buscarMedicacaoPorId(medicacao.getIdMedicacao());




            //  System.out.println(medicacaoNotificacao);


       // if ((medicacaoNotificacao.getMedicacaoTomada()==true)) {
            // Processamento para escolha "Sim"
            return ResponseEntity.ok("Escolha Sim recebida com sucesso"); // id da Pessoa
        } //else {
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escolha inválida");
        }
   // }
//}

