package com.flyease.flyeaseapirest.model.dto;

import com.flyease.flyeaseapirest.model.entity.Asiento;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class BoletoDto implements Serializable {

    private Integer idBoleto;

    private Double precio;

    private Double descuento;

    private Double precioTotal;

    private LocalDateTime fechaRegistro;

    @NotNull(message = "Cliente requerido!")
    private Cliente cliente;

    @NotNull(message = "Asiento requerido!")
    private Asiento asiento;

    @NotNull(message = "Vuelo requerido!")
    private Vuelo vuelo;

}
