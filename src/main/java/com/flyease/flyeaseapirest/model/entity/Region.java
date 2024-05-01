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
@Table(name = "regiones")
public class Region implements Serializable {

    @Id
    @Column(name = "idregion")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idregion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idpais")
    private Pais pais;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ciudad> ciudades;
    
}
