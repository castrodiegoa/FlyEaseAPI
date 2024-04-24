package com.flyease.flyeaseapirest.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class ClienteDto implements Serializable {

    @Size(min = 1, max = 10)
    @NotEmpty(message = "Número de documento requerido!")
    private String numerodocumento;

    @Size(min = 1, max = 15)
    @NotEmpty(message = "Tipo de documento requerido!")
    private String tipodocumento;

    @Size(min = 1, max = 40)
    @NotEmpty(message = "Nombres requeridos!")
    private String nombres;

    @Size(min = 1, max = 40)
    @NotEmpty(message = "Apellidos requeridos!")
    private String apellidos;

    @Size(min = 1, max = 10)
    @NotEmpty(message = "Celular requerido!")
    private String celular;

    @Size(min = 1, max = 50)
    @NotEmpty(message = "Correo requerido!")
    @Email
    private String correo;

    private LocalDateTime fechaRegistro;

}
