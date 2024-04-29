package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Avion;
import com.flyease.flyeaseapirest.model.entity.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class VueloDto implements Serializable {

    private Integer idVuelo;

    private Double precioVuelo;

    private Double tarifaTemporada;

    private Double descuento;

    private Double distanciaRecorrida;

    private LocalDateTime fechayhorallegada;

    private LocalDateTime fechayhoradesalida;

    private Boolean cupo;

    private LocalDateTime fechaRegistro;

    @NotNull(message = "Aeropuerto de despegue requerido!")
    private Aeropuerto aeropuertoDespegue;

    @NotNull(message = "Aeropuerto de destino requerido!")
    private Aeropuerto aeropuertoDestino;

    @NotNull(message = "Estado requerido!")
    private Estado estado;

    @NotNull(message = "Avión requerido!")
    private Avion avion;

}
