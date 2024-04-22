package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AerolineaDto;
import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAerolineaService;
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
public class AerolineaController {

    @Autowired
    private IAerolineaService aerolineaService;

    @GetMapping("aerolineas")
    public ResponseEntity<?> showAll() {
        List<Aerolinea> getList = aerolineaService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("aerolíneas");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("aerolineas/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Aerolinea aerolinea = aerolineaService.findById(id);

        if (aerolinea == null) {
            throw new ResourceNotFoundException("aerolínea", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(AerolineaDto.builder()
                                .idAerolinea(aerolinea.getIdAerolinea())
                                .nombre(aerolinea.getNombre())
                                .codigoIATA(aerolinea.getCodigoIATA())
                                .codigoICAO(aerolinea.getCodigoICAO())
                                .fechaRegistro(aerolinea.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("aerolineas")
    public ResponseEntity<?> create(@RequestBody @Valid AerolineaDto aerolineaDto) {
        Aerolinea aerolineaSave = null;
        try {
            aerolineaSave = aerolineaService.save(aerolineaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .sucess(true)
                    .response(AerolineaDto.builder()
                            .idAerolinea(aerolineaSave.getIdAerolinea())
                            .nombre(aerolineaSave.getNombre())
                            .codigoIATA(aerolineaSave.getCodigoIATA())
                            .codigoICAO(aerolineaSave.getCodigoICAO())
                            .fechaRegistro(aerolineaSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("aerolineas/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid AerolineaDto aerolineaDto, @PathVariable Integer id) {
        Aerolinea aerolineaUpdate = null;
        try {
            if (aerolineaService.existsById(id)) {
                aerolineaDto.setIdAerolinea(id);
                aerolineaUpdate = aerolineaService.update(aerolineaDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(AerolineaDto.builder()
                                .idAerolinea(aerolineaUpdate.getIdAerolinea())
                                .nombre(aerolineaUpdate.getNombre())
                                .codigoIATA(aerolineaUpdate.getCodigoIATA())
                                .codigoICAO(aerolineaUpdate.getCodigoICAO())
                                .fechaRegistro(aerolineaUpdate.getFechaRegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("aerolínea", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("aerolineas/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Aerolinea aerolineaDelete = aerolineaService.findById(id);
            if (aerolineaDelete != null) {
                aerolineaService.delete(aerolineaDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("aerolínea", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
