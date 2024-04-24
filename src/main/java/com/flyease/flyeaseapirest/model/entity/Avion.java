package com.flyease.flyeaseapirest.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "aviones")
public class Avion implements Serializable {

    @Id
    @Column(name = "idavion")
    private String idavion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "fabricante")
    private String fabricante;

    @Column(name = "velocidadpromedio")
    private Double velocidadPromedio;

    @Column(name = "cantidadpasajeros")
    private Integer cantidadPasajeros;

    @Column(name = "cantidadcarga")
    private Double cantidadCarga;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idaereolinea")
    private Aerolinea aerolinea;

    //@OneToMany(mappedBy = "avion", fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Vuelo> vuelos;

}
