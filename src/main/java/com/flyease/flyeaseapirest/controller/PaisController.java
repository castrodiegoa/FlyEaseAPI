package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
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
public class PaisController {

    @Autowired
    private IPaisService paisService;

    @GetMapping("paises")
    public ResponseEntity<?> showAll() {
        List<Pais> getList = paisService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("paises");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
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
                        .sucess(true)
                        .response(PaisDto.builder()
                                .idPais(pais.getIdPais())
                                .nombre(pais.getNombre())
                                .fechaRegistro(pais.getFechaRegistro())
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
                    .sucess(true)
                    .response(PaisDto.builder()
                            .idPais(paisSave.getIdPais())
                            .nombre(paisSave.getNombre() )
                            .fechaRegistro(paisSave.getFechaRegistro())
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
                paisDto.setIdPais(id);
                paisUpdate = paisService.update(paisDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(PaisDto.builder()
                                .idPais(paisUpdate.getIdPais())
                                .nombre(paisUpdate.getNombre())
                                .fechaRegistro(paisUpdate.getFechaRegistro())
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
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
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
