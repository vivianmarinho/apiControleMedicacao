package apiControleMedicacao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity(name = "medicacao_notificacao")
@Table(name="medicacao_notificacao", schema = "public")
@Getter
@Setter
@Data
public class MedicacaoNotificacao  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicacao_notificacao_id")
    private  Long id;
    //@Column(name = "data_notificacao")
    //private LocalDate dataNotificacao;

    @Column(name = "dia_hora_notificacao")
    private LocalTime DiahoraNotificacao;

    @Column(name = "status_hora_medicacao")
    private String statusHoraMedicacao;

    @Column(name = "medicacao_tomada")
    private String medicacaoTomada;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "medicacao_id", nullable = true)
    private Medicacao medicacao;


    public void getDiahoraNotificacao(List<LocalDateTime> diaHorarioNotificacao) {
    }

    public void setDiahoraNotificacao(List<LocalDateTime> diaHorarioNotificacao) {
    }
}
