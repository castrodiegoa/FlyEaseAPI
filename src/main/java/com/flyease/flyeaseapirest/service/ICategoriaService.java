package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Categoria;

import java.util.List;

public interface ICategoriaService {

    List<Categoria> listAlll();

    Categoria save(CategoriaDto categoriaDto);

    Categoria update(CategoriaDto categoriaDto);

    Categoria findById(Integer id);

    void delete(Categoria categoria);

    boolean existsById(Integer id);

}
