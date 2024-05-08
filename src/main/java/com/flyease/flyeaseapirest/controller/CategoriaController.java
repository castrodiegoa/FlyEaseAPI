package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.entity.Categoria;
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
public class CategoriaController {

    @Autowired
    private ICrudService<Categoria, CategoriaDto, Integer> categoriaService;

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
