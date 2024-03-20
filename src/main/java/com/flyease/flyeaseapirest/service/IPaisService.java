package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Pais;

import java.util.List;

public interface IPaisService {
    List<Pais> listAlll();

    Pais save(PaisDto pais);

    Pais update(PaisDto paisDto);

    Pais findById(Integer id);

    void delete(Pais pais);

    boolean existsById(Integer id);
}
