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
    private Integer idaereolinea;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigoiata")
    private String codigoiata;

    @Column(name = "codigoicao")
    private String codigoicao;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @OneToMany(mappedBy = "aereolinea", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Avion> aviones;

}
