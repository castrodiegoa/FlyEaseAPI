package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.CiudadDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.ICiudadService;
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
public class CiudadController {

    @Autowired
    private ICiudadService ciudadService;

    @GetMapping("ciudades")
    public ResponseEntity<?> showAll() {
        List<Ciudad> getList = ciudadService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("ciudades");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("ciudades/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Ciudad ciudad = ciudadService.findById(id);

        if (ciudad == null) {
            throw new ResourceNotFoundException("ciudad", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(CiudadDto.builder()
                                .idciudad(ciudad.getIdciudad())
                                .nombre(ciudad.getNombre())
                                .fecharegistro(ciudad.getFecharegistro())
                                .imagen(ciudad.getImagen())
                                .region(ciudad.getRegion())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("ciudades")
    public ResponseEntity<?> create(@RequestBody @Valid CiudadDto ciudadDto) {
        Ciudad ciudadSave = null;
        try {
            ciudadSave = ciudadService.save(ciudadDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .success(true)
                    .response(CiudadDto.builder()
                            .idciudad(ciudadSave.getIdciudad())
                            .nombre(ciudadSave.getNombre())
                            .fecharegistro(ciudadSave.getFecharegistro())
                            .imagen(ciudadSave.getImagen())
                            .region(ciudadSave.getRegion())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("ciudades/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CiudadDto ciudadDto, @PathVariable Integer id) {
        Ciudad ciudadUpdate = null;
        try {
            if (ciudadService.existsById(id)) {
                ciudadDto.setIdciudad(id);
                ciudadUpdate = ciudadService.update(ciudadDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(CiudadDto.builder()
                                .idciudad(ciudadUpdate.getIdciudad())
                                .nombre(ciudadUpdate.getNombre())
                                .fecharegistro(ciudadUpdate.getFecharegistro())
                                .imagen(ciudadUpdate.getImagen())
                                .region(ciudadUpdate.getRegion())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("ciudad", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("ciudades/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Ciudad ciudadDelete = ciudadService.findById(id);
            if (ciudadDelete != null) {
                ciudadService.delete(ciudadDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .success(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("ciudad", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}