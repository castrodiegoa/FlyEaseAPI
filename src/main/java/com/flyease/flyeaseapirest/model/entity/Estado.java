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
@Table(name = "estados")
public class Estado implements Serializable {

    @Id
    @Column(name = "idestado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idestado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @Column(name = "detencion")
    private Boolean detencion;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vuelo> vuelos;

}
