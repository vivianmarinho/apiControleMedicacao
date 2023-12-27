package apiControleMedicacao.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    @JoinColumn(name = "usuario_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @JsonFormat(pattern = "dd/MM/yyyy")
    //@JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "medicacao_data_inicio")
    private LocalDate dataInicio;

    @JsonFormat(pattern = "dd/MM/yyyy")
    //@JsonFormat(pattern = "yyyy-MM-dd")
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

    @Column(name = "medicacao_nome_medicamento")
    private String nomeMedicamento;




}
