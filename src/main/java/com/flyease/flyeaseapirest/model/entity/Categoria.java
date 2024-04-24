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
@Table(name = "categorias")
public class Categoria implements Serializable {

    @Id
    @Column(name = "idcategoria")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estadocategoria")
    private Boolean estadoCategoria;

    @Column(name = "tarifa")
    private Double tarifa;

    @Column(name = "comercial")
    private Boolean comercial;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    //@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Asiento> asientos;

}
