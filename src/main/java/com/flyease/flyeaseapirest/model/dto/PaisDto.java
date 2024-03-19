package com.flyease.flyeaseapirest.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
public class PaisDto implements Serializable {

    private Integer idPais;
    private String nombre;
    private Date fechaRegistro;

}
