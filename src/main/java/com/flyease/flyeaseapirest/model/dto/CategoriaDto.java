package com.flyease.flyeaseapirest.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class CategoriaDto implements Serializable {

    private Integer idcategoria;

    @Size(min = 1, max = 30)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    @Size(max = 200)
    private String descripcion;

    private Boolean estadocategoria;

    private Double tarifa;

    private Boolean comercial;

    private LocalDateTime fecharegistro;

}
