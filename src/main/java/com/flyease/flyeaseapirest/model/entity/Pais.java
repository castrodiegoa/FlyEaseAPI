package com.flyease.flyeaseapirest.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "paises")
public class Pais implements Serializable {

    @Id
    @Column(name = "idpais")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPais;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecharegistro")
    private Date fechaRegistro;

}
