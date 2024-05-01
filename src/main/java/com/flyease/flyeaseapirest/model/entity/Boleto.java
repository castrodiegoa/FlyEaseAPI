package com.flyease.flyeaseapirest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "boletos")
public class Boleto implements Serializable {

    @Id
    @Column(name = "idboleto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idboleto;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "preciototal")
    private Double preciototal;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "numerodocumento")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idasiento")
    private Asiento asiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idvuelo")
    private Vuelo vuelo;

}
