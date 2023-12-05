package matrix.develop.gerenciadorclientes.service;

import matrix.develop.gerenciadorclientes.domain.Cliente;
import matrix.develop.gerenciadorclientes.enums.ContactType;
import matrix.develop.gerenciadorclientes.enums.DocumentType;
import matrix.develop.gerenciadorclientes.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private ClienteService clienteService;

    @BeforeEach
    @DisplayName("Configuração do Serviço de Cliente")
    public void setUp() {
        clienteService = new ClienteService();
    }

   @Test
   @DisplayName("Testa a busca de cliente por ID")
    public void testFindById() {
        Cliente foundCliente = clienteService.findById(1L);
        assertNotNull(foundCliente);
        assertEquals("Alisson Vital", foundCliente.getName());
    }

    @Test
    @DisplayName("Testa o salvamento de um novo cliente")
    public void testSave() {
        Cliente newCliente = Cliente.builder()
                .name("New Client")
                .identificacao(DocumentType.CPF)
                .cpf("123.456.789-00")
                .registro(DocumentType.RG)
                .numRegistro("9876543")
                .tipoContato(ContactType.TELEFONE)
                .telefone("123456789")
                .dataCadastro("01.01.2023")
                .situacao(Status.ATIVO)
                .build();

        Cliente savedCliente = clienteService.save(newCliente);
        assertNotNull(savedCliente.getId());
        assertEquals("New Client", savedCliente.getName());

        List<Cliente> allClientes = clienteService.listAll();
        assertTrue(allClientes.contains(savedCliente));
    }

    @Test
    @DisplayName("Testa o salvamento de um cliente com CPF duplicado")
    public void testSaveWithDuplicateCpf() {
        Cliente duplicateCpfCliente = Cliente.builder()
                .name("Duplicate CPF")
                .identificacao(DocumentType.CPF)
                .cpf("289.738.050-02") // Existing CPF
                .build();

        assertThrows(ResponseStatusException.class, () -> clienteService.save(duplicateCpfCliente));
    }

    @Test
    @DisplayName("Testa a exclusão de um cliente")
    public void testDelete() {
        clienteService.delete(1L);

        // Ensure the client with ID 1 is no longer in the list
        List<Cliente> allClientes = clienteService.listAll();
        assertTrue(allClientes.stream().noneMatch(cliente -> cliente.getId() == 1L));
    }

    @Test
    @DisplayName("Testa a substituição de um cliente")
    public void testReplace() {
        Cliente updatedCliente = Cliente.builder()
                .id(1L)
                .name("Updated Name")
                .build();

        clienteService.replace(updatedCliente);

        Cliente foundCliente = clienteService.findById(1L);
        assertEquals("Updated Name", foundCliente.getName());
    }
}
