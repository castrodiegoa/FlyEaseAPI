package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.entity.Administrador;

import java.util.List;

public interface IAdministradorService {

    List<Administrador> listAlll();

    Administrador findById(Integer id);

    boolean existsById(Integer id);

}
