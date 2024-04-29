package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.AsientoDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Asiento;
import com.flyease.flyeaseapirest.model.entity.Region;

import java.util.List;

public interface IAsientoService {

    List<Asiento> listAlll();

    Asiento save(AsientoDto asientoDto);

    Asiento update(AsientoDto asientoDto);

    Asiento findById(Integer id);

    void delete(Asiento asiento);

    boolean existsById(Integer id);

}
