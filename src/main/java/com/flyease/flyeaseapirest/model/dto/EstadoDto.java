package com.flyease.flyeaseapirest.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class EstadoDto implements Serializable {

    private Integer idEstado;

    @Size(min = 1, max = 20)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    @Size(max = 200)
    private String descripcion;

    private LocalDateTime fechaRegistro;

    private Boolean detencion;

}
