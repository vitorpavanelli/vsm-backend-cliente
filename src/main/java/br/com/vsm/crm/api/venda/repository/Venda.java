package br.com.vsm.crm.api.venda.repository;

import br.com.vsm.crm.api.cliente.repository.Cliente;
import br.com.vsm.crm.api.pontuacao.repository.Pontuacao;
import br.com.vsm.crm.util.CustomDateDeserializer;
import br.com.vsm.crm.util.CustomDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Venda {

    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CustomDateTimeDeserializer._DATE_TIME_PATTERN)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime ocorrencia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CustomDateDeserializer._DATE_TIME_PATTERN)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate diaOcorrencia;

    private Long referenciaPdv;

    @ManyToOne
    private Cliente cliente;

    private float valor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Pontuacao pontuacao;
}
