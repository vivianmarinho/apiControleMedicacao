package apiControleMedicacao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "medicamento" ,schema = "public")
@Entity(name = "medicamento")
@Getter
@Setter
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medicamento_id")
    private long id;
    @Column(name = "medicamento_nome")
    private String medicamentoNome;
}
