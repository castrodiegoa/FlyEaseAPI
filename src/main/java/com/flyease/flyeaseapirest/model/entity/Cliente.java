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
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @Column(name = "numerodocumento")
    private String numerodocumento;

    @Column(name = "tipodocumento")
    private String tipodocumento;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "celular")
    private String celular;

    @Column(name = "correo")
    private String correo;

    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    //@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Boleto> boletos;

}
