package br.com.vsm.crm.config.security;

import lombok.Data;

import java.util.Set;

@Data
public class AccessGrants {

    private String login;
    private boolean admin;
    private Set<String> accesses;
}
