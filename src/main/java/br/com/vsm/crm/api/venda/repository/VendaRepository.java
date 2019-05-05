package br.com.vsm.crm.api.venda.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends PagingAndSortingRepository<Venda, Long> {

    @Query("select v from Venda v where v.diaOcorrencia >= :inicio and v.diaOcorrencia <= :fim")
    List<Venda> findByPeriod(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
