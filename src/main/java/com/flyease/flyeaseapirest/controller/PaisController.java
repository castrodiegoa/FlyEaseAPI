package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.model.payload.InformeResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.ICrudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class PaisController {

    @Autowired
    private ICrudService<Pais, PaisDto, Integer> paisService;

    @GetMapping("paises")
    public ResponseEntity<?> showAll() {
        List<Pais> getList = paisService.listAll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("paises");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("paises/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Pais pais = paisService.findById(id);

        if (pais == null) {
            throw new ResourceNotFoundException("pais", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(PaisDto.builder()
                                .idpais(pais.getIdpais())
                                .nombre(pais.getNombre())
                                .fecharegistro(pais.getFecharegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("paises")
    public ResponseEntity<?> create(@RequestBody @Valid PaisDto paisDto) {
        Pais paisSave = null;
        try {
            paisSave = paisService.save(paisDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .success(true)
                    .response(PaisDto.builder()
                            .idpais(paisSave.getIdpais())
                            .nombre(paisSave.getNombre() )
                            .fecharegistro(paisSave.getFecharegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("paises/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid PaisDto paisDto, @PathVariable Integer id) {
        Pais paisUpdate = null;
        try {
            if (paisService.existsById(id)) {
                paisDto.setIdpais(id);
                paisUpdate = paisService.update(paisDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(PaisDto.builder()
                                .idpais(paisUpdate.getIdpais())
                                .nombre(paisUpdate.getNombre())
                                .fecharegistro(paisUpdate.getFecharegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("pais", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("paises/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Pais paisDelete = paisService.findById(id);
            if (paisDelete != null) {
                paisService.delete(paisDelete);
                return new ResponseEntity<>(InformeResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .success(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("pais", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
