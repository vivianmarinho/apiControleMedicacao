package apiControleMedicacao.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "medicacao" ,schema = "public")
@Entity(name = "medicacao")
@Getter
@Setter
public class Medicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicacao_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "medicamento_id", nullable = true)
    private Medicamento medicamento;

    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(name = "medicacao_data_inicio")
    private LocalDate dataInicio;

    @JsonFormat(pattern = "dd-MM-yyy")
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
