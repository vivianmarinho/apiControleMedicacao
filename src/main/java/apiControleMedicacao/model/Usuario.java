package apiControleMedicacao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity(name = "usuario")
@Table(name="usuario", schema = "public")
@Getter
@Setter
@Data

public class Usuario  implements Serializable{

    private static final long serialVersionUID = 1L;


    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "usuario_id")
    private  Long idUsuario;
    @Column (name = "usuario_cpf")
    private String cpf;
    @Column (name = "usuario_nome")
    private String nome;
    @Column (name = "usuario_email")
    private String email;
    @Column (name = "usuario_telefone")
    private String telefone;
    @Column (name = "usuario_senha")
    private String senha;


    ///@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Medicacao> medicacaos;

}
