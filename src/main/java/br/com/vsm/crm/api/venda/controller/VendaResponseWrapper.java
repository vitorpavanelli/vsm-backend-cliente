package br.com.vsm.crm.api.venda.controller;

import br.com.vsm.crm.api.cliente.repository.Genero;
import br.com.vsm.crm.api.venda.repository.Venda;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class VendaResponseWrapper {

    private List<Venda> vendas;

    private Map<Genero, Integer> quantidade;
}
