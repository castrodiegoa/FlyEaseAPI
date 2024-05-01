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
    private Double velocidadpromedio;

    @Column(name = "cantidadpasajeros")
    private Integer cantidadpasajeros;

    @Column(name = "cantidadcarga")
    private Double cantidadcarga;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idaereolinea")
    private Aerolinea aereolinea;

    @OneToMany(mappedBy = "avion", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vuelo> vuelos;

}
