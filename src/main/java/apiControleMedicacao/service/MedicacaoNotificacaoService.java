package apiControleMedicacao.service;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.repository.MedicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MedicacaoNotificacaoService {
    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    private MedicacaoService medicacaoService;

    public MedicacaoNotificacao realizarRegistroMedicacaoNotificacao(MedicacaoNotificacao medicacaoNotificacao) {

        Medicacao medicacao = new Medicacao();

        medicacao.setIdMedicacao(medicacao.getIdMedicacao());

        medicacaoNotificacao.setMedicacao(medicacao);

        medicacaoNotificacaoRepository.save(medicacaoNotificacao);
        return medicacaoNotificacao;
    }

    public MedicacaoNotificacao adicionarMedicacaoNotificacao(MedicacaoNotificacao medicacaoNotificacao){
        return medicacaoNotificacaoRepository.save(medicacaoNotificacao);
    }


    public MedicacaoNotificacaoService(MedicacaoNotificacaoRepository medicacaoNotificacaoRepository) {
        this.medicacaoNotificacaoRepository = medicacaoNotificacaoRepository;
    }


        public MedicacaoNotificacao salvarMedicacaoNotificacao(MedicacaoNotificacao medicacaoNotificacao) {
            medicacaoNotificacao.setMedicacao(medicacaoService.buscarMedicacaoPorId(medicacaoNotificacao.getMedicacao().getIdMedicacao()));
            return medicacaoNotificacaoRepository.save(medicacaoNotificacao);
       }

}

