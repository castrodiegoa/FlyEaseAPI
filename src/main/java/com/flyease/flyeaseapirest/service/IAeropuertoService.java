package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.dto.CiudadDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Ciudad;

import java.util.List;

public interface IAeropuertoService {

    List<Aeropuerto> listAlll();

    Aeropuerto save(AeropuertoDto aeropuertoDto);

    Aeropuerto update(AeropuertoDto aeropuertoDto);

    Aeropuerto findById(Integer id);

    void delete(Aeropuerto aeropuerto);

    boolean existsById(Integer id);

}
