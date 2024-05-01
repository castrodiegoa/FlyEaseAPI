package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AdministadorDto;
import com.flyease.flyeaseapirest.model.dto.EstadoDto;
import com.flyease.flyeaseapirest.model.entity.Administrador;
import com.flyease.flyeaseapirest.model.entity.Estado;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAdministradorService;
import com.flyease.flyeaseapirest.service.IEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class AdministradorController {

    @Autowired
    private IAdministradorService administradorService;

    @GetMapping("administradores")
    public ResponseEntity<?> showAll() {
        List<Administrador> getList = administradorService.listAlll();
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

}
