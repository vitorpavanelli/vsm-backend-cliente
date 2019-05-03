package br.com.vsm.crm.api.cliente.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;

    private int idade;

    private Genero genero;

    private String cpf;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
}
