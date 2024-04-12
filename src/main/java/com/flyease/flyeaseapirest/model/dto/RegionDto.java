package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Pais;
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
public class RegionDto implements Serializable {

    private Integer idRegion;

    @Size(min = 1, max = 60)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    private LocalDateTime fechaRegistro;

    @NotNull(message = "País requerido!")
    private Pais pais;

}
