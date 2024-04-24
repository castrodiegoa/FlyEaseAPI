package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AvionDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Avion;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAvionService;
import com.flyease.flyeaseapirest.service.IRegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class AvionController {

    @Autowired
    private IAvionService avionService;

    @GetMapping("aviones")
    public ResponseEntity<?> showAll() {
        List<Avion> getList = avionService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("aviones");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("aviones/{id}")
    public ResponseEntity<?> showById(@PathVariable String id) {
        Avion avion = avionService.findById(id);

        if (avion == null) {
            throw new ResourceNotFoundException("avión", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(AvionDto.builder()
                                .idAvion(avion.getIdavion())
                                .nombre(avion.getNombre())
                                .modelo(avion.getModelo())
                                .fabricante(avion.getFabricante())
                                .velocidadPromedio(avion.getVelocidadPromedio())
                                .cantidadPasajeros(avion.getCantidadPasajeros())
                                .cantidadCarga(avion.getCantidadCarga())
                                .fechaRegistro(avion.getFechaRegistro())
                                .aerolinea(avion.getAerolinea())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("aviones")
    public ResponseEntity<?> create(@RequestBody @Valid AvionDto avionDto) {
        Avion avionSave = null;
        try {
            avionSave = avionService.save(avionDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .sucess(true)
                    .response(AvionDto.builder()
                            .idAvion(avionSave.getIdavion())
                            .nombre(avionSave.getNombre())
                            .modelo(avionSave.getModelo())
                            .fabricante(avionSave.getFabricante())
                            .velocidadPromedio(avionSave.getVelocidadPromedio())
                            .cantidadPasajeros(avionSave.getCantidadPasajeros())
                            .cantidadCarga(avionSave.getCantidadCarga())
                            .fechaRegistro(avionSave.getFechaRegistro())
                            .aerolinea(avionSave.getAerolinea())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("aviones/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid AvionDto avionDto, @PathVariable String id) {
        Avion avionUpdate = null;
        try {
            if (avionService.existsById(id)) {
                avionUpdate = avionService.update(avionDto, id);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(AvionDto.builder()
                                .idAvion(avionUpdate.getIdavion())
                                .nombre(avionUpdate.getNombre())
                                .modelo(avionUpdate.getModelo())
                                .fabricante(avionUpdate.getFabricante())
                                .velocidadPromedio(avionUpdate.getVelocidadPromedio())
                                .cantidadPasajeros(avionUpdate.getCantidadPasajeros())
                                .cantidadCarga(avionUpdate.getCantidadCarga())
                                .fechaRegistro(avionUpdate.getFechaRegistro())
                                .aerolinea(avionUpdate.getAerolinea())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("avión", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("aviones/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Avion avionDelete = avionService.findById(id);
            if (avionDelete != null) {
                avionService.delete(avionDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("avión", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
