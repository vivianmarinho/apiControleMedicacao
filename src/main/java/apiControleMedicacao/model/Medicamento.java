package apiControleMedicacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Table(name = "medicamento" ,schema = "public")
@Entity(name = "medicamento")
@Getter
@Setter
public class Medicamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicamento_id")
    private long id;
    @Column(name = "medicamento_nome")
    private String medicamentoNome;
    //@OneToMany(mappedBy = "medicamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Medicacao> medicacaos;
}
