package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.model.entity.Region;
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
public class CiudadDto implements Serializable {

    private Integer idciudad;

    @Size(min = 1, max = 60)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    private LocalDateTime fecharegistro;

    @NotEmpty(message = "Imagen requerida!")
    private byte[] imagen;

    @NotNull(message = "Región requerida!")
    private Region region;

}
