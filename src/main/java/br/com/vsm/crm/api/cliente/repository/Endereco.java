package br.com.vsm.crm.api.cliente.repository;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue
    private Long id;

    private String rua;

    private String cidade;

    private String estado;

    private String cep;

}
