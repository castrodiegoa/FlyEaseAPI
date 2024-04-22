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
@Table(name = "aereopuertos")
public class Aeropuerto implements Serializable {

    @Id
    @Column(name = "idaereopuerto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAeropuerto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idciudad")
    private Ciudad ciudad;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcoordenada")
    private Coordenadas coordenadas;

}
