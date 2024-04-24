package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.dto.ClienteDto;
import com.flyease.flyeaseapirest.model.entity.Categoria;
import com.flyease.flyeaseapirest.model.entity.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> listAlll();

    Cliente save(ClienteDto clienteDto);

    Cliente update(ClienteDto clienteDto, String numeroDocumentoAntiguo);

    Cliente findById(String id);

    void delete(Cliente cliente);

    boolean existsById(String id);

    // Falta retornar los boletos del cliente

}
