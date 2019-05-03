package br.com.vsm.crm.api.pontuacao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegraPontuacaoRepository extends PagingAndSortingRepository<RegraPontuacao, Long> {

    @Query("select rp from RegraPontuacao rp where :valor >= rp.valorInicio and :valor <= rp.valorFim")
    RegraPontuacao findByValor(@Param("valor") float valor);
}
