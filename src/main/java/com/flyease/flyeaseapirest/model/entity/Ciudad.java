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
@Table(name = "ciudades")
public class Ciudad implements Serializable {

    @Id
    @Column(name = "idciudad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idciudad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

    @Column(name = "imagen")
    private byte[] imagen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idregion")
    private Region region;

    @OneToMany(mappedBy = "ciudad", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Aeropuerto> aeropuertos;

}
