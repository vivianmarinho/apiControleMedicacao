package apiControleMedicacao.service;

import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicacaoNotificacaoService {

    public MedicacaoNotificacao adicionarMedicacaoNotificacao(MedicacaoNotificacao medicacaoNotificacao) {
        return medicacaoNotificacaoRepository.save(medicacaoNotificacao);

    }

    public MedicacaoNotificacao buscarMedicacaoNotificacaoPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }

        return medicacaoNotificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicação Notificacao não encontrada com o ID: " + id));
    }


    public record MedicacaoNotificacaoRequestDTO(String medicacaoTomada) {
    }
    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    private MedicacaoService medicacaoService;




}

