package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.AvionDto;
import com.flyease.flyeaseapirest.model.entity.Avion;

import java.util.List;

public interface IAvionService {

    List<Avion> listAll();

    Avion save(AvionDto avionDto);

    Avion update(AvionDto avionDto, String IdAvionAntiguo);

    Avion findById(String id);

    void delete(Avion avion);

    boolean existsById(String id);

    // Falta retornar los asientos del avión

}
