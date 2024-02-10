package apiControleMedicacao.service;


import apiControleMedicacao.infrasecurity.TokenService;
import apiControleMedicacao.model.Medicacao;
import apiControleMedicacao.model.MedicacaoNotificacao;
import apiControleMedicacao.model.Usuario;
import apiControleMedicacao.repository.MedicacaoNotificacaoRepository;
import apiControleMedicacao.repository.MedicacaoRepository;
import apiControleMedicacao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
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
    private MedicacaoNotificacaoRepository medicacaoNotificacaoRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JavaMailSender mailSender;

    public MedicacaoService(MedicacaoRepository medicacaoRepository, MedicacaoNotificacaoRepository medicacaoNotificacaoRepository) {
        this.medicacaoRepository = medicacaoRepository;
        this.medicacaoNotificacaoRepository = medicacaoNotificacaoRepository;
    }


    public Medicacao realizarRegistroMedicacao(Medicacao medicacao) {

        // Ele está registrando pelo CPF
        medicacao.setUsuario(usuarioService.buscarUsuarioPorCpf(medicacao.getUsuario().getCpf()));

        if (medicacao.getUsuario() == null) {
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
        if (medicacao.getNomeMedicamento() == null) {
            System.out.println("O usuário deve informar o nome do medicamento");
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

        // Validando os horarios das notificações
        // SALVANDO NA TABELA MEDICACAO_NOTIFICACAO

        for (int k = 0; k < numeroDeIntervalos; k++) {
            auxiliar = diaHorarioNotificacao.get(diaHorarioNotificacao.size() - 1);
            LocalDateTime HorarioSalvar = auxiliar.plusSeconds(totalSegundos);
            diaHorarioNotificacao.add(HorarioSalvar);
            MedicacaoNotificacao medicacaoNotificacao = new MedicacaoNotificacao();
            medicacaoNotificacao.setDiahoraNotificacao(HorarioSalvar);
            LocalDateTime horario = LocalDateTime.now();
            if (medicacaoNotificacao.getDiahoraNotificacao() == horario) {
                medicacaoNotificacao.setStatusHoraMedicacao("Consumindo");
            } else if (medicacaoNotificacao.getDiahoraNotificacao().isAfter(horario)) {
                medicacaoNotificacao.setStatusHoraMedicacao("Agendado");
            } else if (medicacaoNotificacao.getDiahoraNotificacao().isBefore(horario)) {
                medicacaoNotificacao.setStatusHoraMedicacao("Consumido");
            }

            Medicacao medicacao = buscarMedicacaoPorId(medicamento.getIdMedicacao());
            medicacaoNotificacao.setMedicacao(medicacao);

            medicacaoNotificacaoRepository.save(medicacaoNotificacao);

        }

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
                        //Medicacao medicacao = new Medicacao();

                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


                        Message message = Message.creator(
                                        new PhoneNumber("whatsapp:+55" + medicamento.getUsuario().getTelefone()), // Número de telefone do destinatário
                                        //new PhoneNumber("whatsapp:+556596135666"),
                                        new PhoneNumber("whatsapp:+14155238886"),

                                        medicamento.getUsuario().getNome() + " está na hora de tomar o remédio " + medicamento.getNomeMedicamento() + " !" + "\n" +
                                                "Para confirmar que tomou a medicação, acesse o link a seguir:" + "\n")

                                .create();

                        System.out.println(message.getSid());


                    }


                }
            }


        }, 60500, 60500);

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
        mensagem.setText("A sua medicação, " + medicacao.getNomeMedicamento() + ", está quase no fim. Restam apenas " + medicacao.getQuantidade() + " comprimidos.");


        mailSender.send(mensagem);
    }

    public Medicacao deletarMedicacaoPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }
        Optional<Medicacao> medicacaoOptional = medicacaoRepository.findById(id);

        if (medicacaoOptional.isPresent()) {

            medicacaoRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Registro não encontrado com o ID: " + id);
        }
        return null;
    }

    public Medicacao buscarMedicacaoPorId(Long id) {
        // Verifica se o ID é nulo
        Objects.requireNonNull(id, "O ID da medicação não pode ser nulo.");


        return medicacaoRepository.findById(id)
                // Retorna a medicação se encontrada, ou lança uma EntityNotFoundException se não encontrada
                .orElseThrow(() -> new EntityNotFoundException("Medicação não encontrada com o ID: " + id));
    }
    public Long deletarMedicacaoDoUsuarioAutenticado(String token, Long idMedicacao) {

        String cpfUsuario = tokenService.validateToken(token);

        buscarMedicacaoPorId(idMedicacao);


        if (!cpfUsuario.isEmpty()) {
            Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpfUsuario);


            List<Medicacao> medicacoesDoUsuario = medicacaoRepository.findByUsuario(usuario);

            // Verificar se a medicação específica está presente na lista
            Optional<Medicacao> medicacaoParaDeletar = medicacoesDoUsuario.stream()
                    .filter(medicacao -> Long.valueOf(medicacao.getIdMedicacao()).equals(idMedicacao))
                    .findFirst();

            if (medicacaoParaDeletar.isPresent()) {
                Long idDeletado = medicacaoParaDeletar.get().getIdMedicacao(); // Captura o ID da medicação antes de deletá-la

                medicacaoNotificacaoRepository.deleteByMedicacao(medicacaoParaDeletar.get());
                medicacaoRepository.delete(medicacaoParaDeletar.get());

                return idDeletado; // Retorna o ID da medicação deletada
            } else {
                throw new NoSuchElementException("Medicação não encontrada para o usuário especificado");
            }
        } else {
            throw new RuntimeException("Token inválido ou não autenticado");
        }
    }


    public void editarMedicacaoDoUsuarioAutenticado(String token, Long idMedicacao, Medicacao novaMedicacao) {
        String cpfUsuario = tokenService.validateToken(token);

        if (!cpfUsuario.isEmpty()) {
            Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpfUsuario);

            // Buscar a medicação específica do usuário
            Optional<Medicacao> medicacaoParaEditarOpt = medicacaoRepository.findById(idMedicacao);

            if (medicacaoParaEditarOpt.isPresent()) {
                Medicacao medicacaoParaEditar = medicacaoParaEditarOpt.get();

                // Verificar se a medicação pertence ao usuário autenticado
                if (medicacaoParaEditar.getUsuario().equals(usuario)) {
                    // Atualizar os campos da medicação com os valores da novaMedicacao
                    medicacaoParaEditar.setNomeMedicamento(novaMedicacao.getNomeMedicamento());
                    medicacaoParaEditar.setIntervalo(novaMedicacao.getIntervalo());
                    medicacaoParaEditar.setQuantidade(novaMedicacao.getQuantidade());
                    medicacaoParaEditar.setDataInicio(novaMedicacao.getDataInicio());
                    medicacaoParaEditar.setDataFim(novaMedicacao.getDataFim());
                    medicacaoParaEditar.setHoraInicio(novaMedicacao.getHoraInicio());

                    // Salvar a medicação atualizada
                    medicacaoRepository.save(medicacaoParaEditar);
                } else {
                    throw new RuntimeException("A medicação não pertence ao usuário autenticado");
                }
            } else {
                throw new NoSuchElementException("Medicação não encontrada para o ID especificado");
            }
        } else {
            throw new RuntimeException("Token inválido ou não autenticado");
        }
    }
    //Para realizar a Busca do historico por CPF

    public List<Medicacao> buscarMedicacoesDoUsuarioAutenticado(String token) {
        String cpfUsuario = tokenService.validateToken(token);

        if (!cpfUsuario.isEmpty()) {
            Usuario usuario = usuarioService.buscarUsuarioPorCpf(cpfUsuario);
            return buscarMedicacoesPorUsuario(usuario);
        } else {
            // Lidar com o token inválido ou não autenticado
            return Collections.emptyList(); // Ou outra lógica de erro/manipulação adequada
        }
    }

    public List<Medicacao> buscarMedicacoesPorUsuario(Usuario usuario) {
        return medicacaoRepository.findByUsuario(usuario);
    }

    public void deletarMedicacao(Long idMedicacao) {
        medicacaoRepository.deleteById(idMedicacao);
    }
}






