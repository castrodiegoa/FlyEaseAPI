package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AsientoDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Asiento;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAsientoService;
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
public class AsientoController {

    @Autowired
    private IAsientoService asientoService;

    @GetMapping("asientos")
    public ResponseEntity<?> showAll() {
        List<Asiento> getList = asientoService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("asientos");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("asientos/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Asiento asiento = asientoService.findById(id);

        if (asiento == null) {
            throw new ResourceNotFoundException("asiento", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(AsientoDto.builder()
                                .idasiento(asiento.getIdasiento())
                                .posicion(asiento.getPosicion())
                                .disponibilidad(asiento.getDisponibilidad())
                                .fecharegistro(asiento.getFecharegistro())
                                .avion(asiento.getAvion())
                                .categoria(asiento.getCategoria())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("asientos")
    public ResponseEntity<?> create(@RequestBody @Valid AsientoDto asientoDto) {
        Asiento asientoSave = null;
        try {
            asientoSave = asientoService.save(asientoDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .success(true)
                    .response(AsientoDto.builder()
                            .idasiento(asientoSave.getIdasiento())
                            .posicion(asientoSave.getPosicion())
                            .disponibilidad(asientoSave.getDisponibilidad())
                            .fecharegistro(asientoSave.getFecharegistro())
                            .avion(asientoSave.getAvion())
                            .categoria(asientoSave.getCategoria())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("asientos/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid AsientoDto asientoDto, @PathVariable Integer id) {
        Asiento asientoUpdate = null;
        try {
            if (asientoService.existsById(id)) {
                asientoDto.setIdasiento(id);
                asientoUpdate = asientoService.update(asientoDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(AsientoDto.builder()
                                .idasiento(asientoUpdate.getIdasiento())
                                .posicion(asientoUpdate.getPosicion())
                                .disponibilidad(asientoUpdate.getDisponibilidad())
                                .fecharegistro(asientoUpdate.getFecharegistro())
                                .avion(asientoUpdate.getAvion())
                                .categoria(asientoUpdate.getCategoria())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("asiento", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("asientos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Asiento asientoDelete = asientoService.findById(id);
            if (asientoDelete != null) {
                asientoService.delete(asientoDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .success(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("asiento", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
