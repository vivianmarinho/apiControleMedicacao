package apiControleMedicacao.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "medicacao" ,schema = "public")
@Entity(name = "medicacao")
@Getter
@Setter
public class Medicacao  implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicacao_id")
    private long idMedicacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
   // @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
   // @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "medicamento_id", nullable = true)
    private Medicamento medicamento;

    //@JsonFormat(pattern = "dd-MM-yyy")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "medicacao_data_inicio")
    private LocalDate dataInicio;

    //@JsonFormat(pattern = "dd-MM-yyy")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "medicacao_data_fim")
    private LocalDate dataFim;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "medicacao_hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "medicacao_quantidade")
    private String quantidade;

    @DateTimeFormat(pattern = "HH:mm")
    @Column (name = "medicacao_intervalo")
    private LocalTime intervalo;




}
