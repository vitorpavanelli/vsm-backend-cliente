package br.com.vsm.crm.api.pontuacao.repository;

import br.com.vsm.crm.util.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class RegraPontuacao {

    @Id
    @GeneratedValue
    private Long id;

    private float valorInicio;

    private float valorFim;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CustomDateDeserializer._DATE_TIME_PATTERN)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate inicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CustomDateDeserializer._DATE_TIME_PATTERN)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate fim;

    private int pontos;
}
