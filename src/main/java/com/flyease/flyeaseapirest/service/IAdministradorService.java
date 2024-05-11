package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.AvionDto;
import com.flyease.flyeaseapirest.model.entity.Administrador;
import com.flyease.flyeaseapirest.model.entity.Avion;

import java.util.List;

public interface IAdministradorService {

    List<Administrador> listAll();

    Administrador findById(Integer id);

    boolean existsById(Integer id);

    boolean existsByUsernameAndPassword(String username, String password);

}
