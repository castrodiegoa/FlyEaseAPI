package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.EstadoDto;
import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Estado;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IEstadoService;
import com.flyease.flyeaseapirest.service.IPaisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;

    @GetMapping("estados")
    public ResponseEntity<?> showAll() {
        List<Estado> getList = estadoService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("estados");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("estados/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Estado estado = estadoService.findById(id);

        if (estado == null) {
            throw new ResourceNotFoundException("estado", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(EstadoDto.builder()
                                .idEstado(estado.getIdEstado())
                                .nombre(estado.getNombre())
                                .descripcion(estado.getDescripcion())
                                .fechaRegistro(estado.getFechaRegistro())
                                .detencion(estado.getDetencion())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

}
