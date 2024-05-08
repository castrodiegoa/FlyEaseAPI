package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Region;
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
public class RegionController {

    @Autowired
    private ICrudService<Region, RegionDto, Integer> regionService;

    @GetMapping("regiones")
    public ResponseEntity<?> showAll() {
        List<Region> getList = regionService.listAll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("regiones");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("regiones/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Region region = regionService.findById(id);

        if (region == null) {
            throw new ResourceNotFoundException("region", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(RegionDto.builder()
                                .idregion(region.getIdregion())
                                .nombre(region.getNombre())
                                .fecharegistro(region.getFecharegistro())
                                .pais(region.getPais())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("regiones")
    public ResponseEntity<?> create(@RequestBody @Valid RegionDto regionDto) {
        Region regionSave = null;
        try {
            regionSave = regionService.save(regionDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .success(true)
                    .response(RegionDto.builder()
                            .idregion(regionSave.getIdregion())
                            .nombre(regionSave.getNombre())
                            .fecharegistro(regionSave.getFecharegistro())
                            .pais(regionSave.getPais())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("regiones/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid RegionDto regionDto, @PathVariable Integer id) {
        Region regionUpdate = null;
        try {
            if (regionService.existsById(id)) {
                regionDto.setIdregion(id);
                regionUpdate = regionService.update(regionDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(RegionDto.builder()
                                .idregion(regionUpdate.getIdregion())
                                .nombre(regionUpdate.getNombre())
                                .fecharegistro(regionUpdate.getFecharegistro())
                                .pais(regionUpdate.getPais())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("region", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("regiones/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Region regionDelete = regionService.findById(id);
            if (regionDelete != null) {
                regionService.delete(regionDelete);
                return new ResponseEntity<>(InformeResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .success(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("region", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
