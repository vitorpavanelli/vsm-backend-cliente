package br.com.vsm.crm.api.cliente.service;

import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public void save(Cliente cliente) {
        repository.save(cliente);
    }

    public Optional<Cliente> findOne(Long id) {
        return repository.findById(id);
    }

    public Page<Cliente> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
