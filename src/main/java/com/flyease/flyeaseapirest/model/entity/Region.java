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
@Table(name = "regiones")
public class Region implements Serializable {

    @Id
    @Column(name = "idregion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpais")
    private Pais pais;

}
