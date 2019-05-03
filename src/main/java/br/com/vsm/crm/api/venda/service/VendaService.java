package br.com.vsm.crm.api.venda.service;

import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.cliente.repository.Genero;
import br.com.vsm.crm.api.cliente.service.ClienteService;
import br.com.vsm.crm.api.pontuacao.repository.Pontuacao;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacao;
import br.com.vsm.crm.api.pontuacao.service.PontuacaoService;
import br.com.vsm.crm.api.venda.repository.Venda;
import br.com.vsm.crm.api.venda.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PontuacaoService pontuacaoService;

    private Pontuacao getPontuacao(Venda venda) {
        RegraPontuacao regraPontuacao = pontuacaoService.findOne(venda.getValor());
        Pontuacao pontuacao = new Pontuacao();
        pontuacao.setOcorrencia(venda.getOcorrencia());
        pontuacao.setRegraPontuacao(regraPontuacao);

        return pontuacao;
    }

    private void validateVenda(Venda venda) throws Exception {
        if (venda == null) {
            throw new Exception("Venda não pode ser null");
        }

        if (venda.getCliente() == null || venda.getCliente().getId() == null) {
            throw new Exception("Cliente precisa ser informado");
        }

        if (venda.getValor() == 0) {
            throw new Exception("Valor da venda não informado");
        }

        if (venda.getReferenciaPdv() == 0) {
            throw new Exception("Número de referência do PDV não foi informado");
        }
    }

    public int doVenda(Venda venda) throws Exception {
        validateVenda(venda);
        Optional<Cliente> clienteOpt = clienteService.findOne(venda.getCliente().getId());
        if (clienteOpt.isEmpty()) {
            throw new Exception("Cliente não existe");
        }

        venda.setCliente(clienteOpt.get());
        venda.setPontuacao(getPontuacao(venda));

        repository.save(venda);

        return venda.getPontuacao().getRegraPontuacao().getPontos();
    }

    public List<Venda> findByPeriod(LocalDate inicio, LocalDate fim) {
        return repository.findByPeriod(inicio, fim);
    }

    public Map<Genero, Integer> findQuantidadeVendasPorGenero(LocalDate inicio, LocalDate fim) {
        List<Venda> vendas = repository.findByPeriod(inicio, fim);
        Map<Genero, List<Venda>> listMap = vendas.stream().collect(Collectors.groupingBy(venda -> venda.getCliente().getGenero()));

        Map map = new HashMap();
        map.put(Genero.F, listMap.get(Genero.F).size());
        map.put(Genero.M, listMap.get(Genero.M).size());

        return map;
    }
}
