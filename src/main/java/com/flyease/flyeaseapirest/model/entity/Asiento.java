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
@Table(name = "asientos")
public class Asiento implements Serializable {

    @Id
    @Column(name = "idasiento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idasiento;

    @Column(name = "posicion")
    private Integer posicion;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idavion")
    private Avion avion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idcategoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "asiento", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Boleto> boletos;

}
