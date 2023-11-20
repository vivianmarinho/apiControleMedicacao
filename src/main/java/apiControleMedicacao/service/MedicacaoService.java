package apiControleMedicacao.service;


import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.repository.MedicacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



import java.net.URI;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.print.attribute.standard.Media;

@Service
public class MedicacaoService {

    @Autowired
    private MedicacaoRepository medicacaoRepository;
    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JavaMailSender mailSender;


    public Medicacao realizarRegistroMedicacao(Medicacao medicacao) {


        medicacao.setUsuario(usuarioService.buscarUsuarioPorId(medicacao.getUsuario().getIdUsuario()));
        medicacao.setMedicamento(medicamentoService.buscarMedicamentoPorId(medicacao.getMedicamento().getId()));

        if (medicacao.getUsuario() == null) {
            return null;
        }

        if (medicacao.getMedicamento() == null) {
            return null;
        }

        if (medicacao.getDataInicio() == null || medicacao.getDataFim() == null) {
            System.out.println("Nunhuma data pode ser nula");
            return null;
        }

        if (medicacao.getHoraInicio() == null) {
            System.out.println("O usuário deve inserir hora de inicio");
            return null;
        }

        if (medicacao.getHoraInicio() == null) {
            System.out.println("O usuário deve informar a hora");
            return null;
        }
        LocalDateTime dataHoraUltimaDose = LocalDateTime.of(medicacao.getDataFim(), medicacao.getHoraInicio());
        LocalDateTime dataHoraPrimeiraDose = LocalDateTime.of(medicacao.getDataInicio(), medicacao.getHoraInicio());
        LocalDate data = LocalDate.of(medicacao.getDataInicio().getYear(), medicacao.getDataInicio().getMonth(), medicacao.getDataInicio().getDayOfMonth());
        LocalDate dataFim = LocalDate.of(medicacao.getDataFim().getYear(), medicacao.getDataFim().getMonth(), medicacao.getDataFim().getDayOfMonth());


        medicacao = medicacaoRepository.save(medicacao);

        List<LocalDateTime> horarioGerado = geraHorarioNotificacao(dataHoraPrimeiraDose, medicacao);

        return medicacao;



    }


    // Inserindo os registros
    public Medicacao adicionarRegistroMedicacao(Medicacao medicacao) {
        return medicacaoRepository.save(medicacao);
    }

    private List<LocalDateTime> geraHorarioNotificacao(LocalDateTime dataHoraPrimeiraDose, Medicacao medicamento) {
        medicamento.setUsuario(usuarioService.buscarUsuarioPorId(medicamento.getUsuario().getIdUsuario()));
        medicamento.setMedicamento(medicamentoService.buscarMedicamentoPorId(medicamento.getMedicamento().getId()));

        LocalDateTime dataHoraUltimodia = LocalDateTime.of(medicamento.getDataFim(), LocalTime.of(23, 59));

        // fazendo a diferença de dias
        long dias = ChronoUnit.DAYS.between(dataHoraPrimeiraDose, dataHoraUltimodia);

        int horas = medicamento.getIntervalo().getHour();
        int minutos = medicamento.getIntervalo().getMinute();
        int segundos = medicamento.getIntervalo().getSecond();
        int totalSegundos = 0;
        totalSegundos += (segundos + (minutos * 60) + (horas * 3600));

        long numeroDeIntervalos = (dias * 86400) / totalSegundos;

        List<LocalDateTime> diaHorarioNotificacao = new ArrayList<>();
        diaHorarioNotificacao.add(dataHoraPrimeiraDose);
        LocalDateTime auxiliar;


        for (int k = 0; k < numeroDeIntervalos; k++) {
            auxiliar = diaHorarioNotificacao.get(diaHorarioNotificacao.size() - 1);
            LocalDateTime HorarioSalvar = auxiliar.plusSeconds(totalSegundos);
            diaHorarioNotificacao.add(HorarioSalvar);
            MedicacaoNotificacao medicacaoNotificacao = new MedicacaoNotificacao();
            medicacaoNotificacao.setDiahoraNotificacao(HorarioSalvar);
            medicacaoNotificacaoRepository.save(medicacaoNotificacao);

        }


        // SALVANDO NA TABELA NOTIFICACAO


        Timer timer = new Timer();


        timer.scheduleAtFixedRate(new TimerTask() {
            public static final String ACCOUNT_SID = "AC4e803c96c60b36d0ffcba0fce34c20ad";
            public static final String AUTH_TOKEN = "";
            // "1008d8d7ada9e77f5d337af3fd595c99";

            public void run() {
                LocalDateTime horaAtual = LocalDateTime.now();
                for (LocalDateTime hora : diaHorarioNotificacao) {
                    horaAtual = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);



                    // Prestar atenção porque a comparação é feita de DIA e HORA
                    if (horaAtual.equals(hora)) {

                        diminuirQuantidadeMedicamento(medicamento);

                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                        String audioUrl = "apiControleMedicacao.audio.HoraDeTomarAMedicacao";

                        Message message = Message.creator(
                                        new PhoneNumber("whatsapp:+55" + medicamento.getUsuario().getTelefone()), // Número de telefone do destinatário
                                        // new PhoneNumber("whatsapp:+5565996135666"),
                                        new PhoneNumber("whatsapp:+14155238886"),

                                        medicamento.getUsuario().getNome() + " está na hora de tomar o remédio " + medicamento.getMedicamento().getMedicamentoNome() + " !" + "\n" +
                                                "Para confirmar que tomou a medicação, acesse o link a seguir:" + "\n")

                                .create();

                        System.out.println(message.getSid());


                    }


                }
            }


        }, 60500, 60500);

        //System.out.println(diaHorarioNotificacao);
        return diaHorarioNotificacao;




    }

    public Medicacao diminuirQuantidadeMedicamento(Medicacao medicacao) {
        int quantidadeAtual = Integer.parseInt(medicacao.getQuantidade());

        if (quantidadeAtual > 0) {
            int novaQuantidade = quantidadeAtual - 1;
            medicacao.setQuantidade(String.valueOf(novaQuantidade));
            Medicacao medicacaoAtualizada = medicacaoRepository.save(medicacao);

            // Verifica se a quantidade é menor ou igual a 3 para enviar notificação
            if (novaQuantidade <= 3) {
                enviarNotificacao(medicacao); // Chama o método para enviar notificação
            }

            return medicacaoAtualizada;
        } else {
            // Lógica para tratar quando a quantidade é zero ou negativa
            return medicacao;
        }
    }

    private void enviarNotificacao(Medicacao medicacao) {

         String enviarEmail = medicacao.getUsuario().getEmail();

        var mensagem = new SimpleMailMessage();
        mensagem.setTo(medicacao.getUsuario().getEmail());
        mensagem.setSubject("ATENÇÃO!");
        mensagem.setText("A sua medicação está quase acabando, se necessario repor " + medicacao.getMedicamento().getMedicamentoNome() + medicacao.getQuantidade());

        mailSender.send(mensagem);
    }

    public Medicacao deletarMedicacaoPorId(Long id) {
        // Verifique se o ID não é nulo antes de realizar a exclusão
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        Optional<Medicacao> medicacaoOptional = medicacaoRepository.findById(id);

        if (medicacaoOptional.isPresent()) {

            medicacaoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        return null;
    }

    public Medicacao bucarMedicacaoPorId(Long id) {
        // Verifique se o ID não é nulo antes de realizar a exclusão
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        Optional<Medicacao> medicacaoOptional = medicacaoRepository.findById(id);

        if (medicacaoOptional.isPresent()) {
            medicacaoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        return null;
    }

}





