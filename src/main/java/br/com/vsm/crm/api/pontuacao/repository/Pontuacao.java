package br.com.vsm.crm.api.pontuacao.repository;

import br.com.vsm.crm.util.CustomDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Entity
public class Pontuacao {

    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CustomDateTimeDeserializer._DATE_TIME_PATTERN)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private LocalDateTime ocorrencia;

    @OneToOne
    private RegraPontuacao regraPontuacao;
}
