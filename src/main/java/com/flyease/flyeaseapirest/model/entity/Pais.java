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
@Table(name = "paises")
public class Pais implements Serializable {

    @Id
    @Column(name = "idpais")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpais;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Region> regiones;

}
