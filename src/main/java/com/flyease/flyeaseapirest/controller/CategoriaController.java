package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.model.entity.Categoria;
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
@Tag(name = "Categorías", description = "Métodos Crud para Categorías.")
public class CategoriaController {

    @Autowired
    private ICrudService<Categoria, CategoriaDto, Integer> categoriaService;

    @Operation(summary = "Obtener todas las categorías registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @GetMapping("categorias")
    public ResponseEntity<?> showAll() {
        List<Categoria> getList = categoriaService.listAll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("categorías");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @Operation(summary = "Obtener una categoría por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @GetMapping("categorias/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);

        if (categoria == null) {
            throw new ResourceNotFoundException("categoría", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .success(true)
                        .response(CategoriaDto.builder()
                                .idcategoria(categoria.getIdcategoria())
                                .nombre(categoria.getNombre())
                                .descripcion(categoria.getDescripcion())
                                .estadocategoria(categoria.getEstadocategoria())
                                .tarifa(categoria.getTarifa())
                                .comercial(categoria.getComercial())
                                .fecharegistro(categoria.getFecharegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @Operation(summary = "Registrar una nueva categoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @PostMapping("categorias")
    public ResponseEntity<?> create(@RequestBody @Valid CategoriaDto categoriaDto) {
        Categoria categoriaSave = null;
        try {
            categoriaSave = categoriaService.save(categoriaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .success(true)
                    .response(CategoriaDto.builder()
                            .idcategoria(categoriaSave.getIdcategoria())
                            .nombre(categoriaSave.getNombre())
                            .descripcion(categoriaSave.getDescripcion())
                            .estadocategoria(categoriaSave.getEstadocategoria())
                            .tarifa(categoriaSave.getTarifa())
                            .comercial(categoriaSave.getComercial())
                            .fecharegistro(categoriaSave.getFecharegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @Operation(summary = "Actualizar los datos de una categoría por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. Operación realizada con éxito.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request. La solicitud no pudo ser procesada debido a un error en los datos enviados o en el formato de la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not Found. El recurso solicitado no se ha encontrado en el servidor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error. Ha ocurrido un error interno en el servidor que ha impedido procesar la solicitud.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InformeResponse.class))})
    })
    @PutMapping("categorias/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable Integer id) {
        Categoria categoriaUpdate = null;
        try {
            if (categoriaService.existsById(id)) {
                categoriaDto.setIdcategoria(id);
                categoriaUpdate = categoriaService.update(categoriaDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .success(true)
                        .response(CategoriaDto.builder()
                                .idcategoria(categoriaUpdate.getIdcategoria())
                                .nombre(categoriaUpdate.getNombre())
                                .descripcion(categoriaUpdate.getDescripcion())
                                .estadocategoria(categoriaUpdate.getEstadocategoria())
                                .tarifa(categoriaUpdate.getTarifa())
                                .comercial(categoriaUpdate.getComercial())
                                .fecharegistro(categoriaUpdate.getFecharegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("categoría", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @Operation(summary = "Eliminar una categoría por su ID.")
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
    @DeleteMapping("categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Categoria categoriaDelete = categoriaService.findById(id);
            if (categoriaDelete != null) {
                categoriaService.delete(categoriaDelete);
                return new ResponseEntity<>(InformeResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .success(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("categoría", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
