package br.com.vsm.crm.api.pontuacao.controller;

import br.com.vsm.crm.api.CustomHttpResponse;
import br.com.vsm.crm.api.CustomHttpResponseStatus;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacao;
import br.com.vsm.crm.api.pontuacao.service.PontuacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/crm/cliente/pontuacao")
public class PontuacaoController {

    @Autowired
    private PontuacaoService service;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    private CustomHttpResponse save(@RequestBody RegraPontuacao regraPontuacao) {
        CustomHttpResponse response = new CustomHttpResponse();
        try {
            service.save(regraPontuacao);
            response.setStatus(CustomHttpResponseStatus.SUCCESS);
            response.setMessage("Regra salva");

        } catch (Exception e) {
            response.setStatus(CustomHttpResponseStatus.ERROR);
            response.setMessage("Ocorreu um erro ao salvar a regra");
        }

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private RegraPontuacao findOneById(@PathVariable Long id) {
        Optional<RegraPontuacao> opt = service.findOne(id);
        return opt.get();
    }

    @RequestMapping(value = "/all/{page}", method = RequestMethod.GET)
    private Page<RegraPontuacao> findAll(@PathVariable int page) {
        return service.findAll(PageRequest.of(page, 20, new Sort(Sort.Direction.ASC, "inicio")));
    }
}
