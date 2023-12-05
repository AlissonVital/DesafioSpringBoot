package matrix.develop.gerenciadorclientes.domain;

import matrix.develop.gerenciadorclientes.enums.ContactType;
import matrix.develop.gerenciadorclientes.enums.DocumentType;
import matrix.develop.gerenciadorclientes.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Cliente Construction")
class ClienteTest {
    @Test
    public void testClienteConstruction() {
        // Create a sample Cliente object
        Cliente cliente = Cliente.builder()
                .id(1L)
                .name("John Doe")
                .identificacao(DocumentType.CPF)
                .cpf("123.456.789-00")
                .registro(DocumentType.RG)
                .numRegistro("9876543")
                .tipoContato(ContactType.TELEFONE)
                .telefone("123456789")
                .dataCadastro("01.01.2023")
                .situacao(Status.ATIVO)
                .build();

        // Check if the object was created successfully
        assertNotNull(cliente);
        assertEquals(1L, cliente.getId());
        assertEquals("John Doe", cliente.getName());
        assertEquals(DocumentType.CPF, cliente.getIdentificacao());
        assertEquals("123.456.789-00", cliente.getCpf());
        assertEquals(DocumentType.RG, cliente.getRegistro());
        assertEquals("9876543", cliente.getNumRegistro());
        assertEquals(ContactType.TELEFONE, cliente.getTipoContato());
        assertEquals("123456789", cliente.getTelefone());
        assertEquals("01.01.2023", cliente.getDataCadastro());
        assertEquals(Status.ATIVO, cliente.getSituacao());
    }

    // Add more test cases as needed
}
