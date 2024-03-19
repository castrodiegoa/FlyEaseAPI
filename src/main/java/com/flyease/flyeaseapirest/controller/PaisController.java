package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class PaisController {

    @Autowired
    private IPaisService paisService;

    @GetMapping("paises")
    public ResponseEntity<?> showAll() {
        List<Pais> getList = paisService.listAlll();
        if (getList == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getList)
                        .build()
                , HttpStatus.OK);
    }


    @PostMapping("pais")
    public ResponseEntity<?> create(@RequestBody PaisDto paisDto) {
        Pais paisSave = null;
        try {
            paisSave = paisService.save(paisDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(PaisDto.builder()
                            .idPais(paisSave.getIdPais())
                            .nombre(paisSave.getNombre() )
                            .fechaRegistro(paisSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("pais/{id}")
    public ResponseEntity<?> update(@RequestBody PaisDto paisDto, @PathVariable Integer id) {
        Pais paisUpdate = null;
        try {
            if (paisService.existsById(id)) {
                paisDto.setIdPais(id);
                paisUpdate = paisService.save(paisDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Guardado correctamente")
                        .object(PaisDto.builder()
                                .idPais(paisUpdate.getIdPais())
                                .nombre(paisUpdate.getNombre())
                                .fechaRegistro(paisUpdate.getFechaRegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("pais/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Pais paisDelete = paisService.findById(id);
            paisService.delete(paisDelete);
            return new ResponseEntity<>(paisDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("pais/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Pais pais = paisService.findById(id);

        if (pais == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar, no existe!!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(PaisDto.builder()
                                .idPais(pais.getIdPais())
                                .nombre(pais.getNombre())
                                .fechaRegistro(pais.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

}
