package apiControleMedicacao.service;

import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.repository.MedicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicacaoNotificacaoService {
    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    private MedicacaoService medicacaoService;

   public MedicacaoNotificacaoService(MedicacaoNotificacaoRepository medicacaoNotificacaoRepository) {
        this.medicacaoNotificacaoRepository = medicacaoNotificacaoRepository;
    }
    public MedicacaoNotificacao salvarMedicacaoNotificacao(MedicacaoNotificacao medicacaoNotificacao) {
        medicacaoNotificacao.setMedicacao(medicacaoService.bucarMedicacaoPorId(medicacaoNotificacao.getMedicacao().getIdMedicacao()));
        return medicacaoNotificacaoRepository.save(medicacaoNotificacao);
    }

}

