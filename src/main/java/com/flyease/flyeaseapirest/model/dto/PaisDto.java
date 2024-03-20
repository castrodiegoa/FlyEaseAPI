package com.flyease.flyeaseapirest.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
public class PaisDto implements Serializable {

    private Integer idPais;
    @Size(min = 1, max = 60)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;
    private Date fechaRegistro;

}
