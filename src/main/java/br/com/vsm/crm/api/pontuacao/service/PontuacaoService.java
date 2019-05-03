package br.com.vsm.crm.api.pontuacao.service;

import br.com.vsm.crm.api.pontuacao.repository.PontuacaoRepository;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacao;
import br.com.vsm.crm.api.pontuacao.repository.RegraPontuacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PontuacaoService {

    @Autowired
    private PontuacaoRepository repository;

    @Autowired
    private RegraPontuacaoRepository regraPontuacaoRepository;

    public RegraPontuacao findOne(float valor) {
        return regraPontuacaoRepository.findByValor(valor);
    }

    public void save(RegraPontuacao regraPontuacao) {
        regraPontuacaoRepository.save(regraPontuacao);
    }

    public Optional<RegraPontuacao> findOne(Long id) {
        return regraPontuacaoRepository.findById(id);
    }

    public Page<RegraPontuacao> findAll(Pageable pageable) {
        return regraPontuacaoRepository.findAll(pageable);
    }
}
