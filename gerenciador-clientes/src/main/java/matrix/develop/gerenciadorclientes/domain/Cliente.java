package matrix.develop.gerenciadorclientes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matrix.develop.gerenciadorclientes.enums.ContactType;
import matrix.develop.gerenciadorclientes.enums.DocumentType;
import matrix.develop.gerenciadorclientes.enums.Status;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

//      -----Representa os dados de cadastros-----

@Data   //-----Cria os getters e setters-----
@AllArgsConstructor     //-----Cria os construtores-----
@NoArgsConstructor
@Entity
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private DocumentType identificacao;
    @CPF
    private String cpf;
    @CNPJ
    private String cnpj;
    private DocumentType registro;
    private String numRegistro;
    private ContactType tipoContato;
    private String telefone;
    private String dataCadastro;
    private Status situacao;

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

