package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AdministadorDto;
import com.flyease.flyeaseapirest.model.dto.VueloDto;
import com.flyease.flyeaseapirest.model.entity.Administrador;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import com.flyease.flyeaseapirest.model.payload.InformeResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAdministradorService;
import com.flyease.flyeaseapirest.service.IReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
@Tag(name = "Administradores", description = "Métodos de autenticación y lectura para Administradores.")
public class AdministradorController {

    @Autowired
    private IAdministradorService administradorService;

    @Operation(summary = "Obtener todos los administradores registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @GetMapping("administradores")
    public ResponseEntity<?> showAll() {
        List<Administrador> getList = administradorService.listAll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("estados");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @Operation(summary = "Obtener un administrador por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @GetMapping("administradores/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Administrador administrador = administradorService.findById(id);

        if (administrador == null) {
            throw new ResourceNotFoundException("administrador", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(AdministadorDto.builder()
                                .idadministrador(administrador.getIdadministrador())
                                .numerodocumento(administrador.getNumerodocumento())
                                .tipodocumento(administrador.getTipodocumento())
                                .nombres(administrador.getNombres())
                                .apellidos(administrador.getApellidos())
                                .celular(administrador.getCelular())
                                .correo(administrador.getCorreo())
                                .estado(administrador.getEstado())
                                .usuario(administrador.getUsuario())
                                .clave(administrador.getClave())
                                .fecharegistro(administrador.getFecharegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @Operation(summary = "Autenticar un administrador usando sus credenciales usuario y contraseña.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Administrador.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @PostMapping("administradores/authentication")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AdministadorDto administadorDto) {
        Boolean administradorEncontrado = administradorService.existsByUsernameAndPassword(administadorDto.getUsuario(), administadorDto.getClave());

        if (!administradorEncontrado) {
            throw new ResourceNotFoundException("Administrador", "Intente nuevamente");
        }

        return new ResponseEntity<>(
                InformeResponse.builder()
                        .mensaje("Administrador válido")
                        .success(true)
                        .build()
                , HttpStatus.OK);

    }

}
