package br.com.vsm.crm;

import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.cliente.repository.ClienteRepository;
import br.com.vsm.crm.api.cliente.repository.Endereco;
import br.com.vsm.crm.api.cliente.repository.Genero;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacao;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacaoRepository;
import br.com.vsm.crm.api.venda.repository.VendaRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application.yml")
public abstract class CrmTest {

    protected static Long _DEFAULT_CLIENT_ID_F = 1L;
    protected static Long _DEFAULT_CLIENT_ID_M = 2L;

    protected static String _HOST;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected RegraPontuacaoRepository regraPontuacaoRepository;

    @Autowired
    protected VendaRepository vendaRepository;

    protected static HttpHeaders headers;

    static {
        if (headers == null) {
            headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
        }

        if (_HOST == null) {
            _HOST = "http://localhost:";
        }
    }

    protected void mockCliente() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCidade("Cidade");
        endereco.setEstado("SP");
        endereco.setCep("12345-678");
        endereco.setRua("Rua dos Sauros, 123");

        Cliente cliente = new Cliente();
        cliente.setId(_DEFAULT_CLIENT_ID_F);
        //cliente.setAdicionadoEm(LocalDateTime.now());
        cliente.setCpf("111.222.333-44");
        cliente.setEmail("cliente@email.com.br");
        cliente.setGenero(Genero.F);
        cliente.setIdade(28);
        cliente.setNome("Mrs da Silva Sauro");
        cliente.setEndereco(endereco);

        clienteRepository.save(cliente);

        cliente = new Cliente();
        cliente.setId(_DEFAULT_CLIENT_ID_M);
        //cliente.setAdicionadoEm(LocalDateTime.now());
        cliente.setCpf("111.222.333-44");
        cliente.setEmail("cliente@email.com.br");
        cliente.setGenero(Genero.M);
        cliente.setIdade(28);
        cliente.setNome("Mr da Silva Sauro");
        cliente.setEndereco(endereco);

        clienteRepository.save(cliente);
    }

    protected void mockRegraPontuacao() {
        RegraPontuacao regraPontuacao = new RegraPontuacao();
        regraPontuacao.setId(1l);
        regraPontuacao.setInicio(LocalDate.now().minusDays(1));
        regraPontuacao.setFim(LocalDate.now().plusDays(1));
        regraPontuacao.setValorInicio(10.50f);
        regraPontuacao.setValorFim(10.60f);
        regraPontuacao.setPontos(10);

        regraPontuacaoRepository.save(regraPontuacao);

        regraPontuacao = new RegraPontuacao();
        regraPontuacao.setId(2l);
        regraPontuacao.setInicio(LocalDate.now().minusDays(1));
        regraPontuacao.setFim(LocalDate.now().plusDays(1));
        regraPontuacao.setValorInicio(10.61f);
        regraPontuacao.setValorFim(10.70f);
        regraPontuacao.setPontos(20);

        regraPontuacaoRepository.save(regraPontuacao);

        regraPontuacao = new RegraPontuacao();
        regraPontuacao.setId(3l);
        regraPontuacao.setInicio(LocalDate.now().minusDays(1));
        regraPontuacao.setFim(LocalDate.now().plusDays(1));
        regraPontuacao.setValorInicio(10.71f);
        regraPontuacao.setValorFim(10.80f);
        regraPontuacao.setPontos(30);

        regraPontuacaoRepository.save(regraPontuacao);
    }
}
