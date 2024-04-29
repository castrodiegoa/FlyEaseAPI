package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Avion;
import com.flyease.flyeaseapirest.model.entity.Categoria;
import jakarta.persistence.*;
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
public class AsientoDto implements Serializable {

    private Integer idAsiento;

    private Integer posicion;

    private Boolean disponibilidad;

    private LocalDateTime fechaRegistro;

    @NotNull(message = "Avión requerido!")
    private Avion avion;

    @NotNull(message = "Categoría requerido!")
    private Categoria categoria;

}
