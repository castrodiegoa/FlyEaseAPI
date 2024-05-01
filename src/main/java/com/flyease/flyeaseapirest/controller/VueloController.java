package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.VueloDto;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
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
                        .success(true)
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
                        .success(true)
                        .response(VueloDto.builder()
                                .idvuelo(vuelo.getIdvuelo())
                                .preciovuelo(vuelo.getPreciovuelo())
                                .tarifatemporada(vuelo.getTarifatemporada())
                                .descuento(vuelo.getDescuento())
                                .distanciarecorrida(vuelo.getDistanciarecorrida())
                                .fechayhoradesalida(vuelo.getFechayhoradesalida())
                                .fechayhorallegada(vuelo.getFechayhorallegada())
                                .cupo(vuelo.getCupo())
                                .aeropuerto_Despegue(vuelo.getAeropuerto_Despegue())
                                .aeropuerto_Destino(vuelo.getAeropuerto_Destino())
                                .estado(vuelo.getEstado())
                                .avion(vuelo.getAvion())
                                .fecharegistro(vuelo.getFecharegistro())
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
                    .success(true)
                    .response(VueloDto.builder()
                            .idvuelo(vueloSave.getIdvuelo())
                            .preciovuelo(vueloSave.getPreciovuelo())
                            .tarifatemporada(vueloSave.getTarifatemporada())
                            .descuento(vueloSave.getDescuento())
                            .distanciarecorrida(vueloSave.getDistanciarecorrida())
                            .fechayhoradesalida(vueloSave.getFechayhoradesalida())
                            .fechayhorallegada(vueloSave.getFechayhorallegada())
                            .cupo(vueloSave.getCupo())
                            .aeropuerto_Despegue(vueloSave.getAeropuerto_Despegue())
                            .aeropuerto_Destino(vueloSave.getAeropuerto_Destino())
                            .estado(vueloSave.getEstado())
                            .avion(vueloSave.getAvion())
                            .fecharegistro(vueloSave.getFecharegistro())
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
                vueloDto.setIdvuelo(id);
                vueloUpdate = vueloService.update(vueloDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(VueloDto.builder()
                                .idvuelo(vueloUpdate.getIdvuelo())
                                .preciovuelo(vueloUpdate.getPreciovuelo())
                                .tarifatemporada(vueloUpdate.getTarifatemporada())
                                .descuento(vueloUpdate.getDescuento())
                                .distanciarecorrida(vueloUpdate.getDistanciarecorrida())
                                .fechayhoradesalida(vueloUpdate.getFechayhoradesalida())
                                .fechayhorallegada(vueloUpdate.getFechayhorallegada())
                                .cupo(vueloUpdate.getCupo())
                                .aeropuerto_Despegue(vueloUpdate.getAeropuerto_Despegue())
                                .aeropuerto_Destino(vueloUpdate.getAeropuerto_Destino())
                                .estado(vueloUpdate.getEstado())
                                .avion(vueloUpdate.getAvion())
                                .fecharegistro(vueloUpdate.getFecharegistro())
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
                        .success(true)
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
