package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.BoletoDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Boleto;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IBoletoService;
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
public class BoletoController {

    @Autowired
    private IBoletoService boletoService;

    @GetMapping("boletos")
    public ResponseEntity<?> showAll() {
        List<Boleto> getList = boletoService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("boletos");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("boletos/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Boleto boleto = boletoService.findById(id);

        if (boleto == null) {
            throw new ResourceNotFoundException("boleto", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(BoletoDto.builder()
                                .idboleto(boleto.getIdboleto())
                                .precio(boleto.getPrecio())
                                .descuento(boleto.getDescuento())
                                .preciototal(boleto.getPreciototal())
                                .cliente(boleto.getCliente())
                                .asiento(boleto.getAsiento())
                                .vuelo(boleto.getVuelo())
                                .fecharegistro(boleto.getFecharegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("boletos")
    public ResponseEntity<?> create(@RequestBody @Valid BoletoDto boletoDto) {
        Boleto boletoSave = null;
        try {
            boletoSave = boletoService.save(boletoDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .success(true)
                    .response(BoletoDto.builder()
                            .idboleto(boletoSave.getIdboleto())
                            .precio(boletoSave.getPrecio())
                            .descuento(boletoSave.getDescuento())
                            .preciototal(boletoSave.getPreciototal())
                            .cliente(boletoSave.getCliente())
                            .asiento(boletoSave.getAsiento())
                            .vuelo(boletoSave.getVuelo())
                            .fecharegistro(boletoSave.getFecharegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("boletos/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid BoletoDto boletoDto, @PathVariable Integer id) {
        Boleto boletoUpdate = null;
        try {
            if (boletoService.existsById(id)) {
                boletoDto.setIdboleto(id);
                boletoUpdate = boletoService.update(boletoDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(BoletoDto.builder()
                                .idboleto(boletoUpdate.getIdboleto())
                                .precio(boletoUpdate.getPrecio())
                                .descuento(boletoUpdate.getDescuento())
                                .preciototal(boletoUpdate.getPreciototal())
                                .cliente(boletoUpdate.getCliente())
                                .asiento(boletoUpdate.getAsiento())
                                .vuelo(boletoUpdate.getVuelo())
                                .fecharegistro(boletoUpdate.getFecharegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("boleto", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("boletos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Boleto boletoDelete = boletoService.findById(id);
            if (boletoDelete != null) {
                boletoService.delete(boletoDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .success(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("boleto", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
