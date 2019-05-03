package br.com.vsm.crm.api;

import lombok.Data;

@Data
public class CustomHttpResponse {

    private String message;

    private CustomHttpResponseStatus status;
}
