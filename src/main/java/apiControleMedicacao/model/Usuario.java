package apiControleMedicacao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


@Entity(name = "usuario")
@Table(name="usuario", schema = "public")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "idUsuario")

public class Usuario  implements UserDetails{

    private static final long serialVersionUID = 1L;


    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "usuario_id")
    private  Long idUsuario;

   // @ManyToOne(fetch = FetchType.LAZY, optional = true)
   // @JoinColumn(name = "medicacao_id", nullable = true)
   // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   // private Medicacao medicacao;

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



    public Usuario(String cpf,String senha, String nome, String email, String telefone){
        this.cpf=cpf;
        this.senha=senha;
        this.nome=nome;
        this.email=email;
        this.telefone=telefone;
    }

    public Usuario(Usuario usuario) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




    // @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Medicacao> medicacaos;

}
