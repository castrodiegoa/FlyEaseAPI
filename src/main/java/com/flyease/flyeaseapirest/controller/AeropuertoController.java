package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.dto.CiudadDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAeropuertoService;
import com.flyease.flyeaseapirest.service.ICiudadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class AeropuertoController {

    @Autowired
    private IAeropuertoService aeropuertoService;

    @GetMapping("aeropuertos")
    public ResponseEntity<?> showAll() {
        List<Aeropuerto> getList = aeropuertoService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("aeropuertos");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("aeropuertos/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Aeropuerto aeropuerto = aeropuertoService.findById(id);

        if (aeropuerto == null) {
            throw new ResourceNotFoundException("aeropuerto", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(AeropuertoDto.builder()
                                .idAeropuerto(aeropuerto.getIdAeropuerto())
                                .nombre(aeropuerto.getNombre())
                                .fechaRegistro(aeropuerto.getFechaRegistro())
                                .ciudad(aeropuerto.getCiudad())
                                .coordenadas(aeropuerto.getCoordenadas())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("aeropuertos")
    public ResponseEntity<?> create(@RequestBody @Valid AeropuertoDto aeropuertoDto) {
        Aeropuerto aeropuertoSave = null;
        try {
            aeropuertoSave = aeropuertoService.save(aeropuertoDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .sucess(true)
                    .response(AeropuertoDto.builder()
                            .idAeropuerto(aeropuertoSave.getIdAeropuerto())
                            .nombre(aeropuertoSave.getNombre())
                            .fechaRegistro(aeropuertoSave.getFechaRegistro())
                            .ciudad(aeropuertoSave.getCiudad())
                            .coordenadas(aeropuertoSave.getCoordenadas())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("aeropuertos/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid AeropuertoDto aeropuertoDto, @PathVariable Integer id) {
        Aeropuerto aeropuertoUpdate = null;
        try {
            if (aeropuertoService.existsById(id)) {
                aeropuertoDto.setIdAeropuerto(id);
                aeropuertoUpdate = aeropuertoService.update(aeropuertoDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(AeropuertoDto.builder()
                                .idAeropuerto(aeropuertoUpdate.getIdAeropuerto())
                                .nombre(aeropuertoUpdate.getNombre())
                                .fechaRegistro(aeropuertoUpdate.getFechaRegistro())
                                .ciudad(aeropuertoUpdate.getCiudad())
                                .coordenadas(aeropuertoUpdate.getCoordenadas())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("aeropuerto", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("aeropuertos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Aeropuerto aeropuertoDelete = aeropuertoService.findById(id);
            if (aeropuertoDelete != null) {
                aeropuertoService.delete(aeropuertoDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("aeropuerto", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }


}
