package apiControleMedicacao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "medicacao_notificacao")
@Table(name="medicacao_notificacao", schema = "public")
@Getter
@Setter
@Data
public class MedicacaoNotificacao  implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicacao_notificacao_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicacao_id")
    private Medicacao medicacao;

    @Column(name = "dia_hora_notificacao")
    private LocalDateTime DiahoraNotificacao;

    @Column(name = "status_hora_medicacao")
    private String statusHoraMedicacao;

    @Column(name = "medicacao_tomada")
    private String medicacaoTomada;


}
