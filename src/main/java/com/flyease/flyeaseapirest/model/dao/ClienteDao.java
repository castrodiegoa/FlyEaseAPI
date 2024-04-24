package com.flyease.flyeaseapirest.model.dao;

import com.flyease.flyeaseapirest.model.entity.Categoria;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, String> {

}
