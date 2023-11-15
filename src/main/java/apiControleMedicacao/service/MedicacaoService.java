package apiControleMedicacao.service;


import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.repository.MedicacaoRepository;
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

@Service
public class MedicacaoService {

    @Autowired
    private MedicacaoRepository medicacaoRepository;
    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JavaMailSender mailSender;





    public Medicacao realizarRegistroMedicacao(Medicacao medicacao) {

            medicacao.setUsuario(usuarioService.buscarUsuarioPorId(medicacao.getUsuario().getId()));
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

        // String enviarEmail = registro.getPessoa().getEmail();

        //var mensagem = new SimpleMailMessage();
        //mensagem.setTo(registro.getPessoa().getEmail());
        //mensagem.setSubject("Cadastro Medicação");
        //mensagem.setText("A medicação " + registro.getMedicacao().getNomeRemedio() + " foi cadastrada com sucesso!"
        //	+ registro.getDataHoraRegistro());
        // medicamento.getNomeRemedio());

        //mailSender.send(mensagem);

        //String salvarEmail = registro.getPessoa().getEmail();

        List<LocalDateTime> horarioGerado = geraHorarioNotificacao(dataHoraPrimeiraDose, medicacao);
        //geraHorarioNotificacao(dataHoraPrimeiraDose, registro.getDataFim(),
        //registro.getMedicacao().getIntervalo(), registro.getIdRegistro());

           return medicacao;
    }


    // Inserindo os registros
    public Medicacao adicionarRegistroMedicacao (Medicacao medicacao){
        return  medicacaoRepository.save(medicacao);
    }

    private List<LocalDateTime> geraHorarioNotificacao(LocalDateTime dataHoraPrimeiraDose, Medicacao medicamento) {
        medicamento.setUsuario(usuarioService.buscarUsuarioPorId(medicamento.getUsuario().getId()));
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

        }

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public static final String ACCOUNT_SID = "AC4e803c96c60b36d0ffcba0fce34c20ad";
            public static final String AUTH_TOKEN = "1008d8d7ada9e77f5d337af3fd595c99";

            public void run() {
                LocalDateTime horaAtual = LocalDateTime.now();
                for (LocalDateTime hora : diaHorarioNotificacao) {
                    horaAtual = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

                    if (horaAtual.equals(hora)) {

                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                        Message message = Message.creator(
                                        new PhoneNumber("whatsapp:" + medicamento.getUsuario().getTelefone()), // Número de telefone do destinatário

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

    private static String gerarCodigoConfirmacao() {
        // Gere um UUID (identificador único) como código de confirmação
        return UUID.randomUUID().toString();
    }

    private static String construirLinkConfirmacao(String confirmacao) {
        // Lógica para gerar um código de confirmação aleatório
        // Neste exemplo, estamos usando um código simples de 4 dígitos
        //
        // return String.format("%04d", (int) (Math.random() * 10000));
        return "http://127.0.0.1:5500/index.html";
    }


}

