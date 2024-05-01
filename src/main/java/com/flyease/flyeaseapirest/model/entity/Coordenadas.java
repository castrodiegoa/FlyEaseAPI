package com.flyease.flyeaseapirest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "coordenadas")
public class Coordenadas implements Serializable {

    @Id
    @Column(name = "idcoordenada")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcoordenada;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @OneToOne(mappedBy = "coordenadas", fetch = FetchType.LAZY)
    @JsonIgnore
    private Aeropuerto aeropuerto;
}
