package apiControleMedicacao.service;

import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MedicacaoNotificacaoService {


    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    public MedicacaoNotificacao adicionarHorarioNotificacao(MedicacaoNotificacao horaNotificacao) {

        //horaNotificacao.add(diaHorarioNotificacao);
        // Verificação - Proibi agendar horarios anteriores a data/hora atual

        if  (LocalDateTime.of(horaNotificacao.getDataNotificacao(),horaNotificacao.getHoraNotificacao()).isAfter(LocalDateTime.now())) {
            horaNotificacao.setStatusHoraMedicacao("AGENDADA");
        }
        return medicacaoNotificacaoRepository.save(horaNotificacao);
    }

}
