package br.com.vsm.crm.api.venda.controller;

import br.com.vsm.crm.CrmTest;
import br.com.vsm.crm.api.CustomHttpResponse;
import br.com.vsm.crm.api.CustomHttpResponseStatus;
import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.cliente.repository.Genero;
import br.com.vsm.crm.api.venda.repository.Venda;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VendaControllerTest extends CrmTest {

    @LocalServerPort
    private int port;

    @Before
    public void before() {
        mockCliente();
        mockRegraPontuacao();
    }

    @Test
    public void doVendaStatusError() {
        Venda venda = new Venda();

        HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
        ResponseEntity<CustomHttpResponse> response = restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                HttpMethod.POST, entity, CustomHttpResponse.class);
        CustomHttpResponse customHttpResponse = response.getBody();

        assertEquals(CustomHttpResponseStatus.ERROR, customHttpResponse.getStatus());
    }

    @Test
    public void doVendaPontos10_GeneroF() {
        Venda venda = new Venda();
        venda.setOcorrencia(LocalDateTime.now());
        venda.setDiaOcorrencia(LocalDate.now());
        venda.setReferenciaPdv(new Random().nextLong());
        venda.setValor(10.50f);

        Cliente cliente = new Cliente();
        cliente.setId(_DEFAULT_CLIENT_ID_F);
        venda.setCliente(cliente);

        HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
        ResponseEntity<CustomHttpResponse> response = restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                HttpMethod.POST, entity, CustomHttpResponse.class);
        CustomHttpResponse customHttpResponse = response.getBody();

        assertEquals("Pontos: 10", customHttpResponse.getMessage());
    }

    @Test
    public void doVendaPontos20_GeneroF() {
        Venda venda = new Venda();
        venda.setOcorrencia(LocalDateTime.now());
        venda.setDiaOcorrencia(LocalDate.now());
        venda.setReferenciaPdv(new Random().nextLong());
        venda.setValor(10.61f);

        Cliente cliente = new Cliente();
        cliente.setId(_DEFAULT_CLIENT_ID_F);
        venda.setCliente(cliente);

        HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
        ResponseEntity<CustomHttpResponse> response = restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                HttpMethod.POST, entity, CustomHttpResponse.class);
        CustomHttpResponse customHttpResponse = response.getBody();

        assertEquals("Pontos: 20", customHttpResponse.getMessage());
    }

    @Test
    public void doVendaPontos20_GeneroM() {
        Venda venda = new Venda();
        venda.setOcorrencia(LocalDateTime.now());
        venda.setDiaOcorrencia(LocalDate.now());
        venda.setReferenciaPdv(new Random().nextLong());
        venda.setValor(10.65f);

        Cliente cliente = new Cliente();
        cliente.setId(_DEFAULT_CLIENT_ID_M);
        venda.setCliente(cliente);

        HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
        ResponseEntity<CustomHttpResponse> response = restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                HttpMethod.POST, entity, CustomHttpResponse.class);
        CustomHttpResponse customHttpResponse = response.getBody();

        assertEquals("Pontos: 20", customHttpResponse.getMessage());
    }

    @Test
    public void doVendaPontos30_GeneroM() {
        Venda venda = new Venda();
        venda.setOcorrencia(LocalDateTime.now());
        venda.setDiaOcorrencia(LocalDate.now());
        venda.setReferenciaPdv(new Random().nextLong());
        venda.setValor(10.80f);

        Cliente cliente = new Cliente();
        cliente.setId(_DEFAULT_CLIENT_ID_M);
        venda.setCliente(cliente);

        HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
        ResponseEntity<CustomHttpResponse> response = restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                HttpMethod.POST, entity, CustomHttpResponse.class);

        CustomHttpResponse customHttpResponse = response.getBody();

        assertEquals("Pontos: 30", customHttpResponse.getMessage());
    }


    @Test
    public void findByPeriod() {
        vendaRepository.deleteAll();
        for (int index = 0; index < 8; index++) {
            Venda venda = new Venda();
            if (index == 0 || index == 1) {
                venda.setOcorrencia(LocalDateTime.now().minusDays(2));
                venda.setDiaOcorrencia(LocalDate.now().minusDays(2));

            } else if (index == 2 || index == 3) {
                venda.setOcorrencia(LocalDateTime.now().plusDays(2));
                venda.setDiaOcorrencia(LocalDate.now().plusDays(2));

            } else {
                venda.setOcorrencia(LocalDateTime.now());
                venda.setDiaOcorrencia(LocalDate.now());
            }

            venda.setReferenciaPdv(new Random().nextLong());
            venda.setValor(10.80f);

            Cliente cliente = new Cliente();
            cliente.setId(_DEFAULT_CLIENT_ID_M);
            venda.setCliente(cliente);

            HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
            restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                    HttpMethod.POST, entity, CustomHttpResponse.class);
        }

        StringBuilder urlBuilder = new StringBuilder("/api/crm/venda/filtro/periodo/");
        urlBuilder.append(LocalDate.now().format(formatter))
                .append("/")
                .append(LocalDate.now().format(formatter));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<VendaResponseWrapper> responseEntity = restTemplate.exchange(_HOST + port + urlBuilder.toString(),
                HttpMethod.GET, entity, VendaResponseWrapper.class);

        VendaResponseWrapper wrapper = responseEntity.getBody();
        List<Venda> vendas = wrapper.getVendas();

        assertNotNull(vendas);
        assertEquals(4, vendas.size());
    }


    @Test
    public void findQuantidadeVendasPorGenero() {
        vendaRepository.deleteAll();
        for (int index = 0; index < 4; index++) {
            Venda venda = new Venda();
            venda.setOcorrencia(LocalDateTime.now());
            venda.setDiaOcorrencia(LocalDate.now());
            venda.setReferenciaPdv(new Random().nextLong());
            venda.setValor(10.80f);

            Cliente cliente = new Cliente();
            cliente.setId(index == 0 ? _DEFAULT_CLIENT_ID_M : _DEFAULT_CLIENT_ID_F);
            venda.setCliente(cliente);

            HttpEntity<Venda> entity = new HttpEntity<>(venda, headers);
            restTemplate.exchange(_HOST + port + "/api/crm/venda/incluir",
                    HttpMethod.POST, entity, CustomHttpResponse.class);
        }

        StringBuilder urlBuilder = new StringBuilder("/api/crm/venda//filtro/quantidadevendas/groupedbygenero/");
        urlBuilder.append(LocalDate.now().format(formatter))
                .append("/")
                .append(LocalDate.now().format(formatter));

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<VendaResponseWrapper> responseEntity = restTemplate.exchange(_HOST + port + urlBuilder.toString(),
                HttpMethod.GET, entity, VendaResponseWrapper.class);

        VendaResponseWrapper wrapper = responseEntity.getBody();
        Map<Genero, Integer> vendas = wrapper.getQuantidade();

        assertNotNull(vendas);
        assertEquals(3, vendas.get(Genero.F).intValue());
        assertEquals(1, vendas.get(Genero.M).intValue());
    }
}
