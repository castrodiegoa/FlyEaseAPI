package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.ClienteDto;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import com.flyease.flyeaseapirest.model.entity.Estado;

import java.util.List;

public interface IEstadoService {

    List<Estado> listAlll();

    Estado findById(Integer id);

    boolean existsById(Integer id);

}
