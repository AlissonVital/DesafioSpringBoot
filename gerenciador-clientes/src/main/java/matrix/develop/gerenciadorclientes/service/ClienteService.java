package matrix.develop.gerenciadorclientes.service;

import matrix.develop.gerenciadorclientes.domain.Cliente;
import matrix.develop.gerenciadorclientes.enums.ContactType;
import matrix.develop.gerenciadorclientes.enums.DocumentType;
import matrix.develop.gerenciadorclientes.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//      -----Lógica de negócio-----

@Service
public class ClienteService {
    private static List<Cliente> clientes;
    static {
        clientes = new ArrayList<>(List.of(
                new Cliente(1L,
                        "Alisson Vital",
                        DocumentType.CPF,
                        "289.738.050-02",
                        null,
                        DocumentType.RG,
                        "40961366-6",
                        ContactType.TELEFONE,
                        "20524503",
                        "01.12.2023",
                        Status.ATIVO
                ), new Cliente(2L,
                        "Marcia Vital",
                        DocumentType.CPF,
                        "853.667.010-07",
                        null,
                        DocumentType.RG,
                        "40961366-6",
                        ContactType.TELEFONE,
                        "20524503",
                        "01.12.2023",
                        Status.ATIVO)
        ));
    }

    public List<Cliente> listAll() {
        return clientes;
    }

    public Cliente findById(long id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Celiente not Found"));
    }

    public Cliente save(Cliente cliente) {
        if (isCpfOrCnpjDuplicated(cliente.getCpf(), cliente.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF or CNPJ is already in use.");
        }

        cliente.setId(ThreadLocalRandom.current().nextLong(3, 100000));
        clientes.add(cliente);
        return cliente;
    }

    private boolean isCpfOrCnpjDuplicated(String cpf, String cnpj) {//  SERVIÇO PARA ALTERAR O TELEFONE DO CLIENTE
        return clientes.stream()
                .anyMatch(cliente ->
                        (cpf != null && cpf.equals(cliente.getCpf())) ||
                                (cnpj != null && cnpj.equals(cliente.getCnpj()))
                );
    }

    public void delete(long id) {
        clientes.remove(findById(id));
    }

    public void replace(Cliente cliente) {
        delete(cliente.getId());
        clientes.add(cliente);
    }
}