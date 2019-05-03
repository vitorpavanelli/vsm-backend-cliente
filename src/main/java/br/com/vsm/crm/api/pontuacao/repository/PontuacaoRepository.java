package br.com.vsm.crm.api.pontuacao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontuacaoRepository extends PagingAndSortingRepository<Pontuacao, Long> {
}
