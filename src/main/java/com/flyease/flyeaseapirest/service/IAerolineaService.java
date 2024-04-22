package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.AerolineaDto;
import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;

import java.util.List;

public interface IAerolineaService {

    List<Aerolinea> listAlll();

    Aerolinea save(AerolineaDto aerolineaDto);

    Aerolinea update(AerolineaDto aerolineaDto);

    Aerolinea findById(Integer id);

    void delete(Aerolinea aerolinea);

    boolean existsById(Integer id);

}
