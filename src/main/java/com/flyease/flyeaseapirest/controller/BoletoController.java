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
                        .sucess(true)
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
                        .sucess(true)
                        .response(BoletoDto.builder()
                                .idBoleto(boleto.getIdBoleto())
                                .precio(boleto.getPrecio())
                                .descuento(boleto.getDescuento())
                                .precioTotal(boleto.getPrecioTotal())
                                .cliente(boleto.getCliente())
                                .asiento(boleto.getAsiento())
                                .vuelo(boleto.getVuelo())
                                .fechaRegistro(boleto.getFechaRegistro())
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
                    .sucess(true)
                    .response(BoletoDto.builder()
                            .idBoleto(boletoSave.getIdBoleto())
                            .precio(boletoSave.getPrecio())
                            .descuento(boletoSave.getDescuento())
                            .precioTotal(boletoSave.getPrecioTotal())
                            .cliente(boletoSave.getCliente())
                            .asiento(boletoSave.getAsiento())
                            .vuelo(boletoSave.getVuelo())
                            .fechaRegistro(boletoSave.getFechaRegistro())
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
                boletoDto.setIdBoleto(id);
                boletoUpdate = boletoService.update(boletoDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(BoletoDto.builder()
                                .idBoleto(boletoUpdate.getIdBoleto())
                                .precio(boletoUpdate.getPrecio())
                                .descuento(boletoUpdate.getDescuento())
                                .precioTotal(boletoUpdate.getPrecioTotal())
                                .cliente(boletoUpdate.getCliente())
                                .asiento(boletoUpdate.getAsiento())
                                .vuelo(boletoUpdate.getVuelo())
                                .fechaRegistro(boletoUpdate.getFechaRegistro())
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
                        .sucess(true)
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
