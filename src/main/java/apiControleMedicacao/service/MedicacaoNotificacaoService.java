package apiControleMedicacao.service;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Medicamento;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.repository.MedicacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicacaoNotificacaoService {

    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    public MedicacaoNotificacaoService(MedicacaoNotificacaoRepository medicacaoNotificacaoRepository) {
        this.medicacaoNotificacaoRepository = medicacaoNotificacaoRepository;
    }

   public MedicacaoNotificacao salvarMedicacaoNotificacao(MedicacaoNotificacao medicacaoNotificacao) {
        return medicacaoNotificacaoRepository.save(medicacaoNotificacao);
    }


}

