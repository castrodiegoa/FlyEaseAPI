package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.CiudadDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.entity.Region;

import java.util.List;

public interface ICiudadService {
    List<Ciudad> listAlll();

    Ciudad save(CiudadDto ciudadDto);

    Ciudad update(CiudadDto ciudadDto);

    Ciudad findById(Integer id);

    void delete(Ciudad Ciudad);

    boolean existsById(Integer id);
}
