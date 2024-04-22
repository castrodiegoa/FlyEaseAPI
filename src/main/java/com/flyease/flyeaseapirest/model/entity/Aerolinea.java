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
@Table(name = "aereolineas")
public class Aerolinea implements Serializable {

    @Id
    @Column(name = "idaereolinea")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAerolinea;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigoiata")
    private String codigoIATA;

    @Column(name = "codigoicao")
    private String codigoICAO;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    //@OneToMany(mappedBy = "aerolinea", fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Avion> aviones;

}
