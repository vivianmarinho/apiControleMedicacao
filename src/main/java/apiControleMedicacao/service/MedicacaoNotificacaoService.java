package apiControleMedicacao.service;


import apiControleMedicacao.model.MedicacaoNotificacao;
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

    public MedicacaoNotificacao deletarMedicacaoNotificacaoPorId(Long id) {
        // Verifique se o ID não é nulo antes de realizar a exclusão
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        Optional<MedicacaoNotificacao> medicacaoNotificacaoOptional = medicacaoNotificacaoRepository.findById(id);

        if (medicacaoNotificacaoOptional.isPresent()) {

            medicacaoNotificacaoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Registro não encontrado com o ID: " + id);
        }
        return null;
    }


    public record MedicacaoNotificacaoRequestDTO(String medicacaoTomada) {
    }
    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    private MedicacaoService medicacaoService;




}

