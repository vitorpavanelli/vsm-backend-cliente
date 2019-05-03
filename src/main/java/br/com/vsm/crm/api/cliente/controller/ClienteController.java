package br.com.vsm.crm.api.cliente.controller;

import br.com.vsm.crm.api.CustomHttpResponse;
import br.com.vsm.crm.api.CustomHttpResponseStatus;
import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/crm/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private CustomHttpResponse save(@RequestBody Cliente cliente) {
        CustomHttpResponse response = new CustomHttpResponse();
        try {
            service.save(cliente);
            response.setStatus(CustomHttpResponseStatus.SUCCESS);
            response.setMessage("Cliente salvo");

        } catch (Exception e) {
            response.setStatus(CustomHttpResponseStatus.ERROR);
            response.setMessage("Ocorreu um erro ao salvar o cliente");
        }

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Cliente findOneById(@PathVariable Long id) {
        Optional<Cliente> opt = service.findOne(id);
        return opt.get();
    }

    @RequestMapping(value = "/all/{page}", method = RequestMethod.GET)
    private Page<Cliente> findAll(@PathVariable int page) {
        return service.findAll(PageRequest.of(page, 20, new Sort(Sort.Direction.ASC, "nome")));
    }
}
