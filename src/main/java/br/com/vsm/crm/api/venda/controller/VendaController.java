package br.com.vsm.crm.api.venda.controller;

import br.com.vsm.crm.api.CustomHttpResponse;
import br.com.vsm.crm.api.CustomHttpResponseStatus;
import br.com.vsm.crm.api.venda.repository.Venda;
import br.com.vsm.crm.api.venda.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/crm/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @RequestMapping(value = "/incluir", method = RequestMethod.POST)
    private CustomHttpResponse doVenda(@RequestBody Venda venda) {
        CustomHttpResponse response = new CustomHttpResponse();

        try {
            int pontos = vendaService.doVenda(venda);
            response.setMessage("Pontos: " + pontos);
            response.setStatus(CustomHttpResponseStatus.SUCCESS);

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(CustomHttpResponseStatus.ERROR);
        }

        return response;
    }

    @RequestMapping(value = "/filtro/periodo/{inicio}/{fim}", method = RequestMethod.GET)
    private VendaResponseWrapper findByPeriod(
            @PathVariable("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
            @PathVariable("fim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {

        VendaResponseWrapper wrapper = new VendaResponseWrapper();
        wrapper.setVendas(vendaService.findByPeriod(inicio, fim));

        return wrapper;
    }

    @RequestMapping(value = "/filtro/quantidadevendas/groupedbygenero/{inicio}/{fim}", method = RequestMethod.GET)
    private VendaResponseWrapper findQuantidadeVendasPorGenero(
            @PathVariable("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
            @PathVariable("fim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {

        VendaResponseWrapper wrapper = new VendaResponseWrapper();
        wrapper.setQuantidade(vendaService.findQuantidadeVendasPorGenero(inicio, fim));

        return wrapper;
    }
}
