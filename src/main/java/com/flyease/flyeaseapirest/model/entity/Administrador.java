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
@Table(name = "administradores")
public class Administrador implements Serializable {

    @Id
    @Column(name = "idadministrador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idadministrador;

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

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "clave")
    private String clave;

    @Column(name = "fecharegistro")
    private LocalDateTime fecharegistro;

}
