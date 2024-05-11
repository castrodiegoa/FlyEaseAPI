package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.BoletoDto;
import com.flyease.flyeaseapirest.model.entity.Boleto;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import com.flyease.flyeaseapirest.model.payload.InformeResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.ICrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
@Tag(name = "Boletos", description = "Métodos Crud para Boletos.")
public class BoletoController {

    @Autowired
    private ICrudService<Boleto, BoletoDto, Integer> boletoService;

    @Operation(summary = "Obtener todos los boletos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boleto.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @GetMapping("boletos")
    public ResponseEntity<?> showAll() {
        List<Boleto> getList = boletoService.listAll();
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

    @Operation(summary = "Obtener un boleto por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boleto.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
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

    @Operation(summary = "Registrar un nuevo boleto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boleto.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
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

    @Operation(summary = "Actualizar los datos de un boleto por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boleto.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
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

    @Operation(summary = "Eliminar un boleto por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @DeleteMapping("boletos/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Boleto boletoDelete = boletoService.findById(id);
            if (boletoDelete != null) {
                boletoService.delete(boletoDelete);
                return new ResponseEntity<>(InformeResponse.builder()
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
