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
public class AerolineaDto implements Serializable {

    private Integer idAerolinea;

    @Size(min = 1, max = 60)
    @NotEmpty(message = "Nombre requerido!")
    private String nombre;

    @Size(min = 2, max = 2)
    @NotEmpty(message = "Código IATA requerido!")
    private String codigoIATA;

    @Size(min = 3, max = 3)
    @NotEmpty(message = "Código ICAO requerido!")
    private String codigoICAO;

    private LocalDateTime fechaRegistro;
}
