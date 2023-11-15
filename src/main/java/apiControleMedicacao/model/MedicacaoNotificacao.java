package apiControleMedicacao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "medicacao_notificacao")
@Table(name="medicacao_notificacao", schema = "public")
@Getter
@Setter
@Data
public class MedicacaoNotificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicacao_notificacao_id")
    private  Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "medicacao_id", nullable = true)
    private Medicacao medicacao;

    @Column(name = "data_notificacao")
    private LocalDate dataNotificacao;

    @Column(name = "hora_notificacao")
    private LocalTime horaNotificacao;

    @Column(name = "status_hora_medicacao")
    private String statusHoraMedicacao;


}
