package matrix.develop.gerenciadorclientes.controller;

import matrix.develop.gerenciadorclientes.domain.Cliente;
import matrix.develop.gerenciadorclientes.service.ClienteService;
import matrix.develop.gerenciadorclientes.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private DateUtil dateUtil;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a busca de cliente por ID")
    void testFindById() {
        when(clienteService.findById(1L)).thenReturn(new Cliente());
        ResponseEntity<Cliente> response = clienteController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(clienteService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Testa o salvamento de um novo cliente")
    void testSave() {
        Cliente clienteParaSalvar = new Cliente();
        when(clienteService.save(clienteParaSalvar)).thenReturn(clienteParaSalvar);
        ResponseEntity<Cliente> response = clienteController.save(clienteParaSalvar);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clienteParaSalvar, response.getBody());
        verify(clienteService, times(1)).save(clienteParaSalvar);
    }

    @Test
    @DisplayName("Testa a validação de um CPF se á duplicidade")
    void testSaveWithDuplicateCpf() {
        Cliente clienteComCpfDuplicado = new Cliente();
        when(clienteService.save(clienteComCpfDuplicado))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF or CNPJ is already in use."));
        assertThrows(ResponseStatusException.class,
                () -> clienteController.save(clienteComCpfDuplicado));
        verify(clienteService, times(1)).save(clienteComCpfDuplicado);
    }

    @Test
    @DisplayName("Testa a exclusão de um cliente")
    void testDelete() {
        long idParaExcluir = 1L;
        ResponseEntity<Void> response = clienteController.delete(idParaExcluir);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).delete(idParaExcluir);
    }

    @Test
    @DisplayName("Testa a listagem de clientes")
    void testList() {
        when(dateUtil.formatLocalDateTimeToDataBaseStyle(any())).thenReturn("data_formatada");
        when(clienteService.listAll()).thenReturn(List.of(new Cliente()));
        ResponseEntity<List<Cliente>> response = clienteController.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(clienteService, times(1)).listAll();
    }
}
