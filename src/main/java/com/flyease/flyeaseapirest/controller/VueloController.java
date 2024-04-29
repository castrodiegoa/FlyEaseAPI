package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.dto.VueloDto;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IRegionService;
import com.flyease.flyeaseapirest.service.IVueloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class VueloController {

    @Autowired
    private IVueloService vueloService;

    @GetMapping("vuelos")
    public ResponseEntity<?> showAll() {
        List<Vuelo> getList = vueloService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("vuelos");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("vuelos/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Vuelo vuelo = vueloService.findById(id);

        if (vuelo == null) {
            throw new ResourceNotFoundException("vuelo", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(VueloDto.builder()
                                .idVuelo(vuelo.getIdVuelo())
                                .precioVuelo(vuelo.getPrecioVuelo())
                                .tarifaTemporada(vuelo.getTarifaTemporada())
                                .descuento(vuelo.getDescuento())
                                .distanciaRecorrida(vuelo.getDistanciaRecorrida())
                                .fechayhoradesalida(vuelo.getFechayhoradesalida())
                                .fechayhorallegada(vuelo.getFechayhorallegada())
                                .cupo(vuelo.getCupo())
                                .aeropuertoDespegue(vuelo.getAeropuertoDespegue())
                                .aeropuertoDestino(vuelo.getAeropuertoDestino())
                                .estado(vuelo.getEstado())
                                .avion(vuelo.getAvion())
                                .fechaRegistro(vuelo.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("vuelos")
    public ResponseEntity<?> create(@RequestBody @Valid VueloDto vueloDto) {
        Vuelo vueloSave = null;
        try {
            vueloSave = vueloService.save(vueloDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .sucess(true)
                    .response(VueloDto.builder()
                            .idVuelo(vueloSave.getIdVuelo())
                            .precioVuelo(vueloSave.getPrecioVuelo())
                            .tarifaTemporada(vueloSave.getTarifaTemporada())
                            .descuento(vueloSave.getDescuento())
                            .distanciaRecorrida(vueloSave.getDistanciaRecorrida())
                            .fechayhoradesalida(vueloSave.getFechayhoradesalida())
                            .fechayhorallegada(vueloSave.getFechayhorallegada())
                            .cupo(vueloSave.getCupo())
                            .aeropuertoDespegue(vueloSave.getAeropuertoDespegue())
                            .aeropuertoDestino(vueloSave.getAeropuertoDestino())
                            .estado(vueloSave.getEstado())
                            .avion(vueloSave.getAvion())
                            .fechaRegistro(vueloSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("vuelos/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid VueloDto vueloDto, @PathVariable Integer id) {
        Vuelo vueloUpdate = null;
        try {
            if (vueloService.existsById(id)) {
                vueloDto.setIdVuelo(id);
                vueloUpdate = vueloService.update(vueloDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(VueloDto.builder()
                                .idVuelo(vueloUpdate.getIdVuelo())
                                .precioVuelo(vueloUpdate.getPrecioVuelo())
                                .tarifaTemporada(vueloUpdate.getTarifaTemporada())
                                .descuento(vueloUpdate.getDescuento())
                                .distanciaRecorrida(vueloUpdate.getDistanciaRecorrida())
                                .fechayhoradesalida(vueloUpdate.getFechayhoradesalida())
                                .fechayhorallegada(vueloUpdate.getFechayhorallegada())
                                .cupo(vueloUpdate.getCupo())
                                .aeropuertoDespegue(vueloUpdate.getAeropuertoDespegue())
                                .aeropuertoDestino(vueloUpdate.getAeropuertoDestino())
                                .estado(vueloUpdate.getEstado())
                                .avion(vueloUpdate.getAvion())
                                .fechaRegistro(vueloUpdate.getFechaRegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("vuelo", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("vuelos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Vuelo vueloDelete = vueloService.findById(id);
            if (vueloDelete != null) {
                vueloService.delete(vueloDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("vuelo", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }


}
