package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.entity.Coordenadas;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class AeropuertoDto implements Serializable {

    private Integer idaeropuerto;

    @Size(min = 1, max = 50)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    private LocalDateTime fecharegistro;

    @NotNull(message = "Ciudad requerida!")
    private Ciudad ciudad;

    @NotNull(message = "Coordenadas requeridas!")
    private Coordenadas coordenadas;

}
