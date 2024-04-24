package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Categoria;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.IAeropuertoService;
import com.flyease.flyeaseapirest.service.ICategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("categorias")
    public ResponseEntity<?> showAll() {
        List<Categoria> getList = categoriaService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("categorías");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("categorias/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);

        if (categoria == null) {
            throw new ResourceNotFoundException("categoría", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(CategoriaDto.builder()
                                .idCategoria(categoria.getIdCategoria())
                                .nombre(categoria.getNombre())
                                .descripcion(categoria.getDescripcion())
                                .estadoCategoria(categoria.getEstadoCategoria())
                                .tarifa(categoria.getTarifa())
                                .comercial(categoria.getComercial())
                                .fechaRegistro(categoria.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("categorias")
    public ResponseEntity<?> create(@RequestBody @Valid CategoriaDto categoriaDto) {
        Categoria categoriaSave = null;
        try {
            categoriaSave = categoriaService.save(categoriaDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .sucess(true)
                    .response(CategoriaDto.builder()
                            .idCategoria(categoriaSave.getIdCategoria())
                            .nombre(categoriaSave.getNombre())
                            .descripcion(categoriaSave.getDescripcion())
                            .estadoCategoria(categoriaSave.getEstadoCategoria())
                            .tarifa(categoriaSave.getTarifa())
                            .comercial(categoriaSave.getComercial())
                            .fechaRegistro(categoriaSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("categorias/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid CategoriaDto categoriaDto, @PathVariable Integer id) {
        Categoria categoriaUpdate = null;
        try {
            if (categoriaService.existsById(id)) {
                categoriaDto.setIdCategoria(id);
                categoriaUpdate = categoriaService.update(categoriaDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(CategoriaDto.builder()
                                .idCategoria(categoriaUpdate.getIdCategoria())
                                .nombre(categoriaUpdate.getNombre())
                                .descripcion(categoriaUpdate.getDescripcion())
                                .estadoCategoria(categoriaUpdate.getEstadoCategoria())
                                .tarifa(categoriaUpdate.getTarifa())
                                .comercial(categoriaUpdate.getComercial())
                                .fechaRegistro(categoriaUpdate.getFechaRegistro())
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

    @DeleteMapping("categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Categoria categoriaDelete = categoriaService.findById(id);
            if (categoriaDelete != null) {
                categoriaService.delete(categoriaDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
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
