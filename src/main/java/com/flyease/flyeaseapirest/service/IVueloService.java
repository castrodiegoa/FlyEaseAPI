package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.CiudadDto;
import com.flyease.flyeaseapirest.model.dto.VueloDto;
import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.entity.Vuelo;

import java.util.List;

public interface IVueloService {

    List<Vuelo> listAlll();

    Vuelo save(VueloDto vueloDto);

    Vuelo update(VueloDto vueloDto);

    Vuelo findById(Integer id);

    void delete(Vuelo vuelo);

    boolean existsById(Integer id);

    // falta retornar vuelos disponibles

    // falta retornar asientos disponibles de un vuelo

}
