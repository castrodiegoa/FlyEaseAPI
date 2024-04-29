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
@Table(name = "vuelos")
public class Vuelo implements Serializable {

    @Id
    @Column(name = "idvuelo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVuelo;

    @Column(name = "preciovuelo")
    private Double precioVuelo;

    @Column(name = "tarifatemporada")
    private Double tarifaTemporada;

    @Column(name = "descuento")
    private Double descuento;

    @Column(name = "distanciarecorrida")
    private Double distanciaRecorrida;

    @Column(name = "fechayhorallegada")
    private LocalDateTime fechayhorallegada;

    @Column(name = "fechayhoradesalida")
    private LocalDateTime fechayhoradesalida;

    @Column(name = "cupo")
    private Boolean cupo;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddespegue")
    private Aeropuerto aeropuertoDespegue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iddestino")
    private Aeropuerto aeropuertoDestino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idestado")
    private Estado estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idavion")
    private Avion avion;

    //@OneToMany(mappedBy = "vuelo", fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Boleto> boletos;

}
