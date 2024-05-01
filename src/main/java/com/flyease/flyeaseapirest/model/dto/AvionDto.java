package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.model.entity.Pais;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class AvionDto implements Serializable {

    @Size(min = 1, max = 10)
    @NotEmpty(message = "Id requerido!")
    private String idavion;

    @Size(min = 1, max = 50)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    @Size(min = 1, max = 20)
    @NotEmpty(message = "Modelo requerido!")
    private String modelo;

    @Size(min = 1, max = 40)
    @NotEmpty(message = "Fabricante requerido!")
    private String fabricante;

    private Double velocidadpromedio;

    private Integer cantidadpasajeros;

    private Double cantidadcarga;

    private LocalDateTime fecharegistro;

    @NotNull(message = "Aerolínea requerida!")
    private Aerolinea aereolinea;

}
